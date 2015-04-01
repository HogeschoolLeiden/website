<%@ page contentType="text/html; charset=UTF-8" language="java"
  trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>


<div class="container">
    <div class="row">
    
<c:forEach items="${menu.siteMenuItems}" var="siteMenuItem" varStatus="step">
  <c:choose>
    <c:when test="${not empty siteMenuItem.hstLink }">
      <c:set var="path" value="${siteMenuItem.hstLink.path}" />
      <c:set var="link">
        <hst:link path="${path}" mount="hsl" />
      </c:set>
      <c:set var="ext" value="false"/>
    </c:when>
    <c:otherwise>
      <c:set var="link" value="${siteMenuItem.externalLink}" />
      <c:set var="ext" value="true"/>
    </c:otherwise>
  </c:choose>
  <c:choose>
    <c:when test="${siteMenuItem.expanded and step.last}">
      <c:set var="cssClass" value="active last" />
    </c:when>
    <c:when test="${siteMenuItem.expanded}">
      <c:set var="cssClass" value="active" />
    </c:when>
    <c:when test="${step.last}">
      <c:set var="cssClass" value="last" />
    </c:when>
    <c:otherwise>
      <c:set var="cssClass" value="" />
    </c:otherwise>
  </c:choose>
  
  <c:if test="${step.index eq 3 }">
    <div class="divider visible-sm"></div>
  </c:if>

  <div class="col-md-2 col-sm-4 navlist">
    <h2>
      <a href="${link}" ${ext ? 'rel="nofollow"': '' }> 
        <c:out value="${siteMenuItem.name}" /> 
      </a>
    </h2>
    <ul class="hidden-xs">
      <c:forEach items="${siteMenuItem.childMenuItems}" var="subItem">
        <c:choose>
          <c:when test="${not empty subItem.hstLink }">
            <c:set var="path" value="${subItem.hstLink.path}" />
            <c:set var="link">
              <hst:link path="${path}" mount="hsl" />
            </c:set>
            <c:set var="ext" value="false"/>
          </c:when>
          <c:otherwise>
            <c:set var="link" value="${subItem.externalLink}" />
            <c:set var="ext" value="true"/>
          </c:otherwise>
        </c:choose>
        <li ${not empty cssClass ? ' class=\"': ''} ${cssClass} ${not empty cssClass ? '\"': ''}>
          <a href="${link}" ${ext ? 'rel="nofollow"': '' }> 
            <c:out value="${subItem.name}" />
          </a>
        </li>
      </c:forEach>
    </ul>
  </div>

</c:forEach>

<ul class="social">
  <tag:follow />
</ul>

</div>
  </div>