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
  
  <figure>
    <iframe width="515" height="290" src="${fn:escapeXml(content.url)}" ${content.youtubePlayerParameters.allowFullScreen ? 'allowfullscreen' : ''}></iframe>
    <%--
    <figcaption>
      <span>       Bijschrift bij video      </span>  where does this come from?? add new field to data model
    </figcaption>
    --%>
  </figure>
  
</c:if>

