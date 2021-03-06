<%@tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<%@ attribute name="document" type="hslbeans.WebPage" rtexprvalue="true" required="true"%>
<%@ attribute name="dateClass" type="java.lang.String" rtexprvalue="true"%>
<%@ attribute name="showYear" type="java.lang.Boolean" rtexprvalue="true"%>

<hst:setBundle basename="nl.hsleiden.widget.Messages" />

<c:set var="cssDateClass">
  <c:choose>
    <c:when test="${not empty dateClass }">
      <c:out value="${dateClass }"/>
    </c:when>
    <c:otherwise>
      <c:out value="datum"/>
    </c:otherwise>
  </c:choose>
</c:set>

<fmt:formatDate value="${document.startDate.time}" type="date" pattern="dd" dateStyle="medium" timeStyle="short" var="day" />
<fmt:formatDate value="${document.startDate.time}" type="date" pattern="MMM" dateStyle="medium" timeStyle="short" var="month" />
<fmt:formatDate value="${document.startDate.time}" type="date" pattern="yyyy" dateStyle="medium" timeStyle="short" var="year" />

<fmt:formatDate type="date" pattern="yyyy-MM-dd" var="dateTimeS" value="${document.startDate.time}"/>
 
<c:if test="${not empty day and not empty month}">
  <time datetime="${dateTime}" class="${cssDateClass}">
    ${fn:escapeXml(day)}
    <span>${fn:escapeXml(month)}</span>
  </time>
</c:if>

                


