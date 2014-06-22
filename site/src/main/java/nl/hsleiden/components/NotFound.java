package nl.hsleiden.components;

import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;

public class NotFound extends BaseHstComponent {

    @Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) {
        response.setStatus(404);
    }
}
