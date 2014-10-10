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

<section class="catalog image teasers">

  <c:forEach var="item" items="${model.items}" varStatus="zebra">
    <section class="blok download image">
      <c:choose>
        
        <%-- External link case --%>
        <c:when test="${tag:getConfiguredLink(item) eq 'ext' }">
        
          <c:set var="alt">
            <c:choose>
              <c:when test="${not empty item.externallink.alt }">
                <c:out value="${item.externallink.alt}"></c:out>
              </c:when>
              <c:otherwise>
                <c:out value="${item.externallink.linkTitle}"></c:out>
              </c:otherwise>
            </c:choose>
          </c:set>
          
          <a href="${item.externallink.linkUrl}" title="${alt}" ${item.externallink.newWindow ? 'class="external link" target="_blank"': '' }>      
              <div class="img">
                 <img src="<hst:link hippobean="${item.image.teaserImage }" />" 
                      alt="<c:out value="${item.image.alt }" escapeXml="true" />" />
              </div>
              <c:if test="${not empty item.icon }">
                <img class="icon imageTeaser" src="<hst:link hippobean="${item.icon.listImageMedium }" />" 
                     alt="<c:out value="${item.icon.alt }" escapeXml="true" />" />
              </c:if>
              <span class="btn"><c:out value="${item.externallink.linkTitle }" escapeXml="true" /></span>
          </a>
        </c:when>

        <%-- Internal link case --%>
        <c:when test="${tag:getConfiguredLink(item) eq 'int' }">
          <c:set var="link"><hst:link hippobean="${item.internallink.link }" /></c:set>
          <a href="${link}" title="${item.internallink.alt}">
              <div class="img">
                 <img src="<hst:link hippobean="${item.image.teaserImage }" />" 
                      alt="<c:out value="${item.image.alt }" escapeXml="true" />" />
              </div>
              <c:if test="${not empty item.icon }">
                <img class="icon imageTeaser" src="<hst:link hippobean="${item.icon.listImageMedium }" />" 
                     alt="<c:out value="${item.icon.alt }" escapeXml="true" />" />
              </c:if>
              <span class="btn"><c:out value="${item.internallink.linkTitle }" escapeXml="true" /></span>
          </a>
        </c:when>
        
        <%-- Just image, no link case --%>        
        <c:otherwise>
          <div class="img">
             <img src="<hst:link hippobean="${item.image.teaserImage }" />" 
                  alt="<c:out value="${item.image.alt }" escapeXml="true" />" 
                  title="<c:out value="${item.image.alt }" escapeXml="true" />" />
          </div>
          <c:if test="${not empty item.icon }">
            <img class="icon imageTeaser" src="<hst:link hippobean="${item.icon.listImageMedium }" />" 
               alt="<c:out value="${item.icon.alt }" escapeXml="true" />" />
          </c:if>  
        </c:otherwise>
      </c:choose>
      
    
    </section>
  </c:forEach>
  
  </section>

</c:if>
