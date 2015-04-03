package nl.hsleiden.utils;

import javax.jcr.Node;

import nl.hsleiden.tags.LinksAndBrowserTitleFunctions;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.content.rewriter.impl.SimpleContentRewriter;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.utils.SimpleHtmlExtractor;

public class HslHtmlRewriter extends SimpleContentRewriter {

    @Override
    public String rewrite(final String html, Node node, HstRequestContext requestContext, Mount targetMount) {

        // strip off html & body tag
        String rewrittenHtml = getInnerHtml(html);
        if (StringUtils.isEmpty(rewrittenHtml)) {
            return rewrittenHtml;
        }

        // only create if really needed
        StringBuilder sb = null;
        int globalOffset = 0;
        String documentLinkHref = null;

        while (rewrittenHtml.indexOf(LINK_TAG, globalOffset) > -1) {
            int offset = rewrittenHtml.indexOf(LINK_TAG, globalOffset);

            int hrefIndexStart = rewrittenHtml.indexOf(HREF_ATTR_NAME, offset);
            if (hrefIndexStart == -1) {
                break;
            }

            if (sb == null) {
                sb = new StringBuilder(rewrittenHtml.length());
            }

            hrefIndexStart += HREF_ATTR_NAME.length();
            offset = hrefIndexStart;
            int endTag = rewrittenHtml.indexOf(END_TAG, offset);
            boolean appended = false;
            if (hrefIndexStart < endTag) {
                int hrefIndexEnd = rewrittenHtml.indexOf(ATTR_END, hrefIndexStart);
                if (hrefIndexEnd > hrefIndexStart) {
                    String documentPath = rewrittenHtml.substring(hrefIndexStart, hrefIndexEnd);

                    offset = endTag;
                    sb.append(rewrittenHtml.substring(globalOffset, hrefIndexStart));

                    documentLinkHref = rewriteDocumentLink(documentPath, node, requestContext, targetMount);
                    if (documentLinkHref != null) {
                        documentLinkHref = composeMailToLink(documentLinkHref);
                        sb.append(documentLinkHref);
                        addRelNoFollow(sb, documentLinkHref);
                    }

                    sb.append(rewrittenHtml.substring(hrefIndexEnd, endTag));
                    appended = true;
                }
            }
            if (!appended && offset > globalOffset) {
                sb.append(rewrittenHtml.substring(globalOffset, offset));
            }
            globalOffset = offset;
        }

        if (sb != null) {
            sb.append(rewrittenHtml.substring(globalOffset, rewrittenHtml.length()));
            rewrittenHtml = String.valueOf(sb);
            sb = null;
        }

        globalOffset = 0;
        String binaryLinkSrc = null;

        while (rewrittenHtml.indexOf(IMG_TAG, globalOffset) > -1) {
            int offset = rewrittenHtml.indexOf(IMG_TAG, globalOffset);

            int srcIndexStart = rewrittenHtml.indexOf(SRC_ATTR_NAME, offset);

            if (srcIndexStart == -1) {
                break;
            }

            if (sb == null) {
                sb = new StringBuilder(rewrittenHtml.length());
            }
            srcIndexStart += SRC_ATTR_NAME.length();
            offset = srcIndexStart;
            int endTag = rewrittenHtml.indexOf(END_TAG, offset);
            boolean appended = false;
            if (srcIndexStart < endTag) {
                int srcIndexEnd = rewrittenHtml.indexOf(ATTR_END, srcIndexStart);
                if (srcIndexEnd > srcIndexStart) {
                    String srcPath = rewrittenHtml.substring(srcIndexStart, srcIndexEnd);
                    
                    offset = endTag;
                    sb.append(rewrittenHtml.substring(globalOffset, srcIndexStart));

                    binaryLinkSrc = rewriteBinaryLink(srcPath, node, requestContext, targetMount);
                    if (binaryLinkSrc != null) {
                        sb.append(binaryLinkSrc);
                    }

                    sb.append(rewrittenHtml.substring(srcIndexEnd, endTag));
                    appended = true;
                }
            }
            if (!appended && offset > globalOffset) {
                sb.append(rewrittenHtml.substring(globalOffset, offset));
            }
            globalOffset = offset;
        }

        if (sb == null) {
            return rewrittenHtml;
        } else {
            sb.append(rewrittenHtml.substring(globalOffset, rewrittenHtml.length()));
            return sb.toString();
        }
    }

    private String getInnerHtml(final String html) {
        if (html == null) {
            return null;
        }
        String innerHTML = SimpleHtmlExtractor.getInnerHtml(html, "body", false);
        if (innerHTML == null) {
            if (HTML_TAG_PATTERN.matcher(html).find() || BODY_TAG_PATTERN.matcher(html).find()) {
                return null;
            }
            return html;
        } else {
            return innerHTML;
        }
    }

    private void addRelNoFollow(StringBuilder sb, String documentLinkHref) {
        if(isExternal(documentLinkHref)){                        
            sb.append("\" rel=\"nofollow");
        }
    }

    private String composeMailToLink(String documentLinkHref) {
        String result = documentLinkHref;
        if(result.startsWith("mailto")){
            result ="#\""
                    +" class=\"nospam\" data-n=\"" 
                    + LinksAndBrowserTitleFunctions.getMailName(result)
                    +"\" data-d=\"" 
                    + LinksAndBrowserTitleFunctions.getMailDomain(result)
                    +"\" data-e=\""
                    + LinksAndBrowserTitleFunctions.getExtraMailInfo(result);
        }
        return result;
    }
}
