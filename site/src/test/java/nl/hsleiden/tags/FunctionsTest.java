package nl.hsleiden.tags;

import hslbeans.ArticlePage;
import hslbeans.Basedocument;
import hslbeans.Image;
import hslbeans.WebPage;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoGalleryImageSetBean;
import org.hippoecm.hst.content.beans.standard.HippoResource;
import org.junit.Assert;
import org.junit.Test;


public class FunctionsTest {

    private static final String BEAN_PATH_TO_FILE_TXT = "/bean/path/to/file.txt";
    private static final String FLEXIBLEBLOCK_IMAGE_BEAN_PATH = "/flexibleblock/image/bean/path";

    @Test
    public void isSubclassOfWebPageSuccess() {
        boolean actual = Functions.isSubclassOfWebPage(new WebPage());
        Assert.assertEquals(true, actual);
    }
    
    @Test
    public void isSubclassOfWebPageFailure() {
        boolean actual = Functions.isSubclassOfWebPage(new Basedocument());
        Assert.assertEquals(false, actual);
    }
    
    @Test
    public void getAssetTitle(){
        String assetTitle = Functions.getAssetTitle(createHippoResourseMock(BEAN_PATH_TO_FILE_TXT));
        Assert.assertEquals(assetTitle, "file.txt");
    }
    
    @Test
    public void getFirstFlexibleBlockImageTestNullOnInstanceOf(){
        ArticlePage bean = getArticlePageBeanMock(FLEXIBLEBLOCK_IMAGE_BEAN_PATH, false, true);
        HippoBean firstFlexibleBlockImage = Functions.getFirstFlexibleBlockImage(bean);
        Assert.assertEquals(null, firstFlexibleBlockImage);
    }

    @Test
    public void getFirstFlexibleBlockImageTestNullonGetImage(){
        ArticlePage bean = getArticlePageBeanMock(FLEXIBLEBLOCK_IMAGE_BEAN_PATH, true, true);
        HippoBean firstFlexibleBlockImage = Functions.getFirstFlexibleBlockImage(bean);
        Assert.assertEquals(null, firstFlexibleBlockImage);
    }

    @Test
    public void getFirstFlexibleBlockImageTest(){
        ArticlePage bean = getArticlePageBeanMock(FLEXIBLEBLOCK_IMAGE_BEAN_PATH, true, false);
        HippoBean firstFlexibleBlockImage = Functions.getFirstFlexibleBlockImage(bean);
        Assert.assertEquals(FLEXIBLEBLOCK_IMAGE_BEAN_PATH, firstFlexibleBlockImage.getPath());
    }

    private ArticlePage getArticlePageBeanMock(String imageBeanPath, boolean imageBeanInstance, boolean emptyimage) {
        List<HippoBean> flexibleBlocksBeanList = getFlexibleBlockBeansList(imageBeanPath, imageBeanInstance, emptyimage);

        ArticlePage mock = EasyMock.createMock(ArticlePage.class);
        EasyMock.expect(mock.getFlexibleblock()).andReturn(flexibleBlocksBeanList).anyTimes();
        
        EasyMock.replay(mock);
        return mock;
    }

    private Image getImageBeanMock(String imageBeanPath, boolean imageNull) {
        
        Image mock = EasyMock.createMock(Image.class);
        if(imageNull){            
            EasyMock.expect(mock.getImage()).andReturn(null).anyTimes();
        }else{
            EasyMock.expect(mock.getImage()).andReturn(getHippoGalleryImageSetBeanMock(imageBeanPath)).anyTimes();
            EasyMock.expect(mock.getPath()).andReturn(getHippoGalleryImageSetBeanMock(imageBeanPath).getPath()).anyTimes();
        }
        
        EasyMock.replay(mock);
        return mock;
    }
    
    private HippoGalleryImageSetBean getHippoGalleryImageSetBeanMock(String imageBeanPath){
        HippoGalleryImageSetBean mock = EasyMock.createMock(HippoGalleryImageSetBean.class);
        EasyMock.expect(mock.getPath()).andReturn(imageBeanPath).anyTimes(); 
        EasyMock.replay(mock);
        return mock;
    }

    private List<HippoBean> getFlexibleBlockBeansList(String imageBeanPath, boolean imageBeanInstance, boolean emptyimage) {
        List<HippoBean> flexibleBlocksBeanList = new ArrayList<HippoBean>();
        if(imageBeanInstance){
            Image imageBean = getImageBeanMock(imageBeanPath, emptyimage);            
            flexibleBlocksBeanList.add(imageBean);
        }else{            
            HippoBean imageBean = getMockContentBean(imageBeanPath);
            flexibleBlocksBeanList.add(imageBean);
        }
        return flexibleBlocksBeanList;
    }

    private HippoResource createHippoResourseMock(String beanPath) {
        HippoResource mock = EasyMock.createMock(HippoResource.class);
        EasyMock.expect(mock.getParentBean()).andReturn(getMockContentBean(beanPath));
        EasyMock.replay(mock);
        return mock;
    }

    private HippoBean getMockContentBean(String contentBeanPath) {
        HippoBean mock = EasyMock.createMock(HippoBean.class);
        EasyMock.expect(mock.getPath()).andReturn(contentBeanPath).anyTimes();
        EasyMock.replay(mock);
        return mock;
    }   
    
}
