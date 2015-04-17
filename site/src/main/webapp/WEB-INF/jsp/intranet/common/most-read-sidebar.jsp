<%@ include file="/WEB-INF/jspf/htmlTags.jspf"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>

<c:if test="${fn:length(links) gt 0 }">

	<div class="widget">
	    <ul class="widget-list arrows">
	        <li class="first">Veelgebruikte informatie</li>
	        <c:forEach var="link" items="${links}" begin="0" end="4">
                <li>
                    <hst:link var="detailUrl" hippobean="${link.document}" />
                    <a href="${detailUrl}" title="${fn:escapeXml(link.document.introduction)}">${fn:escapeXml(link.title)}</a>
                </li>
            </c:forEach>
	    </ul>
	</div>
            
</c:if>