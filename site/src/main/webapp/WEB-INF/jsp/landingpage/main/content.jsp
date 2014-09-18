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

          <c:forEach items="${items }" var="item" varStatus="loop">
            <c:if test="${loop.index < 3}">
                <c:if test="${loop.index eq 2}">
                  <div class="divider visible-sm"></div>
                </c:if>
                <tag:landingOverviewItem item="${item}"/>
            </c:if>
          </c:forEach>

      </c:when>
      <c:when test="${tag:getSitemapConfigParameter(pageContext.request, 'columnNr') eq '4' }">
        
        <c:forEach items="${items }" var="item" varStatus="loop">
          <c:if test="${loop.index < 4}">
            <c:if test="${loop.index eq 2}">
              <div class="divider visible-sm"></div>
            </c:if>
            <tag:landingOverviewItem item="${item}"/>
          </c:if>
        </c:forEach>
        
      </c:when>
      <c:otherwise></c:otherwise>
    </c:choose>
    
  </c:otherwise>
</c:choose>
