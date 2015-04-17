<%@ include file="/WEB-INF/jspf/htmlTags.jspf" %>
<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<c:if test="${not hideFilter}">
	<section class="section-filter">
	    <form class="filter-form" name="" action="" method="GET">
	        <%@include file="/WEB-INF/jsp/intranet/common/filter.jsp" %>
	    </form>
	</section>   
</c:if>
         
<section class="sections clearfix">
    
    <div id="maincontent" >            
		<c:choose>
			<c:when test="${not empty browseResult and fn:length(browseResult.groups) gt 0}">
	
		        <tag:browseColumn begin="${browseResult.leftColumnStartIndex}" end="${browseResult.leftColumnEndIndex}" browseResult="${browseResult}" />
		        <tag:browseColumn begin="${browseResult.rightColumnStartIndex}" end="${browseResult.rightColumnEndIndex}" browseResult="${browseResult}" />
	
			</c:when>
		    <c:otherwise>
		       
		       <section class="sections-column">
		           <section class="sections-head">
	                   Er zijn geen documenten gevonden.
	               </section>
		       </section>
		       
		    </c:otherwise>
		</c:choose>
    </div>
</section>                
            
