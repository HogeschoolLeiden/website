<%@ include file="/WEB-INF/jspf/htmlTags.jspf"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
            
<c:if test="${fn:length(tableOfContents.items) gt 1}">
	<div class="widget">
        <nav>
			
			<ul>
			    <li class="first">${fn:escapeXml(tableOfContents.title)}</li>
	        
				<c:forEach var="tocItem" items="${tableOfContents.items}">
					<li><a href="#${tocItem.anchorId}">${fn:escapeXml(tocItem.title)}</a></li>
				</c:forEach>
			</ul>
		</nav>
	</div>
</c:if>
