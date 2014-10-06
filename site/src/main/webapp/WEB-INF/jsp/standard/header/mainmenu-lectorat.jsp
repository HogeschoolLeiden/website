<%@ page contentType="text/html; charset=UTF-8" language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>

<hst:setBundle basename="nl.hsleiden.general.Messages"/>

<ul class="nav navbar-nav lectoraten" id="mainnav">
  <c:forEach items="${menu.menuItems}" var="siteMenuItem" varStatus="loop">
	
    <c:set var="myMenuItem" value="${tag:getMenuItem(pageContext.request, siteMenuItem) }"/>
          
    <c:choose>
    	<c:when test="${not empty myMenuItem.hstLink }">
    		<hst:link var="link" link="${myMenuItem.hstLink}" />
    	</c:when>
    	<c:otherwise>
    		<c:set var="link" value="${myMenuItem.externalLink}" />
    	</c:otherwise>
    </c:choose>
	
    <c:choose>
	 <c:when test="${myMenuItem.expanded and loop.last}">
	   <c:set var="cssClass" value="active last"/>
	 </c:when>
	 <c:when test="${myMenuItem.expanded and loop.first}">
	   <c:set var="cssClass" value="active firstItem"/>
	 </c:when>
	 <c:when test="${myMenuItem.expanded}">
	   <c:set var="cssClass" value="active"/>
	 </c:when>
	 <c:when test="${loop.last}">
	   <c:set var="cssClass" value="last"/>
	 </c:when>
	 <c:when test="${loop.first}">
	   <c:set var="cssClass" value="firstItem"/>
	 </c:when>
	 <c:otherwise>
	   <c:set var="cssClass" value=""/>
	 </c:otherwise>
	</c:choose>
	
        <li ${not empty cssClass ? ' class=\"': ''}${cssClass}${not empty cssClass ? '\"': ''}>
          <a href="${link}"> 
            <c:out value="${myMenuItem.name}" />
          </a>
          
          <c:if test="${not empty myMenuItem.childMenuItems}">
            <ul class="dropdownmenu visible-lg visible-md clearfix">
            
	          <tag:submenu menuItem="${myMenuItem}" columNr="0"/>
              <tag:submenu menuItem="${myMenuItem}" columNr="1"/>
              <tag:submenu menuItem="${myMenuItem}" columNr="2"/>
              <tag:submenu menuItem="${myMenuItem}" columNr="3"/>
            
            </ul>
          </c:if>
          
        </li> 
                       
  </c:forEach>  
</ul>
