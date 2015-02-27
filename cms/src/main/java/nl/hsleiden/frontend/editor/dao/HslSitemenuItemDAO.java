package nl.hsleiden.frontend.editor.dao;

import nl.hsleiden.frontend.editor.domain.HslSitemenuItem;

import org.hippoecm.frontend.model.JcrNodeModel;
import org.hippoecm.hst.plugins.frontend.editor.context.HstContext;
import org.hippoecm.hst.plugins.frontend.editor.dao.SitemenuItemDAO;
import org.hippoecm.hst.plugins.frontend.editor.domain.SitemenuItem;
import org.hippoecm.hst.plugins.frontend.util.JcrUtilities;

public class HslSitemenuItemDAO extends SitemenuItemDAO {

    private static final long serialVersionUID = 1L;
    private static final String HST_MOUNT_ALIAS = "hst:mountalias";

    public HslSitemenuItemDAO(HstContext context, String namespace) {
        super(context, namespace);
    }

    @Override
    public SitemenuItem load(JcrNodeModel model) {
        
        SitemenuItem item = super.load(model);
        HslSitemenuItem myItem = new HslSitemenuItem(model);

        myItem.setDepth(item.getDepth());
        myItem.setExternalLink(item.getExternalLink());
        myItem.setFoldersOnly(item.isFoldersOnly());
        myItem.setModel(item.getModel());
        myItem.setName(item.getName());
        myItem.setParameters(item.getParameters());
        myItem.setRepobased(item.isRepobased());
        myItem.setSitemapReference(item.getSitemapReference());        
        
        if (JcrUtilities.hasProperty(model,HST_MOUNT_ALIAS)) {
            myItem.setMountalias(JcrUtilities.getProperty(model,HST_MOUNT_ALIAS));
        }

        return myItem;
    }

    @Override
    protected void persist(SitemenuItem item, JcrNodeModel model) {
        super.persist(item, model);
        if(item instanceof HslSitemenuItem){            
            JcrUtilities.updateProperty(model, HST_MOUNT_ALIAS, ((HslSitemenuItem) item).getMountalias());
        }
    }
}
