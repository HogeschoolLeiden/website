<%@ include file="/WEB-INF/jspf/htmlTags.jspf"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>

<%@include file="/WEB-INF/jsp/intranet/common/content.jsp" %>

<c:if test="${fn:length(images) gt 0}">
    
    <%-- This image gallery uses the free version of the galleria plugin --%>
    
 <div id="galleria">
    <c:forEach var="image" items="${images}">
        <hst:link var="largeImageUrl" hippobean="${image.original}"/>
        <hst:link var="thumbnailImageUrl" hippobean="${image.thumbnail}"/>
        <a href="${largeImageUrl }"><img src="${thumbnailImageUrl}" data-description="${image.description}"></a>
    </c:forEach>
 </div>
 
 <script>
     <hst:link var="themeUrl" path="js/intranet/galleria/themes/classic/galleria.classic.min.js" />
     
     Galleria.loadTheme('${themeUrl}');
  Galleria.run('#galleria', {
      autoplay: 5000 
  });
 </script>
 
</c:if>

<%@include file="/WEB-INF/jsp/intranet/common/metadata.jsp" %>   
    