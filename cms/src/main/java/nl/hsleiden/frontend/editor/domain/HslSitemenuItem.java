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

package nl.hsleiden.frontend.editor.domain;

import org.hippoecm.frontend.model.JcrNodeModel;
import org.hippoecm.hst.plugins.frontend.editor.domain.SitemenuItem;

/**
 * The SitemenuItem class represents a domain object of the hst:sitemenuitem
 * node.
 */
public class HslSitemenuItem extends SitemenuItem {

    private static final long serialVersionUID = 1L;

    String mountalias;

    public HslSitemenuItem(JcrNodeModel model) {
        super(model);
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

}
