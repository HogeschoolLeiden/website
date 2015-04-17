<%@ include file="/WEB-INF/jspf/htmlTags.jspf"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>

<section class="categories-category">
    <h2>${fn:escapeXml(mainTitle)}</h2>
    <p class="categories-subheader">
        Veelgebruikte informatie
    </p>
    <ul>
        <c:forEach var="link" items="${links}" begin="0" end="4">
            <li>
				<hst:link var="detailUrl" hippobean="${link.document}" />
				<a href="${detailUrl}" title="${fn:escapeXml(link.document.introduction)}">${fn:escapeXml(link.title)}</a>
            </li>
        </c:forEach>
       
    </ul>
    <hst:link var="overviewUrl" path="${overviewPath}"/>
    <a class="categories-button" href="${overviewUrl}" title="${fn:escapeXml(overviewLinkText)}">${fn:escapeXml(overviewLinkText)}</a>
</section>

