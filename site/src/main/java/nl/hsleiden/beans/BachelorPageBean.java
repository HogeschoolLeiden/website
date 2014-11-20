package nl.hsleiden.beans;

import java.util.ArrayList;
import java.util.List;

import org.hippoecm.hst.content.beans.Node;

import com.tdclighthouse.prototype.beans.compounds.SelectionBean;
import com.tdclighthouse.prototype.beans.compounds.SelectionItemBean;

import hslbeans.BachelorPage;

@Node(jcrType = "hsl:BachelorPage")
public class BachelorPageBean extends BachelorPage {

    public List<String> getEducationtagsLabels() {
        SelectionBean sb = getSelectionBean("hsl:educationtags",
                "/content/documents/hsl/configuratie/valuelists/opleidingen-values");
        return getLabels(sb);
    }

    public List<String> getFormtagLabels() {
        SelectionBean sb = getSelectionBean("hsl:formtag",
                "content/documents/hsl/configuratie/valuelists/opleidingen-vorm-values");
        return getLabels(sb);
    }

    private List<String> getLabels(SelectionBean selectionBean) {
        List<String> result = new ArrayList<String>();
        for (SelectionItemBean sib : selectionBean.getItems()) {
            result.add(sib.getLabel());
        }
        return result;
    }
}
