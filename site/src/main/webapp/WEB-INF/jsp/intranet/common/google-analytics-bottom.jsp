<c:if test="${not composermode}">

	<script type="text/javascript">
	
	  (function() {
	    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
	    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
	    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
	  })();
	  
	  function trackOutboundLink(link, category, action) { 
	      
	      try { 
	    	  _gaq.push(['_trackEvent', category , action]); 
	      } catch(err){}
	       
		      setTimeout(function() {
		      document.location.href = link.href;
	      }, 100);
	  }
	        
	</script>
	
</c:if>
