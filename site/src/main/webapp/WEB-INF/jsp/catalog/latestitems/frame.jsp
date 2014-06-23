<%@ page contentType="text/html; charset=UTF-8" language="java"
  trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>

<div class="catalog latestItems">
  <hst:defineObjects />
  <c:set var="isCmsRequest" value="${hstRequest.requestContext.cmsRequest}" />

  <c:if test="${(empty model.items or fn:length(model.items) eq 0) and isCmsRequest}">
    <c:choose>
      <c:when test="${not empty model.webmasterErrorMessage}">
        <p class="error-message">${model.webmasterErrorMessage}</p>
      </c:when>
      <c:otherwise>
        <div class="catalog latestItems">
          <p class="error-message">There are no newspage documents under folder:
            ${model.info.contentBeanPath}. The widget will not display.
            </p>
        </div>
      </c:otherwise>
    </c:choose>
  </c:if>

  <c:if test="${not empty model.items }">
    <div class="widget-title">
      <h2><c:out value="${model.info.widgetTitle}" escapeXml="true" /></h2>
    </div>
    <%-- Items in-inlined for better performance --%>
    <c:forEach var="item" items="${model.items}" varStatus="zebra">
      <div class="item-with-image">
        <div class="image-space">
          <c:if test="${hst:isReadable(item, 'image')}">
            <c:if test="${ not empty item.image.link.deref }">
              <hst:link var="image"
                hippobean="${item.image.link.deref.listSmallImage }" />
              <img alt="${item.title }" src="${image }" />
            </c:if>
          </c:if>
          <div class="date">
            <fmt:formatDate value="${item.releaseDate.time}" type="Date"
              pattern="dd.MM.yyyy" />
          </div>
        </div>
        <div class="itemContent">
          <div class=itemTitle>
            <hst:link hippobean="${item }" var="link" />
            <c:set var="title">
              <c:out value="${item.title }" escapeXml="true" />
            </c:set>
            <a href="${link }" title="${title }">${title }</a>
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
        <div class="clear-both"></div>
      </div>
    </c:forEach>

    <%-- <div class="pager-wrapper" rel="${ajaxUrl}">
      <opw:componentParameterName name="page" var="pageParameterName" />
      <opw:verysimplepaginator paginator="${model.paginator}"
        pageParamerter="${pageParameterName}" />
    </div> --%>

    <c:if
      test="${model.info.showOverviewLink && not empty model.info.overviewLinkLabel }">
      <div class="read_more">
        <h4>
          <a href='<hst:link hippobean="${model.overviewLink }" />'>
            <span> 
              <c:choose>
                <c:when test="${not empty model.info.overviewLinkLabel }">
                  <c:out value="${model.info.overviewLinkLabel }" escapeXml="true" />
                </c:when>
                <c:otherwise>
                  <c:out value="Overview" escapeXml="true" />
                </c:otherwise>
              </c:choose>
            </span>
          </a>
        </h4>
      </div>
    </c:if>
  </c:if>
</div>
