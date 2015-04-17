package nl.hinttech.hsleiden.pi.beans;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.content.beans.standard.HippoAsset;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoGalleryImageBean;
import org.hippoecm.hst.content.beans.standard.HippoGalleryImageSet;
import org.hippoecm.hst.content.beans.standard.HippoResourceBean;

/**
 * Convenience class that represents an Asset (either an Image or an Attachment).
 * 
 * @author rob
 */
public class Asset {
    
    public enum FileType {word, pdf, excel, img, powerpoint, zip}
    
    private HippoBean document;
    private String title;
    private FileType type;
    private String size;
    
    private static Map<String, FileType> typeMap;
    private static DecimalFormat sizeFormat = new DecimalFormat("#.00");
    
    static {
        typeMap = new HashMap<String, FileType>();
        typeMap.put("pdf", FileType.pdf);
        typeMap.put("doc", FileType.word);
        typeMap.put("docx", FileType.word);
        typeMap.put("xls", FileType.excel);
        typeMap.put("xlsx", FileType.excel);
        typeMap.put("zip", FileType.zip);
        typeMap.put("rar", FileType.zip);
        typeMap.put("jpg", FileType.img);
        typeMap.put("png", FileType.img);  
    }
    
    public Asset(HippoGalleryImageSet document) {
        this(document, document.getFileName());
    }

    public Asset(HippoGalleryImageSet document, String title) {

        this.document = document;
        this.title = title;

        HippoGalleryImageBean image = document.getOriginal();

        type = getTypeFromFileExtension(document.getFileName());
        size = getSize(image.getLengthKB(), image.getLengthMB());
    }

    public Asset(HippoAsset document, String title) {

        this.document = document;
        this.title = title;

        HippoResourceBean asset = document.getAsset();

        type = getTypeFromFileExtension(document.getName());
        size = getSize(asset.getLengthKB(), asset.getLengthMB());    
    }
    
    public Asset(HippoAsset document) {
        this(document, document.getLocalizedName());
    }
    
    private FileType getTypeFromFileExtension(String fileName) {
        String extension = FilenameUtils.getExtension(fileName);
       
        if (StringUtils.isNotBlank(extension)) {
            extension = extension.toLowerCase();
            return typeMap.get(extension);
        }
        return null;
    }

    private String getSize(BigDecimal sizeInKiloBytes, BigDecimal sizeInMegaBytes) {
        String size = "";
        if (sizeInKiloBytes.longValue() < 1000) {
            size = sizeFormat.format(sizeInKiloBytes) + " KB";
        } else {
            size = sizeFormat.format(sizeInMegaBytes) + " MB";
        }
        return size;
    }
    
    public HippoBean getDocument() {
        return document;
    }

    public String getTitle() {
        return title;
    }

    public FileType getType() {
        return type;
    }

    public String getSize() {
        return size;
    }

}
