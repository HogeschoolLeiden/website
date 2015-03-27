package nl.hsleiden.beans.interfaces;

import nl.hsleiden.beans.compounds.ExternalLinkBean;
import nl.hsleiden.beans.compounds.InternalLinkBean;

public interface TeasersInterface {

    public InternalLinkBean getLink();
    public ExternalLinkBean getExternallink();
}
