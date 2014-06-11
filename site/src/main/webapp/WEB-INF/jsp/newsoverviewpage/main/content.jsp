<%@ page contentType="text/html; charset=UTF-8" language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<c:if test="$tag:isSubclassOfWebPage(document)}">
		<div class="inner">
			<c:if test="${not empty document.introduction }">
				<p class="intro">
					<c:out value="${document.introduction }" />
				</p>
			</c:if>
		</div>
</c:if>
				
<c:forEach var="item" items="${items}">
    <hst:link var="link" hippobean="${item}"/>
    <article class="well well-large">
        <hst:cmseditlink hippobean="${item}"/>
        <h3><a href="${link}">${fn:escapeXml(item.title)}</a></h3>
        <c:if test="${hst:isReadable(item, 'releaseDate.time')}">
          	<p class="badge badge-info">
            	<fmt:formatDate value="${item.releaseDate.time}" type="both" dateStyle="medium" timeStyle="short"/>
          	</p>
        </c:if>
        <p>${fn:escapeXml(item.introduction)}</p>
	</article>
</c:forEach>

<div class="paginator-style">
	<tag:simplepaginator paginator="${paginator}"/> 
</div>