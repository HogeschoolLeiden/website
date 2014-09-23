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

    @Override
    public void onValidationSuccess(HstRequest request, HstResponse response, ComponentConfiguration config,
            FormBean bean, Form form, FormMap map) {
        FormField applicationId = new FormField(UNIQUE_ID);
        applicationId.addValue(UUID.randomUUID().toString());
        map.addFormField(applicationId);
    }

}
