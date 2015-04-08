package nl.hsleiden.schedulers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.onehippo.repository.scheduling.RepositoryJob;
import org.onehippo.repository.scheduling.RepositoryJobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SitemapGeneratorScheduledJob implements RepositoryJob {

    private static final Logger LOG = LoggerFactory.getLogger(SitemapGeneratorScheduledJob.class);

    @Override
    public void execute(RepositoryJobExecutionContext context) throws RepositoryException {

        BufferedReader bReader = null;
        String address = null;
        try {
            address = getAddressFromProperties();
            if (StringUtils.isNotBlank(address)) {
                URL url = new URL(address);
                URLConnection connection = url.openConnection();
                connection.connect();
                bReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                LOG.debug(bReader.readLine());
            } else {
                LOG.warn("No address configured or property file missing");
            }
        } catch (IOException e) {
            LOG.error("Exception connecting to configured address: " + address, e);
        } finally {
            closeConnection(bReader);
        }
    }

    private String getAddressFromProperties() throws IOException {
        FileInputStream inStream = null;
        try {
            String address = null;
            String ssc = System.getProperty("sitemap.scheduler.config");
            if (StringUtils.isNoneBlank(ssc)) {
                Properties props = new Properties();
                inStream = new FileInputStream(ssc);
                props.load(inStream);
                address = props.getProperty("sitemap.generator.url");
            }
            return address;
        } finally {
            closeStream(inStream);
        }
    }

    private void closeStream(FileInputStream inStream) {
        try {
            if (inStream != null) {
                inStream.close();
            }
        } catch (IOException e) {
            LOG.warn(e.getMessage(), e);
        }
    }

    private void closeConnection(BufferedReader bReader) throws RepositoryException {
        try {
            if (bReader != null) {
                LOG.debug("closing the connection!");
                bReader.close();
            }
        } catch (IOException e) {
            LOG.error("Exception closing the connection to sitemap generator url: ", e);
        }
    }
}