<%@tag description="display image in header width" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<hst:setBundle basename="nl.hsleiden.general.Messages"/>

<%@ attribute name="content" rtexprvalue="true" required="true" type="hslbeans.Documents" %>

<c:if test="${hst:isReadable(content, 'asset') && not empty content.asset[0] }">
    
    <c:set var="title">
      <c:choose>
        <c:when test="${not empty content.title }"><c:out value="${content.title }"/></c:when>
        <c:otherwise><fmt:message key="documents.title" /></c:otherwise>
      </c:choose>
    </c:set>
	
    <div class="mod box closed downloads">
		<h2 class="head">${title}</h2>
		<ul class="downloads">
			<c:forEach items="${content.asset }" var="doc">
				<c:set var="filetype">
                  <opw:assettype asset="${doc.asset }" />
                </c:set>
				<li class="${filetype }">
                  <a href="<hst:link hippobean="${doc.asset }" />?forceDownload=true" target="_blank" title="<c:out value="${tag:getAssetTitle(doc.asset)}"/>">
                    <c:out value="${tag:getAssetTitle(doc.asset)}"/>
                     ( ${filetype }, <opw:assetsize asset="${doc.asset }" /> )
                  </a>
                </li>
			</c:forEach>
		</ul>
	</div>
</c:if>