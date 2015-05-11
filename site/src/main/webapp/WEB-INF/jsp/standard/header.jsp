<%@ page contentType="text/html; charset=UTF-8" language="java"
  trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<hst:setBundle basename="nl.hsleiden.general.Messages"/>

<header class="navbar" id="top">

  <div class="container">
	<div class="navbar-header">
  
      <button class="navbar-toggle navbar-header menu" data-target=".navbar-collapse" data-toggle="collapse" type="button">
        <span class="sr-only">Toggle navigation</span>
        <span class="menuButton"><fmt:message key="mobile.menu.message"/></span>
      </button>       
      
      <button class="navbar-toggle navbar-header toggle-filter-button fa fa-filter hidden-lg hidden-md" onclick="switchFiltering()" type="button">
        <span class="sr-only">Toggle filter</span>
      </button>
      
      <hst:link var="homeLink" path="/" mount="hsl"/>

      <a class="navbar-brand" href="${homeLink}">
        <c:choose>
          <c:when test="${not empty logo}">
            <img src="<hst:link hippobean="${logo.listImageLarge}"/>"
                 alt="<c:out value="${headerName}"/>"
                 title="<c:out value="${headerName}"/>" 
                 class="hidden-xs img-responsive"/>
            <img src="<hst:link hippobean="${logo.thumbnail}"/>"
                 alt="<c:out value="${headerName}"/>"
                 title="<c:out value="${headerName}"/>" 
                 class="visible-xs-block img-responsive"/>
          </c:when>
          <c:otherwise>
            <img src="<hst:link path="images/logo-hsleiden-large.png"/>"
              alt="<c:out value="${headerName}"/>"
              title="<c:out value="${headerName}"/>" 
              class="img-responsive"/>
          </c:otherwise>
        </c:choose>
      </a>
      
    </div>
    
    <div class="navbar-searchbox">
      <hst:include ref="searchbox"/>
    </div>
        
    <div class="navbar-collapse collapse" role="navigation">      
         
            <hst:include ref="serviceMenu" /> 
            <hst:include ref="mainMenu" />
            <div class="clear"></div>
            
    </div> <!--- // navbar-collapse -->
        
  </div>

</header>