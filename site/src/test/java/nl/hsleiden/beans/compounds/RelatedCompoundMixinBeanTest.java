package nl.hsleiden.beans.compounds;

import hslbeans.RelatedFilterParameters;
import hslbeans.RelatedOverviewParameters;
import hslbeans.RelatedSortParameters;
import hslbeans.RelatedWidgetParameters;

import java.lang.reflect.Field;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.mock.content.beans.standard.MockHippoBean;
import org.junit.Assert;


public class RelatedCompoundMixinBeanTest {

//    @Test
    public void getWidgetTitle() throws NoSuchFieldException, IllegalAccessException {
       String mockWidgetTitle = createMockRelatedCompoundMixin().getWidgetParameters().getWidgetTitle();
       Assert.assertEquals("test widgetTitle", mockWidgetTitle);
    }

    private RelatedCompoundMixinBean createMockRelatedCompoundMixin() throws NoSuchFieldException, IllegalAccessException{
        RelatedCompoundMixinBean relatedCompoundMixin = new RelatedCompoundMixinBean();
        setPrivateField(relatedCompoundMixin, "sortParameters", createMockRelatedSortParameters());
        setPrivateField(relatedCompoundMixin, "overviewParameters", createMockRelatedOverviewParameters());
        setPrivateField(relatedCompoundMixin, "widgetParameters", createMockWidgetParameters());
        setPrivateField(relatedCompoundMixin, "filterParameters", createMockRelatedFilterParameters());
        
        return relatedCompoundMixin;
    }
    
    private RelatedWidgetParameters createMockWidgetParameters() throws NoSuchFieldException, IllegalAccessException {
        RelatedWidgetParameters relatedWidgetParameters = new RelatedWidgetParameters();
        setPrivateField(relatedWidgetParameters, "widgetTitle", "test widgetTitle");
        setPrivateField(relatedWidgetParameters, "size", 5L);
       
        //TODO: how to set the path of this bean
        MockHippoBean hbMock = new MockHippoBean();
        setPrivateField(relatedWidgetParameters, "contentBeanPath", hbMock);

        return relatedWidgetParameters;
    }
    
    private RelatedOverviewParameters createMockRelatedOverviewParameters() throws NoSuchFieldException, IllegalAccessException{
        RelatedOverviewParameters relatedOverviewParameters = new RelatedOverviewParameters();
        setPrivateField(relatedOverviewParameters, "showOverview", new Boolean(false));
        setPrivateField(relatedOverviewParameters, "overviewLinkLabel", "test overviewLinkLabel");
        
        //TODO: how to set the path of this bean
        HippoBean hbMock = new MockHippoBean();
        setPrivateField(relatedOverviewParameters, "overviewBeanPath", hbMock);
        
        return relatedOverviewParameters;
    }

    private RelatedFilterParameters createMockRelatedFilterParameters() throws NoSuchFieldException, IllegalAccessException{
        RelatedFilterParameters relatedOverviewParameters = new RelatedFilterParameters();
        setPrivateField(relatedOverviewParameters, "themaFilter", new Boolean(false));
        setPrivateField(relatedOverviewParameters, "overFilter",  new Boolean(false));
        return relatedOverviewParameters;
    }

    private RelatedSortParameters createMockRelatedSortParameters() throws NoSuchFieldException, IllegalAccessException{
        RelatedSortParameters relatedSortParameters = new RelatedSortParameters();
        setPrivateField(relatedSortParameters, "sortOrder", "test sortOrder");
        setPrivateField(relatedSortParameters, "sortBy", "test sortBy");
        return relatedSortParameters;
    }
    
    private void setPrivateField(Object target, String fieldName, Object value) throws NoSuchFieldException,
        IllegalAccessException {
        Field youtubeUrl = target.getClass().getDeclaredField(fieldName);
        youtubeUrl.setAccessible(true);
        youtubeUrl.set(target, value);
    }
}
