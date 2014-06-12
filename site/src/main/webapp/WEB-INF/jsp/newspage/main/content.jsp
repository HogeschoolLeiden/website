<%@ page contentType="text/html; charset=UTF-8" language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<c:choose>
	<c:when test="${empty document}">
		<tag:pagenotfound />
	</c:when>
	<c:otherwise>
	   <article class="well well-large">
		 <hst:cmseditlink hippobean="${document}" />
		 <header>
		   <h2>
			 <c:out value="${document.title }" escapeXml="true" />
		   </h2>
		   <c:if test="${hst:isReadable(document, 'date.time')}">
			 <p class="badge badge-info">
			   <fmt:formatDate value="${document.date.time}" type="both"
							dateStyle="medium" timeStyle="short" />
			 </p>
		    </c:if>
		  </header>

		  <div class="inner">
			<c:if test="${not empty document.introduction }">
			  <p class="intro">
			    <c:out value="${document.introduction }" />
			  </p>
			</c:if>

	         <tag:flexibleblock content="${document.flexibleblock }"/>

			 <c:set var="path" value="${pageContext.request.pathInfo }" />
		  </div>
	   </article>
    </c:otherwise>
</c:choose>
