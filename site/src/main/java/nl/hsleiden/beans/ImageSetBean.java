package nl.hsleiden.beans;

import org.hippoecm.hst.content.beans.Node;

import nl.hsleiden.utils.Constants.Values;
import hslbeans.ImageSet;

@Node(jcrType = "hsl:ImageSet")
public class ImageSetBean extends ImageSet {
	
	public String getAlt() {
	    String result = super.getAlt();
		if (result==null || result.isEmpty()){
	    	result = Values.DEFAULT_IMAGE_ALT;
	    }
	    return result;
	}

}
