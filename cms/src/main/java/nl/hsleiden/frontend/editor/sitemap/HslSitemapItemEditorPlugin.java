package nl.hsleiden.frontend.editor.sitemap;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nl.hsleiden.frontend.editor.dao.HslSitemapItemDAO;
import nl.hsleiden.frontend.editor.domain.HslSitemapItem;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.hippoecm.frontend.plugin.IPluginContext;
import org.hippoecm.frontend.plugin.config.IPluginConfig;
import org.hippoecm.hst.plugins.frontend.editor.dao.EditorDAO;
import org.hippoecm.hst.plugins.frontend.editor.domain.Parameter;
import org.hippoecm.hst.plugins.frontend.editor.domain.SitemapItem;
import org.hippoecm.hst.plugins.frontend.editor.sitemap.SitemapItemEditorPlugin;

public class HslSitemapItemEditorPlugin extends SitemapItemEditorPlugin {

    protected static final String TYPE_VALUES_PARAM = "typeValues";
    protected static final String TYPE_NAMES_PARAM = "typeNames";

    private static final long serialVersionUID = 1L;

    private static final String VALUE = "value";
    private static final String NAME = "name";
    private static final String REMOVE = "remove";

    private List<String> translatedDocTypeNames;

    public HslSitemapItemEditorPlugin(final IPluginContext context, final IPluginConfig config) {
        super(context, config);
        this.translatedDocTypeNames = Arrays.asList(config.getStringArray(TYPE_VALUES_PARAM));
        addComponentConfigMappings();
    }

    @Override
    protected EditorDAO<SitemapItem> newDAO() {
        List<String> namesList = Arrays.asList(getPluginConfig().getStringArray(TYPE_NAMES_PARAM));
        List<String> valuesList = Arrays.asList(getPluginConfig().getStringArray(TYPE_VALUES_PARAM));
        return new HslSitemapItemDAO(hstContext, hstContext.sitemap.getNamespace(), namesList, valuesList);
    }

    @SuppressWarnings({ "rawtypes" })
    private void addComponentConfigMappings() {
        ListView containers = new ListViewConcreteClass("configMappings");
        containers.setOutputMarkupId(true);
        containers.setEnabled(!getLockInfo().isLocked());
        form.add(containers);

        AjaxSubmitLink addParam = new AjaxSubmitLink("addConfigMapping") {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form form) {
                SitemapItem bean = getBean();
                if (bean instanceof HslSitemapItem) {
                    ((HslSitemapItem) bean).addConfigMapping();
                }
                redraw();
            }

        };
        addParam.setDefaultFormProcessing(false);
        addParam.setEnabled(!getLockInfo().isLocked());
        form.add(addParam);
    }

    public List<String> getTranslatedDocTypeNames() {
        return translatedDocTypeNames;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private class ListViewConcreteClass extends ListView {
        private static final long serialVersionUID = 1L;

        public ListViewConcreteClass(String id) {
            super(id);
        }

        @Override
        protected void populateItem(final ListItem item) {
            Parameter param = (Parameter) item.getModelObject();

            DropDownChoice<String> name = getDropDownChoiceField(param, getTranslatedDocTypeNames(), NAME);
            item.add(name);

            List<String> pages = hstContext.page.getReferenceables();
            Collections.sort(pages);

            DropDownChoice<String> value = getDropDownChoiceField(param, pages, VALUE);
            item.add(value);

            AjaxSubmitLink remove = new AjaxSubmitLink(REMOVE) {
                private static final long serialVersionUID = 1L;

                @Override
                protected void onSubmit(AjaxRequestTarget target, Form form) {
                    SitemapItem bean = getBean();
                    if (bean instanceof HslSitemapItem) {
                        ((HslSitemapItem) bean).removeConfigMapping(item.getIndex());
                    }
                    redraw();
                }
            };
            remove.setDefaultFormProcessing(false);
            item.add(remove);
        }

        private DropDownChoice<String> getDropDownChoiceField(Parameter param, List<String> options, String fieldName) {

            DropDownChoice<String> ddcField = new DropDownChoice(fieldName, new PropertyModel(param, fieldName),
                    options);

            ddcField.setNullValid(false);
            ddcField.setRequired(true);
            ddcField.setOutputMarkupId(true);
            ddcField.add(new OnChangeAjaxBehavior() {
                private static final long serialVersionUID = 1L;

                @Override
                protected void onUpdate(AjaxRequestTarget target) {
                    // This method is empty because I found it this way
                }
            });
            return ddcField;
        }

        @Override
        public boolean isVisible() {
            SitemapItem bean = getBean();
            if (bean instanceof HslSitemapItem) {
                return !((HslSitemapItem) bean).getConfigMappings().isEmpty();
            } else {
                return false;
            }
        }
    }
}
