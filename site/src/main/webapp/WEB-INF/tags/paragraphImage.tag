<%@tag trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tag" %>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<%@ attribute name="position" type="java.lang.String" rtexprvalue="true" required="true"%>
<%@ attribute name="image" type="hslbeans.ParagraphImage" rtexprvalue="true" required="true"%>

<figure class="${position}">
<c:choose>
    <c:when test="${position == 'left' || position == 'right'}">
        <img src="<hst:link hippobean="${image.paragraphImageLink.paragraphImage }" />" 
             alt="<c:out value="${image.paragraphImageLink.alt }" escapeXml="true" />" 
             title="<c:out value="${image.paragraphImageLink.alt }" escapeXml="true" />" />
    </c:when>
    <c:otherwise>
        <img  src="<hst:link hippobean="${image.paragraphImageLink.wideImage }" />" 
              alt="<c:out value="${image.paragraphImageLink.alt }" escapeXml="true" />" 
              title="<c:out value="${image.paragraphImageLink.alt }" escapeXml="true" />" />
    </c:otherwise>
  </c:choose>
</figure>

<c:if test="${not empty image.paragraphImageLink.description }">
    <figcaption><span>
      <c:out value="${image.paragraphImageLink.description }" escapeXml="true" />
    </span></figcaption>
</c:if>