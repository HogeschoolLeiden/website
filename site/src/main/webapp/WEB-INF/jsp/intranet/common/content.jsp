<%@ include file="/WEB-INF/jspf/htmlTags.jspf"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>

<div id="maincontent" >
	<article>
	    <h1>${fn:escapeXml(document.title)}</h1>
	
		<%-- Display the image (if any)  --%>
	
		<c:if test="${not empty document.image}">
			<hst:link var="imageUrl" hippobean="${document.image.original}" />
			<img src="${imageUrl}" alt="${fn:escapeXml(document.image.description)}" title="${fn:escapeXml(document.image.fileName)}" />
		</c:if>
	   
	    
		<%-- Display the contents of the text blocks --%>
		
		<c:forEach var="textBlock" items="${document.textBlocks}" varStatus="status">
			<a id="block${status.index}"></a>
			<c:if test="${not empty textBlock.title}">
				<h2>${fn:escapeXml(textBlock.title)}</h2>
			</c:if>
			<hst:html hippohtml="${textBlock.html}" />
		
		</c:forEach>
	</article>
</div>
