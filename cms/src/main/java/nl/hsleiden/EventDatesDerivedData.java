package nl.hsleiden;

import java.util.Calendar;
import java.util.Map;

import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import javax.jcr.Value;

import nl.hsleiden.derivatives.GenericDerivedDataFunction;

public class EventDatesDerivedData extends GenericDerivedDataFunction {

    @Override
    public Map<String, Value[]> compute(Map<String, Value[]> parameters) {

        try {
            Calendar eventDate = getSingleDateValue(parameters, "eventDate");
            Calendar eventEndDate = getSingleDateValue(parameters, "eventEndDate");

            if (eventEndDate == null && eventDate != null) {
                eventEndDate = eventDate;
                parameters.put("eventEndDate", new Value[] { getValueFactory().createValue(eventEndDate) });
            }

            return parameters;

        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
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
