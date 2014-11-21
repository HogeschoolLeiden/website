package nl.hsleiden.utils;

import java.util.Calendar;
import java.util.Date;

import nl.hsleiden.beans.EventPageBean;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;

public class HslDateUtils {

    private HslDateUtils() {
        super();
    }
    
    public static Date getEndOfDay(Date date) {
        return DateUtils.addMilliseconds(DateUtils.ceiling(date, Calendar.DATE), -1);
    }

    public static Date getStartOfDay(Date date) {
        return DateUtils.truncate(date, Calendar.DATE);
    }

    public static Calendar dateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
    
    public static int getEventDaysDuration(EventPageBean event) {
        return Days.daysBetween(new DateTime(event.getEventDate()), new DateTime(event.getEventEndDate())).getDays();
    }
    
    public static Date incrementDate(Date date, int increment){
        Date result = date;
        
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, increment);
        result = c.getTime();
        
        return result;
    }
}
