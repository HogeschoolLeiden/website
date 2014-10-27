package nl.hsleiden.beans;

import hslbeans.InfoBlock;

import java.util.Calendar;

import org.hippoecm.hst.content.beans.Node;

@Node(jcrType = "hsl:EventPage")
public class EventPageBean extends ArticlePageBean {
    
    public static final String JCR_TYPE = "hsl:EventPage";
    
    private InfoBlock infoBlock;
    private Calendar eventDate;
    private Calendar eventEndDate;

    public InfoBlock getInfoBlock() {
        if (this.infoBlock == null) {
            this.infoBlock = getBean("hsl:infoBlock", InfoBlock.class);
        }
        return this.infoBlock;
    }
    
    public Calendar getEventDate() {
        if (this.eventDate == null) {
            this.eventDate = getProperty("hsl:eventDate");
        }
        return this.eventDate;
    }
    
    public Calendar getEventEndDate() {
        if (this.eventEndDate == null) {
            this.eventEndDate = getProperty("hsl:eventEndDate");
        }

        //if empty event end date is considered equal to event start date
        if(this.eventEndDate == null) {
            this.eventEndDate =getEventDate();
        }
        
        return this.eventEndDate;
    }

}
