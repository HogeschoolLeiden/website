<%@ tag language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<%@ attribute name="highLightedItem" type="hslbeans.WebPage" required="true" %>

<article class="well well-large">
  <hst:cmseditlink hippobean="${highLightedItem}" />
  <div class="higlighted-image">
      <hst:link var="image" hippobean="${highLightedItem.headerImage.wideImage}" />
      <img alt="${highLightedItem.title }" title="${highLightedItem.title }" src="${image }" />
  </div>
  
  <div class="higlighted-itemContent">
    <div class=higlighted-itemTitle>
      <hst:link hippobean="${highLightedItem }" var="link" />
      <c:set var="title">
        <c:out value="${highLightedItem.title }" escapeXml="true" />
      </c:set>
      <h3><a href="${link }" title="${title }">${title }</a></h3>
    </div>
    
    <c:choose>
      <c:when test="${highLightedItem['class'].name=='hslbeans.EventPage' }">
        <div class="date">
          <fmt:formatDate value="${highLightedItem.eventDate.time}" type="date" dateStyle="long"/>
        </div>
      </c:when>
      <c:otherwise>
        <div class="date">
          <fmt:formatDate value="${highLightedItem.releaseDate.time}" type="date" dateStyle="long"/>
        </div>
      </c:otherwise>
    </c:choose>
    
    <c:if test="${not empty highLightedItem.introduction }">
      <div class=introduction>
        <p>
          <opw:string-chopper
            maxLength="250" bean="${highLightedItem }"
            stringPath="introduction"
            allowedLengthTolerance="10"
            showDots="true" />
        </p>
      </div>
    </c:if>
  </div>

</article>
