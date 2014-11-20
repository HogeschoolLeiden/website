package nl.hsleiden;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.model.IModel;
import org.hippoecm.frontend.plugin.IPluginContext;
import org.hippoecm.frontend.plugin.config.IPluginConfig;
import org.hippoecm.frontend.validation.IFieldValidator;
import org.hippoecm.frontend.validation.ValidationException;
import org.hippoecm.frontend.validation.Violation;

public class StringLengthValidator30 extends StringFieldCmsValidator {

	private static final long serialVersionUID = 1L;

	private static final String MAX_LENGTH = "maxLength"; 

	private final int maxLength;

	public int getMaxLength() {
		return maxLength;
	}
	
	public StringLengthValidator30(IPluginContext context, IPluginConfig config) throws Exception{
		super(context, config);
		if (config.containsKey(MAX_LENGTH)) {
			maxLength = config.getInt(MAX_LENGTH);
        } else {
            throw new Exception("maxLenght property should be set in the iplugin configuration of: " + config.getName());
        }
	}

	@Override
	protected void checkViolations(IFieldValidator fieldValidator,
			@SuppressWarnings("rawtypes") IModel childModel, Set<Violation> violations, String value)
			throws ValidationException {
		if (StringUtils.length(value)>getMaxLength()) {
        	violations.add(fieldValidator.newValueViolation(childModel, getTranslation()));
        }
	}
}
