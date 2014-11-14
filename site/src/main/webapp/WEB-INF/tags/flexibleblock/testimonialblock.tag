<%@tag trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tag"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<%@ attribute name="content" rtexprvalue="true" required="true" type="hslbeans.Testimonial"%>

<hst:setBundle basename="nl.hsleiden.sharebox.Messages" />

<blockquote class="media testimonial">
  
  <div class="media-body">
  	<c:forEach items="${content.testimonialText}" var="text" varStatus="loop">
  		<c:choose>
  			<c:when test="${loop.last}">
			    <h2 class="media-heading">
			      <c:out value="${text}"></c:out>
			      <span></span>
			    </h2>
  			</c:when>
  			<c:otherwise>
			    <h2 class="media-heading-testimonial">
			      <c:out value="${text}"></c:out>
			      <span></span>
			    </h2>
  			</c:otherwise>
  		</c:choose>
	    
    </c:forEach>

    <h3>
      <c:out value="${content.name }" />
    </h3>

  </div>
  
</blockquote>
