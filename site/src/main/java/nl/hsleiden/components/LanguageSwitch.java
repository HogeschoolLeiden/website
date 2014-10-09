package nl.hsleiden.components;

import java.util.ArrayList;
import java.util.List;

import nl.hsleiden.utils.Constants.Attributes;

import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.content.beans.standard.HippoAvailableTranslationsBean;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.linking.HstLink;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdclighthouse.prototype.components.WebDocumentDetail;
import com.tdclighthouse.prototype.componentsinfo.LabelsInfo;
import com.tdclighthouse.prototype.utils.BeanUtils;

@ParametersInfo(type = LabelsInfo.class)
public class LanguageSwitch extends WebDocumentDetail {

    private static final String CONTENT_BEAN_AVAILABLE_TRANSLATIONS = "contentBeanAvailableTranslations";

    static final Logger log = LoggerFactory.getLogger(LanguageSwitch.class);

    public void doBeforeRender(HstRequest request, HstResponse response) {
        try {
            List<Translation> result = new ArrayList<Translation>();
            
            HippoBean bean = request.getRequestContext().getContentBean();
            HippoAvailableTranslationsBean<HippoBean> translations = bean.getAvailableTranslations();
            
//            System.out.println("Printing translations");
//            for (String availableLocale : translations.getAvailableLocales()) {
//                System.out.println("availableLocale  = " + availableLocale);                
//            }
            
            if(translations.getAvailableLocales().size()>1){                
                for (String baseLocale : translations.getAvailableLocales()) {
                    Translation tr = createTranslation(request, baseLocale);
                    if(tr!=null){                    
                        result.add(tr);
                    }
                }
            }
            
            if(result.size()>0){                
                request.setAttribute(Attributes.TRANSLATIONS, result);
                request.setAttribute(Attributes.LABELS, BeanUtils.getLabels(request, getComponentParametersInfo(request)));
                request.setAttribute(Attributes.CURRENT_LANGUAGE, request.getLocale().getLanguage());
            }else{
                request.setAttribute(Attributes.NO_TRANSLATION, true);
            }
            
        } catch (ObjectBeanManagerException e) {
            throw new HstComponentException(e);
        }
    }

    private Translation createTranslation(final HstRequest request, final String locale)
            throws ObjectBeanManagerException {
        boolean selected = getSelected(request, locale);
        boolean available = true;
        HippoBean translationBean = getContentBeanTranslation(request).getTranslation(locale);
        if (translationBean == null) {
            available = false;
            return null;
        }
        return new Translation(locale, createLink(request, translationBean), available, selected);
    }

    @SuppressWarnings("unchecked")
    private HippoAvailableTranslationsBean<HippoBean> getContentBeanTranslation(final HstRequest request) {
        HippoAvailableTranslationsBean<HippoBean> result;
        Object obj = request.getAttribute(CONTENT_BEAN_AVAILABLE_TRANSLATIONS);
        if (obj instanceof HippoAvailableTranslationsBean) {
            result = (HippoAvailableTranslationsBean<HippoBean>) obj;
        } else {
            result = request.getRequestContext().getContentBean().getAvailableTranslations();
            request.setAttribute(CONTENT_BEAN_AVAILABLE_TRANSLATIONS, result);
        }
        return result;
    }

    private boolean getSelected(final HstRequest request, final String locale) {
        final String requestLocale = request.getLocale().getLanguage();
        boolean selected = locale.equals(requestLocale);
        return selected;
    }

    private HstLink createLink(final HstRequest request, HippoBean translationBean) {
        HstLink link = null;
        HstRequestContext requestContext = request.getRequestContext();
        if (translationBean != null) {
            link = requestContext.getHstLinkCreator().create(translationBean.getNode(), requestContext);
        }
        return link;
    }

    public static class Translation {

        private final String language;
        private final boolean selected;
        private final boolean available;
        private final HstLink link;

        public Translation(String language, final HstLink link, boolean available, boolean selected) {
            this.language = language;
            this.available = available;
            this.link = link;
            this.selected = selected;
        }

        public String getLanguage() {
            return language;
        }

        public boolean isAvailable() {
            return available;
        }

        public HstLink getLink() {
            return link;
        }

        public boolean isSelected() {
            return selected;
        }
    }
}
