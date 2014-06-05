<%@ page contentType="text/html; charset=UTF-8" language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<!doctype html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<html lang="${pageContext.request.locale.language}">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <hst:headContributions categoryExcludes="scripts" xhtml="true" />
  <!--[if lt IE 9]>
    <hst:link var="html4shiv" path="/js/html5shiv.js"/>
    <script type="text/javascript" src="${html4shiv}"></script>
  <![endif]-->
    
  <c:if test="${tag:isSubclassOfWebPage(document)}">
    <c:if test="${hst:isReadable(document, 'description') && not empty document.description }">
      <meta name="description" content="<c:out value="${document.description}" escapeXml="true" />" />
    </c:if>
    <c:if test="${not empty document.author }">
      <meta name="author" content="<c:out value="${document.author}" escapeXml="true" />" />
    </c:if>
    <c:if test="${not empty document.keywords }">
      <meta name="keywords" content="<c:out value="${document.keywords}" escapeXml="true" />" />
    </c:if>
  </c:if>

  <script type="text/javascript">
  	var contextPath = "${tag:contextPath(pageContext.request)}";
  </script>
    
  <link rel="shortcut icon" href="<hst:link path="/images/favicon.ico" />" />
  <link rel="stylesheet" href="<hst:link path="/css/style.css" />" type="text/css" />

  <c:choose>
    <c:when test="${tag:isSubclassOfWebPage(document)}">
	 <title><c:out value="${document.browserTitle}" escapeXml="true" /></title>
    </c:when>
    <c:otherwise>
      <title><c:out value="${tag:getDefaultBrowserTitle(pageContext.request)}" escapeXml="true" /></title>
    </c:otherwise>
  </c:choose>
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
</head>

<body>
	<hst:include ref="header" />
	<hst:include ref="main" />
	<hst:include ref="footer" />
	<hst:headContributions categoryIncludes="scripts" xhtml="true" />
		
	<%-- <c:if test="${not composermode}">
	    <ga:accountId/>
	    <hst:link var="googleAnalytics" path="/resources/google-analytics.js"/>
    	<script src="${googleAnalytics}" type="text/javascript"></script>
  	</c:if> --%>	
</body>
</html>