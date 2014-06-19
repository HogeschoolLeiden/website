package nl.hsleiden.maven;

import net.sourceforge.mavenhippo.gen.HandlerResponse;
import net.sourceforge.mavenhippo.gen.ImportRegistry;
import net.sourceforge.mavenhippo.model.ContentTypeBean;
import net.sourceforge.mavenhippo.model.ContentTypeBean.Item;
import net.sourceforge.mavenhippo.model.ContentTypeBean.Template;
import net.sourceforge.mavenhippo.utils.exceptions.GeneratorException;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

public class SelectionHandlerTest {

    @Test
    public void handleTest() throws GeneratorException {
        SelectionHandler selectionHandler = new SelectionHandler(null, null, null, null);

        ContentTypeBean contentTypeBean = EasyMock.createMock(ContentTypeBean.class);
        Item item = createMockItem(contentTypeBean, "ns:myField", "myField", false);
        Template template = createMockTemplate("/path/to/value/list");
        EasyMock.expect(contentTypeBean.getTemplate(item)).andReturn(template);

        EasyMock.replay(item, contentTypeBean, template);

        ImportRegistry importRegistry = new ImportRegistry();
        HandlerResponse handle = selectionHandler.handle(item, importRegistry);
        Assert.assertEquals(1, handle.getPropertyGenerators().size());
        Assert.assertEquals(1, handle.getMethodGenerators().size());
        Assert.assertEquals("private SelectionBean myField;", handle.getPropertyGenerators().get(0).getFragment());
        Assert.assertEquals(
                "public SelectionBean getMyField() {\r\n    if (this.myField == null) {\r\n        this.myField = getSelectionBean(\"ns:myField\", \"/path/to/value/list\");\r\n    }\r\n    return this.myField;\r\n}",
                handle.getMethodGenerators().get(0).getFragment());
    }

    private Template createMockTemplate(String source) {
        Template template = EasyMock.createMock(Template.class);
        EasyMock.expect(template.getOptionsValue("source")).andReturn(source);
        return template;
    }

    private Item createMockItem(ContentTypeBean contentTypeBean, String relativePath, String simpleName,
            boolean isMultible) {
        Item item = EasyMock.createMock(Item.class);
        EasyMock.expect(item.getType()).andReturn("DynamicDropdown");
        EasyMock.expect(item.getContentType()).andReturn(contentTypeBean);
        EasyMock.expect(item.getRelativePath()).andReturn(relativePath);
        EasyMock.expect(item.getSimpleName()).andReturn(simpleName).anyTimes();
        EasyMock.expect(item.isMultiple()).andReturn(isMultible);
        return item;
    }

}
