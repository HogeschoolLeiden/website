package nl.hinttech.hsleiden.pi.beans;

import static nl.hinttech.hsleiden.pi.util.HSLeiden.NAMESPACE_PREFIX;
import static nl.hinttech.hsleiden.pi.util.HSLeiden.TYPE_EMPLOYEE;
import static nl.hinttech.hsleiden.pi.util.HSLeiden.TYPE_SERVICE;
import static nl.hinttech.hsleiden.pi.util.HSLeiden.TYPE_STUDENT;

import java.util.List;

import nl.hinttech.hsleiden.pi.util.HSLeiden;
import nl.hinttech.hsleiden.pi.util.ValueListUtil;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoGalleryImageSetBean;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.hippoecm.hst.core.component.HstRequest;
import org.onehippo.forge.selection.hst.contentbean.ValueList;

/**
 * The superclass of all of our content documents.
 */
@Node(jcrType = "intranet:TekstPagina")
public class TextDocument extends BaseDocument {

    public static final String PROPERTY_TITLE = NAMESPACE_PREFIX + "title";
    public static final String PROPERTY_IMAGE = NAMESPACE_PREFIX + "image";
    public static final String PROPERTY_BODY = NAMESPACE_PREFIX + "body";
    public static final String PROPERTY_STUDENT_CATEGORY = NAMESPACE_PREFIX + "studentenlijst";
    public static final String PROPERTY_EMPLOYEE_CATEGORY = NAMESPACE_PREFIX + "medewerkerslijst";
    public static final String PROPERTY_SERVICE_CATEGORY = NAMESPACE_PREFIX + "servicelijst";
    public static final String PROPERTY_TEXT_BLOCKS = NAMESPACE_PREFIX + "titelTekst";
    public static final String PROPERTY_IS_FOR_STUDENT = NAMESPACE_PREFIX + "student";
    public static final String PROPERTY_IS_FOR_EMPLOYEE = NAMESPACE_PREFIX + "werknemer";
    public static final String PROPERTY_IS_FOR_SERVICE = NAMESPACE_PREFIX + "service";
    public static final String PROPERTY_DEPARTMENT = NAMESPACE_PREFIX + "afdeling";
    public static final String PROPERTY_TRAINING = NAMESPACE_PREFIX + "opleiding";
    public static final String PROPERTY_INTRODUCTION = NAMESPACE_PREFIX + "introduction";
    public static final String PROPERTY_TAGS = "hippostd:tags";

    public String getTitle() {
        return getProperty(PROPERTY_TITLE);
    }

    public HippoGalleryImageSetBean getImage() {
        return getLinkedBean(PROPERTY_IMAGE, HippoGalleryImageSetBean.class);
    }

    @Deprecated
    public HippoHtml getHtml() {
        return getHippoHtml(PROPERTY_BODY);
    }

    public String getIntroduction() {
        String intro = getProperty(PROPERTY_INTRODUCTION);
        if (StringUtils.isBlank(intro)) {
            intro = getHtmlAsFlatText();
        }
        return intro;
    }
    
    private String getHtmlAsFlatText() {
        if (getHtml() != null) {
            String content = getHtml().getContent().replaceAll("\\<h[1-6]\\>.*\\<\\/h[1-6]\\>", "").replaceAll("\\<.*?\\>", " ");
            if (content.length() >= 250) {
              String tmp = content.substring(0, 250);
              String tmp2 = tmp.replaceAll("\\n", " ");
              return tmp2.replaceAll("\\s{2,}", " ")  + " ...";
            } else {
              String tmp = content.replace("\\n", " ");
              return tmp.replaceAll("\\s{2,}", " ");
            }
          } else {
            return "";
          }
    }
    
    public List<String> getStudentCategories() {
        return getPropertyAsList(PROPERTY_STUDENT_CATEGORY);
    }

    public List<String> getTrainings() {
        return getPropertyAsList(PROPERTY_TRAINING);
    }

