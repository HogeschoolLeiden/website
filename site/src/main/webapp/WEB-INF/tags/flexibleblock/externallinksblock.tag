<%@tag description="display image in header width" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<hst:setBundle basename="nl.hsleiden.general.Messages"/>

<%@ attribute name="content" rtexprvalue="true" required="true" type="hslbeans.ExternalLinks" %>

<c:if test="${not empty content.title }">
  <h2>
    <c:if test="${not empty content.titleIcon }">
      <img src="<hst:link hippobean="${content.titleIcon.paragraphImage }" />" 
           alt="<c:out value="${content.title }" escapeXml="true" />" 
           title="<c:out value="${content.title }" escapeXml="true" />" 
           class="title-icon"/>
    </c:if>
    <c:out value="${content.title }" escapeXml="true" />
  </h2>
</c:if>

<ul class="externalLinks">
  <c:forEach items="${content.externallinks}" var="item">
    <c:set var="alt">
      <c:choose>
        <c:when test="${not empty item.alt }">
          <c:out value="${item.alt}"></c:out>
        </c:when>
        <c:otherwise>
          <c:out value="${item.linkTitle}"></c:out>
        </c:otherwise>
      </c:choose>
    </c:set>
    <li class="link">
      <a href="${item.linkUrl}" title="${alt}" ${item.newWindow ? 'class="external link" target="_blank"': '' }>
        <span>${item.linkTitle}</span>
      </a>
    </li>
  </c:forEach>
</ul>