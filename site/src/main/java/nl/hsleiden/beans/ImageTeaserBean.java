package nl.hsleiden.beans;

import org.hippoecm.hst.content.beans.Node;

import hslbeans.ImageTeaser;
import nl.hsleiden.beans.interfaces.TeasersInterface;

@Node(jcrType = "hsl:ImageTeaser")
public class ImageTeaserBean extends ImageTeaser implements TeasersInterface {

}
