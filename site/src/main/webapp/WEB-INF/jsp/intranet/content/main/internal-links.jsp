<%@ include file="/WEB-INF/jspf/htmlTags.jspf" %>
<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<c:if test="${fn:length(document.internalLinks) gt 0}">
    
    <div class="widget">
        <ul class="widget-list">
        
		    <c:set var="title" value="${document.internalLinksTitle}" />
		    <c:if test="${empty title}">
		        <c:set var="title" value="Links" />
		    </c:if>
    
            <li class="first">${fn:escapeXml(title)}</li>
		    
	        <c:forEach var="link" items="${document.internalLinks}">
	            <li>    
	                <hst:link var="linkUrl" hippobean="${link.document}" ></hst:link>
	                <a href="${linkUrl}">${fn:escapeXml(link.title)}</a>
	            </li>
	        </c:forEach>
	        
        </ul>    	    
    </div>

</c:if>
