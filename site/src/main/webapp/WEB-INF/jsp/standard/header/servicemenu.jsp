<%@ page contentType="text/html; charset=UTF-8" language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>

<hst:setBundle basename="nl.hsleiden.general.Messages"/>

<%-- <hst:include ref="searchbox"/> --%>

<ul class="nav navbar-nav" id="utilities">

  <hst:include ref="langaugeswitch" />
      
  <c:forEach items="${menu.siteMenuItems}" var="siteMenuItem" varStatus="step">
	 <c:choose>
		<c:when test="${not empty siteMenuItem.hstLink }">
			<hst:link var="link" link="${siteMenuItem.hstLink}" />
            <c:set var="ext" value="false"/>
		</c:when>
		<c:otherwise>
			<c:set var="link" value="${siteMenuItem.externalLink}" />
            <c:set var="ext" value="true"/>
		</c:otherwise>
	 </c:choose>
  	 
     <c:choose>
  		<c:when test="${siteMenuItem.expanded and step.last}">
  		  <c:set var="cssClass" value="active last"/>
  		</c:when>
  		<c:when test="${siteMenuItem.expanded}">
  	   	  <c:set var="cssClass" value="active"/>
  		</c:when>
  		<c:when test="${step.last}">
  		  <c:set var="cssClass" value="last"/>
  		</c:when>
  		<c:otherwise>
  		  <c:set var="cssClass" value=""/>
  	    </c:otherwise>
  	</c:choose>
  		
  	<li ${not empty cssClass ? ' class=\"': ''}${cssClass}${not empty cssClass ? '\"': ''}>
  	  <a href="${link}" ${ext ? 'rel="nofollow"': '' }> 
  		<c:out value="${siteMenuItem.name}" />
  	  </a>
    </li>
  
  </c:forEach>
</ul>
  

