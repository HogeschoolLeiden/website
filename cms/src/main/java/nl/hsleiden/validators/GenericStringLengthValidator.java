package nl.hsleiden.validators;

import java.util.HashSet;
import java.util.Set;

import javax.jcr.RepositoryException;

import org.apache.wicket.model.IModel;
import org.hippoecm.frontend.editor.validator.plugins.AbstractCmsValidator;
import org.hippoecm.frontend.model.JcrNodeModel;
import org.hippoecm.frontend.plugin.IPluginContext;
import org.hippoecm.frontend.plugin.config.IPluginConfig;
import org.hippoecm.frontend.validation.IFieldValidator;
import org.hippoecm.frontend.validation.ValidationException;
import org.hippoecm.frontend.validation.Violation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class GenericStringLengthValidator extends AbstractCmsValidator {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(GenericStringLengthValidator.class);
    private static final String MAX_LENGTH = "maxLength";
    private int maxLength;

    public GenericStringLengthValidator(IPluginContext context, IPluginConfig config) {
        super(context, config);
    }

    @Override
    public void preValidation(IFieldValidator type) throws ValidationException {
        if (!"String".equals(type.getFieldType().getType())) {
            LOG.error("Cannot count charachters of field: " + type.getFieldType().getName() + " of type: "
                    + type.getFieldType().getType());
            throw new ValidationException(
                    "Invalid validation exception; cannot validate non-string field for charachter length");
        }
    }

    @Override
    public Set<Violation> validate(IFieldValidator fieldValidator, JcrNodeModel model,
            @SuppressWarnings("rawtypes") IModel childModel) throws ValidationException {
        Set<Violation> violations = new HashSet<Violation>();
        String value = (String) childModel.getObject();

        checkViolations(fieldValidator, childModel, violations, value);

        return violations;
    }
    
    public int getMaxLength() {
        return maxLength;
    }
    
    protected void setMaxLength(IPluginConfig config) throws RepositoryException {
        if (config.containsKey(MAX_LENGTH)) {
            this.maxLength = config.getInt(MAX_LENGTH);
        } else {
            throw new RepositoryException("maxLenght property should be set in the iplugin configuration of: " + config.getName());
        }
    }

    protected abstract void checkViolations(IFieldValidator fieldValidator,
            @SuppressWarnings("rawtypes") IModel childModel, Set<Violation> violations, String value)
            throws ValidationException;
}
