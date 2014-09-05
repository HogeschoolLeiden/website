<%@tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<%@ attribute name="link" type="hslbeans.Link" rtexprvalue="true" required="true"%>

<hst:setBundle basename="nl.hsleiden.widget.Messages" />

<c:set var="doNotDisplay" value="false"></c:set>
<c:choose>
  <c:when test="${not empty link.internallink}">
    <c:set var="displayLink"><hst:link hippobean="${link.internallink.link }" /></c:set>
    <c:set var="alt" value="${link.internallink.alt}"/>
    <c:set var="newWindow" value=""/>
    <c:set var="title" value="${link.internallink.linkTitle}"/>
  </c:when>
  <c:when test="${not empty link.externallink}">
    <c:set var="displayLink" value="${link.externallink.linkUrl}"/>
    <c:set var="alt" value="${link.externallink.alt}"/>
    <c:set var="newWindow">
      ${link.externallink.newWindow ? "class='external link' target='_blank'": "" }
    </c:set>
    <c:set var="title" value="${link.externallink.linkTitle}"/>                  
  </c:when>
  <c:otherwise>
    <c:set var="doNotDisplay" value="true"></c:set>
  </c:otherwise>
</c:choose>

<c:if test="${not doNotDisplay }">                
  <li class="link">
    <a href="${displayLink}" title="${alt}" ${newWindow}>
      <span class="h3">${title}</span>
    </a>
  </li>
</c:if>

