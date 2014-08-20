<%@ page contentType="text/html; charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>

<hst:setBundle basename="nl.hsleiden.widget.Messages" />

<div class="left">
  
  <c:if test="${not empty paramInfo.overviewSitemapRefId}">
    <div class="backToOverview">
      <c:set var="refId" value="${paramInfo.overviewSitemapRefId}"/>
      <hst:link siteMapItemRefId="${refId}" var="overviewLink"/>
      <a href="${overviewLink}" title="<fmt:message key="back.overview.${refId}" />">
        <fmt:message key="back.overview.${refId}"/>
      </a>
    </div>
  </c:if>
  
  <c:set var="parentItem" value="${not empty menu.deepestExpandedItem.parentItem ? menu.deepestExpandedItem.parentItem : menu.deepestExpandedItem}"/>
  <c:set var="grandparentItem" value="${not empty parentItem.parentItem ? parentItem.parentItem : parentItem}"/>
  <ul class="nav nav-pills nav-stacked">
    <c:forEach items="${hst:isReadable(grandparentItem, 'childMenuItems') ? grandparentItem.childMenuItems : grandparentItem.menuItems}" var="item">
          <opw:menuitem siteMenuItem="${item}" depth="1"
            expandedClass="current arrow-down"
            selectedClass="active arrow-down"
            unexpandedClass="unexpanded arrow-side" leafClass="arrow-side" 
            recurseOnlyExpanded="true"/>
    </c:forEach>
  </ul>
  
  <%-- <ul class="nav nav-pills nav-stacked">
    <c:forEach items="${menuItem.childMenuItems[columNr].childMenuItems}" var="item">
      <opw:menuitem siteMenuItem="${item}" depth="1"
        expandedClass="current arrow-down"
        selectedClass="active arrow-down"
        unexpandedClass="unexpanded arrow-side" leafClass="arrow-side" 
        recurseOnlyExpanded="false"/>
    </c:forEach>
  </ul> --%>
</div>