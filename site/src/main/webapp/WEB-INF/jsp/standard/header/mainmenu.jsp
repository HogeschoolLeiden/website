<%@ page contentType="text/html; charset=UTF-8" language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>

<div class="main-menu">

  <ul class="nav mainMenuLink main-menu">
	<c:forEach items="${menu.menuItems}" var="siteMenuItem" varStatus="loop">
		<c:choose>
			<c:when test="${not empty siteMenuItem.hstLink }">
				<hst:link var="link" link="${siteMenuItem.hstLink}" />
			</c:when>
			<c:otherwise>
				<c:set var="link" value="${siteMenuItem.externalLink}" />
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${siteMenuItem.expanded and loop.last}">
				<c:set var="cssClass" value="active last"/>
			</c:when>
			<c:when test="${siteMenuItem.expanded}">
				<c:set var="cssClass" value="active"/>
			</c:when>
			<c:when test="${loop.last}">
				<c:set var="cssClass" value="last"/>
			</c:when>
			<c:otherwise>
				<c:set var="cssClass" value=""/>
			</c:otherwise>
		</c:choose>
		
		<li ${not empty cssClass ? ' class=\"': ''}${cssClass}${not empty cssClass ? '\"': ''}>
			<a href="${link}"> 
			  <c:out value="${siteMenuItem.name}" />
			</a>
            <div class="showOnHover">
              <div class="column one">
                <tag:submenu menuItem="${siteMenuItem}" columNr="0"/>
              </div>
              <div class="column two">
                <tag:submenu menuItem="${siteMenuItem}" columNr="1"/>
              </div>
              <div class="column three">
                <tag:submenu menuItem="${siteMenuItem}" columNr="2"/>
              </div>
              <div class="column four">
                <tag:submenu menuItem="${siteMenuItem}" columNr="3"/>
              </div>  
            </div>
		</li> 
                       
	</c:forEach>  
  </ul> 
</div>