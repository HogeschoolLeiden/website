package nl.hsleiden.beans;

import org.hippoecm.hst.content.beans.Node;

import nl.hsleiden.beans.interfaces.TeasersInterface;
import hslbeans.TextTeaser;

@Node(jcrType = "hsl:TextTeaser")
public class TextTeaserBean extends TextTeaser implements TeasersInterface {

}
