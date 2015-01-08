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
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta http-equiv="x-ua-compatible" content="IE=edge">
  
  <meta property="og:type" content="website"/>
       
  <c:if test="${tag:isSubclassOfWebPage(document)}">
    <c:if test="${not empty document.title }">
      <%-- <meta name="title" content="<c:out value="${document.title}" escapeXml="true" />" /> --%>
      <meta property="og:title" content="${document.title}"/>
    </c:if>
    <c:if test="${hst:isReadable(document, 'description') && not empty document.description }">
      <meta name="description" content="<c:out value="${document.description}" escapeXml="true" />" />
    </c:if>
    <c:if test="${not empty document.keywords }">
      <meta name="keywords" content="<c:out value="${document.keywords}" escapeXml="true" />" />
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
    
  <link rel="stylesheet" href="<hst:link path="/css/kees/ekko-lightbox.min.css"/>" >
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
  
  <c:choose>
    <c:when test="${tag:isSubclassOfWebPage(document) and not empty document.browserTitle}">
	 <title><c:out value="${document.browserTitle}" escapeXml="true" /></title>
    </c:when>
    <c:otherwise>
      <title><c:out value="${tag:getDefaultBrowserTitle(pageContext.request)}" escapeXml="true" /></title>
    </c:otherwise>
  </c:choose>
  <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0" />
  
  <hst:headContributions categoryIncludes="extraCss" xhtml="true" />
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
    	    
        <!-- Google Tag Manager test and dev -->
          <noscript><iframe src="//www.googletagmanager.com/ns.html?id=GTM-55GJBQ"
          height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
          <script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
          new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
          j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
          '//www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
          })(window,document,'script','dataLayer','GTM-55GJBQ');</script>
        <!-- End Google Tag Manager -->
		
  	</c:if>
    
   <%--   
    the next 2 scripts are causing problems to prettyPhoto - are they absolutely needed ??
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="<hst:link path="/js/kees/vendor/jquery-1.11.1.min.js"/>"><\/script>')</script>
   
    this should be used only in production, take it from some property maybe
    
     --%>
    
    <!-- Google Tag Manager production -->
    <%-- <noscript><iframe src="//www.googletagmanager.com/ns.html?id=GTM-NW5KCS"
    height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
    <script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
    new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
    j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
    '//www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
    })(window,document,'script','dataLayer','GTM-NW5KCS');</script> --%>
    <!-- End Google Tag Manager -->
   
  
     
    <script type="text/javascript" src="<hst:link path="/js/kees/vendor/bootstrap.min.js" />"></script>
    
    <%--   included directly from the homepage (the page that uses them)
    <script type="text/javascript" src="<hst:link path="/js/kees/vendor/idangerous.swiper.js"/>"></script>
    <script type="text/javascript" src="<hst:link path="/js/kees/vendor/idangerous.swiper.progress.min.js"/>"></script>
    --%>
    
    <script type="text/javascript" src="<hst:link path="/js/kees/vendor/ekko-lightbox.js"/>" ></script>
    <script type="text/javascript" src="<hst:link path="/js/kees/plugins.js" />"></script>
    <script type="text/javascript" src="<hst:link path="/js/kees/main.js" />"></script>
    
</body>
</html>