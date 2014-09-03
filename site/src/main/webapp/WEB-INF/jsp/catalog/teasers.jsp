<%@ page contentType="text/html; charset=UTF-8" language="java"
  trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>

<hst:setBundle basename="nl.hsleiden.channelmanager.Messages, nl.hsleiden.widget.Messages, nl.hsleiden.blog.Messages"/>

<hst:defineObjects />
<c:set var="isCmsRequest" value="${hstRequest.requestContext.cmsRequest}" />

<c:if test="${(empty model.items or fn:length(model.items) eq 0) and not empty webMasterMessage and isCmsRequest}">
	<p class="error-message"><fmt:message key="${webMasterMessage}" /></p>
</c:if>

<c:if test="${not empty model.items and fn:length(model.items)>0}">

  <div class="catalog teasers">
    <div class="widget-title">
      <h2><c:out value="${model.info.widgetTitle}" escapeXml="true" /></h2>
    </div>
    
    <c:forEach var="item" items="${model.items}" varStatus="zebra">
      <c:if test="${not empty item }">
        
        <c:set var="sectionClass">
          <c:choose>
            <c:when test="${item.color eq 'blauw'}">
              <c:out value="colorbg blauw large"/>
            </c:when>
            <c:when test="${item.color eq 'groen'}"> 
              <c:out value="colorbg groen medium"/>
            </c:when>
            <c:otherwise>
               <c:out value="colorbg"/>
            </c:otherwise>
          </c:choose>
        </c:set> 
        
        <section class="blok ${sectionClass}">
            <c:set var="displayLink"><hst:link hippobean="${link.internallink.link }" /></c:set>
            <a href="${displayLink}" title="${item.link.alt}">
              <h1>
                <c:out value="${item.title}"/>
              </h1>
              <h2><c:out value="${item.body}"/></h2>
              <span class="btn"><c:out value="${item.link.linkTitle}"/></span>
            </a>
        </section>
        
      </c:if>
    </c:forEach>
    
  </div>
</c:if>
