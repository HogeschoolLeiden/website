package nl.hsleiden.validators;

import java.util.Set;

import javax.jcr.RepositoryException;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.model.IModel;
import org.hippoecm.frontend.plugin.IPluginContext;
import org.hippoecm.frontend.plugin.config.IPluginConfig;
import org.hippoecm.frontend.validation.IFieldValidator;
import org.hippoecm.frontend.validation.ValidationException;
import org.hippoecm.frontend.validation.Violation;

public class StringLengthValidator extends GenericStringLengthValidator {

    private static final long serialVersionUID = 1L;

    public StringLengthValidator(IPluginContext context, IPluginConfig config) throws RepositoryException {
        super(context, config);
        setMaxLength(config);
        if (config.containsKey(MIN_LENGTH)) {
            setMinLength(config);
        }
    }

    @Override
    protected void checkViolations(IFieldValidator fieldValidator, @SuppressWarnings("rawtypes") IModel childModel,
            Set<Violation> violations, String value) throws ValidationException {
        if (StringUtils.length(value) > getMaxLength() || StringUtils.length(value) < getMinLength() ) {
            violations.add(fieldValidator.newValueViolation(childModel, getTranslation()));
        }
    }
}
