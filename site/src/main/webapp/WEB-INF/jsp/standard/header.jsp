<%@ page contentType="text/html; charset=UTF-8" language="java"
  trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>

<hst:setBundle basename="nl.hsleiden.general.Messages"/>

<div class="container-fluid">
<div class="row-fluid">
<div class="header span8">
  <div class="navbar">
    <div class="navbar-inner">
        
      <div class="container">
        <hst:link var="homeLink" path="/" />
        <div class="logo">
          <a href="${homeLink}"> 
            <c:choose>
            <c:when test="${not empty logo}">
              <img src="<hst:link hippobean="${logo.original}"/>"
                   alt="<c:out value="${headerName}"/>"
                   title="<c:out value="${headerName}"/>" />
            </c:when>
            <c:otherwise>
              <img src="<hst:link path="images/logo_big.png"/>"
                alt="<c:out value="${headerName}"/>"
                title="<c:out value="${headerName}"/>" />
            </c:otherwise>
            </c:choose>
          </a>
        </div>
        
        <div class="navbar nav pull-right h-menu">
          <hst:include ref="serviceMenu" /> 
          <hst:include ref="mainMenu" />
          <div class="clear"></div>
        </div>

        <div class="clear"></div>
      </div>
      	
    </div>
  </div>
</div>
</div>
</div>