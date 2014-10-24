package nl.hsleiden;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
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

public class StringLengthValidator extends AbstractCmsValidator {

	private static final long serialVersionUID = 1L;

	private static Logger LOG = LoggerFactory.getLogger(StringLengthValidator.class);
	
	private final static String MAX_LENGTH = "maxLength"; 

	public StringLengthValidator(IPluginContext context, IPluginConfig config) throws Exception{
		super(context, config);
		if (config.containsKey(MAX_LENGTH)) {
			maxLength = config.getInt(MAX_LENGTH);
        } else {
            throw new Exception("maxLenght property should be set in the iplugin configuration of: " + config.getName());
        }
	}

	private final int maxLength;

	/**
	 * @return the maxLenght
	 */
	public int getMaxLength() {
		return maxLength;
	}

	@Override
	public void preValidation(IFieldValidator type) throws ValidationException {
		if (!"String".equals(type.getFieldType().getType())) {
		    LOG.error("Cannot count charachters of field: " + type.getFieldType().getName() + " of type: " + type.getFieldType().getType());
			throw new ValidationException("Invalid validation exception; cannot validate non-string field for charachter length");
	    }
	}

	@Override
	public Set<Violation> validate(IFieldValidator fieldValidator,
			JcrNodeModel model, @SuppressWarnings("rawtypes") IModel childModel) throws ValidationException {
		Set<Violation> violations = new HashSet<Violation>();
        String value = (String) childModel.getObject();
        
        if (StringUtils.length(value)>getMaxLength()) {
        	violations.add(fieldValidator.newValueViolation(childModel, getTranslation()));
        }

        return violations;
	}
}
