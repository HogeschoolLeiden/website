<%@ include file="/WEB-INF/jspf/htmlTags.jspf"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
            
<c:if test="${fn:length(document.textBlocks) gt 1}">
	<div class="widget">
        <nav>
			
			<ul>
			    <li class="first">${fn:escapeXml(tocTitle)}</li>
	        
				<c:forEach var="textBlock" items="${document.textBlocks}"
					varStatus="status">
					<li><a href="#block${status.index}">${fn:escapeXml(textBlock.title)}</a></li>
				</c:forEach>
			</ul>
		</nav>
	</div>
</c:if>
