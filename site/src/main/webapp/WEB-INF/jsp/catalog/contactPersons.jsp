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

<div itemscope itemtype="http://data-vocabulary.org/Person" class="catalog contactPersons">
  <hst:defineObjects />
  <c:set var="isCmsRequest" value="${hstRequest.requestContext.cmsRequest}" />

  <c:if test="${(empty model.items or fn:length(model.items) eq 0) and not empty webMasterMessage and isCmsRequest}">
  	<p class="error-message"><fmt:message key="${webMasterMessage}" /></p>
  </c:if>

  <c:if test="${not empty model.items and fn:length(model.items)>0}">
    <div class="widget-title">
      <h2><c:out value="${model.info.widgetTitle}" escapeXml="true" /></h2>
    </div>
    
    <c:forEach var="item" items="${model.items}" varStatus="zebra">
      <c:if test="${not empty item }">
      <div class="item-with-image">
        
        <h3 class="contact"><span itemprop="name">${item.name }</span></h3>
        <c:if test="${not empty item.image }">
          <div class="image-space contact">
            <hst:link var="image" hippobean="${item.image.listImageMedium }" />
            <img itemprop="photo" alt="${item.name }" src="${image }" />
          </div>
        </c:if>   

        <div class="itemContent">
          <p class="function"><span itemprop="role">${item.function }</span></p>
          <c:if test="${not empty item.mail }">
            <c:set var="email">
                mailto:${item.mail}?subject=<fmt:message key="contact.person.subject" />
            </c:set>
            <p class="contact email">
              <a class="contact email" title="${item.mail }" 
                 href="<c:out value="${email}" escapeXml="true" />">
                 <c:out value="${item.mail }"/>     
              </a>   
            </p>
          </c:if>
          <p>${item.phone }</p>
        </div>
        
        <div class="clear"></div>
      </div>
      </c:if>
    </c:forEach>
    
  </c:if>
</div>
