<%@tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tag" %>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<%@ attribute name="content" rtexprvalue="true" required="true" type="hslbeans.Paragraph" %>
<c:choose>
  <c:when test="${content.paragraphImage.position == 'right' }">
    <c:set var="position" value="right" />
  </c:when>
  <c:when test="${content.paragraphImage.position == 'top' }">
    <c:set var="position" value="top" />
  </c:when>
  <c:when test="${content.paragraphImage.position == 'bottom' }">
    <c:set var="position" value="bottom" />
  </c:when>
  <c:otherwise>
    <c:set var="position" value="left" />
  </c:otherwise>
</c:choose>

<c:if test="${not empty content.title }">
  <h2>
    <c:if test="${not empty content.titleIcon }">
      <img src="<hst:link hippobean="${content.titleIcon.listImageSmall }" />" 
           alt="<c:out value="${content.title }" escapeXml="true" />" 
           title="<c:out value="${content.title }" escapeXml="true" />" 
           class="title-icon"/>
    </c:if>
    <c:out value="${content.title }" escapeXml="true" />
  </h2>
</c:if>
                
<c:choose>
  <c:when test="${not empty content.paragraphImage }">
    <c:choose>
      <c:when test="${position == 'left' || position == 'right' || position == 'top'}"> 
        <tag:paragraphImage position="${position }" image="${content.paragraphImage }" />
        <hst:html hippohtml="${content.content }" 
            contentRewriter="${pageContext.request.requestContext.attributes['hslHtmlRewriter']}" />
      </c:when>
      <c:otherwise>
        <hst:html hippohtml="${content.content }"
            contentRewriter="${pageContext.request.requestContext.attributes['hslHtmlRewriter']}" />
        <tag:paragraphImage position="${position }" image="${content.paragraphImage }" />
      </c:otherwise>
    </c:choose>
  </c:when>
  <c:otherwise>
    <hst:html hippohtml="${content.content }"
            contentRewriter="${pageContext.request.requestContext.attributes['hslHtmlRewriter']}" />
  </c:otherwise>
</c:choose>