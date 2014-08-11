package nl.hsleiden.beans.compounds;

import hslbeans.PublicImagesParameters;
import nl.hsleiden.utils.TestUtils;

import org.easymock.EasyMock;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoGalleryImageSetBean;
import org.junit.Assert;
import org.junit.Test;

public class PublicImagesCompoundMixinBeanTest {

    @Test
    public void getUseMixinTest(){
        PublicImagesCompoundMixinBean publicimagesCompoundMixinBean = new PublicImagesCompoundMixinBean();
        try{
            publicimagesCompoundMixinBean.getUseMixin();  
        }catch(UnsupportedOperationException e){
            Assert.assertEquals(e.getClass().getSimpleName(), "UnsupportedOperationException");
        }
    }

    @Test
    public void getContentBeanPathTest() throws NoSuchFieldException, IllegalAccessException {
        String path = "/path/to/a/bean";
        PublicImagesCompoundMixinBean publicimagesCompoundMixinBean = new PublicImagesCompoundMixinBean();
        TestUtils.setPrivateField(publicimagesCompoundMixinBean, "publicImagesParameters",
                createMockPublicImagesParameters(null, path, null));
        Assert.assertEquals(path, publicimagesCompoundMixinBean.getContentBeanPath());
    }

    @Test
    public void getContentBeanPathNullTest() throws NoSuchFieldException, IllegalAccessException {
        String path = null;
        PublicImagesCompoundMixinBean publicimagesCompoundMixinBean = new PublicImagesCompoundMixinBean();
        TestUtils.setPrivateField(publicimagesCompoundMixinBean, "publicImagesParameters",
                createMockPublicImagesParameters(null, path, null));
        Assert.assertEquals(path, publicimagesCompoundMixinBean.getContentBeanPath());
    }
    
    @Test
    public void getSizeTest() throws NoSuchFieldException, IllegalAccessException{
        long size =5l;
        PublicImagesCompoundMixinBean publicimagesCompoundMixinBean = new PublicImagesCompoundMixinBean();
        TestUtils.setPrivateField(publicimagesCompoundMixinBean, "publicImagesParameters",
                createMockPublicImagesParameters(null, null, size));
        Assert.assertEquals(size, publicimagesCompoundMixinBean.getSize());
        
    }

    @Test
    public void getImagesPerRowTest() throws NoSuchFieldException, IllegalAccessException{
        String imagesPerRow ="4";
        PublicImagesCompoundMixinBean publicimagesCompoundMixinBean = new PublicImagesCompoundMixinBean();
        TestUtils.setPrivateField(publicimagesCompoundMixinBean, "publicImagesParameters",
                createMockPublicImagesParameters(imagesPerRow, null, null));
        Assert.assertEquals(new Integer(imagesPerRow).intValue(), publicimagesCompoundMixinBean.getImagesPerRow());
        
    }

    @Test
    public void getImagesPerRowNullTest() throws NoSuchFieldException, IllegalAccessException{
        String imagesPerRow = null;
        PublicImagesCompoundMixinBean publicimagesCompoundMixinBean = new PublicImagesCompoundMixinBean();
        TestUtils.setPrivateField(publicimagesCompoundMixinBean, "publicImagesParameters",
                createMockPublicImagesParameters(imagesPerRow, null, null));
        Assert.assertEquals(0, publicimagesCompoundMixinBean.getImagesPerRow());
        
    }

    @Test
    public void getImagesPerRowEmptyTest() throws NoSuchFieldException, IllegalAccessException{
        String imagesPerRow = "";
        PublicImagesCompoundMixinBean publicimagesCompoundMixinBean = new PublicImagesCompoundMixinBean();
        TestUtils.setPrivateField(publicimagesCompoundMixinBean, "publicImagesParameters",
                createMockPublicImagesParameters(imagesPerRow, null, null));
        Assert.assertEquals(0, publicimagesCompoundMixinBean.getImagesPerRow());
        
    }

    @Test
    public void getImagesPerRowExceptionTest() throws NoSuchFieldException, IllegalAccessException{
        String imagesPerRow = "exception";
        PublicImagesCompoundMixinBean publicimagesCompoundMixinBean = new PublicImagesCompoundMixinBean();
        TestUtils.setPrivateField(publicimagesCompoundMixinBean, "publicImagesParameters",
                createMockPublicImagesParameters(imagesPerRow, null, null));
        Assert.assertEquals(0, publicimagesCompoundMixinBean.getImagesPerRow());
        
    }
    
    @Test
    public void getImageFolderBeanPathTest () throws NoSuchFieldException, IllegalAccessException{
        String imageFolderPath ="/path/to/folder/image";
        PublicImagesCompoundMixinBean publicimagesCompoundMixinBean = new PublicImagesCompoundMixinBean();
        TestUtils.setPrivateField(publicimagesCompoundMixinBean, "publicImagesParameters",
                createMockPublicImagesParameters(imageFolderPath));
        Assert.assertEquals(imageFolderPath, publicimagesCompoundMixinBean.getImageFolderBeanPath());
    }

    @Test
    public void getImageFolderBeanPathNullTest () throws NoSuchFieldException, IllegalAccessException{
        String imageFolderPath = null;
        PublicImagesCompoundMixinBean publicimagesCompoundMixinBean = new PublicImagesCompoundMixinBean();
        TestUtils.setPrivateField(publicimagesCompoundMixinBean, "publicImagesParameters",
                createMockPublicImagesParameters(imageFolderPath));
        Assert.assertEquals(PublicImagesCompoundMixinBean.CONTENT_GALLERY_HSL_FOLDER_ICON_JPG , publicimagesCompoundMixinBean.getImageFolderBeanPath());
    }
    
    private PublicImagesParameters createMockPublicImagesParameters(String imagesPerRow, String contentBeanPath, Long size) {
        PublicImagesParameters mock = EasyMock.createMock(PublicImagesParameters.class);
        EasyMock.expect(mock.getImagesPerRow()).andReturn(imagesPerRow).anyTimes();
        if(contentBeanPath!=null){            
            EasyMock.expect(mock.getContentBeanPath()).andReturn(createMockContentBean(contentBeanPath)).anyTimes();
        }else{
            EasyMock.expect(mock.getContentBeanPath()).andReturn(null).anyTimes(); 
        }
        EasyMock.expect(mock.getSize()).andReturn(size).anyTimes();
        EasyMock.replay(mock);
        return mock;
    }
    
    private PublicImagesParameters createMockPublicImagesParameters(String folderimagePath) {
        PublicImagesParameters mock = EasyMock.createMock(PublicImagesParameters.class);
        if(folderimagePath!=null){            
            EasyMock.expect(mock.getImageFolderBeanPath()).andReturn(createMockHippoGalleryImageSetBean(folderimagePath)).anyTimes();
        }else{
            EasyMock.expect(mock.getImageFolderBeanPath()).andReturn(null).anyTimes();
        }
        EasyMock.replay(mock);
        return mock;
    }

    private HippoBean createMockContentBean(String contentBeanPath) {
        HippoBean mock = EasyMock.createMock(HippoBean.class);
        EasyMock.expect(mock.getPath()).andReturn(contentBeanPath).anyTimes();
        EasyMock.replay(mock);
        return mock;
    }

    private HippoGalleryImageSetBean createMockHippoGalleryImageSetBean(String contentBeanPath) {
        HippoGalleryImageSetBean mock = EasyMock.createMock(HippoGalleryImageSetBean.class);
        EasyMock.expect(mock.getPath()).andReturn(contentBeanPath).anyTimes();
        EasyMock.replay(mock);
        return mock;
    }
}
