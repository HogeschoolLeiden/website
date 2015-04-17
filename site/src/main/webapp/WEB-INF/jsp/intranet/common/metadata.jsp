<%@ include file="/WEB-INF/jspf/htmlTags.jspf"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>

<c:if test="${not empty metadata}">
    <%-- display the metadata of the document --%>    
    <div class="article-meta">
        <c:forEach var="group" items="${metadata.groups}">
            <ul>
                <li>${fn:escapeXml(group.title)}</li>
                <c:forEach var="item" items="${group.items}" varStatus="status">
                    <hst:link var="itemUrl" path="${item.path}" />
                    <li><a href="${itemUrl}" title="toon alles voor ${fn:escapeXml(item.title)}">${fn:escapeXml(item.title)} ${(not status.last)?', ':''}</a></li>
                </c:forEach>
            </ul>
        </c:forEach>    
    </div>
</c:if>
    