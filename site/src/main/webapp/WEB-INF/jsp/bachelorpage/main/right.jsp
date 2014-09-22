<%@ page contentType="text/html; charset=UTF-8" language="java"
  trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>


<section class="blok colorbg lichtpaars info">
  
  <%-- should use an icon instead of time --%>
  <time datetime="2014-06-11" class="datum large">16<span>okt</span></time>
  
  <c:if test="${fn:length(document.infoLines) > 0 }">
    <ul>
      <c:forEach items="${document.infoLines}" var="item">
        <li>
          <img src="<hst:link hippobean="${item.iconLine.original}"/>"
               alt="<c:out value="${item.iconLine.alt}"/>"
               title="<c:out value="${item.iconLine.alt}"/>" />
          <span class="details"><c:out value="${item.infoLine }"/></span>
        </li>
      </c:forEach>
    </ul>
  </c:if>
  
  <p><c:out value="${document.infoText}"/></p>
  <c:set var="internalLink"><hst:link hippobean="${document.infoLink.link }" /></c:set>              
  <a class="btn" href="${internalLink}" title="${document.infoLink.alt}">
     <span><c:out value="${document.infoLink.linkTitle}"/></span>
  </a>
</section>