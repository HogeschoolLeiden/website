package nl.hsleiden.beans;

import hslbeans.YoutubePlayerParameters;
import hslbeans.YoutubeUrlParameters;

import javax.jcr.Node;

import nl.hsleiden.utils.TestUtils;

import org.easymock.EasyMock;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.content.beans.manager.ObjectConverter;
import org.junit.Assert;
import org.junit.Test;

public class YoutubeBeanTest {

    @Test
    public void getUrlTest() throws Exception {
        ObjectConverter objectConverter = createMockObjectConverter();
        YoutubeBean youtubeBean = new YoutubeBean();
        youtubeBean.setObjectConverter(objectConverter);
        String url = youtubeBean.getUrl();
        Assert.assertEquals("http://www.youtube.com/v/yhKB-VxJWpg?disablekb=0&theme=black&showinfo=0&rel=0&autoplay=1",
                url);
    }

    private ObjectConverter createMockObjectConverter() throws Exception {
        ObjectConverter objectConverter = EasyMock.createMock(ObjectConverter.class);
        try {
            EasyMock.expect(objectConverter.getObject((Node) null, "hsl:youtubeUrlParameters"))
                    .andReturn(createMockYoutubeUrlParameters()).anyTimes();
            EasyMock.expect(objectConverter.getObject((Node) null, "hsl:youtubePlayerParameters"))
                    .andReturn(createMockYoutubePlayerParameters()).anyTimes();
            EasyMock.replay(objectConverter);
        } catch (ObjectBeanManagerException e) {
            // never going to happens
        }
        return objectConverter;
    }

    private YoutubeUrlParameters createMockYoutubeUrlParameters() throws NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException {
        YoutubeUrlParameters youtubeUrlParameters = new YoutubeUrlParameters();
        TestUtils.setPrivateField(youtubeUrlParameters, "youtubeUrl", "https://www.youtube.com/watch?v=yhKB-VxJWpg");
        TestUtils.setPrivateField(youtubeUrlParameters, "theme", "black");
        return youtubeUrlParameters;
    }

    private YoutubePlayerParameters createMockYoutubePlayerParameters() throws NoSuchFieldException,
            IllegalAccessException {
        YoutubePlayerParameters youtubePlayerParameters = new YoutubePlayerParameters();
        TestUtils.setPrivateField(youtubePlayerParameters, "autoplay", true);
        return youtubePlayerParameters;
    }

}
