package nl.hsleiden.components.base;

import hslbeans.WebPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nl.hsleiden.utils.Constants.HstParameters;
import nl.hsleiden.utils.Constants.Values;
import nl.hsleiden.utils.HslUtils;
import nl.hsleiden.utils.NavigationUtils;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.configuration.HstNodeTypes;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoDocumentBean;
import org.hippoecm.hst.content.beans.standard.HippoFolderBean;
import org.hippoecm.hst.content.beans.standard.facetnavigation.HippoFacetNavigation;
import org.hippoecm.hst.content.beans.standard.facetnavigation.HippoFacetSubNavigation;
import org.hippoecm.hst.content.beans.standard.facetnavigation.HippoFacetsAvailableNavigation;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.linking.HstLink;
import org.hippoecm.hst.core.request.ResolvedSiteMapItem;
import org.hippoecm.hst.core.sitemenu.CommonMenuItem;
import org.hippoecm.hst.core.sitemenu.EditableMenu;
import org.hippoecm.hst.core.sitemenu.EditableMenuItem;
import org.hippoecm.hst.core.sitemenu.EditableMenuItemImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RepoBasedMenuProvider {

    private static final Logger LOG = LoggerFactory.getLogger(RepoBasedMenuProvider.class);

    private final HstRequest request;
    private final String selectedNodeCanonicalPath;
    private final HippoBean siteContentBaseBean;
    private final boolean showFacetNavigations;

    public RepoBasedMenuProvider(HippoBean siteContentBaseBean, HstRequest request) {
        this(siteContentBaseBean, false, request);
    }

    public RepoBasedMenuProvider(HippoBean siteContentBaseBean, boolean showFacetNavigations, HstRequest request) {
        this.request = request;
        this.siteContentBaseBean = siteContentBaseBean;
        this.showFacetNavigations = showFacetNavigations;
        String relativeContentPath = request.getRequestContext().getResolvedSiteMapItem().getRelativeContentPath();
        if (relativeContentPath != null) {
            selectedNodeCanonicalPath = siteContentBaseBean.<HippoBean> getBean(relativeContentPath).getCanonicalPath();
        } else {
            selectedNodeCanonicalPath = null;
        }
    }

    public EditableMenu addRepoBasedMenuItems(EditableMenu editableMenu) {
        List<EditableMenuItem> menuItems = editableMenu.getMenuItems();
        addRepoBasedMenuItems(menuItems);
        return editableMenu;
    }

    private void addRepoBasedMenuItems(List<EditableMenuItem> menuItems) {
        for (EditableMenuItem item : menuItems) {
            addRepoBasedMenuItems(item.getChildMenuItems());
            expandForcedExpandedItems(item);
            if (item.isRepositoryBased()) {
                List<String> locations = getParameterValues(HstParameters.ROOT, item);
                HippoBean[] beanOfMenuItems = null;
                if (locations != null && !locations.isEmpty()) {
                    beanOfMenuItems = getBeans(locations);
                } else {
                    beanOfMenuItems = new HippoBean[] { getBeanOfMenuItem(item) };
                }
                for (HippoBean hippoBean : beanOfMenuItems) {
                    addSubitems(item, hippoBean, 1);
                }
            }
        }
    }
    
    private void expandForcedExpandedItems(EditableMenuItem item) {
        String value = getParameterValue(HstParameters.EXPANDED, item);
        if (Values.TRUE.equals(value)) {
            String expandeOnlyCurrentItem = getParameterValue(HstParameters.EXPAND_ONLY_CURRENT_ITEM, item);
            if (Values.TRUE.equals(expandeOnlyCurrentItem)) {
                markOnlyCurrentItemAsExpanded(item);
            } else {
                markAsExpanded(item);
            }
        }
    }

    private HippoBean[] getBeans(List<String> locations) {
        HippoBean[] beanOfMenuItems;
        beanOfMenuItems = new HippoBean[locations.size()];
        for (int i = 0; i < locations.size(); i++) {
            beanOfMenuItems[i] = siteContentBaseBean.getBean(locations.get(i));
        }
        return beanOfMenuItems;
    }

    private void addSubitems(EditableMenuItem item, HippoBean indexPageBean, int depth) {
        if (item.getDepth() >= depth) {
            HippoBean childbearingBean = getFolderOrFacet(indexPageBean);
            if (childbearingBean != null) {
                List<? extends HippoBean> childbearingChildren = getChildbearingChildren(childbearingBean);
                for (HippoBean childbearingChild : childbearingChildren) {
                    HippoBean foldersIndex = NavigationUtils.getIndexBean(childbearingChild);
                    if (foldersIndex != null) {
                        EditableMenuItem folderItem = addItem(item, foldersIndex, childbearingChild.getLocalizedName());
                        addSubitems(folderItem, foldersIndex, depth + 1);
                    }
                }
                List<HippoDocumentBean> documents = getNonChildbearingChilds(childbearingBean);
                for (final HippoBean document : documents) {
                    if (!document.getCanonicalPath().equals(indexPageBean.getCanonicalPath())) {
                        addItem(item, document);
                    }
                }
            }
        }
    }

    private List<HippoDocumentBean> getNonChildbearingChilds(HippoBean childbearingBean) {
        List<HippoDocumentBean> result;
        if (childbearingBean instanceof HippoFolderBean) {
            result = ((HippoFolderBean) childbearingBean).getDocuments();
        } else {
            result = new ArrayList<HippoDocumentBean>();
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private <T extends HippoBean> List<T> getChildbearingChildren(HippoBean childbearingBean) {
        List<T> result;
        if (childbearingBean instanceof HippoFacetNavigation) {
            result = getChildbearingChildrenOfFacet((HippoFacetNavigation) childbearingBean);
        } else if (childbearingBean instanceof HippoFolderBean) {
            result = (List<T>) getChildbearingChildrenOfFolder(childbearingBean);
        } else {
            throw new IllegalArgumentException(
                    "Expect childbearingBean to be either a HippoFolderBean or a HippoFacetNavigationBean");
        }
        return result;
    }

    private List<HippoBean> getChildbearingChildrenOfFolder(HippoBean childbearingBean) {
        List<HippoBean> items = new ArrayList<HippoBean>();
        items.addAll(((HippoFolderBean) childbearingBean).getFolders());
        if (showFacetNavigations) {
            items.addAll(childbearingBean.getChildBeans(HippoFacetNavigation.class));
        }
        return items;
    }

    @SuppressWarnings("unchecked")
    private <T extends HippoBean> List<T> getChildbearingChildrenOfFacet(HippoFacetNavigation facetNavigation) {
        List<T> result;
        List<HippoFacetsAvailableNavigation> availableNavigation = facetNavigation
                .getChildBeans(HippoFacetsAvailableNavigation.class);
        if (availableNavigation == null || availableNavigation.size() != 1) {
            result = new ArrayList<T>();
        } else {
            result = (List<T>) availableNavigation.get(0).getChildBeans(HippoFacetSubNavigation.class);
        }
        return result;
    }

    public static void markAsSeleted(EditableMenuItem item) {
        if (item instanceof SimpleEditableMenuItem) {
            ((SimpleEditableMenuItem) item).setSelected(true);
        }
        markAsExpanded(item);
    }

    public static void markAsExpanded(EditableMenuItem item) {
        EditableMenuItem temp = item;
        while (temp != null) {
            temp.setExpanded(true);
            temp = temp.getParentItem();
        }
    }

    private void markOnlyCurrentItemAsExpanded(EditableMenuItem item) {
        EditableMenuItem parentItem = item.getParentItem();
        if (parentItem != null) {
            boolean isParentItemExpanded;
            isParentItemExpanded = parentItem.isExpanded();
            item.setExpanded(true);
            parentItem.setExpanded(isParentItemExpanded);
        } else {
            item.setExpanded(true);
        }
    }

    private void addItem(EditableMenuItem item, final HippoBean document) {
        String localizedName = document.getLocalizedName();
        if (document instanceof WebPage) {
            if (((WebPage) document).getHideFromSitemap()) {
                if (selectedNodeCanonicalPath != null && selectedNodeCanonicalPath.equals(document.getCanonicalPath())) {
                    markAsExpanded(item);
                }
            } else {
                addItem(item, document, localizedName);
            }
        } else {
            addItem(item, document, localizedName);
        }
    }

    private EditableMenuItem addItem(EditableMenuItem item, final HippoBean document, String localizedName) {
        HstLink hstLink = HslUtils.createHstLink(document, request);
        EditableMenuItem repoMenuItem = new SimpleEditableMenuItem(item, hstLink, localizedName);
        item.addChildMenuItem(repoMenuItem);
        if (selectedNodeCanonicalPath != null && selectedNodeCanonicalPath.equals(document.getCanonicalPath())) {
            markAsSeleted(repoMenuItem);
        }
        return repoMenuItem;
    }

    private HippoBean getFolderOrFacet(HippoBean bean) {
        HippoBean result = null;
        if (bean instanceof HippoFacetNavigation) {
            result = bean;
        } else {
            HippoBean entityBean = bean;
            while (entityBean != null) {
                if (entityBean.isHippoFolderBean()) {
                    result = entityBean;
                    break;
                }
                entityBean = entityBean.getParentBean();
            }
        }

        return result;
    }

    private HippoBean getBeanOfMenuItem(CommonMenuItem item) {
        HippoBean bean = null;
        ResolvedSiteMapItem resolveToSiteMapItem = item.resolveToSiteMapItem(request);
        if (resolveToSiteMapItem != null) {
            bean = siteContentBaseBean.getBean(resolveToSiteMapItem.getRelativeContentPath());
        }
        return bean;
    }

    public static String getParameterValue(String parameterName, EditableMenuItem menuItem) {
        String result = null;
        List<String> values = getParameterValues(parameterName, menuItem);
        if (!values.isEmpty()) {
            result = values.get(0);
        }
        return result;
    }

    public static List<String> getParameterValues(String parameterName, EditableMenuItem menuItem) {
        if (StringUtils.isBlank(parameterName) || menuItem == null) {
            throw new IllegalArgumentException("Both parameterName and menuItem are required.");
        }
        List<String> result = new ArrayList<String>();
        Map<String, Object> properties = menuItem.getProperties();
        String[] paramNames = (String[]) properties.get(HstNodeTypes.GENERAL_PROPERTY_PARAMETER_NAMES);
        String[] paramValues = (String[]) properties.get(HstNodeTypes.GENERAL_PROPERTY_PARAMETER_VALUES);
        if (paramNames != null && paramValues != null) {
            if (paramNames.length == paramValues.length) {
                for (int i = 0; i < paramNames.length; i++) {
                    String propName = paramNames[i];
                    if (parameterName.equals(propName)) {
                        result.add(paramValues[i]);
                    }
                }
            } else {
                LOG.warn("Parameter name array and parameter values arrays have different lengths");
            }
        }
        return result;
    }

    public static class SimpleEditableMenuItem extends EditableMenuItemImpl {

        private final HstLink hstLink;
        private final String localizedName;
        private final int depth;

        public SimpleEditableMenuItem(EditableMenuItem item, HstLink hstLink, String localizedName) {
            super(item);
            this.hstLink = hstLink;
            this.localizedName = localizedName;
            depth = item.getDepth();
        }

        @Override
        public HstLink getHstLink() {
            return hstLink;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        @Override
        public String getName() {
            return localizedName;
        }

        @Override
        public boolean isRepositoryBased() {
            return true;
        }

        @Override
        public int getDepth() {
            return depth;
        }
    }

}