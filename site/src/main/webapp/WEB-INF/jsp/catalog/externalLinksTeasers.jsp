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
  
  <section class="catalog externalLinks teasers">     
  
    <c:forEach var="item" items="${model.items}" varStatus="zebra">
      <c:if test="${not empty item }">
        <div class="col-xs-12 col-sm-6">
        
        <article class="tekstitem">
            
            <hst:cmseditlink hippobean="${item}" />
            
            <h2>
              <c:if test="${not empty item.titleIcon }">
                <span class="icon">
                  <img src="<hst:link hippobean="${item.titleIcon.listImageSmall }" />" 
                     alt="<c:out value="${item.title }" escapeXml="true" />" 
                     title="<c:out value="${item.title }" escapeXml="true" />" 
                     class="title-icon"/>
                </span>
              </c:if>
              <c:out value="${item.title }" escapeXml="true" />
            </h2>
            
            <ul>
              <c:forEach items="${item.externallinks}" var="linkListItem">
                <li>
                  <a href="${linkListItem.linkUrl}" rel="nofollow" title="${tag:getLinkAlt(linkListItem)}" ${linkListItem.newWindow ? 'class="external link" target="_blank"': '' }>
                    <span>${linkListItem.linkTitle}</span>
                  </a>
                </li>
              </c:forEach>
            </ul>
            
            <c:if test="${hst:isReadable(item.link, 'link') and 
                        not empty item.link.link and 
                        not empty item.link.linkTitle}">            
              <c:set var="displayLink"><hst:link hippobean="${item.link.link }" /></c:set>
              <a class="more" href="${displayLink}" title="${item.link.alt}"><c:out value="${item.link.linkTitle}"/></a>
            </c:if>
            
        </article>
        
        </div>
        
      </c:if>
    </c:forEach>
  </section>  
  
  <div class="clearfix"></div>
  
</c:if>
