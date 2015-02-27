/*
 *  Copyright 2008-2013 Hippo B.V. (http://www.onehippo.com)
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package nl.hsleiden.frontend.editor.sitemap;

import nl.hsleiden.frontend.editor.dao.HslSitemenuItemDAO;

import org.apache.wicket.markup.html.form.TextField;
import org.hippoecm.frontend.plugin.IPluginContext;
import org.hippoecm.frontend.plugin.config.IPluginConfig;
import org.hippoecm.hst.plugins.frontend.editor.dao.EditorDAO;
import org.hippoecm.hst.plugins.frontend.editor.domain.SitemenuItem;
import org.hippoecm.hst.plugins.frontend.editor.sitemenu.SitemenuItemEditorPlugin;

public class HslSitemenuItemEditorPlugin extends SitemenuItemEditorPlugin {
    
    private static final long serialVersionUID = 1L;

    public HslSitemenuItemEditorPlugin(final IPluginContext context, final IPluginConfig config) {
        super(context, config);
        addHstMountaliasProperty();
    }

    @SuppressWarnings({ "rawtypes"})
    private void addHstMountaliasProperty() {

        TextField mountalias = new TextField("mountalias");
        mountalias.setOutputMarkupId(true);
        mountalias.setEnabled(!getLockInfo().isLocked());
        form.add(mountalias);

    }

    @Override
    protected EditorDAO<SitemenuItem> newDAO() {
        return new HslSitemenuItemDAO(hstContext, hstContext.sitemenu.getNamespace());
    }
}
