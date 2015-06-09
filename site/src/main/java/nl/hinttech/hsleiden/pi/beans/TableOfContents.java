// $Id: TableOfContents.java 1501 2014-01-27 08:48:58Z rharing $
package nl.hinttech.hsleiden.pi.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 
 *
 * @version $Id: TableOfContents.java 1501 2014-01-27 08:48:58Z rharing $
 */
public class TableOfContents {
    
    private List<TocItem> tocItems = new ArrayList<TableOfContents.TocItem>();
    private String title;
    
    public TableOfContents(final List<TextBlock> textBlocks) {
        this("Inhoudsopgave", textBlocks);
    }
    
    public TableOfContents(final String title, final List<TextBlock> textBlocks) {
        
        if (!StringUtils.isBlank(title)) {
            this.title = title; 
        } else {
            this.title = "Inhoudsopgave";
        }
        
        int id = 0;
        for (TextBlock block : textBlocks) {
            TocItem tocItem = new TocItem(block.getTitle(), "block" + id++);
            tocItems.add(tocItem);
        }
    }
    
    public void addTocItem(final String title, final String anchorId) {
        TocItem tocItem = new TocItem(title, anchorId);
        tocItems.add(tocItem);
    }
    
    public List<TocItem> getItems() {
        return tocItems;
    }
    
    public String getTitle() {
        return title;
    }


    public class TocItem {
        
        private String title;
        private String anchorId;
        
        public TocItem(final String title, final String anchorId) {
            this.title = title;
            this.anchorId = anchorId;
        }

        public String getTitle() {
            return title;
        }


        public String getAnchorId() {
            return anchorId;
        }
        
    }
}
