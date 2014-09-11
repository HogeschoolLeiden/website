<%@ page contentType="text/html; charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>

<hst:setBundle basename="nl.hsleiden.channelmanager.Messages, nl.hsleiden.widget.Messages, nl.hsleiden.blog.Messages" />

<hst:defineObjects />
<c:set var="isCmsRequest" value="${hstRequest.requestContext.cmsRequest}" />

<c:if test="${(empty model.items or fn:length(model.items) eq 0) and not empty webMasterMessage and isCmsRequest}">
  <p class="error-message">
    <fmt:message key="${webMasterMessage}" />
  </p>
</c:if>

<c:if test="${not empty model.items and fn:length(model.items)>0}">
  
  <c:forEach var="item" items="${model.items}" varStatus="zebra">
  
    <section class="blok colorbg lichtpaars contacts">
      
      <%-- should it be part of the contact item or common 
            through all the contacts added from the same widget --%>
      <h1>
        <span class="col-md-8 col-xs-8 col-md-offset-4 col-xs-offset-4">
        <c:out value="${model.info.widgetTitle}" escapeXml="true" />
        </span>
      </h1>
    
      <c:if test="${not empty item }">
        <div itemscope itemtype="http://data-vocabulary.org/Person" class="contact clearfix">
          <ul class="col-md-8 col-xs-8 pull-right">
            <li><h2><span itemprop="name">${item.name }</span></h2></li>
            <li><h2><span itemprop="role">${item.function }</span></h2></li>            
            <c:if test="${not empty item.mail }">
              <li>
                <c:set var="email">mailto:${item.mail}?subject=<fmt:message key="contact.person.subject"/></c:set>
                <a title="${item.mail }" href="${fn:replace(email, ' ', '%20')}">
                    <c:out value="${item.mail }" />
                  </a>
              </li>
            </c:if>
            <li>${item.phone}</li>
          </ul>
          
          <figure class="col-md-4 col-xs-4 pull-left">
            <!-- img size 60 x 60 -->
            <hst:link var="image" hippobean="${item.image.listImageMedium }" />
            <img itemprop="photo" alt="${item.name }" src="${image }" title="${item.name }" />
            
            <c:if test="${fn:length(item.accounts)>0}">
              <tag:socialaccounts document="${item}"/>
            </c:if>
            
          </figure>
                    
        </div>
      </c:if>    
    
    </section>
  </c:forEach>
</c:if>
