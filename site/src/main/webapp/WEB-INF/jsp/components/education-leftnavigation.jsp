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
  
  <c:forEach items="${menu.menuItems }" var="menuItem">
    <c:if test="${menuItem.expanded}">
      <c:forEach items="${menuItem.childMenuItems }" var="secondMenuItem">
        <c:if test="${secondMenuItem.expanded}">
           <c:forEach items="${secondMenuItem.childMenuItems }" var="thirdMenuItem">
              <c:if test="${thirdMenuItem.expanded}">
                 <c:forEach items="${thirdMenuItem.childMenuItems }" var="fourthMenuItem">
                    <c:if test="${fourthMenuItem.expanded}">
                       <c:set var="parentItem" value="${fourthMenuItem}"/>
                    </c:if>
                  </c:forEach>
              </c:if>
            </c:forEach>
        </c:if>
      </c:forEach>
    </c:if>
  </c:forEach> 

  <c:choose>
    <c:when test="${tag:getSitemenuConfigParameter(parentItem, 'invisible') eq true}">
      <h3>&nbsp;</h3>
    </c:when>
    <c:otherwise>
      <c:choose>
        <c:when test="${tag:getSitemenuConfigParameter(parentItem, 'disabled') eq true}">
          <h3><c:out value=" ${parentItem.name}"/></h3>
        </c:when>
        <c:otherwise>
          <hst:link var="link" link="${parentItem.hstLink}"/>
          <h3><a href="${link}"><c:out value=" ${parentItem.name}"/></a></h3>
        </c:otherwise>
      </c:choose> 
    </c:otherwise>
  </c:choose>
  
  <ul class="nav nav-pills nav-stacked">
    <c:forEach items="${hst:isReadable(parentItem, 'childMenuItems') ? parentItem.childMenuItems : parentItem.menuItems}" var="item">
      <opw:menuitem siteMenuItem="${item}" depth="1"
        expandedClass="current arrow-down"
        selectedClass="active arrow-down"
        unexpandedClass="unexpanded arrow-side" leafClass="arrow-side" 
        recurseOnlyExpanded="false"/>
    </c:forEach>
  </ul>
</div>