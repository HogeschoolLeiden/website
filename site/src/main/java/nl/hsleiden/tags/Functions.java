package nl.hsleiden.tags;

import hslbeans.ArticlePage;
import hslbeans.Image;
import hslbeans.WebPage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.hsleiden.channels.WebsiteInfo;

import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoResource;
import org.hippoecm.hst.core.component.HstRequest;

public class Functions {

    private Functions() {
    }

    public static String getDefaultBrowserTitle(HttpServletRequest request) {
        String result = "";
        HstRequest req = (HstRequest) request;
        final Mount mount = req.getRequestContext().getResolvedMount().getMount();
        final WebsiteInfo info = mount.getChannelInfo();
        result = info.getDefaultBrowserTitle();

        return result;
    }

    public static boolean isSubclassOfWebPage(HippoBean hippoBean) {
        boolean result = false;
        if (hippoBean instanceof WebPage) {
            result = true;
        }
        return result;
    }

    public static String getAssetTitle(HippoResource asset) {
        String result = asset.getParentBean().getPath();
        result = result.substring(result.lastIndexOf("/") + 1);
        return result;
    }

    public static HippoBean getFirstFlexibleBlockImage(ArticlePage bean) {
        HippoBean result = null;
        List<HippoBean> flexibleblock = bean.getFlexibleblock();
        for (HippoBean hippoBean : flexibleblock) {
            if (hippoBean instanceof Image && ((Image) hippoBean).getImage()!=null) {
                result = hippoBean;
            }
        }
        return result;
    }
}
