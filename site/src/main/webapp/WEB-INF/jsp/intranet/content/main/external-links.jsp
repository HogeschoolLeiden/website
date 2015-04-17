<%@ include file="/WEB-INF/jspf/htmlTags.jspf" %>
<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<c:if test="${fn:length(document.externalLinks) gt 0}">
    
    <div class="widget">
        <ul class="widget-list">
        
            <c:set var="title" value="${document.externalLinksTitle}" />
            <c:if test="${empty title}">
                <c:set var="title" value="Externe links" />
            </c:if>
    
            <li class="first">${fn:escapeXml(title)}</li>
            
            <c:forEach var="link" items="${document.externalLinks}">
                <li>    
                    <a href="${linkUrl}" onClick="trackOutboundLink(this, 'Outbound Links', '${link.url}'); return false;">${fn:escapeXml(link.title)}</a>
                </li>
            </c:forEach>
            
        </ul>    
    </div>

</c:if>