package nl.hsleiden.validators;

import java.util.HashSet;
import java.util.Set;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.hippoecm.frontend.model.JcrNodeModel;
import org.hippoecm.frontend.plugin.IPluginContext;
import org.hippoecm.frontend.plugin.config.IPluginConfig;
import org.hippoecm.frontend.validation.IFieldValidator;
import org.hippoecm.frontend.validation.ValidationException;
import org.hippoecm.frontend.validation.Violation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicStringLengthValidator extends GenericStringLengthValidator {

    private static final String HSL_COLOR_PROPERTY = "hsl:color";
    private static final String MAX_LENGTH_BLUE = "maxLengthBlue";
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(DynamicStringLengthValidator.class);

    private int maxLengthBlue;
    private int dynamicMaxLength;
    
    public DynamicStringLengthValidator(IPluginContext context, IPluginConfig config) throws RepositoryException {
        super(context, config);
        setMaxLength(config);
        setMaxLengthBlue(config);
    }

    @Override
    public Set<Violation> validate(IFieldValidator fieldValidator, JcrNodeModel model,
            @SuppressWarnings("rawtypes") IModel childModel) throws ValidationException {
        
        Set<Violation> violations = new HashSet<Violation>();
        String value = (String) childModel.getObject();

        try {
            setDynamicMaxLength(getDynamicLength(model));
            if (StringUtils.length(value) > getDynamicMaxLength()) {
                violations.add(fieldValidator.newValueViolation(childModel, getTranslation()));
            }
        } catch (RepositoryException e) {
            LOG.error("repository exceptionn trying to validate document field: ", e);
        }

        return violations;
    }

    private int getDynamicLength(JcrNodeModel model) throws RepositoryException {
        int result = getMaxLength();
        if(model!=null){
            Node node = model.getNode();
            if(node != null){
                result = getMaxLengthIfBlue(result, node);
            }
        }
        return result;
    }

    private int getMaxLengthIfBlue(int defaultMax, Node node) throws RepositoryException {
        int result = defaultMax;
        if(node.hasProperty(HSL_COLOR_PROPERTY)){
            String color = node.getProperty(HSL_COLOR_PROPERTY).getString();
            if(color!=null && color.equals("blauw")){
                result = getMaxLengthBlue();
            }
        }
        return result;
    }

    @Override
    protected void checkViolations(IFieldValidator fieldValidator, @SuppressWarnings("rawtypes") IModel childModel,
            Set<Violation> violations, String value) throws ValidationException {
        if (StringUtils.length(value) > getMaxLength()) {
            violations.add(fieldValidator.newValueViolation(childModel, getTranslation()));
        }
    }
    
    @Override
    protected IModel<String> getTranslation() {
        return new LoadableDetachableModel<String>() {
            private static final long serialVersionUID = 1L;
            @Override
            protected String load() {
                return translateKey(getName()+"-"+getDynamicMaxLength());
            }
        };
    }

    public void setDynamicMaxLength(int dynamicMaxLength) {
        this.dynamicMaxLength = dynamicMaxLength;
    }

    public void setMaxLengthBlue(IPluginConfig config) throws RepositoryException {
        if (config.containsKey(MAX_LENGTH_BLUE)) {
            this.maxLengthBlue = config.getInt(MAX_LENGTH_BLUE);
        } else {
            throw new RepositoryException("maxLengthBlue property should be set in the iplugin configuration of: " + config.getName());
        }
    }
    
    public int getMaxLengthBlue() {
        return maxLengthBlue;
    }

    public int getDynamicMaxLength() {
        return dynamicMaxLength;
    }

}
