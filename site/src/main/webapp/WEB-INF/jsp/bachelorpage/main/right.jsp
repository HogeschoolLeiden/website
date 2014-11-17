<%@ page contentType="text/html; charset=UTF-8" language="java"
  trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>


<c:choose>
  <c:when test="${tag:isInfoBlockDisplayable(document) }">
    <tag:infoBlock document="${document}"/>
  </c:when>
  <c:otherwise>
    <figure class="datum large info"> 
      <img class="info" src="<hst:link hippobean="${document.icon.listImageMedium}"/>"
         alt="<c:out value="${document.icon.alt}"/>"
         title="<c:out value="${document.icon.alt}"/>" />  <%-- size:60x60 or width: 60px--%>
    </figure>
  </c:otherwise>
</c:choose>

<tag:fbLikeShare document="${document}"/>


