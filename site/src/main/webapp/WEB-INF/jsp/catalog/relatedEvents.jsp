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

<div class="catalog relatedNews">
  <hst:defineObjects />
  <c:set var="isCmsRequest" value="${hstRequest.requestContext.cmsRequest}" />

  <c:if test="${(empty model.items or fn:length(model.items) eq 0) and not empty webMasterMessage and isCmsRequest}">
  	<p class="error-message"><fmt:message key="${webMasterMessage}" ></fmt:message></p>
  </c:if>

  <c:if test="${not empty model.items }">
    <div class="widget-title">
      <h2><c:out value="${model.info.widgetTitle}" escapeXml="true" /></h2>
    </div>
    <%-- Items in-inlined for better performance --%>
    <c:forEach var="item" items="${model.items}" varStatus="zebra">
      <div class="item-with-image">
          <c:if test="${not empty tag:getFirstFlexibleBlockImage(item) }">
            <div class="image-space">
              <hst:link var="image" hippobean="${tag:getFirstFlexibleBlockImage(item).image.listImageMedium }" />
              <img alt="${item.title }" src="${image }" />
            </div>
          </c:if>   
        
        <div class="itemContent">
          <div class=itemTitle>
            <hst:link hippobean="${item }" var="link" />
            <c:set var="title">
              <c:out value="${item.title }" escapeXml="true" />
            </c:set>
            <a href="${link }" title="${title }">${title }</a>
          </div>
          
          <div class="date">
            <fmt:formatDate value="${item.eventDate.time}" type="Date" pattern="dd.MM.yyyy" />
          </div>

          <c:if test="${not empty item.introduction }">
            <div class=introduction>
              <p>
                <opw:string-chopper
                  maxLength="250" bean="${item }"
                  stringPath="introduction"
                  allowedLengthTolerance="10"
                  showDots="true" />
              </p>
            </div>
          </c:if>
        </div>
        <div class="clear"></div>
      </div>
    </c:forEach>

    <c:if test="${model.info.showOverview && not empty model.overviewLink }">
      <div class="read_more">
        <h4>
          <a href='<hst:link hippobean="${model.overviewLink }" />'>
            <span> 
              <c:choose>
                <c:when test="${not empty model.info.overviewLinkLabel }">
                  <c:out value="${model.info.overviewLinkLabel }" escapeXml="true" />
                </c:when>
                <c:otherwise>
                  <fmt:message key="overiewlink.default.label" />
                </c:otherwise>
              </c:choose>
            </span>
          </a>
        </h4>
      </div>
    </c:if>
  </c:if>
</div>
