<%@tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tag" %>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<%@ attribute name="content" rtexprvalue="true" required="true" type="nl.hsleiden.beans.YoutubeBean" %>

<c:if test="${not empty content.url}">
  <div class="catalog video">
    <div class="align center">
    <object class="youtube">
      <param name="movie" value="${content.url}"/>
        <param name="allowFullScreen" value="${content.youtubePlayerParameters.allowFullScreen}"/>
        <param name="allowScriptAccess" value="always"/>
        <embed src="${content.url}" type="application/x-shockwave-flash" 
            allowfullscreen="${content.youtubePlayerParameters.allowFullScreen}"
            allowscriptaccess="always" style="width: 515px; height: 260px;"/> 
    </object>
    </div>
  </div>
</c:if>

