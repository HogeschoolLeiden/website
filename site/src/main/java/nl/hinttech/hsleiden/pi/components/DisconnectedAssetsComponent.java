// $Id: DisconnectedAssetsComponent.java 422 2013-07-15 14:50:43Z rharing $
package nl.hinttech.hsleiden.pi.components;

import java.util.ArrayList;
import java.util.List;

import nl.hinttech.hsleiden.pi.beans.TextDocument;
import nl.hinttech.hsleiden.pi.util.SearchUtil;

import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoAsset;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;

/**
 * Component for rendering an overview of Assets that have no documents referring to them. 
 *
 */
public class DisconnectedAssetsComponent extends BaseComponent {

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);
        
        List<HippoAsset> assets = getDisconnectedAssets(request);
        
        request.setAttribute("assets", assets);
    }

    @SuppressWarnings("unchecked")
    private List<HippoAsset> getDisconnectedAssets(HstRequest request) {
        
        List<HippoAsset> assets = new ArrayList<HippoAsset>();
        
        try {
            HippoBean scope = getAssetBaseBean(request);
            HstQuery query  = request.getRequestContext().getQueryManager().createQuery(scope, HippoAsset.class);
            query.setLimit(5000);
            
            HstQueryResult result = query.execute();     
            request.setAttribute("totalNumberOfAssets", result.getSize());
            
            HippoBeanIterator it = result.getHippoBeans();
            while (it.hasNext()) {
                
                HippoAsset asset = (HippoAsset)it.nextHippoBean();
                List<TextDocument> referingDocuments = SearchUtil.findReferingDocuments(this, request, asset);
                if (referingDocuments.isEmpty()) {
                    assets.add(asset);
                }
            }
            
            
        } catch (QueryException x) {
            log.error("Failed to get disconnected assets", x);
        }
        
        return assets;
    }
    
    
}
