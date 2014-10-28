package nl.hsleiden.eforms;

import java.util.UUID;

import org.hippoecm.hst.component.support.forms.FormField;
import org.hippoecm.hst.component.support.forms.FormMap;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.request.ComponentConfiguration;

import com.onehippo.cms7.eforms.hst.api.OnValidationSuccessBehavior;
import com.onehippo.cms7.eforms.hst.beans.FormBean;
import com.onehippo.cms7.eforms.hst.model.Form;

public class UUIDBehaivor implements OnValidationSuccessBehavior {

    public static final String UNIQUE_ID = "unique id";
    public static final String REGISTRATION_COMPLETE = "Aanmelding";

    @Override
    public void onValidationSuccess(HstRequest request, HstResponse response, ComponentConfiguration config,
            FormBean bean, Form form, FormMap map) {
        addField(map, UNIQUE_ID, UUID.randomUUID().toString());
        addField(map, REGISTRATION_COMPLETE, "false");
    }

    private void addField(FormMap map, String fieldName, String value) {
        FormField applicationId = new FormField(fieldName);
        applicationId.addValue(value);
        map.addFormField(applicationId);
    }

}
