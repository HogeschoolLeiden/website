<%@ page contentType="text/html; charset=UTF-8" language="java"
  trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>

<hst:setBundle basename="nl.hsleiden.channelmanager.Messages, nl.hsleiden.widget.Messages"/>

<hst:defineObjects />
<c:set var="isCmsRequest" value="${hstRequest.requestContext.cmsRequest}" />

<c:if test="${(empty model.items or fn:length(model.items) eq 0) and not empty webMasterMessage and isCmsRequest}">
	<p class="error-message"><fmt:message key="${webMasterMessage}" /></p>
</c:if>

<c:if test="${not empty model.items and fn:length(model.items)>0}">

  <section class="catalog head teasers">
        
    <c:forEach var="item" items="${model.items}" varStatus="zebra">
      <c:if test="${not empty item }">
        
        <c:set var="sectionClass">
          <c:choose>
            <c:when test="${item.color eq 'blauw'}">
              <c:out value="colorbg blauw large headteaser"/>
            </c:when>
            <c:when test="${item.color eq 'groen'}"> 
              <c:out value="colorbg groen medium headteaser"/>
            </c:when>
            <c:when test="${item.color eq 'oranje'}"> 
              <c:out value="colorbg oranje medium headteaser"/>
            </c:when>
            <c:when test="${item.color eq 'magenta'}"> 
              <c:out value="colorbg magenta medium headteaser"/>
            </c:when>
            <c:otherwise>
               <c:out value="colorbg"/>
            </c:otherwise>
          </c:choose>
        </c:set> 
        
        <section class="blok ${sectionClass}">
            <hst:cmseditlink hippobean="${item}" />
            
            <c:choose>
            
              <%-- External link case --%>
              <c:when test="${tag:getConfiguredLink(item) eq 'ext' }">
            
                <a href="${item.externallink.linkUrl}" title="${tag:getLinkAlt(item.externallink)}" 
                       ${item.externallink.newWindow ? 'class="external link" target="_blank"': '' }>      
                  <h1><c:out value="${item.title}"/></h1>
                  <h2><c:out value="${item.body}"/></h2>
                  <span class="btn"><c:out value="${item.externallink.linkTitle}"/></span>
                </a>
              </c:when>
        
              <%-- Internal link case --%>
              <c:when test="${tag:getConfiguredLink(item) eq 'int' }">            
                <a href='<hst:link hippobean="${item.link.link }"/>' title="${tag:getLinkAlt(item.link)}">
                  <h1><c:out value="${item.title}"/></h1>
                  <h2><c:out value="${item.body}"/></h2>
                  <span class="btn"><c:out value="${item.link.linkTitle}"/></span>
                </a>
              </c:when>
              
              <%-- no link case --%>
              <c:otherwise>
                <a href="#" title="${item.title}">
                  <h1><c:out value="${item.title}"/></h1>
                  <h2><c:out value="${item.body}"/></h2>
                  <span class="btn"><c:out value="take it from properties"/></span>
                </a>
              </c:otherwise>
            </c:choose>
            
            
        </section>
        
      </c:if>
    </c:forEach>
    
  </section>
</c:if>
