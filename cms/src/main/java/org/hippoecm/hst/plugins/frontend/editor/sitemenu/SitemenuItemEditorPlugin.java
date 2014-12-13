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

package org.hippoecm.hst.plugins.frontend.editor.sitemenu;

import java.io.Serializable;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.validator.RangeValidator;
import org.apache.wicket.validation.validator.UrlValidator;
import org.hippoecm.frontend.dialog.AbstractDialog;
import org.hippoecm.frontend.dialog.DialogLink;
import org.hippoecm.frontend.dialog.IDialogFactory;
import org.hippoecm.frontend.dialog.IDialogService.Dialog;
import org.hippoecm.frontend.model.JcrNodeModel;
import org.hippoecm.frontend.plugin.IPluginContext;
import org.hippoecm.frontend.plugin.config.IPluginConfig;
import org.hippoecm.hst.plugins.frontend.editor.BasicEditorPlugin;
import org.hippoecm.hst.plugins.frontend.editor.context.HstContext;
import org.hippoecm.hst.plugins.frontend.editor.dao.EditorDAO;
import org.hippoecm.hst.plugins.frontend.editor.dao.SitemenuItemDAO;
import org.hippoecm.hst.plugins.frontend.editor.dialogs.HstSitemapItemPickerDialog;
import org.hippoecm.hst.plugins.frontend.editor.domain.Parameter;
import org.hippoecm.hst.plugins.frontend.editor.domain.SitemenuItem;
import org.hippoecm.hst.plugins.frontend.editor.validators.NodeUniqueValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SitemenuItemEditorPlugin extends BasicEditorPlugin<SitemenuItem> {
    private static final long serialVersionUID = 1L;


    static final Logger log = LoggerFactory.getLogger(SitemenuItemEditorPlugin.class);

    private static final String BROWSE_LABEL = "[...]";

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public SitemenuItemEditorPlugin(final IPluginContext context, final IPluginConfig config) {
        super(context, config);

        TextField itemName = new TextField("name");
        itemName.setOutputMarkupId(true);
        itemName.setEnabled(!getLockInfo().isLocked());
        itemName.add(new NodeUniqueValidator<SitemenuItem>(this));
        form.add(itemName);

        IDialogFactory dialogFactory = new IDialogFactory() {
            private static final long serialVersionUID = 1L;

            public AbstractDialog createDialog() {
                String path = getBean().getSitemapReference();
                Model model = new Model(hstContext.sitemap.absolutePath(path));
                return new HstSitemapItemPickerDialog(context, config, model) {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void saveNode(Node node) {
                        try {
                            String siteMapItemPath = hstContext.sitemap.relativePath(node.getPath());
                            if (siteMapItemPath.contains("/hst:sitemap/")) {
                                siteMapItemPath = StringUtils.substringAfter(siteMapItemPath, "/hst:sitemap/");
                            }
                            siteMapItemPath = StringUtils.removeStart(siteMapItemPath, "/");
                            getBean().setSitemapReference(siteMapItemPath);
                            redraw();
                        } catch (RepositoryException e) {
                            log.error(e.getMessage());
                        }
                    }
                };
            }
        };

        DialogLink link = new DialogLink("path", new Model() {
            private static final long serialVersionUID = 1L;

            @Override
            public Serializable getObject() {
                return BROWSE_LABEL;
            }
        }, dialogFactory, getDialogService());
        link.setOutputMarkupId(true);
        link.setEnabled(!getLockInfo().isLocked());
        form.add(link);

        TextField sitemapReference = new TextField("sitemapReference");
        sitemapReference.setOutputMarkupId(true);
        sitemapReference.setEnabled(!getLockInfo().isLocked());
        form.add(sitemapReference);

        TextField externalLink = new TextField("externalLink");
        externalLink.setOutputMarkupId(true);

        IValidator urlValidator = null;
        String urlValidatorClassName = config.getString("url.validator", UrlValidator.class.getName());

        try {
            Class<?> urlValidatorClass = Class.forName(urlValidatorClassName);
            urlValidator = (IValidator) urlValidatorClass.newInstance();
        } catch (Exception e) {
            log.warn("Invalid url validator class: '{}'. {}", urlValidatorClassName, e);
        }

        if (urlValidator != null) {
            externalLink.add(urlValidator);
        }
        externalLink.setEnabled(!getLockInfo().isLocked());
        form.add(externalLink);


        addHstParameters();
        addHstRepositoryBasedMenuConfiguration();
        addHstMountaliasProperty();

    }

    @Override
    protected HstContext.HstModelContext.LockInfo getLockInfo() {
        return hstContext.sitemenu.getLockInfo();
    }

/*
    - hst:mountalias (string)
*/
    //TODO
    @SuppressWarnings({ "rawtypes"})
    private void addHstMountaliasProperty() {

        TextField mountalias = new TextField("mountalias");
        mountalias.setOutputMarkupId(true);
        mountalias.setEnabled(!getLockInfo().isLocked());
        form.add(mountalias);

    }
/*
    - hst:foldersonly (boolean)
    - hst:repobased (boolean)
    - hst:depth (long)
*/
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void addHstRepositoryBasedMenuConfiguration() {
        CheckBox repositoryBased = new CheckBox("repobased");
        repositoryBased.setOutputMarkupId(true);
        repositoryBased.setEnabled(!getLockInfo().isLocked());
        form.add(repositoryBased);

        CheckBox foldersOnly = new CheckBox("foldersOnly");
        foldersOnly.setOutputMarkupId(true);
        foldersOnly.setEnabled(!getLockInfo().isLocked());
        form.add(foldersOnly);

        TextField depth = new TextField("depth");
        depth.setOutputMarkupId(true);
        depth.add(new RangeValidator(0l, null));
        depth.setEnabled(!getLockInfo().isLocked());
        form.add(depth);

    }

    @SuppressWarnings("rawtypes")
    private void addHstParameters() {
        ListView containers = new ListView("parameters") {
            private static final long serialVersionUID = 1L;

            @SuppressWarnings("unchecked")
            @Override
            protected void populateItem(final ListItem item) {
                Parameter param = (Parameter) item.getModelObject();
                RequiredTextField keyField = new RequiredTextField("name", new PropertyModel(param, "name"));
                keyField.setOutputMarkupId(true);
                keyField.add(new OnChangeAjaxBehavior() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void onUpdate(AjaxRequestTarget target) {
                    }
                });
                item.add(keyField);

                TextField value = new RequiredTextField("value", new PropertyModel(param, "value"));
                value.setOutputMarkupId(true);
                value.add(new OnChangeAjaxBehavior() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void onUpdate(AjaxRequestTarget target) {
                    }
                });

                item.add(value);

                AjaxSubmitLink remove = new AjaxSubmitLink("remove") {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected void onSubmit(AjaxRequestTarget target, Form form) {
                        getBean().removeParameter(item.getIndex());
                        redraw();
                    }
                };
                remove.setDefaultFormProcessing(false);
                item.add(remove);
            }

            @Override
            public boolean isVisible() {
                return getBean().getParameters().size() > 0;
            }

        };
        containers.setOutputMarkupId(true);
        containers.setEnabled(!getLockInfo().isLocked());
        form.add(containers);

        AjaxSubmitLink addParam = new AjaxSubmitLink("addParameter") {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form form) {
                getBean().addParameter();
                redraw();
            }

        };
        addParam.setDefaultFormProcessing(false);
        addParam.setEnabled(!getLockInfo().isLocked());
        form.add(addParam);
    }

    //FIXME: This is a nasty hack
    @Override
    protected String getAddDialogTitle() {
        return new StringResourceModel("dialog.title", this, null).getString();
    }

    @Override
    protected EditorDAO<SitemenuItem> newDAO() {
        return new SitemenuItemDAO(hstContext, hstContext.sitemenu.getNamespace());
    }

    @Override
    protected Dialog newAddDialog() {
        return new AddSitemenuItemDialog(dao, this, (JcrNodeModel) getModel());
    }

}
