package nl.hsleiden.beans;

import hslbeans.CssDocument;

import org.hippoecm.hst.content.beans.Node;

@Node(jcrType = "hsl:CssDocument")
public class CssDocumentBean extends CssDocument{
    
    public String getExtraCss() {
       return "<style>"+getCss()+"</style>"; 
    }
}
