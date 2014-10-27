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


<fmt:formatDate value="${document.eventDate.time}" type="date" pattern="dd" dateStyle="medium" timeStyle="short" var="dayS" />
<fmt:formatDate value="${document.eventDate.time}" type="date" pattern="MMM" dateStyle="medium" timeStyle="short" var="monthS" />
<fmt:formatDate value="${document.eventDate.time}" type="date" pattern="yyyy" dateStyle="medium" timeStyle="short" var="yearS" />

<fmt:formatDate type="date" pattern="yyyy-MM-dd" var="dateTimeS" value="${document.eventDate.time}"/>

<fmt:formatDate value="${document.eventEndDate.time}" type="date" pattern="dd" dateStyle="medium" timeStyle="short" var="dayE" />
<fmt:formatDate value="${document.eventEndDate.time}" type="date" pattern="MMM" dateStyle="medium" timeStyle="short" var="monthE" />
<fmt:formatDate value="${document.eventEndDate.time}" type="date" pattern="yyyy" dateStyle="medium" timeStyle="short" var="yearE" />

<fmt:formatDate type="date" pattern="yyyy-MM-dd" var="dateTimeE" value="${document.eventDate.time}"/>


<c:choose>
  <c:when test="${(dayS eq dayE) and (monthS eq monthE)}">
    <%-- show single date --%>
    <time datetime="${dateTimeS}" class="${cssDateClass}">
      ${fn:escapeXml(dayS)}
      <span>${fn:escapeXml(monthS)}</span>
    </time>    
  </c:when>
  <c:otherwise>
    <%-- show double dates --%>
    <c:set var="day">
      <c:choose>
        <c:when test="${(dayS eq dayE)}">
            ${fn:escapeXml(dayS)}
        </c:when>
        <c:otherwise>
            ${fn:escapeXml(dayS)} - ${fn:escapeXml(dayE)}
        </c:otherwise>
      </c:choose>
    </c:set>
    
    <c:set var="month">
      <c:choose>
        <c:when test="${(monthS eq monthE)}">
            ${fn:escapeXml(monthS)}
        </c:when>
        <c:otherwise>
            ${fn:escapeXml(monthS)} - ${fn:escapeXml(monthE)}
        </c:otherwise>
      </c:choose>
    </c:set>
    
    <c:choose>
    
      <c:when test="${fn:contains(cssDateClass, 'large')}">
        <%-- show double dates in one circle --%>
        <time datetime="${dateTimeS}" class="${cssDateClass}">
          ${fn:escapeXml(day)}
          <span>${fn:escapeXml(month)}</span>
        </time>
      </c:when>
      
      <c:otherwise>
        <%-- show double dates in two circles --%>
        <time datetime="${dateTimeS}" class="${cssDateClass}">
          ${fn:escapeXml(dayS)}
          <span>${fn:escapeXml(monthS)}</span>
        </time>
        
        <time datetime="${dateTimeE}" class="datum end">
          ${fn:escapeXml(dayE)}
          <span>${fn:escapeXml(monthE)}</span>
        </time>
      </c:otherwise>
    
    </c:choose>
    
  </c:otherwise>
</c:choose>
                


