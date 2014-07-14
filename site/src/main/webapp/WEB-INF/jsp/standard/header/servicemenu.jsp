<%@ page contentType="text/html; charset=UTF-8" language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>

<hst:setBundle basename="nl.hsleiden.general.Messages"/>

<div class="service-menu">
  
  <div class="menulinks">
    
    <%-- <hst:include ref="langaugeswitch" /> --%> 
    <ul class="nav mainMenuLink international">
      <li>
        <a href="#"> 
          <c:out value="international" />
        </a>
      </li>
    </ul>
    <%-- <hst:include ref="langaugeswitch" /> --%> 
    
    <ul class="nav mainMenuLink">
  	  <c:forEach items="${menu.siteMenuItems}" var="siteMenuItem" varStatus="step">
  		<c:choose>
  			<c:when test="${not empty siteMenuItem.hstLink }">
  				<hst:link var="link" link="${siteMenuItem.hstLink}" />
  			</c:when>
  			<c:otherwise>
  				<c:set var="link" value="${siteMenuItem.externalLink}" />
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
  			<a href="${link}"> 
  				<c:out value="${siteMenuItem.name}" />
  			</a>
  		</li>
  	  </c:forEach>
    </ul>
  </div>
  
  <div class="search-box">
    
    <fmt:message var="submitText" key="search.submit.text" /> <hst:link var="link" siteMapItemRefId="search"/>
    <form class="navbar-search form-search" action="${link}" method="get">
      <p>
        <input type="text" name="q" class="search-query input-xlarge" placeholder="${submitText}" required="required" />
        <button class="btn btn-primary inline" type="submit" value="${submitText}">${submitText}</button>
      </p>
    </form> 

  </div>
  <div class="clear"></div>   
</div>

