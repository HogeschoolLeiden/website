<%@ page contentType="text/html; charset=UTF-8" language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<!doctype html>

<!--[if IE 7]> <html class="no-js lt-ie9 lt-ie8" lang="${pageContext.request.locale.language}"> <![endif]-->
<!--[if IE 8]> <html class="no-js lt-ie9" lang="${pageContext.request.locale.language}"> <![endif]-->
<!--[if IE 9]> <html class="no-js lt-ie10" lang="${pageContext.request.locale.language}"> <![endif]-->
<!--[if gt IE 9]><!--> <html class="no-js" lang="${pageContext.request.locale.language}"> <!--<![endif]-->

<%-- xmlns:og="http://ogp.me/ns#" xmlns:fb="http://www.facebook.com/2008/fbml" removed from html declaration --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>
<%@ taglib prefix="ga" uri="http://www.onehippo.org/jsp/google-analytics"%>

<head>
  <title><c:out value="${tag:getBrowserTitle(pageContext.request, document)}" escapeXml="true" /></title>
  
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta http-equiv="x-ua-compatible" content="IE=edge">
  <meta property="og:type" content="website"/>
       
  <c:if test="${tag:isSubclassOfWebPage(document)}">
    <c:if test="${not empty document.title }">
      <meta property="og:title" content="${document.title}"/>
    </c:if>
    <c:if test="${hst:isReadable(document, 'description') && not empty document.description }">
      <meta name="description" content="<c:out value="${document.description}" escapeXml="true" />" />
    </c:if>
    <c:if test="${hst:isReadable(document, 'author') && not empty document.author.firstItem.label }">
      <meta name="author" content="<c:out value="${document.author.firstItem.label}" escapeXml="true" />" />
    </c:if>
  </c:if>
    
  <script type="text/javascript">
  	var contextPath = "${tag:contextPath(pageContext.request)}";
  </script>
  
  <!-- Favicon -->
  <link rel="shortcut icon" href="<hst:link path="/images/icons/favicon.ico" />" />
  
  <!-- Windows 8 Bookmark -->
  <meta name="application-name" content="Hogeschool Leiden"/>
  <meta name="msapplication-TileColor" content="#ffffff"/>
  <meta name="msapplication-square70x70logo" content="/images/icons/ms-icon-70.png"/>
  <meta name="msapplication-square150x150logo" content="/images/icons/ms-icon-150.png"/>
  
    <!-- Apple Bookmark -->
  <link rel="apple-touch-icon" href="<hst:link path="/images/icons/touch-icon.png"/>">
  <link rel="apple-touch-icon" sizes="76x76" href="<hst:link path="/images/icons/touch-icon-76.png"/>">
  <link rel="apple-touch-icon" sizes="120x120" href="<hst:link path="/images/icons/touch-icon-120.png"/>">
  <link rel="apple-touch-icon" sizes="152x152" href="<hst:link path="/images/icons/touch-icon-152.png"/>">
  
   <!-- CSS -->  
  <link rel="stylesheet" href="<hst:link path="/css/kees/bootstrap.min.css"/>" >
  <link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css" >
  
  <!-- SWIPER SLIDER -->
  <link rel="stylesheet" href="<hst:link path="/css/kees/idangerous.swiper.css"/>">
  <!-- // SWIPER SLIDER -->
  
  <hst:headContributions categoryIncludes="formsCssHere" xhtml="true" />
    
  <link rel="stylesheet" href="<hst:link path="/css/kees/style.css"/>" >
  
  <link rel="stylesheet" href="<hst:link path="/css/kees/style-additions.css"/>" >
  
  <link rel="stylesheet" type="text/css" href="<hst:link path="/css/print-style.css"/>" media="print"> 
  
  <link rel="stylesheet" href="<hst:link path="/css/kees/normalize.css"/>" >
  
  <script type="text/javascript" src="<hst:link path="/js/kees/vendor/modernizr-2.6.2.min.js" />"></script>
  <script type="text/javascript" src="<hst:link path="/js/kees/vendor/retina.min.js" />"></script>
  <script type="text/javascript" src="<hst:link path="/js/kees/vendor/respond.js" />"></script>
    
  <script src="<hst:link path="/js/jquery.min.js" />" type="text/javascript"></script>
  <script src="<hst:link path="/js/jquery.browser.min.js" />" type="text/javascript"></script>
 
  <hst:headContributions categoryExcludes="scripts,formsCssHere,extraCss" xhtml="true" />
  
  <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0" />
  
  <hst:headContributions categoryIncludes="extraCss" xhtml="true" />
  
  <hst:link var="canonicalLink" hippobean="${document}" canonical="true" fullyQualified="true"/>  
  <link rel="canonical" href="${canonicalLink}"/>

</head>

<body itemscope itemtype="http://schema.org/WebPage">
    
    <!--[if lt IE 9]>
    <p class="chromeframe">Uw gebruikt een oudere versie van uw browser. Wij raden u aan deze te <a href="http://www.google.com/chromeframe/?redirect=true">upgraden</a>.</p>
    <![endif]-->
    <a class="sr-only" href="#main">Direct naar de inhoud</a>

	<hst:include ref="header" />
    
    <hst:include ref="breadcrumb"/>
        
  	<hst:include ref="main" />
      	
    <hst:include ref="footer" />
 
    <hst:include ref="help" />
	 
    <hst:headContributions categoryIncludes="scripts" xhtml="true" />
  	
	<c:if test="${(not composermode ) and ( not tag:isCookiesRefused(pageContext.request))}">
	  <ga:accountId/>
	  <hst:link var="googleAnalytics" path="/resources/google-analytics.js"/>
      <script src="${googleAnalytics}" type="text/javascript"></script>
      
      <c:set var="gtm" value="${(tag:getMainWebsitePropertyList(pageContext.request)).googleTagManagerConfig}"/>
      <c:set var="disableGTM" value="${(tag:getMainWebsitePropertyList(pageContext.request)).disableGoogleTagManager}"/>
      
      <c:if test="${not disableGTM eq 'true'}">
        ${gtm}
      </c:if>
  	</c:if>
      
    <script type="text/javascript" src="<hst:link path="/js/kees/vendor/bootstrap.min.js" />"></script>
    <script type="text/javascript" src="<hst:link path="/js/kees/plugins.js" />"></script>
    <script type="text/javascript" src="<hst:link path="/js/kees/main.js" />"></script>
    
    <c:set var="chatJs" value="${(tag:getMainWebsitePropertyList(pageContext.request)).chatJsConfig}"/>
    <c:set var="disableChat" value="${(tag:getMainWebsitePropertyList(pageContext.request)).disableChatJs}"/>
    
    <c:if test="${not disableChat eq 'true'}">     
      <script type="text/javascript" src="${chatJs}"></script> 
    </c:if>
    
</body>
</html>