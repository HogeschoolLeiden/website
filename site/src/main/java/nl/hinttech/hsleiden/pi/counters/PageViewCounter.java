package nl.hinttech.hsleiden.pi.counters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jcr.Credentials;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import nl.hinttech.hsleiden.pi.beans.TextDocument;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.content.beans.manager.ObjectBeanManager;
import org.hippoecm.hst.content.beans.standard.HippoDocumentBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Singleton which records and tabulates the most read articles in the application Reads and writes its result in the
 * writable hippo repository as a serialized map.
 * 
 * @author pieter
 */
public class PageViewCounter extends AbstractCounter {

    private static final Logger log = LoggerFactory.getLogger(PageViewCounter.class);

    static final String SYSTEM_PROPERTY_PAGEVIEW_COUNTER_FILE = "pageview.counter.file";

    static final int SAVE_INTERVAL_MINUTES = 90;
    static final int SIZE = 1000;
    private static final int COUNTS_PER_SESSION = 20;
    static final String PAGE_VIEW_COUNTER_NODE = "content/documents/page_view_counter";
    static final String PAGE_VIEW_COUNTER_PROPERTY = "counts";
    static final String PAGE_VIEW_SESSION_VISITED = "page_view_session_visited";

    private String persistentFile = null;
    private static PageViewCounter instance = null;

    /**
     * Execute an action within a JCR Session, retrieved using the HST request.
     * 
     * @author pieter
     * 
     */
    private static abstract class WithSession {

        void execute(final HstRequest request) {
            Session session = null;
            try {
                session = getPersistableSession(request);
                action(session);
            } catch (RepositoryException e) {
                log.error("Can't get persistable session ", e.getMessage());
            } finally {
                if (session != null) {
                    session.logout();
                }
            }
        }

        abstract void action(final Session session);
    }

    Date lastSave = new Date();

    private PageViewCounter() {

        // check system property if we need to persist the counters to file or repository
        // If no system property is set, we persist to repository
        String propertyValue = System.getProperty(SYSTEM_PROPERTY_PAGEVIEW_COUNTER_FILE);
        if (StringUtils.isNotEmpty(propertyValue)) {
            persistentFile = propertyValue;
        }
    }

    public boolean persistToFile() {
        return (persistentFile != null);
    }

    public static boolean hasInstance() {
        return (instance != null);
    }

    /**
     * Answer the current instance of the counter.
     * 
     * @param session
     *            writable jcr repository session, use getPersistableSession() in the component.
     * @return
     */
    public static synchronized PageViewCounter getInstance(final HstRequest request) {
        if (!hasInstance()) {
            instance = new PageViewCounter();
            if (instance.persistToFile()) {
                instance.load();
            } else {
                new WithSession() {
                    @Override
                    public void action(final Session session) {
                        instance.load(session);
                    }
                }.execute(request);
            }
        }
        return instance;
    }

    public static PageViewCounter getInstance() {
        if (hasInstance()) {
            return instance;
        }
        return null;
    }

    /* Break out the bean retrieving logic for unit testing */

    /**
     * 
     * @author pieter
     * 
     */
    public interface BeanRetriever {
        HippoDocumentBean retrieve(final String uuid, final ObjectBeanManager objectBeanManager);
    }

    public static final BeanRetriever retriever = new BeanRetriever() {

        public HippoDocumentBean retrieve(final String uuid, final ObjectBeanManager objectBeanManager) {
            try {
                HippoDocumentBean bean = (HippoDocumentBean) objectBeanManager.getObjectByUuid(uuid);
                return bean;
            } catch (ObjectBeanManagerException e) {
                log.error("Can't retrieve node: {} : {}", uuid, e.getMessage());
            }
            return null;
        }
    };

    /**
     * Increment the pageview count for the given bean.
     * 
     * @param document
     *            the bean to register as visited
     * @param httpSession
     *            the HttpSession to prevent double hits during a user session
     * @param request
     *            the request, used to retrieve the persistable session (only when really saving the counts)
     */
    public void increment(final TextDocument document, final javax.servlet.http.HttpSession httpSession,
            final HstRequest request) {
        updateCounts(document, httpSession);
        save(request, SAVE_INTERVAL_MINUTES);
    }

