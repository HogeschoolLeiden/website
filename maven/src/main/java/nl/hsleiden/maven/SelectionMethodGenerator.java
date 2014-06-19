package nl.hsleiden.maven;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.mavenhippo.gen.AnnotationGenerator;
import net.sourceforge.mavenhippo.gen.ClassReference;
import net.sourceforge.mavenhippo.gen.FreemarkerUtils;
import net.sourceforge.mavenhippo.gen.MethodGenerator;
import net.sourceforge.mavenhippo.model.ContentTypeBean.Item;
import net.sourceforge.mavenhippo.utils.NammingUtils;
import net.sourceforge.mavenhippo.utils.exceptions.GeneratorException;

/**
 * @author Ebrahim Aharpour
 *
 */
public class SelectionMethodGenerator implements MethodGenerator {

    private final Item item;
    private final String valueListPath;
    private final ClassReference returnType;

    public SelectionMethodGenerator(Item item, String valueListPath, ClassReference returnType) {
        this.item = item;
        this.valueListPath = valueListPath;
        this.returnType = returnType;
    }

    @Override
    public String getFragment() throws GeneratorException {
        try {
            Map<String, Object> model = new HashMap<String, Object>();
            String fieldName = item.getSimpleName();
            model.put("fieldName", fieldName);
            model.put("propertyName", item.getRelativePath());
            model.put("methodName", NammingUtils.stringToClassName(fieldName));
            model.put("valueListPath", valueListPath);
            model.put("type", returnType);
            return FreemarkerUtils.renderTemplate("nl/hsleiden/maven/selection-method-generator.ftl", model,
                    this.getClass());
        } catch (Exception e) {
            throw new GeneratorException(e.getMessage(), e);
        }
    }

    @Override
    public List<AnnotationGenerator> getAnnotations() {
        return new ArrayList<AnnotationGenerator>();
    }

}
