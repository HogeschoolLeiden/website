package nl.hsleiden.beans.compounds;

import hslbeans.FormCompoundMixin;
import nl.hsleiden.componentsinfo.FormComponentInfo;

import org.hippoecm.hst.content.beans.Node;

@Node(jcrType = "hsl:FormCompoundMixin")
public class FormCompoundMixinBean extends FormCompoundMixin implements FormComponentInfo {
    
    @Override
    public Boolean getUseMixin() {
       throw new UnsupportedOperationException();
    }

    @Override
    public String getContentBeanPath() {
        String result = null;
        if(getFormPicker()!=null){
            result = getFormPicker().getPath();
        }
        return result;
    }
}
