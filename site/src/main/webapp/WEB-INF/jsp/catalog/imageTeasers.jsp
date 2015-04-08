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
      <hst:cmseditlink hippobean="${item}" />
      <c:choose>
        
        <%-- External link case --%>
        <c:when test="${tag:getConfiguredLink(item) eq 'ext' }">
        
          <a href="${item.externallink.linkUrl}" rel="nofollow" title="${tag:getLinkAlt(item.externallink)}" 
                   ${item.externallink.newWindow ? 'class="external link" target="_blank"': '' }>      
              <div class="img">
                 <img src="<hst:link hippobean="${item.image.teaserImage }" />" 
                      alt="<c:out value="${item.image.alt }" escapeXml="true" />" />
              </div>
              
              <c:if test="${not empty item.icon }">
                <img class="icon imageTeaser" src="<hst:link hippobean="${item.icon.listImageMedium }" />" 
                     alt="<c:out value="${item.icon.alt }" escapeXml="true" />" />
              </c:if>
              
              <c:if test="${not item.noLinkTekst}">
                <span class="${item.darkLinkBackground ? 'btn darkLinkBackground' : 'btn'}">
                  <c:out value="${item.externallink.linkTitle }" escapeXml="true" />
                </span>
              </c:if>
          </a>
        </c:when>

        <%-- Internal link case --%>
        <c:when test="${tag:getConfiguredLink(item) eq 'int' }">
          <c:set var="link"><hst:link hippobean="${item.link.link }" /></c:set>
          <a href="${link}" title="${tag:getLinkAlt(item.link)}">
              <div class="img">
                 <img src="<hst:link hippobean="${item.image.teaserImage }" />" 
                      alt="<c:out value="${item.image.alt }" escapeXml="true" />" />
              </div>
              
              <c:if test="${not empty item.icon }">
                <img class="icon imageTeaser" src="<hst:link hippobean="${item.icon.listImageMedium }" />" 
                     alt="<c:out value="${item.icon.alt }" escapeXml="true" />" />
              </c:if>
              
              <c:if test="${not item.noLinkTekst}">
                <span class="${item.darkLinkBackground ? 'btn darkLinkBackground' : 'btn'}">
                  <c:out value="${item.link.linkTitle }" escapeXml="true" />
                </span>
              </c:if>
          </a>
        </c:when>
        
        <%-- Just image, no link case --%>        
        <c:otherwise>
          <a href="#" title="${item.image.alt }">
            <div class="img">
               <img src="<hst:link hippobean="${item.image.teaserImage }" />" 
                    alt="<c:out value="${item.image.alt }" escapeXml="true" />" 
                    title="<c:out value="${item.image.alt }" escapeXml="true" />" />
            </div>
            <c:if test="${not empty item.icon }">
              <img class="icon imageTeaser" src="<hst:link hippobean="${item.icon.listImageMedium }" />" 
                 alt="<c:out value="${item.icon.alt }" escapeXml="true" />" />
            </c:if>  
          </a>
        </c:otherwise>
      </c:choose>
      
    
    </section>
  </c:forEach>
  
  </section>

</c:if>
