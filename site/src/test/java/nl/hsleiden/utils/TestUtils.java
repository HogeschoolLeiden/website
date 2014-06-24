package nl.hsleiden.utils;

import java.lang.reflect.Field;

public class TestUtils {
    private TestUtils() {
    }

    public static void setPrivateField(Object target, String fieldName, Object value) throws NoSuchFieldException,
            IllegalAccessException {
        Field field = null;
        Class<? extends Object> clazz = target.getClass();
        while (clazz != Object.class) {
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field f : declaredFields) {
                if (f.getName().equals(fieldName)) {
                    field = f;
                    break;
                }
            }
            if (field != null) {
                break;
            } else {
                clazz = clazz.getSuperclass();
            }
        }

        field.setAccessible(true);
        field.set(target, value);
    }
}
