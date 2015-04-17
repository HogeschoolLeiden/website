<%@ include file="/WEB-INF/jspf/htmlTags.jspf"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>

<h1>Niet gekoppelde bestanden</h1>

<p>Er zijn in totaal ${totalNumberOfAssets} bestanden aanwezig. ${fn:length(assets)} hiervan zijn niet gekoppeld aan documenten.</p>

<ul>
    <c:set var="cmsUrl"><hs:siteSetting key="cms.url"/></c:set>
	<c:forEach var="asset" items="${assets}">
	    <li>
	       <hst:link var="assetUrl" hippobean="${asset}" />
	       <c:set var="cmsItemUrl" value="${cmsUrl}/?path=${asset.path}" />
	       <a href="${cmsItemUrl}" target="cms">open in CMS</a> | <a href="${assetUrl}" target="asset">${asset.path}</a> 
	    </li>
	
	</c:forEach>
</ul>