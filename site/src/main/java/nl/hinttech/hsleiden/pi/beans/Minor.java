// $Id: Minor.java 1495 2014-01-24 12:05:56Z rharing $
package nl.hinttech.hsleiden.pi.beans;

import static nl.hinttech.hsleiden.pi.util.HSLeiden.NAMESPACE_PREFIX;

import java.util.List;

import nl.hinttech.hsleiden.pi.util.HSLeiden;

import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.core.component.HstRequest;

/**
 * Document type that represents a Minor. A short education program for students.
 * 
 * @author rob
 */
@Node(jcrType = "intranet:Minor")
public class Minor extends MetadataDocument {

    public static final String PROPERTY_TITLE = NAMESPACE_PREFIX + "title";
    public static final String PROPERTY_CLUSTER = NAMESPACE_PREFIX + "cluster";
    public static final String PROPERTY_ECTS = NAMESPACE_PREFIX + "ects";
    public static final String PROPERTY_EDUCATION_PERIODS = NAMESPACE_PREFIX + "educationperiods";
    public static final String PROPERTY_EDUCATIONAL_UNIT = NAMESPACE_PREFIX + "educationalunit";
    public static final String PROPERTY_AVAILABLE_ON_KOM = NAMESPACE_PREFIX + "availableOnKOM";
    public static final String PROPERTY_MODULE_CODE = NAMESPACE_PREFIX + "module_code";
    public static final String PROPERTY_GOALS = NAMESPACE_PREFIX + "goals";
    public static final String PROPERTY_KEYWORDS = NAMESPACE_PREFIX + "keywords";
    public static final String PROPERTY_CLASS_CODES_INTERNAL_EDUCATION = NAMESPACE_PREFIX + "class_codes_internal_education";
    public static final String PROPERTY_EXTRA_INFORMATION = NAMESPACE_PREFIX + "extrainformation";
    public static final String PROPERTY_INTRODUCTION = NAMESPACE_PREFIX + "introduction";
    public static final String PROPERTY_EXAMINATION = NAMESPACE_PREFIX + "examination";
    public static final String PROPERTY_ASSESMENT_METHOD = NAMESPACE_PREFIX + "assesment_method";
    public static final String PROPERTY_LITERATURE = NAMESPACE_PREFIX + "literature";
    public static final String PROPERTY_EDUCATION_TYPE_OTHER = NAMESPACE_PREFIX + "educationtype_other";
    public static final String PROPERTY_TEACHERS = NAMESPACE_PREFIX + "teachers";
    public static final String PROPERTY_MIN_NR_OF_STUDENTS = NAMESPACE_PREFIX + "min_nr_students";
    public static final String PROPERTY_MAX_NR_OF_STUDENTS = NAMESPACE_PREFIX + "max_nr_students";
    public static final String PROPERTY_MAX_NR_OF_STUDENTS_OWN_EDUCATION = NAMESPACE_PREFIX + "max_nr_students_own_education";
    public static final String PROPERTY_MAX_NR_OF_STUDENTS_OTHER_EDUCATION = NAMESPACE_PREFIX + "max_nr_students_other_educations";
    public static final String PROPERTY_MAX_NR_OF_STUDENTS_KOM = NAMESPACE_PREFIX + "max_nr_students_kom";
    public static final String PROPERTY_INVOLVED_EDUCATIONS = NAMESPACE_PREFIX + "involved_educations";
    public static final String PROPERTY_ROSTER = NAMESPACE_PREFIX + "roster";
    public static final String PROPERTY_EDUCATION_TYPE = NAMESPACE_PREFIX + "educationtype";
    public static final String PROPERTY_START_YEAR = NAMESPACE_PREFIX + "startyear";
    public static final String PROPERTY_CONTACT_PERSON = NAMESPACE_PREFIX + "contactperson";
    public static final String PROPERTY_ACCESSIBILITY_INTERNAL = NAMESPACE_PREFIX + "accessibility_internal";
    public static final String PROPERTY_ACCESSIBILITY_KOM = NAMESPACE_PREFIX + "accessibility_kom";
    public static final String PROPERTY_LANGUAGES = NAMESPACE_PREFIX + "languages";
    public static final String PROPERTY_MINOR_TYPE = NAMESPACE_PREFIX + "minortype";
    
    
    @Override
    public String getTitle() {
        return getProperty(PROPERTY_TITLE);
    }
    
