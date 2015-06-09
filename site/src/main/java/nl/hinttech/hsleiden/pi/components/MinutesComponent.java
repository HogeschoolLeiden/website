package nl.hinttech.hsleiden.pi.components;

import static nl.hinttech.hsleiden.pi.util.HSLeiden.PAGE_NOT_FOUND;
import nl.hinttech.hsleiden.pi.beans.Asset;
import nl.hinttech.hsleiden.pi.beans.Assets;
import nl.hinttech.hsleiden.pi.beans.Metadata;
import nl.hinttech.hsleiden.pi.beans.MinutesDocument;
import nl.hinttech.hsleiden.pi.beans.TableOfContents;
import nl.hinttech.hsleiden.pi.counters.PageViewCounter;

import org.hippoecm.hst.content.beans.standard.HippoAsset;
import org.hippoecm.hst.content.beans.standard.HippoFolderBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.util.HstResponseUtils;

/**
 * Component for rendering a {@link MinutesDocument}.
 */
public class MinutesComponent extends BaseComponent {

    @Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) throws HstComponentException {

        super.doBeforeRender(request, response);

        MinutesDocument document = request.getRequestContext().getContentBean(MinutesDocument.class);
        if (document == null) {
            HstResponseUtils.sendRedirect(request, response, PAGE_NOT_FOUND);
            return;
        }
        
        // get the metadata of the document in our Metadata object.
        Metadata metadata = document.getMetadata(this, request);
        
        // A MinutesDocument contains a link to an Asset folder.
        // Within this folder all minutes (pdf- or word documents) are organized
        // in year folders. Get all these minutes and add them to our Assets object
        // that groups the minutes by year.
        Assets assets = getAssets(request, document.getAssetFolder());
        
        request.setAttribute("document", document);
        request.setAttribute("metadata", metadata);
        request.setAttribute("assets", assets);
        
        TableOfContents toc = new TableOfContents(document.getTextBlocks());
        request.getRequestContext().setAttribute("tableOfContents", toc);
        
        PageViewCounter.getInstance(request).increment(document, request.getSession(), request);
        
        setBreadcrumb(request, document.getTitle());
    }

    private Assets getAssets(final HstRequest request, final HippoFolderBean assetFolder) {
        
        Assets assets = new Assets();
        
        if (assetFolder != null) {
            for (HippoFolderBean subFolder : assetFolder.getChildBeans(HippoFolderBean.class)) {
                String groupTitle = subFolder.getLocalizedName();
                for (HippoAsset assetBean : subFolder.getChildBeans(HippoAsset.class)) {
                    Asset asset = new Asset(assetBean);
                    assets.addAsset(groupTitle, asset);
                }
            }
        }
        
        return assets;
    }

}
