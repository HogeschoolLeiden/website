<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix='hst' uri="http://www.hippoecm.org/jsp/hst/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<c:choose>
  <c:when test="${empty document}">
    <tag:pagenotfound/>
  </c:when>
  <c:otherwise>

    <article class="well well-large">
      <hst:cmseditlink hippobean="${document}"/>
      <header>
        <c:choose>
        	<c:when test="${status eq 'SUCCESSFUL' or status eq 'AWAITING_STATUS_REPORT'}">
				<c:if test="${hst:isReadable(document, 'title') }">
		          <h1><c:out value="${document.title}"/></h1>
		        </c:if>
		        <c:if test="${hst:isReadable(document, 'introduction') }">
		          <p><c:out value="${document.introduction}"/></p> 
		        </c:if>        	
        	</c:when>
        	<c:when test="${status eq 'CANCELLED'}">
				<c:if test="${hst:isReadable(document, 'cancelTitle') }">
		          <h1><c:out value="${document.cancelTitle}"/></h1>
		        </c:if>
		        <c:if test="${hst:isReadable(document, 'cancelIntroduction') }">
		          <p><c:out value="${document.cancelIntroduction}"/></p> 
		        </c:if>        	
        	</c:when>
        	<c:when test="${status eq 'FAILED'}">
				<c:if test="${hst:isReadable(document, 'failTitle') }">
		          <h1><c:out value="${document.failTitle}"/></h1>
		        </c:if>
		        <c:if test="${hst:isReadable(document, 'failIntroduction') }">
		          <p><c:out value="${document.failIntroduction}"/></p> 
		        </c:if>        	
        	</c:when>
        </c:choose>
        
        
      </header>
      
    </article>

  </c:otherwise>
</c:choose>