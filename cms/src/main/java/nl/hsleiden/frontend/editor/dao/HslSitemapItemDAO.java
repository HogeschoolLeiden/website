package nl.hsleiden.frontend.editor.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.hsleiden.frontend.editor.domain.HslSitemapItem;

import org.hippoecm.frontend.model.JcrNodeModel;
import org.hippoecm.hst.plugins.frontend.editor.context.HstContext;
import org.hippoecm.hst.plugins.frontend.editor.dao.SitemapItemDAO;
import org.hippoecm.hst.plugins.frontend.editor.domain.Parameter;
import org.hippoecm.hst.plugins.frontend.editor.domain.SitemapItem;
import org.hippoecm.hst.plugins.frontend.util.JcrUtilities;

public class HslSitemapItemDAO extends SitemapItemDAO {
    private static final long serialVersionUID = 1L;
    private static final String HST_COMP_CONFIG_NAMES = "hst:componentconfigurationmappingnames";
    private static final String HST_COMP_CONFIG_VALUES = "hst:componentconfigurationmappingvalues";

    public HslSitemapItemDAO(HstContext context, String namespace) {
        super(context, namespace);
    }

    @Override
    public SitemapItem load(JcrNodeModel model) {

        SitemapItem item = super.load(model);
        
        HslSitemapItem myItem = new HslSitemapItem(model);
        
        myItem.setContentPath(item.getContentPath());
        myItem.setMatcher(item.getMatcher());
        myItem.setPage(item.getPage());
        myItem.setParameters(item.getParameters());
        
        setComponentConfigurationMapping(model, myItem);

        return myItem;
    }

    private void setComponentConfigurationMapping(JcrNodeModel model, HslSitemapItem item) {

        HslSitemapItem myItem = (HslSitemapItem) item;
        if (JcrUtilities.hasProperty(model, HST_COMP_CONFIG_NAMES)) {
            List<String> names = JcrUtilities.getMultiValueProperty(model, HST_COMP_CONFIG_NAMES);
            if (names != null && !names.isEmpty()) {
                String[] values = new String[names.size()];
                if (JcrUtilities.hasProperty(model, HST_COMP_CONFIG_VALUES)) {
                    values = JcrUtilities.getMultiValueProperty(model, HST_COMP_CONFIG_VALUES).toArray(values);
                } else {
                    Arrays.fill(values, "");
                }
                for (int i = 0; i < names.size(); i++) {
                    myItem.addConfigMapping(names.get(i), values[i]);
                }
            }
        }
    }

    @Override
    protected void persist(SitemapItem k, JcrNodeModel model) {

        super.persist(k, model);
        
        if (k instanceof HslSitemapItem) {
            HslSitemapItem myItem = (HslSitemapItem) k;

            List<String> mappingNames = new ArrayList<String>();
            List<String> mappingValues = new ArrayList<String>();
            for (Parameter param : myItem.getConfigMappings()) {
                mappingNames.add(param.getName());
                mappingValues.add(param.getValue());
            }

            JcrUtilities.updateMultiValueProperty(model, HST_COMP_CONFIG_NAMES, mappingNames);
            JcrUtilities.updateMultiValueProperty(model, HST_COMP_CONFIG_VALUES, mappingValues);
        }
    }
}
