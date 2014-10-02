package nl.hsleiden.componentsinfo;

import org.hippoecm.hst.core.parameters.Parameter;

public interface DisqusInfo {

    @Parameter(name = "forumShortname", displayName = "Disqus forum korte naam", required = true)
    public String getForumShortname();

}
