<%@tag description="display image in header width" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<%@ attribute name="document" rtexprvalue="true" required="true" type="org.hippoecm.hst.content.beans.standard.HippoDocument" %>

<c:if test="${hst:isReadable(document, 'headerImage')}">
  <c:if test="${not empty document.headerImage }">
    
    <div class="background img">
      <div class="img">
        <%-- afmeting afbeelindg: 1280x621 --%>
        <hst:link var="img" hippobean="${document.headerImage.allWidthImage}"/>
        <c:if test="${not empty img }">
            <img src="${img}" title="<c:out value="${document.headerImage.alt}"/>"
                 alt="<c:out value="${document.headerImage.alt}"/>"/>
        </c:if>
      </div>
    </div>   
  
  </c:if>
</c:if>