    private void updateCounts(final TextDocument document, final javax.servlet.http.HttpSession httpSession) {

        // Use the handle, so counting different versions of an article works.
        String uuid = document.getCanonicalHandleUUID();

        // User could have visited this bean in the current session, don't count
        // it.
        @SuppressWarnings("unchecked")
        Set<String> visited = (Set<String>) httpSession.getAttribute(PAGE_VIEW_SESSION_VISITED);
        if (visited == null) {
            visited = new HashSet<String>(COUNTS_PER_SESSION);
            httpSession.setAttribute(PAGE_VIEW_SESSION_VISITED, visited);
        }

        if (!visited.contains(uuid)) {
            visited.add(uuid);

            Count count = counts.get(uuid);
            if (count == null) {
                count = new Count(uuid, document.isForStudent(), document.isForEmployee(), document.isForService());
            }
            count.increase();
            counts.put(uuid, count);
        }

    }

    /**
     * Save yourself every n minutes to the repository.
     */
    public synchronized void save(final Session session, final int saveInterval) {
        if (isTimeToSave(saveInterval)) {
            boolean isSaved = false;
            if (persistToFile()) {
                isSaved = save();
            } else {
                isSaved = save(session);
            }
            if (isSaved) {
                lastSave = new Date();
            }
        }
    }

    /**
     * Save yourself every n minutes to the repository.
     */
    public void save(final HstRequest request, final int saveInterval) {
        if (isTimeToSave(saveInterval)) {
            if (persistToFile()) {
                if (save()) {
                    lastSave = new Date();
                }
            } else {
                new WithSession() {
                    @Override
                    void action(final Session session) {
                        Date prevSave = lastSave;
                        lastSave = new Date(); // Keep other threads from saving without blocking them
                        if (!save(session)) {
                            lastSave = prevSave;
                        }
                    }
                }.execute(request);
            }
        }
    }

    private boolean isTimeToSave(final int saveInterval) {
        Calendar last = Calendar.getInstance();
        last.add(Calendar.MINUTE, -1 * SAVE_INTERVAL_MINUTES);
        if (last.getTimeInMillis() > lastSave.getTime()) {
            return true;
        }
        return false;
    }

    public void save(final int saveInterval) {
        if (isTimeToSave(saveInterval)) {
            if (save()) {
                lastSave = new Date();
            }
        }
    }

    /**
     * Load the stored counts map from the file system. If no file was found... create a new empty map.
     */
    @SuppressWarnings("unchecked")
    private void load() {

        try {
            FileInputStream fs = new FileInputStream(persistentFile);
            ObjectInputStream ois = new ObjectInputStream(fs);
            counts = (Map<String, Count>) ois.readObject();
            ois.close();

        } catch (FileNotFoundException x) {
            log.error(x.getMessage());
        } catch (IOException x) {
            log.error(x.getMessage());
        } catch (ClassNotFoundException x) {
            log.error(x.getMessage());
        }
    }

