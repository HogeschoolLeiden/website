<%@ page contentType="text/html; charset=UTF-8" language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>

<c:choose>
  <c:when test="${empty document}">
	<tag:pagenotfound />
  </c:when>
  <c:otherwise>
	<article class="well well-large">
	  <hst:cmseditlink hippobean="${document}" />
		<header>
		  <h1>
			 <c:out value="${document.title }" escapeXml="true" />
		  </h1>
		</header>

		<div class="inner">
		  <c:if test="${not empty document.introduction }">
		    <p class="intro">
		      <c:out value="${document.introduction }" />
			</p>
		  </c:if>             
		 </div>
	</article>
  </c:otherwise>
</c:choose>
