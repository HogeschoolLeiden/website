package nl.hsleiden.utils;

import java.util.List;

import nl.hsleiden.utils.Constants.NodeName;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoDocumentBean;
import org.hippoecm.hst.content.beans.standard.HippoFolderBean;
import org.hippoecm.hst.content.beans.standard.facetnavigation.HippoFacetNavigation;
import org.hippoecm.hst.core.sitemenu.EditableMenu;
import org.hippoecm.hst.core.sitemenu.EditableMenuItem;

public class NavigationUtils {

    private NavigationUtils() {
    }

    public static HippoBean getIndexBean(HippoBean childbeering) {
        HippoBean result;
        if (childbeering instanceof HippoFacetNavigation) {
            result = childbeering;
        } else if (childbeering instanceof HippoFolderBean) {
            HippoFolderBean folder = (HippoFolderBean) childbeering;
            result = folder.getBean(NodeName.INDEX);
            // when there is no index document in a folder then the first
            // document is selected instead
            if (result == null) {
                List<HippoDocumentBean> documents = folder.getDocuments();
                result = (!documents.isEmpty() ? documents.get(0) : null);
            }
        } else {
            throw new IllegalArgumentException(
                    "Expect childbearingBean to be either a HippoFolderBean or a HippoFacetNavigation");
        }
        return result;
    }

    public static EditableMenuItem getSiteMapItemByPath(EditableMenu editableMenu, String relativePath) {
        EditableMenuItem result = null;
        String normalizedPath = normalizePath(relativePath);
        String[] split = normalizedPath.split("/");
        List<EditableMenuItem> menuItems = editableMenu.getMenuItems();
        EditableMenuItem temp = result;
        for (String segment : split) {
            for (EditableMenuItem editable : menuItems) {
                if (editable.getName().equals(segment)) {
                    temp = editable;
                    menuItems = editable.getChildMenuItems();
                    break;
                }
            }
            if (temp == result) {
                break;
            } else {
                result = temp;
            }
        }
        return result;
    }

    private static String normalizePath(String relativePath) {
        String result = relativePath;
        if (result.startsWith("/")) {
            result = result.substring(1);
        }
        return result;
    }
}
