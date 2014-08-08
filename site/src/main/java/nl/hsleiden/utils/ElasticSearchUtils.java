package nl.hsleiden.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.tdclighthouse.prototype.utils.PathUtils;

public class ElasticSearchUtils {

    private ElasticSearchUtils() {
    }

    public static Map<String, List<String>> parseFacetUrl(Map<String, List<String>> result, String parameter)
            throws FacetMapException {
        if (StringUtils.isNotBlank(parameter)) {
            String[] split = PathUtils.normalize(parameter).split("/");
            if (split.length % 2 == 0) {
                for (int i = 0; i < split.length / 2; i++) {
                    ElasticSearchUtils.addToMap(result, split[i * 2], split[(i * 2) + 1]);
                }
            } else {
                throw new FacetMapException("odd number of fact segment was encountered.");
            }
        }
        return result;
    }

    public static void addToMap(Map<String, List<String>> map, String key, String value) {
        if (map.containsKey(key)) {
            map.get(key).add(value);
        } else {
            List<String> list = new ArrayList<String>();
            list.add(value);
            map.put(key, list);
        }
    }

    public static void removeFromMap(Map<String, List<String>> map, String key, String value) {
        if (map.containsKey(key)) {
            List<String> list = map.get(key);
            if (list.contains(value)) {
                list.remove(value);
            } else {
                throw new IllegalArgumentException("the map does not contain the following value: \"" + value + "\".");
            }
        } else {
            throw new IllegalArgumentException("the map does not contain the following key: \"" + key + "\"");
        }

    }

}
