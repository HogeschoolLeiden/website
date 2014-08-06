<%@tag description="display image in header width" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<%@ attribute name="content" rtexprvalue="true" required="true" type="nl.hsleiden.beans.PublicImagesBean" %>



<hst:headContribution keyHint="jquery">
  <script type="text/javascript" src="<hst:link path="/js/jquery-1.9.1.min.js"/>" charset="utf-8"></script>
</hst:headContribution>

<hst:headContribution keyHint="jqueryUICss">
  <link rel="stylesheet" href="<hst:link path="/css/prettyPhoto.css"/>" type="text/css" media="screen" />
</hst:headContribution>

<hst:headContribution keyHint="prettyPhoto">
  <script type="text/javascript" src="<hst:link path="/js/jquery.prettyPhoto.js"/>" charset="utf-8"></script>
</hst:headContribution>


<c:if test="${fn:length(content.images) > 0 }">
  
  <div class="images${content.imagesPerRow}">
    
    <c:forEach items="${content.images}" var="image" varStatus="loop">
      
      <div class="singleImage">
        <div class="singleImagePadding">

          <figure class="fexibleblock image">
                  
            <a href="<hst:link hippobean="${image.wideImage }" />" rel="prettyPhoto" class="pin-it-button">
              <img  src="<hst:link hippobean="${image.wideImage }" />" 
                    alt="<c:out value="${image.alt }" escapeXml="true" />" 
                    title="<c:out value="${image.alt }" escapeXml="true" />" 
              />
            </a>
                      
          </figure>
       
        </div>        
      </div>
      
    </c:forEach>
    

  </div>
  
 <%-- 
   <a href="http://pinterest.com/pin/create/button/?url='+encodeURIComponent(location.href.replace(location.hash,""))+'&media={path}&description={title}" class="pin-it-button" count-layout="horizontal" target="_blank">
    <img border="0" src="http://assets.pinterest.com/images/PinExt.png" title="Pin It" />

  </a> --%>

</c:if>
