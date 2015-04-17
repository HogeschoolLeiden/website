<%@ include file="/WEB-INF/jspf/htmlTags.jspf"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>

<h1>Niet gekoppelde afbeeldingen</h1>

<p>Er zijn in totaal ${totalNumberOfImages} afbeeldingen aanwezig. ${fn:length(images)} hiervan zijn niet gekoppeld aan documenten.</p>

<ul>
    <c:set var="cmsUrl"><hs:siteSetting key="cms.url"/></c:set>
	<c:forEach var="image" items="${images}">
	    <li>
	       <hst:link var="imageUrl" hippobean="${image}" />
	       <c:set var="cmsItemUrl" value="${cmsUrl}/?path=${image.path}" />
	       <a href="${cmsItemUrl}" target="cms">open in CMS</a> | <a href="${imageUrl}" target="image">${image.path}</a> 
	    </li>
	
	</c:forEach>
</ul>