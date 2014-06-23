package nl.hsleiden.beans.compounds;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoMirror;

@Node(jcrType = "hsl:RelatedWidgetParameters")
public class RelatedWidgetParametersBean extends hslbeans.RelatedWidgetParameters {
	
	private HippoBean contentBeanPath;

	@Override
	public HippoBean getContentBeanPath() {
		if (this.contentBeanPath == null) {
			this.contentBeanPath = this.<HippoMirror>getBean("hsl:contentBeanPath").getReferencedBean(); 
		}
		return this.contentBeanPath;
	}

}
