package nl.hsleiden.derivatives;

import java.util.Calendar;
import java.util.Map;

import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import javax.jcr.Value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventDatesDerivedData extends GenericDerivedDataFunction {

    private static final String EVENT_DATE = "eventDate";
    private static final String EVENT_END_DATE = "eventEndDate";
    
    private static final Logger LOG = LoggerFactory.getLogger(EventDatesDerivedData.class);

    @Override
    public Map<String, Value[]> compute(Map<String, Value[]> parameters) {

        try {
            Calendar eventDate = getSingleDateValue(parameters, EVENT_DATE);
            Calendar eventEndDate = getSingleDateValue(parameters, EVENT_END_DATE);

            if (eventEndDate == null && eventDate != null) {
                eventEndDate = eventDate;
                parameters.put(EVENT_END_DATE, new Value[] { getValueFactory().createValue(eventEndDate) });
            }

        } catch (RepositoryException e) {
            LOG.error("repository exception computing event derived data", e);
        }
        
        return parameters;
    }

    private Calendar getSingleDateValue(Map<String, Value[]> parameters, String propertyName)
            throws RepositoryException {
        Calendar result = null;
        Value[] values = parameters.get(propertyName);
        if ((values != null) && (values.length > 0) && (values[0].getType() == PropertyType.DATE)) {
            result = values[0].getDate();
        }
        return result;
    }

}
