package nl.hsleiden.search;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.FactoryBean;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigFactor implements FactoryBean<ElasticsearchConfig> {
    private final ElasticsearchConfig config;

    public ConfigFactor(String systemPropertyName, String defaultPropertiesFileLocation) throws JsonParseException,
            JsonMappingException, IOException {
        if (defaultPropertiesFileLocation == null) {
            throw new IllegalArgumentException("default properties file location is required.");
        }
        InputStream src = null;
        if (StringUtils.isNotEmpty(systemPropertyName)) {
            src = tryToFetchJsonViaSystemProperty(systemPropertyName);
        }
        if (src == null) {
            src = ConfigFactor.class.getClassLoader().getResourceAsStream(defaultPropertiesFileLocation);
        }
        config = new ObjectMapper().readValue(src, ElasticsearchConfig.class);
    }

    public ElasticsearchConfig getConfig() {
        return config;
    }

    private InputStream tryToFetchJsonViaSystemProperty(String systemPropertyName) throws FileNotFoundException {
        InputStream src = null;
        String propertyFilePath = System.getProperties().getProperty(systemPropertyName);
        if (StringUtils.isNotBlank(propertyFilePath)) {
            src = new FileInputStream(propertyFilePath);
        }
        return src;
    }

    @Override
    public ElasticsearchConfig getObject() throws Exception {
        return config;
    }

    @Override
    public Class<?> getObjectType() {
        return ElasticsearchConfig.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
