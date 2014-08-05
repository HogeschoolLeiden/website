<%@tag description="display image in header width" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<%@ attribute name="content" rtexprvalue="true" required="true" type="nl.hsleiden.beans.PublicImagesBean" %>

<c:if test="${fn:length(content.images) > 0 }">
  
  <div class="images${content.imagesPerRow}">
    <c:forEach items="${content.images}" var="image" varStatus="loop">
      <%-- <c:choose>
        <c:when test="${loop.index mod content.imagesPerRow != 0 }">
        </c:when>
      </c:choose> --%>
      
      <div class="singleImage">
        <div class="singleImagePadding">
        <c:set var="imageTag">
          <figure class="fexibleblock image">
            <img  src="<hst:link hippobean="${image.wideImage }" />" 
                  alt="<c:out value="${image.alt }" escapeXml="true" />" 
                  title="<c:out value="${image.alt }" escapeXml="true" />" />
            
            <hst:headContribution category="metadata" keyHint="image">
              <meta property="og:image" content="<hst:link hippobean="${image.wideImage }" />"/>
            </hst:headContribution>      
            
                  <c:if test="${not empty image.description }">
                    <figcaption>
                    <c:out value="${image.description }" escapeXml="true" />
                    </figcaption>
                  </c:if>
          </figure>
        </c:set>
        ${imageTag}
        </div>        
      </div>
      
    </c:forEach>

  </div>
</c:if>
