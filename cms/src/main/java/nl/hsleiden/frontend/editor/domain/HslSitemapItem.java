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

import java.util.ArrayList;
import java.util.List;

import org.hippoecm.frontend.model.JcrNodeModel;
import org.hippoecm.hst.plugins.frontend.editor.domain.Parameter;
import org.hippoecm.hst.plugins.frontend.editor.domain.SitemapItem;

public class HslSitemapItem extends SitemapItem {
    private static final long serialVersionUID = 1L;
    List<Parameter> configMappings;

    public HslSitemapItem(JcrNodeModel model) {
        super(model);
        configMappings = new ArrayList<Parameter>();
    }

    public List<Parameter> getConfigMappings() {
        return configMappings;
    }

    public void setConfigMappings(final List<Parameter> configMappings) {
        this.configMappings = configMappings;
    }

    public void addConfigMapping() {
        configMappings.add(new Parameter());
    }

    public void addConfigMapping(String name, String value) {
        Parameter p = new Parameter();
        p.setName(name);
        p.setValue(value);
        configMappings.add(p);
    }

    public void removeConfigMapping(int index) {
        configMappings.remove(index);
    }
}
