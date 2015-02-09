package nl.hsleiden.utils;

import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoFacetNavigationBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdclighthouse.prototype.utils.Constants.HippoFacetAttributesConstants;

// to be deleted after facet bug is fixed 
// getFacetDocumentType() method is not bullet proof

public class FacetsUtils {

    private static final String HIPPOFACNAV_FILTERS = "hippofacnav:filters";
    private static final String JCR_PRIMARY_TYPE_EQ = "jcr:primaryType=";
    
    private static final Logger LOG = LoggerFactory.getLogger(FacetsUtils.class);

    private FacetsUtils() {
        super();
    }
    
    public static String getFacetDocumentType(HippoFacetNavigationBean facetBean){
        String result = "";
        HippoFacetNavigationBean rootFacetBean = facetBean.getRootFacetNavigationBean();
        
        Object facetProp = rootFacetBean.getProperty(HIPPOFACNAV_FILTERS);
        if(facetProp instanceof String[]){
            String[] facetFilterProperties = (String[])facetProp;
            for (String filterProperty : facetFilterProperties) {
                result = getJcrPrimaryType(filterProperty);
                if(!result.isEmpty()){
                    break;
                }
            }
        }else if(facetProp instanceof String){
            String filterProperty = (String) facetProp;
            result = getJcrPrimaryType(filterProperty);
        }
        
        return result;
    }
    
    public static HippoBean getFacetScope(HippoFacetNavigationBean facetBean){
        
        HippoBean result = null;
        HippoFacetNavigationBean rootFacetBean = facetBean.getRootFacetNavigationBean();
        String docBase = (String) rootFacetBean.getProperty(HippoFacetAttributesConstants.HIPPO_DOCBASE);
        
        try {
            Object object = rootFacetBean.getObjectConverter().getObject(docBase, rootFacetBean.getNode());
            if(object instanceof HippoBean){
                result = (HippoBean) object;
            }
        } catch (ObjectBeanManagerException e) {
           LOG.error("ERROR retrieving facet doc base", e);
        }
        
        return result;
    }

    private static String getJcrPrimaryType(String property) {
        String result="";
        String filterProperty = property.replaceAll(" ", "");
        if(filterProperty.startsWith(JCR_PRIMARY_TYPE_EQ)){
            result = filterProperty.substring(filterProperty.indexOf(JCR_PRIMARY_TYPE_EQ) + JCR_PRIMARY_TYPE_EQ.length());
        }
        return result;
    }
}
