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
        boolean wrongVideoId = false;
        ObjectConverter objectConverter = createMockObjectConverter(wrongVideoId);
        YoutubeBean youtubeBean = new YoutubeBean();
        youtubeBean.setObjectConverter(objectConverter);
        String url = youtubeBean.getUrl();
        
        Assert.assertTrue(url.contains("//www.youtube.com/embed/yhKB-VxJWpg"));
        Assert.assertTrue(url.contains("disablekb=0"));
        Assert.assertTrue(url.contains("theme=black"));
        Assert.assertTrue(url.contains("showinfo=0"));
        Assert.assertTrue(url.contains("rel=0"));
        Assert.assertTrue(url.contains("autoplay=1"));

    }

    @Test
    public void getUrlTestVideoIdEmpty() throws Exception {
        boolean wrongVideoId = true;
        ObjectConverter objectConverter = createMockObjectConverter(wrongVideoId);
        YoutubeBean youtubeBean = new YoutubeBean();
        youtubeBean.setObjectConverter(objectConverter);
        String url = youtubeBean.getUrl();
        Assert.assertEquals(null, url);
    }

    private ObjectConverter createMockObjectConverter(boolean wrongVideoid) throws Exception {
        ObjectConverter objectConverter = EasyMock.createMock(ObjectConverter.class);
        try {
            EasyMock.expect(objectConverter.getObject((Node) null, "hsl:youtubeUrlParameters"))
                    .andReturn(createMockYoutubeUrlParameters(wrongVideoid)).anyTimes();
            EasyMock.expect(objectConverter.getObject((Node) null, "hsl:youtubePlayerParameters"))
                    .andReturn(createMockYoutubePlayerParameters()).anyTimes();
            EasyMock.replay(objectConverter);
        } catch (ObjectBeanManagerException e) {
            // never going to happens
        }
        return objectConverter;
    }

    private YoutubeUrlParameters createMockYoutubeUrlParameters(boolean wrongVideoId) throws NoSuchFieldException,
            SecurityException, IllegalArgumentException, IllegalAccessException {

        YoutubeUrlParameters youtubeUrlParameters = new YoutubeUrlParameters();
        if (wrongVideoId) {
            TestUtils.setPrivateField(youtubeUrlParameters, "youtubeUrl", "wrong");
        } else {
            TestUtils
                    .setPrivateField(youtubeUrlParameters, "youtubeUrl", "https://www.youtube.com/watch?v=yhKB-VxJWpg");
        }

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
