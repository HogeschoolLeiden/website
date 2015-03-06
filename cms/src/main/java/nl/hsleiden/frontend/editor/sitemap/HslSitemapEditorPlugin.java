package nl.hsleiden.frontend.editor.sitemap;

import java.util.Arrays;
import java.util.List;

import nl.hsleiden.frontend.editor.dao.HslSitemapItemDAO;

import org.hippoecm.frontend.plugin.IPluginContext;
import org.hippoecm.frontend.plugin.config.IPluginConfig;
import org.hippoecm.hst.plugins.frontend.editor.dao.EditorDAO;
import org.hippoecm.hst.plugins.frontend.editor.domain.SitemapItem;
import org.hippoecm.hst.plugins.frontend.editor.sitemap.SitemapEditorPlugin;

public class HslSitemapEditorPlugin extends SitemapEditorPlugin{

    private static final long serialVersionUID = 1L;

    public HslSitemapEditorPlugin(IPluginContext context, IPluginConfig config) {
        super(context, config);
    }
    
    @Override
    protected EditorDAO<SitemapItem> newDAO() {
        List<String> namesList = Arrays.asList(getPluginConfig().getStringArray(HslSitemapItemEditorPlugin.TYPE_NAMES_PARAM));
        List<String> valuesList = Arrays.asList(getPluginConfig().getStringArray(HslSitemapItemEditorPlugin.TYPE_VALUES_PARAM));
        return new HslSitemapItemDAO(hstContext, hstContext.sitemap.getNamespace(), namesList, valuesList);
    }
}
