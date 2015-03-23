package nl.hsleiden.utils;

import hslbeans.RelatedFilterParameters;
import hslbeans.RelatedOverviewParameters;
import hslbeans.RelatedSortParameters;
import hslbeans.RelatedWidgetParameters;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import nl.hsleiden.beans.compounds.RelatedEventsCompoundMixinBean;
import nl.hsleiden.beans.compounds.RelatedNewsCompoundMixinBean;

import org.hippoecm.hst.content.beans.manager.ObjectConverterImpl;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoFolder;
import org.hippoecm.hst.content.beans.standard.HippoMirror;

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

    public static ObjectConverterImpl getObjectConverter() {
        Map<String, Class<? extends HippoBean>> jcrPrimaryNodeTypeBeanPairs = new HashMap<String, Class<? extends HippoBean>>();
        jcrPrimaryNodeTypeBeanPairs.put("hsl:RelatedEventsCompoundMixin", RelatedEventsCompoundMixinBean.class);
        jcrPrimaryNodeTypeBeanPairs.put("hsl:RelatedCompoundMixin", RelatedNewsCompoundMixinBean.class);
        jcrPrimaryNodeTypeBeanPairs.put("hsl:RelatedSortParameters", RelatedSortParameters.class);
        jcrPrimaryNodeTypeBeanPairs.put("hsl:RelatedOverviewParameters", RelatedOverviewParameters.class);
        jcrPrimaryNodeTypeBeanPairs.put("hsl:RelatedSortParameters", RelatedSortParameters.class);
        jcrPrimaryNodeTypeBeanPairs.put("hsl:RelatedWidgetParameters", RelatedWidgetParameters.class);
        jcrPrimaryNodeTypeBeanPairs.put("hsl:RelatedFilterParameters", RelatedFilterParameters.class);
        jcrPrimaryNodeTypeBeanPairs.put("hippostd:folder", HippoFolder.class);
        jcrPrimaryNodeTypeBeanPairs.put("hippo:mirror", HippoMirror.class);

        ObjectConverterImpl objectConverter = new ObjectConverterImpl(jcrPrimaryNodeTypeBeanPairs, null);
        return objectConverter;
    }
    
    
}
