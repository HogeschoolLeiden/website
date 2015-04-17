// $Id: SearchResultItem.java 353 2013-07-10 14:41:19Z rharing $
package nl.hinttech.hsleiden.pi.beans;

import java.util.ArrayList;
import java.util.List;

import org.hippoecm.hst.content.beans.standard.HippoBean;

/**
 * Convenience class that represents one single search result item.
 * When a searchresult is an asset the this item will also contains a list 
 * of documents  (if present) that refer to this asset.
 * 
 */
public class SearchResultItem {

    private HippoBean document;
    private Metadata metadata = new Metadata();
    private Asset attachment;
    private List<? extends HippoBean> referingDocuments = new ArrayList<HippoBean>();

    public SearchResultItem(HippoBean document, Metadata metadata) {
        this.document = document;
        if (metadata != null) {
            this.metadata = metadata;
        }
    }

    public SearchResultItem(Asset attachment, List<? extends HippoBean> referingDocuments) {
        if (referingDocuments != null) {
            this.referingDocuments = referingDocuments;
        }
        this.attachment = attachment;
    }

    public SearchResultItem(Asset attachment) {
        this.attachment = attachment;
    }

    public SearchResultItem(Asset attachment, List<? extends HippoBean> referingDocuments, Metadata metadata) {
        if (referingDocuments != null) {
            this.referingDocuments = referingDocuments;
        }
        this.attachment = attachment;
        if (metadata != null) {
            this.metadata = metadata;
        }
    }

    public HippoBean getDocument() {
        return document;
    }

    public Asset getAttachment() {
        return attachment;
    }

    public List<? extends HippoBean> getReferingDocuments() {
        return referingDocuments;
    }

    public Metadata getMetadata() {
        return metadata;
    }

}
