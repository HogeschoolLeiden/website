package nl.hsleiden.componentsinfo;

import org.hippoecm.hst.core.parameters.FieldGroup;
import org.hippoecm.hst.core.parameters.FieldGroupList;
import org.onehippo.cms7.essentials.components.info.EssentialsBlogAuthorPostsComponentInfo;

@FieldGroupList({ @FieldGroup(titleKey = "fields.widget", value = { "postScope",
        "pageSize", "sortField" }) })
public interface BlogAuthorPostsInfo extends EssentialsBlogAuthorPostsComponentInfo {

}
