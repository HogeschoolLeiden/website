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
	<p class="error-message"><fmt:message key="${webMasterMessage}" ></fmt:message></p>
</c:if>

<c:if test="${not empty model.items }">

  <section class="overzichtlijst related events">
  
    <c:if test="${not empty model.info.widgetParameters.widgetTitle}">
      <h2 class="widgetTitle"><c:out value="${model.info.widgetParameters.widgetTitle}" escapeXml="true" /></h2>
    </c:if>
    
    <c:forEach var="item" items="${model.items}">
    
      <hst:link var="link" hippobean="${item}" />
      
      <article class="media clearfix">
        <hst:cmseditlink hippobean="${item}" />
        <c:set var="image" value=""/>
        
        <c:if test="${not empty item.headerImage }">
            <hst:link var="image" hippobean="${item.headerImage.listImageMedium}" />  
        </c:if>
    
        <a href="${link}">
          <!-- afmeting afbeelding: 100x100 -->
          <figure class="media-object pull-left">
            <c:if test="${not empty image}">
              <img alt="${item.title }" title="${item.title }" src="${image }" />
            </c:if>
          </figure>
          <tag:renderDate document="${item}" dateClass="datum start"/>
          <div class="media-body">
            <h1 class="media-heading"><c:out value="${item.title }"/></h1>
            <p><c:out value="${item.introduction }"/></p>
          </div>   
        </a>     
    
      </article> 
      
    </c:forEach>
    
  </section>
</c:if>