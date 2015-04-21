<%@ include file="/WEB-INF/jspf/htmlTags.jspf"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>

<!doctype html>
<html lang="nl">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>HS Leiden Praktische Informatie</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="<hst:link path="/css/style-intranet.css"/>">

        <link rel="shortcut icon" href="<hst:link path="/favicon.ico"/>" type="image/x-icon">
        
        <style>
            #galleria{padding-bottom: 20px; padding-left: 30px; padding-right: 30px; width: 600px; height: 600px; background: #fff;}
            .galleria-container{background: #fff;}
        </style>
        
        <script src="<hst:link path="/js/intranet/vendor/jquery-1.10.1.min.js"/>"></script>
        <script src="<hst:link path="/js/intranet/vendor/modernizr-2.6.2.min.js"/>"></script>
        <script src="<hst:link path="/js/intranet/galleria/galleria-1.2.9.min.js"/>"></script>
        
        <%@include file="/WEB-INF/jsp/intranet/common/google-analytics-head.jsp" %>
        
    </head>

    <body>
        <ul id="skiplinks">
	      <li><a href="#maincontent">Meteen naar inhoud</a></li>
	      <li><a href="#search">Meteen naar zoeken</a></li>
	      <li><a href="#sidebar">Meteen naar zijbalk</a></li>
	      <li><a href="#searchresult">Meteen naar zoekresultaat</a></li>
	    </ul>
    
	    <hst:include ref="header" />
	        
	    <div class="wrapper">

            <hst:include ref="main" />

	    </div>
	
        <script src="<hst:link path="/js/intranet/plugins.js"/>"></script>
        <script src="<hst:link path="/js/intranet/main.js"/>"></script>
        <script src="/appbar/inc/js/intranet/appBarRemote.js"></script>
        
	    <%@include file="/WEB-INF/jsp/intranet/common/google-analytics-bottom.jsp" %>
	    
    </body>
  
</html>