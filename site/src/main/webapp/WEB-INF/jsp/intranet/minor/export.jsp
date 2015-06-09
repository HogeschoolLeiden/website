<%@ include file="/WEB-INF/jspf/htmlTags.jspf"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>

<!doctype html>
<html lang="nl">
    <head>
        <meta charset="utf-8">
    </head>
    <body>
    
		<h1>Minoren voor schooljaar ${startYear} - ${endYear}</h1>
		<p>Selecteer één of meerdere minoren om te exporteren naar Excel formaat.</p>

		<c:choose>
		       <c:when test="${not empty groupedResult}">
		          <c:set var="actionUrl"><hst:actionURL /></c:set>
		           <hst:resourceURL var="downloadUrl" />
		          <form method="post" action="${downloadUrl}" >
			         <c:forEach var="group" items="${groupedResult.groups}">
			         
			             <h2>Minoren van ${fn:escapeXml(group.category)}</h2>
			             
			              <c:forEach var="minor" items="${group.documents}">
                             
                             <c:set var="checked">${fn:contains(selected, minor.canonicalUUID)?'checked':''}</c:set>
		                     <hst:link var="detailUrl" hippobean="${minor}"/>
		                     <input type="checkbox" name="uuid" value="${minor.canonicalUUID}" ${checked}/>
		                     <a href="${detailUrl}" title="${fn:escapeXml(minor.introduction)}">${fn:escapeXml(minor.title)}</a><br>

			              </c:forEach>
			         </c:forEach>
			         <p>
			             <input type="submit" value="Export" />
			             <c:if test="${not empty fileName}">
			                 <hst:resourceURL var="downloadUrl" />
			                 <a href="${downloadUrl}">download ${fileName}</a>
			             </c:if>
			         </p>
			       </form>
		       </c:when>
		       
		       <c:otherwise>
		         <p>Er zijn geen minoren gevonden</p>
		       </c:otherwise>
        </c:choose>
    </body>
</html>
