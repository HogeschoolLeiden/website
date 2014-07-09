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

          <%-- maybe there are too many divs before main content ?? --%>
          <div class="container">
            <hst:link var="homeLink" path="/" />
            <div class="logo">
              <a href="${homeLink}"> 
                <c:choose>
                  <c:when test="${not empty logo}">
                    <img src="<hst:link hippobean="${logo.original}"/>"
                      alt="${fn:escapeXml(headerName)}"
                      title="${fn:escapeXml(headerName)}" />
                  </c:when>
                  <c:otherwise>
                    <img src="<hst:link path="images/logo.png"/>"
                      alt="${fn:escapeXml(headerName)}"
                      title="${fn:escapeXml(headerName)}" />
                  </c:otherwise>
                </c:choose>
              </a>
            </div>
            <div class="navbar nav pull-right h-menu">
            <hst:include ref="serviceMenu" />
				<%-- <hst:include ref="langaugeswitch" /> --%> 
				<fmt:message var="submitText" key="search.submit.text" /> <hst:link var="link" siteMapItemRefId="search"/>
				<form class="navbar-search form-search" action="${link}" method="get">
					<p>
						<input type="text" name="q" class="search-query input-xlarge" placeholder="${submitText}" required="required" />
						<button class="btn btn-primary inline" type="submit" value="${submitText}">${submitText}</button>
					</p>
				</form> 
            </div>

            <div class="nav-collapse">

            <hst:include ref="mainMenu" />
            </div>

            <div class="clear-both"></div>
          </div>
          <%-- maybe there are too many divs before main content ?? --%>

        </div>
      </div>
    </div>
  </div>
</div>