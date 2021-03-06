<%@tag description="display image in header width" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<%@ attribute name="content" rtexprvalue="true" required="true" type="hslbeans.Image" %>
<%@ attribute name="event" type="java.lang.Boolean" rtexprvalue="true" required="false" %>

<c:if test="${not empty content.image }">
  <c:set var="imageTag">
    <figure class="fexibleblock image">
      <img  ${event ? 'itemprop="photo"': ''} src="<hst:link hippobean="${content.image.article }" />" 
            alt="<c:out value="${content.image.alt }" escapeXml="true" />" 
            title="<c:out value="${content.image.alt }" escapeXml="true" />" />
      
      <hst:headContribution category="metadata" keyHint="image">
        <meta property="og:image" content="<hst:link hippobean="${content.image.article }" />"/>
      </hst:headContribution>      
      
            <c:if test="${not empty content.image.description }">
              <figcaption>
              <c:out value="${content.image.description }" escapeXml="true" />
              </figcaption>
            </c:if>
    </figure>
  </c:set>
  ${imageTag }
</c:if>
