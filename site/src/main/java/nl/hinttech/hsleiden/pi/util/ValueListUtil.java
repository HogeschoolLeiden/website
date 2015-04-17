package nl.hinttech.hsleiden.pi.util;

import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.site.HstServices;
import org.onehippo.forge.selection.hst.contentbean.ValueList;
import org.onehippo.forge.selection.hst.contentbean.ValueListItem;
import org.onehippo.forge.selection.hst.manager.ValueListManager;

public class ValueListUtil {

	public static String getLabelForKey(String key, ValueList valueList) {
		for (ValueListItem item : valueList.getItems()) {
			if (key.equals(item.getKey())) {
				return item.getLabel();
			}
		}
		return key;
	}

	public static String getKeyForLabel(String label, ValueList valueList) {
		for (ValueListItem item : valueList.getItems()) {
			if (label.equals(item.getLabel())) {
				return item.getKey();
			}
		}
		return label;
	}

	public static ValueList getValueList(BaseHstComponent component,
			HstRequest request, String valueListName) {

		ValueListManager valueListManager = HstServices.getComponentManager()
				.getComponent(ValueListManager.class.getName());
		HippoBean contentRoot = request.getRequestContext()
				.getSiteContentBaseBean();

		ValueList valueList = valueListManager.getValueList(contentRoot,
				valueListName);

		return valueList;
	}
}