    public List<String> getDepartments() {
        return getPropertyAsList(PROPERTY_DEPARTMENT);
    }

    public List<String> getEmployeeCategories() {
        return getPropertyAsList(PROPERTY_EMPLOYEE_CATEGORY);
    }

    public List<String> getServiceCategories() {
        return getPropertyAsList(PROPERTY_SERVICE_CATEGORY);
    }

    public List<TextBlock> getTextBlocks() {
        List<TextBlock> textBlocks = getChildBeansByName(PROPERTY_TEXT_BLOCKS, TextBlock.class);
        if (textBlocks.isEmpty()) {
            HippoHtml html = getHtml();
            if (html != null && !"<html><body></body></html>".equals(html.getContent().trim())) {
                textBlocks.add(new TextBlock(html));
            }
        }
        return textBlocks;
    }

    public boolean isForStudent() {
        return (Boolean) getProperty(PROPERTY_IS_FOR_STUDENT);
    }

    public boolean isForEmployee() {
        return (Boolean) getProperty(PROPERTY_IS_FOR_EMPLOYEE);
    }

    public boolean isForService() {
        return (Boolean) getProperty(PROPERTY_IS_FOR_SERVICE);
    }

    public static String getTypePropertyForType(String type) {
        if (TYPE_STUDENT.equals(type)) {
            return PROPERTY_IS_FOR_STUDENT;
        }
        if (TYPE_EMPLOYEE.equals(type)) {
            return PROPERTY_IS_FOR_EMPLOYEE;
        }
        if (TYPE_SERVICE.equals(type)) {
            return PROPERTY_IS_FOR_SERVICE;
        }
        return PROPERTY_IS_FOR_STUDENT;
    }

    public List<String> getCategoriesForType(String type) {
        if (TYPE_STUDENT.equals(type)) {
            return getStudentCategories();
        }
        if (TYPE_EMPLOYEE.equals(type)) {
            return getEmployeeCategories();
        }
        if (TYPE_SERVICE.equals(type)) {
            return getServiceCategories();
        }
        return getServiceCategories();
    }

    public static String getCategoryValueListNameForType(String type) {
        if (TYPE_STUDENT.equals(type)) {
            return "studentenlijst";
        }
        if (TYPE_EMPLOYEE.equals(type)) {
            return "medewerkerslijst";
        }
        if (TYPE_SERVICE.equals(type)) {
            return "servicelijst";
        }
        return "studentenlijst";
    }

    public Metadata getMetadata(BaseHstComponent component, HstRequest request) {

        Metadata metadata = new Metadata();

        if (isForStudent()) {
            metadata.addItem(HSLeiden.METADATA_LABEL_INFO_FOR, "Studenten", "/studenten");
        }
        if (isForService()) {
            metadata.addItem(HSLeiden.METADATA_LABEL_INFO_FOR, "Zelf regelen", "/zelf-regelen");
        }
        if (isForEmployee()) {
            metadata.addItem(HSLeiden.METADATA_LABEL_INFO_FOR, "Medewerkers", "/medewerkers");
        }

        ValueList departments = ValueListUtil.getValueList(component, request, HSLeiden.VALUELIST_DEPARTMENTS);
        ValueList trainings = ValueListUtil.getValueList(component, request, HSLeiden.VALUELIST_TRAININGS);

        for (String department : getDepartments()) {
            String label = ValueListUtil.getLabelForKey(department, departments);
            metadata.addItem(HSLeiden.METADATA_LABEL_DEPARTMENT, label, "/zoeken?afdeling=" + department);
        }

        for (String training : getTrainings()) {
            String label = ValueListUtil.getLabelForKey(training, trainings);
            metadata.addItem(HSLeiden.METADATA_LABEL_TRAINING, label, "/zoeken?opleiding=" + training);
        }

        return metadata;
    }

    public List<String> getTags() {
        return getPropertyAsList(PROPERTY_TAGS);
    }

}