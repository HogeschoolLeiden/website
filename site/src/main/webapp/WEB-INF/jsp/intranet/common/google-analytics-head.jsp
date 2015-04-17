<%@ include file="/WEB-INF/jspf/htmlTags.jspf"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>

<c:if test="${not composermode}">
    
	<script type="text/javascript">
	  
	  var _gaq = _gaq || [];
	  _gaq.push(['_setAccount', '<hs:siteSetting key="google.analytics.accountid" />']);
	  _gaq.push(['_trackPageview']);
	 
	  
	</script>
	
</c:if>