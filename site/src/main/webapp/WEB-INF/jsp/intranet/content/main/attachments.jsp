<%@ include file="/WEB-INF/jspf/htmlTags.jspf" %>
<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<c:if test="${fn:length(attachments) gt 0}">
    <div class="widget">
        <ul class="widget-filelist">
        
	        <c:set var="title" value="${document.attachmentsTitle}" />
	        <c:if test="${empty title}">
	            <c:set var="title" value="Bijlagen" />
	        </c:if>

            <li class="first">${fn:escapeXml(title)}</li>
            <c:forEach var="attachment" items="${attachments}" varStatus="status">
                <li>
                    <span class="icon pdf"></span>
                    <p>
	                    <hst:link var="attachmentUrl" hippobean="${attachment.document}" />
	                    <a href="${attachmentUrl}" onClick="trackOutboundLink(this, 'Outbound Links', '${attachmentUrl}'); return false;">${fn:escapeXml(attachment.title)}</a>
	                    <span class="file-size">(${attachment.size})</span> 
                    </p>
                </li>
            </c:forEach>
        </ul>
    </div>
</c:if>
