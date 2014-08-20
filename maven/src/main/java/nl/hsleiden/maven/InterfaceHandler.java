package nl.hsleiden.maven;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sourceforge.mavenhippo.gen.ClassReference;
import net.sourceforge.mavenhippo.gen.ImportRegistry;
import net.sourceforge.mavenhippo.gen.InterfacesHandler;
import net.sourceforge.mavenhippo.gen.annotation.Weight;
import net.sourceforge.mavenhippo.model.ContentTypeBean;
import net.sourceforge.mavenhippo.model.ContentTypeBean.ContentTypeException;
import net.sourceforge.mavenhippo.model.HippoBeanClass;
import nl.openweb.omnikassa.beans.OmnikassaAware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.tdclighthouse.prototype.beans.Page;

@Weight(0)
public class InterfaceHandler extends InterfacesHandler {
    private static final Logger LOG = LoggerFactory.getLogger(InterfaceHandler.class);
    private Multimap<String, Class<?>> nodeTypeInterfaceMapping = HashMultimap.create();

    public InterfaceHandler(Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject,
            ClassLoader classLoader, Set<String> namespaces, Map<String, ContentTypeBean> mixins) {
        super(beansOnClassPath, beansInProject, classLoader, namespaces, mixins);
        nodeTypeInterfaceMapping.put("hsl:WebPage", Page.class);
        nodeTypeInterfaceMapping.put("hsl:OmnikassaPage", OmnikassaAware.class);
    }

    @Override
    public List<ClassReference> getInterfaces(ContentTypeBean contentTypeBean, ImportRegistry importRegistry,
            String packageName) {
        List<ClassReference> result = new ArrayList<ClassReference>();
        try {
            String fullyQualifiedName = contentTypeBean.getFullyQualifiedName();
            if (nodeTypeInterfaceMapping.containsKey(fullyQualifiedName)) {
                Collection<Class<?>> interfaces = nodeTypeInterfaceMapping.get(fullyQualifiedName);
                for (Class<?> i : interfaces) {
                    ClassReference inter = new ClassReference(i);
                    result.add(inter);
                    importRegistry.register(inter);
                }

            }
        } catch (ContentTypeException e) {
            LOG.debug(e.getMessage(), e);
        }
        return result;
    }

}
