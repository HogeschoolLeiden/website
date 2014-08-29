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
       <div>
	   <article class="well well-large">
		 <hst:cmseditlink hippobean="${document}" />
		 <header>
		   <h1>
			 <span><c:out value="${document.title }"/></span>
		   </h1>
		   <%-- <h2>
			 <c:out value="${document.subtitle }" escapeXml="true" />
		   </h2> --%>
		   <c:if test="${hst:isReadable(document, 'eventDate.time')}">
			 <p class="badge badge-info">
               <time datetime="${document.eventDate.time}">
			     <fmt:formatDate value="${document.eventDate.time}" type="both" dateStyle="medium" timeStyle="short" />
			   </time>
              </p>
		    </c:if>
		  </header>

		  <div class="inner">
             <tag:toolbox document="${document }" />
		  </div>
      
	   </article>
       </div>
    </c:otherwise>
</c:choose>
