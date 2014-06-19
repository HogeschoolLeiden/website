package nl.hsleiden.maven;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;

import java.util.List;

import net.sourceforge.mavenhippo.gen.ClassReference;
import net.sourceforge.mavenhippo.gen.ImportRegistry;
import net.sourceforge.mavenhippo.model.ContentTypeBean;
import net.sourceforge.mavenhippo.model.ContentTypeBean.ContentTypeException;

import org.easymock.EasyMock;
import org.junit.Test;

public class InterfaceHandlerTest {

    @Test
    public void getInterfaceTest() throws ContentTypeException {

        InterfaceHandler interfaceHandler = new InterfaceHandler(null, null, ClassLoader.getSystemClassLoader(), null,
                null);
        ContentTypeBean webPageContentTypeBean = EasyMock.createMock(ContentTypeBean.class);
        ContentTypeBean newsPageContentTypeBean = EasyMock.createMock(ContentTypeBean.class);
        expect(webPageContentTypeBean.getFullyQualifiedName()).andReturn("hsl:WebPage");
        expect(newsPageContentTypeBean.getFullyQualifiedName()).andReturn("hsl:NewsPage");
        replay(webPageContentTypeBean, newsPageContentTypeBean);
        ImportRegistry importRegistry = new ImportRegistry();
        List<ClassReference> interfaces = interfaceHandler.getInterfaces(webPageContentTypeBean, importRegistry, null);
        assertEquals(1, interfaces.size());
        assertEquals("com.tdclighthouse.prototype.beans.Page", interfaces.get(0).getClassName());
        List<String> imports = importRegistry.getImports();
        assertEquals(1, imports.size());
        assertEquals("com.tdclighthouse.prototype.beans.Page", imports.get(0));
        interfaces = interfaceHandler.getInterfaces(newsPageContentTypeBean, importRegistry, null);
        assertEquals(0, interfaces.size());
    }
}
