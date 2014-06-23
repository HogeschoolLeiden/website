package nl.hsleiden.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

public class ContantsTest {

    @Test
    public void constructorTest() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        testConstructor(Constants.class);
        testConstructor(Constants.Attributes.class);
        testConstructor(Constants.FieldName.class);
        testConstructor(Constants.HstParameters.class);
        testConstructor(Constants.NodeName.class);
        testConstructor(Constants.Parameters.class);
        testConstructor(Constants.PikcerTypes.class);
        testConstructor(Constants.Values.class);

    }

    private void testConstructor(Class<?> clazz) throws InstantiationException, IllegalAccessException,
            InvocationTargetException {
        Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        constructor.newInstance();
    }

}
