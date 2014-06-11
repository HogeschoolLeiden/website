<%@tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tag" %>

<%@ attribute name="content" rtexprvalue="true" required="true" type="hslbeans.ParagraphCompound" %>
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
  <h3><c:out value="${content.title }" escapeXml="true" /> </h3>
</c:if>

<c:choose>
  <c:when test="${not empty content.paragraphImage }">
    <c:choose>
      <c:when test="${position == 'bottom' }">
        <div class="image">
          <hst:html hippohtml="${content.content }"/>
          <tag:paragraphImage position="${position }" image="${content.paragraphImage }" />
        </div>
      </c:when>
      <c:otherwise>
        <div class="image">
          <tag:paragraphImage position="${position }" image="${content.paragraphImage }" />
          <hst:html hippohtml="${content.content }"/>
        </div>
      </c:otherwise>
    </c:choose>
  </c:when>
  <c:otherwise>
    <hst:html hippohtml="${content.content }"/>
  </c:otherwise>
</c:choose>