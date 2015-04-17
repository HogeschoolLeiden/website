// $Id: Count.java 237 2013-07-03 06:32:10Z rharing $
package nl.hinttech.hsleiden.pi.counters;

import java.io.Serializable;
import java.util.Date;

import nl.hinttech.hsleiden.pi.util.HSLeiden;

/**
 * Utility class to keep track of a counter for a specific UUID.
 * 
 * @author Rob Haring
 */
public class Count implements Serializable {

    private static final long serialVersionUID = -9180609681139288651L;
    private String uuid;
    private int count;
    private Date date;
    private boolean isForStudent;
    private boolean isForEmployee;
    private boolean isForService;

    Count(String uuid, boolean isForStudent, boolean isForEmployee, boolean isForService) {
        this.uuid = uuid;
        date = new Date();
        this.isForStudent = isForStudent;
        this.isForEmployee= isForEmployee;
        this.isForService = isForService;
    }

   
    public boolean isForStudent() {
        return isForStudent;
    }

    public boolean isForEmployee() {
        return isForEmployee;
    }

    public boolean isForService() {
        return isForService;
    }

    public String getUuid() {
        return uuid;
    }

    public int getCount() {
        return count;
    }

    public void increase() {
        count++;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public boolean isForType(String type) {
        
        if (HSLeiden.TYPE_STUDENT.equals(type)) {
            return isForStudent();
        } else if (HSLeiden.TYPE_EMPLOYEE.equals(type)) {
            return isForEmployee();
        } else if (HSLeiden.TYPE_SERVICE.equals(type)) {
            return isForService();
        }
        return false;
    }

}
