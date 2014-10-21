<%@tag description="display image in header width" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<%@ attribute name="document" rtexprvalue="true" required="true" type="org.hippoecm.hst.content.beans.standard.HippoDocument" %>
<%@ attribute name="large" rtexprvalue="true" type="java.lang.String" %>

<c:if test="${hst:isReadable(document, 'headerImage')}">
  <c:if test="${not empty document.headerImage }">
    
    <div class="background img">
      <div class="img">
       <c:choose>
        <%-- afmeting afbeelindg: 1280x620 --%>
        <c:when test="${not empty large }">
          <hst:link var="img" hippobean="${document.headerImage.detailBackground}"/> 
        </c:when>
        <c:otherwise>
          <%-- afmeting afbeelindg: 1280x295 --%>
          <hst:link var="img" hippobean="${document.headerImage.detailBackgroundSmall}"/>         
        </c:otherwise>
       </c:choose>
        
        <c:if test="${not empty img }">
            <img src="${img}" title="<c:out value="${document.headerImage.alt}"/>"
                 alt="<c:out value="${document.headerImage.alt}"/>"/>
        </c:if>
      </div>
    </div>   
  
  </c:if>
</c:if>