    /**
     * Save the counts map to the file system.
     * 
     * @return true, if successful
     */
    private synchronized boolean save() {

        try {
            FileOutputStream fos = new FileOutputStream(persistentFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(counts);
            oos.close();
            fos.close();

            return true;
        } catch (IOException x) {
            log.error(x.getMessage());
        }
        return false;
    }

    private void load(final Session session) {
        // For unit tests
        if (session == null) {
            return;
        }
        log.info("Loading pageview counts.");
        try {
            Node root = session.getRootNode();
            Node node = null;
            if (!root.hasNode(PAGE_VIEW_COUNTER_NODE)) {
                log.info("No pageview counts found in repository");
                return; // do nothing, no map was saved...
            }
            node = root.getNode(PAGE_VIEW_COUNTER_NODE);
            Property prop = node.getProperty(PAGE_VIEW_COUNTER_PROPERTY);
            InputStream is = prop.getStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            counts = (Map<String, Count>) ois.readObject();
            ois.close();
            log.info("loaded " + counts.size() + " counts from repository");
        } catch (RepositoryException e) {
            log.error("Can't load page view counts: " + e.getMessage());
        } catch (IOException ioe) {
            log.error("Can't load page view counts: " + ioe.getMessage());
        } catch (ClassNotFoundException ioe) {
            log.error("Can't load page view counts: " + ioe.getMessage());
        }
    }

    private synchronized boolean save(final Session session) {
        // For unit tests
        if (session == null) {
            return false;
        }
        log.info("Saving page view counts as user: {}", session.getUserID());
        try {
            Node root = session.getRootNode();
            Node node = null;
            if (!root.hasNode(PAGE_VIEW_COUNTER_NODE)) {
                node = root.addNode(PAGE_VIEW_COUNTER_NODE, "nt:unstructured");
            } else {
                node = root.getNode(PAGE_VIEW_COUNTER_NODE);
            }
            ByteArrayOutputStream os = new ByteArrayOutputStream(SIZE);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(counts);
            oos.close();
            os.close();
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            node.setProperty(PAGE_VIEW_COUNTER_PROPERTY, is);
            is.close();

            // Save the node changes.
            session.save();
            
            return true;
        } catch (RepositoryException e) {
            log.error("Can't save page view counts: " + e.getMessage());
        } catch (IOException ioe) {
            log.error("Can't save page view counts: " + ioe.getMessage());
        }
        return false;
    }

    public List<TextDocument> getMostReadTextDocumentsForType(String type, int nrOfEntries, int maxAge,
            ObjectBeanManager objectBeanManager) {

        List<TextDocument> answer = new LinkedList<TextDocument>();

        List<Count> temp = sort(Sort.SortDirection.DESCENDING);

        Calendar last = Calendar.getInstance();
        last.add(Calendar.DAY_OF_YEAR, -1 * maxAge);
        for (Count count : temp) {
            
            
            if (count.isForType(type)) {
                
                // document could be too old
                if (count.getDate().getTime() < last.getTimeInMillis()) {
                    continue;
                }

                // ... or no longer available
                HippoDocumentBean bean = retriever.retrieve(count.getUuid(), objectBeanManager);
                
                if (bean != null) {
                    if (bean instanceof TextDocument) {
                        answer.add((TextDocument)bean);
                    }
                }
                
                if (answer.size() == nrOfEntries) {
                    break;
                }
            }

        }
        return answer;
    }

    /**
     * Answer the top number of documents, which are younger then a certain age.
     * 
     * @param nofEntries
     *            maximum number of entries in the list
     * @param oldestDays
     *            only show documents younger then this many days
     * @param objectBeanManager
     *            servlet request
     * @return list of most-viewed articles.
     */
    public List<HippoDocumentBean> topOfChart(final int nofEntries, final int oldestDays,
            final ObjectBeanManager objectBeanManager) {
        List<HippoDocumentBean> answer = new LinkedList<HippoDocumentBean>();

        List<Count> temp = sort(Sort.SortDirection.DESCENDING);

        Calendar last = Calendar.getInstance();
        last.add(Calendar.DAY_OF_YEAR, -1 * oldestDays);
        for (Count count : temp) {
            // document could be too old
            if (count.getDate().getTime() < last.getTimeInMillis()) {
                continue;
            }
            // ... or no longer available
            HippoDocumentBean bean = retriever.retrieve(count.getUuid(), objectBeanManager);
            if (bean != null) {
                answer.add(bean);
            }
            if (answer.size() == nofEntries) {
                break;
            }
        }
        return answer;
    }

    public int getCount(final HippoDocumentBean bean) {
        Count count = counts.get(bean.getCanonicalHandleUUID());
        if (count != null) {
            return count.getCount();
        }
        return 0;
    }

    public Map<String, Count> getCounts() {
        return counts;
    }

    protected static Session getPersistableSession(final HstRequest request) throws RepositoryException {
        HstRequestContext requestContext = request.getRequestContext();
        Credentials credentials = requestContext.getContextCredentialsProvider().getWritableCredentials(
                requestContext);
        return requestContext.getSession().impersonate(credentials);
    }

}
