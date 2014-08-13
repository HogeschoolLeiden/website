<%@ page contentType="text/html; charset=UTF-8" language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>


<c:choose>
  <c:when test="${empty document}">
	<tag:pagenotfound />
  </c:when>
  <c:otherwise>
    <c:choose>
      <c:when test="${tag:getSitemapConfigParameter(pageContext.request, 'columnNr') eq '3' }">
        <div class="three column container">
          <c:forEach items="${items }" var="item" varStatus="loop">
            <c:if test="${loop.index<3}">
              <div ${loop.index==0 ? 'class="oneThird first"': 'class="oneThird"'}>
                <tag:landingOverviewItem item="${item}"/>
              </div>
            </c:if>
          </c:forEach>
          <div class="clear"></div>
        </div>
      </c:when>
      <c:when test="${tag:getSitemapConfigParameter(pageContext.request, 'columnNr') eq '4' }">
        <div class="four column container">
          <c:forEach items="${items }" var="item" varStatus="loop">
            <c:if test="${loop.index<4}">
              <div ${loop.index==0 ? 'class="oneFourth first"': 'class="oneFourth"'}>
                <tag:landingOverviewItem item="${item}"/>
              </div>
            </c:if>
          </c:forEach>
          <div class="clear"></div>
        </div>
      </c:when>
      <c:otherwise></c:otherwise>
    </c:choose>
    
  </c:otherwise>
</c:choose>
