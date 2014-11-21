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

public class StringLengthValidator30 extends GenericStringLengthValidator {

    private static final long serialVersionUID = 1L;

    public StringLengthValidator30(IPluginContext context, IPluginConfig config) throws RepositoryException {
        super(context, config);
        setMaxLength(config);
    }

    @Override
    protected void checkViolations(IFieldValidator fieldValidator, @SuppressWarnings("rawtypes") IModel childModel,
            Set<Violation> violations, String value) throws ValidationException {
        if (StringUtils.length(value) > getMaxLength()) {
            violations.add(fieldValidator.newValueViolation(childModel, getTranslation()));
        }
    }
}
