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
        <div class="item-with-image">
        
          <%-- teaser title --%>
          <c:if test="${not empty item.title }">
            <h2>
              <c:if test="${not empty item.icon }">
                <img src="<hst:link hippobean="${item.icon.paragraphImage }" />" 
                     alt="<c:out value="${item.title }" escapeXml="true" />" 
                     title="<c:out value="${item.title }" escapeXml="true" />" 
                     class="title-icon"/>
              </c:if>
              <c:out value="${item.title }" escapeXml="true" />
            </h2>
          </c:if>
          
          <%-- teaser image --%>
          <c:if test="${not empty item.image }">
            <div class="teaser-image">
              <hst:link var="image" hippobean="${item.image.listImageLarge }" />
              <img alt="${item.title }" src="${image }" title="${item.title }"/>
            </div>
          </c:if>
          
          <%-- teaser text --%>
          <c:if test="${not empty item.body }">
            <hst:html hippohtml="${item.body }"/>
          </c:if>
                      
          <%-- teaser list of links --%>
          <c:if test="${not empty item.links }">
            <ul class="teaserLinks">
              <c:forEach items="${item.links }" var="link">
                <tag:renderLink link="${link}"/>
              </c:forEach>
            </ul>
          </c:if>
          
        </div>
      </c:if>
    </c:forEach>
    
  </div>
</c:if>
