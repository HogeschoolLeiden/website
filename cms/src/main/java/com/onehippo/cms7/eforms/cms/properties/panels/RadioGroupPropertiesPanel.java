/**
 * Copyright 2009-2014 Hippo B.V. (http://www.onehippo.com)
 */

package com.onehippo.cms7.eforms.cms.properties.panels;

import javax.jcr.RepositoryException;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.StringResourceModel;
import org.hippoecm.frontend.model.properties.JcrPropertyModel;

import com.onehippo.cms7.eforms.cms.common.Constants;
import com.onehippo.cms7.eforms.cms.form.FormPlugin;
import com.onehippo.cms7.eforms.cms.model.AbstractFieldModel;
import com.onehippo.cms7.eforms.cms.model.SingleValuePropertyModel;

public class RadioGroupPropertiesPanel extends ValidatedFieldPropertiesPanel {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings({ "unchecked", "rawtypes", "serial" })
    public RadioGroupPropertiesPanel(String id, final FormPlugin formPlugin, AbstractFieldModel fieldModel) throws RepositoryException {
        super(id, formPlugin, fieldModel);
        JcrPropertyModel valuesPropertyModel = new JcrPropertyModel(fieldModel.getNodeModel().getNode()
                .getProperty(Constants.VALUES_PROP));
        MultiValuePropertyEditorPanel valuesEditorPanel = new MultiValuePropertyEditorPanel("options-panel", formPlugin,
                valuesPropertyModel);
        add(valuesEditorPanel);

        Label lengthLabel = new Label("length", new StringResourceModel("field.property.length", this, null));

        TextField lengthField = new TextField("length-field", new SingleValuePropertyModel(fieldModel.getNodeModel(),
                Constants.LENGTH_PROP));

        lengthField.add(new OnChangeAjaxBehavior() {

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                target.add(formPlugin);
            }
        });

        add(lengthLabel);
        add(lengthField);

        Label minlengthLabel = new Label("minlength", new StringResourceModel("field.property.minlength", this, null));

        TextField minlengthField = new TextField("minlength-field", new SingleValuePropertyModel(fieldModel
                .getNodeModel(), Constants.MIN_LENGTH_PROP));

        minlengthField.add(new OnChangeAjaxBehavior() {

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                target.add(formPlugin);
            }
        });

        add(minlengthLabel);
        add(minlengthField);

        Label maxlengthLabel = new Label("maxlength", new StringResourceModel("field.property.maxlength", this, null));

        TextField maxlengthField = new TextField("maxlength-field", new SingleValuePropertyModel(fieldModel
                .getNodeModel(), Constants.MAX_LENGTH_PROP));

        maxlengthField.add(new OnChangeAjaxBehavior() {

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                target.add(formPlugin);
            }
        });

        add(maxlengthLabel);
        add(maxlengthField);
        
/**
 *          ALLOW OTHER FOR RADIOS DO NOT LET THE FORM TO THE BE SAVED - REMOVING IT 
 *          
//         Label allowOtherLabel = new Label("allowOtherLabel", new StringResourceModel("field.property.allowOtherLabel",
//
//               this, null));
//
//        CheckBox allowOtherField = new CheckBox("allowOtherField",
//                new SingleValuePropertyModel(fieldModel.getNodeModel(), Constants.ALLOWOTHER_PROP));
//        allowOtherField.add(new OnChangeAjaxBehavior() {
//            @Override
//            protected void onUpdate(AjaxRequestTarget target) {
//                target.add(formPlugin);
//            }
//        });
//
//        add(allowOtherLabel);
//        add(allowOtherField);
 * */
    }
}
