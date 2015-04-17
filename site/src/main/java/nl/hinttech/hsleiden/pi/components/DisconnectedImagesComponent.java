// $Id: DisconnectedImagesComponent.java 422 2013-07-15 14:50:43Z rharing $
package nl.hinttech.hsleiden.pi.components;

import java.util.ArrayList;
import java.util.List;

import nl.hinttech.hsleiden.pi.beans.TextDocument;
import nl.hinttech.hsleiden.pi.util.SearchUtil;

import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.content.beans.standard.HippoGalleryImageSet;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;

/**
 * Component for rendering an overview of Images that have no documents referring to them. 
 *
 */
public class DisconnectedImagesComponent extends BaseComponent {

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);
        
        List<HippoGalleryImageSet> images = getDisconnectedImages(request);
        
        request.setAttribute("images", images);
    }

    @SuppressWarnings("unchecked")
    private List<HippoGalleryImageSet> getDisconnectedImages(HstRequest request) {
        
        List<HippoGalleryImageSet> images = new ArrayList<HippoGalleryImageSet>();
        
        try {
            HippoBean scope = getGalleryBaseBean(request);
            HstQuery query  = request.getRequestContext().getQueryManager().createQuery(scope, HippoGalleryImageSet.class);
            query.setLimit(5000);
            
            HstQueryResult result = query.execute();     
            request.setAttribute("totalNumberOfImages", result.getSize());
            
            HippoBeanIterator it = result.getHippoBeans();
            while (it.hasNext()) {
                
                HippoGalleryImageSet image = (HippoGalleryImageSet)it.nextHippoBean();
                List<TextDocument> referingDocuments = SearchUtil.findReferingDocuments(this, request, image);
                if (referingDocuments.isEmpty()) {
                    images.add(image);
                }
            }
            
            
        } catch (QueryException x) {
            log.error("Failed to get disconnected assets", x);
        }
        
        return images;
    }
    
    
}
