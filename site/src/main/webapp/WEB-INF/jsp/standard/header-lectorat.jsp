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
  
      <button class="navbar-toggle navbar-header" data-target=".navbar-collapse" data-toggle="collapse" type="button">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>       
      
      <button class="navbar-toggle navbar-header toggle-filter-button fa fa-filter hidden-lg hidden-md"  data-target=".filter-collapse" data-toggle="collapse" type="button">
        <span class="sr-only">Toggle filter</span>
      </button>
      
      <hst:link var="homeLink" path="/" mount="hsl"/>

      <a class="navbar-brand" href="${homeLink}">
        <c:choose>
          <c:when test="${not empty logo}">
            <img src="<hst:link hippobean="${logo.listImageLarge}"/>"
                 alt="<c:out value="${headerName}"/>"
                 title="<c:out value="${headerName}"/>" 
                 class="img-responsive"/>
          </c:when>
          <c:otherwise>
            <img src="<hst:link path="images/logo-hsleiden-large.png"/>"
              alt="<c:out value="${headerName}"/>"
              title="<c:out value="${headerName}"/>" 
              class="img-responsive"/>
          </c:otherwise>
        </c:choose>
      </a>
      
      <hst:link var="lectoraatHome" path="/" />

      <div class="container lectoraattitle">
        <div class="row">
          <div class="col-md-7 col-sm-7 col-xs-10 col-md-offset-2 col-sm-offset-2 col-xs-offset-1">
            <div class="table">
              <div class="table-cell">
                <div class="centered">
                  <h1>
                    <a href="${lectoraatHome}">
                      <c:out value="${headerIntroTitle}"/>
                      <span><c:out value="${headerName}"/></span>
                    </a>
                  </h1>
                </div>
              </div>
            </div>
            
          </div>
        </div>
      </div>
           
    </div>
        
    <div class="navbar-collapse collapse" role="navigation">      
         
            <hst:include ref="serviceMenu" /> 
            <hst:include ref="mainMenu" />
            <div class="clear"></div>
            
    </div> <!--- // navbar-collapse -->
        
  </div>

</header>


    