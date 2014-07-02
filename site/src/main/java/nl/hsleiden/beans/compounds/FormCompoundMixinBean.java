package nl.hsleiden.beans.compounds;

import hslbeans.FormCompoundMixin;
import nl.hsleiden.componentsinfo.FormComponentInfo;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoBean;

@Node(jcrType = "hsl:FormCompoundMixin")
public class FormCompoundMixinBean extends FormCompoundMixin implements FormComponentInfo {
    
    @Override
    public Boolean getUseMixin() {
       throw new UnsupportedOperationException();
    }

    @Override
    public String getContentBeanPath() {
        String result = null;
        HippoBean formBean = getFormWidgetParameters().getFormPicker();
        if(formBean!=null){
            result = formBean.getPath();
        }
        return result;
    }
    

    @Override
    public String getThanksBeanPath() {
        String result = null;
        HippoBean thanksBean = getFormWidgetParameters().getThanksPicker();
        if(thanksBean!=null){
            result = thanksBean.getPath();
        }
        return result;
    }
}
