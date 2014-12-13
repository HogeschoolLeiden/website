/*
 *  Copyright 2008-2014 Hippo B.V. (http://www.onehippo.com)
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

package org.hippoecm.hst.plugins.frontend.editor.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jcr.RepositoryException;

import org.hippoecm.frontend.model.JcrNodeModel;
import org.hippoecm.hst.plugins.frontend.editor.context.HstContext;
import org.hippoecm.hst.plugins.frontend.editor.domain.Parameter;
import org.hippoecm.hst.plugins.frontend.editor.domain.SitemenuItem;
import org.hippoecm.hst.plugins.frontend.util.JcrUtilities;
import org.hippoecm.repository.api.NodeNameCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DAO of the SitemenuItem
 */
public class SitemenuItemDAO extends EditorDAO<SitemenuItem> {

    private static final long serialVersionUID = 1L;


    private static final Logger LOG = LoggerFactory.getLogger(SitemenuItemDAO.class);

    /** SITEMAP_PROPERTY represents the jcr propertyname of the referenced sitemap item. */
    private static final String SITEMAP_PROPERTY = "hst:referencesitemapitem";

    /** EXTERNAL_URL_PROPERTY represents the jcr propertyname of the external url. */
    private static final String EXTERNAL_URL_PROPERTY = "hst:externallink";

    private static final String HST_PARAMETERVALUES = "hst:parametervalues";
    private static final String HST_PARAMETERNAMES = "hst:parameternames";
    private static final String HST_DEPTH = "hst:depth";
    private static final String HST_REPOS_BASED = "hst:repobased";
    private static final String HST_FOLDERS_ONLY = "hst:foldersonly";

    private static final String HST_MOUNT_ALIAS = "hst:mountalias";

    /**
     * Instantiates a new sitemenu item dao.
     * 
     * @param context the context
     * @param namespace the config
     */
    public SitemenuItemDAO(HstContext context, String namespace) {
        super(context, namespace);
    }

    /* (non-Javadoc)
     * @see org.hippoecm.hst.plugins.frontend.editor.dao.EditorDAO#load(org.hippoecm.frontend.model.JcrNodeModel)
     */
    @Override
    public SitemenuItem load(JcrNodeModel model) {
        SitemenuItem item = new SitemenuItem(model);

        //Set name value
        try {
            String nodeName = NodeNameCodec.decode(model.getNode().getName());
            item.setName(nodeName);
        } catch (RepositoryException e) {
            LOG.error("Error setting matcher value", e);
        }

        //set referenced sitemap value
        if (JcrUtilities.hasProperty(model, SITEMAP_PROPERTY)) {
            String ref = JcrUtilities.getProperty(model, SITEMAP_PROPERTY);
            if(ref != null) {
                ref = NodeNameCodec.decode(ref);
            }
            item.setSitemapReference(ref);
        }
        if (JcrUtilities.hasProperty(model, EXTERNAL_URL_PROPERTY)) {
            item.setExternalLink(JcrUtilities.getProperty(model, EXTERNAL_URL_PROPERTY));
        }

        if (JcrUtilities.hasProperty(model, HST_REPOS_BASED)) {
            item.setRepobased(Boolean.valueOf(JcrUtilities.getProperty(model, HST_REPOS_BASED)));
        }
        
        if (JcrUtilities.hasProperty(model,HST_FOLDERS_ONLY)) {
            item.setFoldersOnly(Boolean.valueOf(JcrUtilities.getProperty(model, HST_FOLDERS_ONLY)));
        }
        
        if (JcrUtilities.hasProperty(model,HST_MOUNT_ALIAS)) {
            item.setMountalias(JcrUtilities.getProperty(model,HST_MOUNT_ALIAS));
        }

        if (JcrUtilities.hasProperty(model,HST_DEPTH)) {
            item.setDepth(Long.valueOf(JcrUtilities.getProperty(model,HST_DEPTH)));
        }

        if (JcrUtilities.hasProperty(model, HST_PARAMETERNAMES)) {
            List<String> names = JcrUtilities.getMultiValueProperty(model, HST_PARAMETERNAMES);
            if (names != null && !names.isEmpty()) {
                String[] values = new String[names.size()];
                if (JcrUtilities.hasProperty(model, HST_PARAMETERVALUES)) {
                    values = JcrUtilities.getMultiValueProperty(model, HST_PARAMETERVALUES).toArray(values);
                } else {
                    Arrays.fill(values, "");
                }
                for (int i = 0; i < names.size(); i++) {
                    item.addParameter(names.get(i), values[i]);
                }
            }
        }

        return item;
    }

    /* (non-Javadoc)
     * @see org.hippoecm.hst.plugins.frontend.editor.dao.EditorDAO#persist(org.hippoecm.hst.plugins.frontend.editor.domain.EditorBean, org.hippoecm.frontend.model.JcrNodeModel)
     */
    @Override
    protected void persist(SitemenuItem item, JcrNodeModel model) {
        //Set matcher value as nodeName if it's not already the same
        String newName = item.getName();
        try {
            if (!item.getName().equals(model.getNode().getName())) {
                item.setModel(JcrUtilities.rename(model, newName));
            }
        } catch (RepositoryException e) {
            LOG.warn("Exception occurred while trying to get name from model: ", e);
        }

        //save sitemapReference
        JcrUtilities.updateProperty(model, SITEMAP_PROPERTY, JcrUtilities.encodePath(item.getSitemapReference()));
        
        //save externalLink
        JcrUtilities.updateProperty(model, EXTERNAL_URL_PROPERTY, item.getExternalLink());

        if(item.getDepth()!=null) {
            JcrUtilities.updateProperty(model, HST_DEPTH, item.getDepth());
        }

        JcrUtilities.updateProperty(model, HST_MOUNT_ALIAS, item.getMountalias());

        JcrUtilities.updateProperty(model, HST_REPOS_BASED, item.isRepobased());

        JcrUtilities.updateProperty(model, HST_FOLDERS_ONLY, item.isFoldersOnly());

        //update parameters
        //- hst:parameternames (string) multiple
        //- hst:parametervalues (string) multiple
        List<String> names = new ArrayList<String>();
        List<String> values = new ArrayList<String>();
        for (Parameter param : item.getParameters()) {
            names.add(param.getName());
            values.add(param.getValue());
        }
        JcrUtilities.updateMultiValueProperty(model, HST_PARAMETERNAMES, names);
        JcrUtilities.updateMultiValueProperty(model, HST_PARAMETERVALUES, values);

    }

}
