<%@ page contentType="text/html; charset=UTF-8" language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<c:choose>
  <c:when test="${empty document}">
    <tag:pagenotfound/>
  </c:when>
  <c:otherwise>

    <article class="well well-large">
      <hst:cmseditlink hippobean="${document}"/>
      <header>
        
        <c:if test="${hst:isReadable(document, 'title') }">
          <h1><c:out value="${document.title}"/></h1>
        </c:if>
        <%-- <c:if test="${hst:isReadable(document, 'subtitle') }">
          <h2><c:out value="${document.subtitle}"/></h2>
        </c:if>
        <c:if test="${hst:isReadable(document, 'introduction') }">
          <p><c:out value="${document.introduction}"/></p> 
        </c:if> --%>
      </header>
      
      <tag:toolbox document="${document }" />
      
    </article>

  </c:otherwise>
</c:choose>