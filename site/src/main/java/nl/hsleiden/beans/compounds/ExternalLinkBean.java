package nl.hsleiden.beans.compounds;

import hslbeans.ExternalLink;
import nl.hsleiden.beans.interfaces.LinkInterface;

import org.hippoecm.hst.content.beans.Node;

@Node(jcrType = "hsl:ExternalLink")
public class ExternalLinkBean extends ExternalLink implements LinkInterface {

}
