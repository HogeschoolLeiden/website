package nl.hsleiden.maven;

import java.util.ArrayList;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdclighthouse.prototype.beans.Page;

@Weight(0)
public class InterfaceHandler extends InterfacesHandler {
    private static final Logger LOG = LoggerFactory.getLogger(InterfaceHandler.class);

    public InterfaceHandler(Map<String, HippoBeanClass> beansOnClassPath, Map<String, HippoBeanClass> beansInProject,
            ClassLoader classLoader, Set<String> namespaces, Map<String, ContentTypeBean> mixins) {
        super(beansOnClassPath, beansInProject, classLoader, namespaces, mixins);
    }

    @Override
    public List<ClassReference> getInterfaces(ContentTypeBean contentTypeBean, ImportRegistry importRegistry,
            String packageName) {
        List<ClassReference> result = new ArrayList<ClassReference>();
        try {
            if ("hsl:WebPage".equals(contentTypeBean.getFullyQualifiedName())) {
                ClassReference page = new ClassReference(Page.class);
                result.add(page);
                importRegistry.register(page);
            }
        } catch (ContentTypeException e) {
            LOG.debug(e.getMessage(), e);
        }
        return result;
    }

}