    public String getClusterKey() {
        return getProperty(PROPERTY_CLUSTER);
    }

    public String getPeriods() {
        return getProperty(PROPERTY_EDUCATION_PERIODS);
    }

    public String getECTs() {
        return getProperty(PROPERTY_ECTS);
    }
    
    public String getEducationalUnit() {
        return getProperty(PROPERTY_EDUCATIONAL_UNIT);
    }
    
    public boolean getIsAvailableOnKom() {
        return getProperty(PROPERTY_AVAILABLE_ON_KOM);
    }
    
    public String getModuleCode() {
        return getProperty(PROPERTY_MODULE_CODE);
    }
    
    public String getGoals() {
        return getProperty(PROPERTY_GOALS);
    }
    
    public String getKeywords() {
        return getProperty(PROPERTY_KEYWORDS);
    }
    
    public String getClassCodesInternalEducation() {
        return getProperty(PROPERTY_CLASS_CODES_INTERNAL_EDUCATION);
    }
    
    public String getExtraInformation() {
        return getProperty(PROPERTY_EXTRA_INFORMATION);
    }
    
    public String getIntroduction() {
        return getProperty(PROPERTY_INTRODUCTION);
    }
   
    
    public String getExamination() {
        return getProperty(PROPERTY_EXAMINATION);
    }
    
    public String getAssesmentMethod() {
        return getProperty(PROPERTY_ASSESMENT_METHOD);
    }
    
    public String getLiterature() {
        return getProperty(PROPERTY_LITERATURE);
    }
    
    public String getEducationTypeOther() {
        return getProperty(PROPERTY_EDUCATION_TYPE_OTHER);
    }
    
    public String getTeachers() {
        return getProperty(PROPERTY_TEACHERS);
    }
    
    public Long getMinNrOfStudents() {
        return getProperty(PROPERTY_MIN_NR_OF_STUDENTS, 0L);
    }
    
    public Long getMaxNrOfStudents() {
        return getProperty(PROPERTY_MAX_NR_OF_STUDENTS, 0L);
    }
    
    public Long getMaxNrOfStudentsOwnEducation() {
        return getProperty(PROPERTY_MAX_NR_OF_STUDENTS_OWN_EDUCATION);
    }
    
    public Long getMaxNrOfStudentsOtherEducation() {
        return getProperty(PROPERTY_MAX_NR_OF_STUDENTS_OTHER_EDUCATION);
    }
    
    public Long getMaxNrOfStudentsKom() {
        return getProperty(PROPERTY_MAX_NR_OF_STUDENTS_KOM);
    }
    
    public String getInvolvedEducations() {
        return getProperty(PROPERTY_INVOLVED_EDUCATIONS);
    }
    
    public Roster getRoster() {
        return getChildBeanByName(PROPERTY_ROSTER);
    }
    
    public List<String> getEducationTypes() {
        return getPropertyAsList(PROPERTY_EDUCATION_TYPE);
    }
    
    public long getStartYear() {
        return getProperty(PROPERTY_START_YEAR);
    }
    
    public ContactPerson getContactPerson() {
        return getChildBeanByName(PROPERTY_CONTACT_PERSON);
    }
    
    public Accessibility getAccessibilityInternal() {
        return getChildBeanByName(PROPERTY_ACCESSIBILITY_INTERNAL);
    }
    
    public Accessibility getAccessibilityKOM() {
        return getChildBeanByName(PROPERTY_ACCESSIBILITY_KOM);
    }
    
    public List<String> getLanguages() {
        return getPropertyAsList(PROPERTY_LANGUAGES);
    }
    
    public String getMinorType() {
        return getProperty(PROPERTY_MINOR_TYPE);
    }


    @Override
    public boolean isForStudent() {
        return true;
    }

    @Override
    public boolean isForEmployee() {
        return false;
    }

    @Override
    public boolean isForService() {
        return false;
    }

    @Override
    public Metadata getMetadata(final BaseHstComponent component, final HstRequest request) {
        Metadata metadata = new Metadata();
        metadata.addItem(HSLeiden.METADATA_LABEL_INFO_FOR, "Studenten", "/studenten");
        return metadata;
    }
}
