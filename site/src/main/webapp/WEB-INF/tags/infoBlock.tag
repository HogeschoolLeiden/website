<%@tag description="display image in header width" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<%@ attribute name="document" rtexprvalue="true" required="true" type="org.hippoecm.hst.content.beans.standard.HippoBean" %>
<%@ attribute name="event" rtexprvalue="true" type="java.lang.Boolean" %>

<c:set var="nolink">
  <c:choose>
    <c:when test="${not empty document.infoBlock.infoLink and not empty document.infoBlock.infoLink.linkTitle }">
      <c:out value=""/>
    </c:when>
    <c:otherwise>    
      <c:out value="nolink"/>
    </c:otherwise>
  </c:choose>
</c:set>

<section class="blok colorbg lichtpaars info ${nolink}">
  <c:choose>
    <c:when test="${event}">
      <tag:renderDate document="${document}" dateClass="datum large info"/>
    </c:when>
    <c:otherwise>
      <figure class="datum large info">
        <img class="info" src="<hst:link hippobean="${document.icon.listImageMedium}"/>"
           alt="<c:out value="${document.icon.alt}"/>"
           title="<c:out value="${document.icon.alt}"/>" />
      </figure>
    </c:otherwise>
  </c:choose>
  
  <c:if test="${fn:length(document.infoBlock.infoLines) > 0 }">
    <ul>
      <c:forEach items="${document.infoBlock.infoLines}" var="item">
        <li>
          <span class="fa ${item.icon.firstItem.key }"></span>
          <span class="details"><c:out value="${item.infoLine }"/></span>
        </li>
      </c:forEach>
    </ul>
  </c:if>
  
  <p><c:out value="${document.infoBlock.infoText}"/></p>
    
  <c:if test="${empty nolink }">
    <c:set var="internalLink"><hst:link hippobean="${document.infoBlock.infoLink.link }" /></c:set>              
    <a class="btn" href="${internalLink}" title="${document.infoBlock.infoLink.alt}">
       <span><c:out value="${document.infoBlock.infoLink.linkTitle}"/></span>
    </a> 
  </c:if>
  
</section>