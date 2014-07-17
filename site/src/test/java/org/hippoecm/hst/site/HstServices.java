package org.hippoecm.hst.site;

import net.sourceforge.hstmixinsupport.DynamicProxyFactory;
import net.sourceforge.hstmixinsupport.MixinInterfaceScannerService;

import org.hippoecm.hst.core.container.ComponentManager;
import org.hippoecm.hst.mock.core.container.MockComponentManager;

public class HstServices {

    public static ComponentManager getComponentManager() {
        MockComponentManager componentManager = new MockComponentManager();
        componentManager.addComponent(DynamicProxyFactory.class.getName(), new DynamicProxyFactory());
        componentManager.addComponent(MixinInterfaceScannerService.class.getName(), new MixinInterfaceScannerService());
        return componentManager;
    }
}
