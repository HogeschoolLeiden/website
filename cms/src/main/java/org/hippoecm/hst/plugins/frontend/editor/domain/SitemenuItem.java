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

package org.hippoecm.hst.plugins.frontend.editor.domain;

import java.util.ArrayList;
import java.util.List;

import org.hippoecm.frontend.model.JcrNodeModel;

/**
 * The SitemenuItem class represents a domain object of the hst:sitemenuitem
 * node.
 */
public class SitemenuItem extends EditorBean {

    private static final long serialVersionUID = 1L;

    String name;
    String mountalias;
    String sitemapReference;
    String externalLink;

    Long depth;

    boolean repobased;

    boolean foldersOnly;
    List<Parameter> parameters;

    /**
     * Instantiates a new sitemenu item.
     * 
     * @param model
     *            the jcr node model
     */
    public SitemenuItem(JcrNodeModel model) {
        super(model);
        parameters = new ArrayList<Parameter>();
    }

    /**
     * Gets the mountAlias of the sitemenu item.
     * 
     * @return the mountAlias of the sitemenu item
     */
    public String getMountalias() {
        return mountalias;
    }

    /**
     * Sets the mountAlias of the sitemenu item.
     * 
     * @param mountAlias
     *            the mountAlias of the sitemenu item
     */
    public void setMountalias(String mountalias) {
        this.mountalias = mountalias;
    }

    /**
     * Gets the name of the sitemenu item.
     * 
     * @return the name of the sitemenu item
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the sitemenu item.
     * 
     * @param name
     *            the name of the sitemenu item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the sitemap reference.
     * 
     * @return the sitemap reference
     */
    public String getSitemapReference() {
        return sitemapReference;
    }

    /**
     * Sets the sitemap reference.
     * 
     * @param sitemapReference
     *            the new sitemap reference
     */
    public void setSitemapReference(String sitemapReference) {
        this.sitemapReference = sitemapReference;
    }

    /**
     * Gets the external link.
     * 
     * @return the external link
     */
    public String getExternalLink() {
        return externalLink;
    }

    /**
     * Sets the external link.
     * 
     * @param externalLink
     *            the external link
     */
    public void setExternalLink(String externalLink) {
        this.externalLink = externalLink;
    }

    /**
     * Returns the 'hst:parameternames and values'
     *
     * @return a list of {@link Parameter}s
     */
    public List<Parameter> getParameters() {
        return parameters;
    }

    /**
     * Sets a the list of parameters.
     * 
     * @param parameters
     *            a list of {@link Parameter}
     */
    public void setParameters(final List<Parameter> parameters) {
        this.parameters = parameters;
    }

    /**
     * Add an empty parameter to the list of hst parameters
     */
    public void addParameter() {
        parameters.add(new Parameter());
    }

    /**
     * Add a parameter to the list of hst parameters
     * 
     * @param name
     *            the name of the parameter
     * @param value
     *            the value of the parameter
     */
    public void addParameter(String name, String value) {
        Parameter p = new Parameter();
        p.setName(name);
        p.setValue(value);
        parameters.add(p);
    }

    /**
     * Removes a parameter based on the index position in the list
     * 
     * @param index
     *            the index inside the list of parameters
     */
    public void removeParameter(int index) {
        parameters.remove(index);
    }

    /**
     * Gets the max depth for the repository based menu items
     * 
     * @return the depth
     */
    public Long getDepth() {
        return depth;
    }

    /**
     * Sets the max depth for repository based menu items
     * 
     * @param depth
     *            the depth
     */
    public void setDepth(final Long depth) {
        this.depth = depth;
    }

    public boolean isRepobased() {
        return repobased;
    }

    public void setRepobased(final boolean repobased) {
        this.repobased = repobased;
    }

    public boolean isFoldersOnly() {
        return foldersOnly;
    }

    public void setFoldersOnly(final boolean foldersOnly) {
        this.foldersOnly = foldersOnly;
    }
}
