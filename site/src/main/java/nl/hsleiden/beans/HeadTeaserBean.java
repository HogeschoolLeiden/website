package nl.hsleiden.beans;

import org.hippoecm.hst.content.beans.Node;

import hslbeans.HeadTeaser;
import nl.hsleiden.beans.interfaces.TeasersInterface;

@Node(jcrType = "hsl:HeadTeaser")
public class HeadTeaserBean extends HeadTeaser implements TeasersInterface {

}
