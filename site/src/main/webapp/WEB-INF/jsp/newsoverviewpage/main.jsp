<%@ page contentType="text/html; charset=UTF-8" language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<div class="container-fluid">
	<hst:include ref="top-container" />    
  	<div class="row-fluid">
        <div class="header span2"></div>
		<nav class="span2">
			<hst:include ref="leftTop" />
			<tag:facetednavigation facetnav="${model.facetBean}" labels="${model.labels}"></tag:facetednavigation>
			<hst:include ref="leftBottom" />
		</nav>
		<div class="span8">
			<hst:include ref="contentTop" />
			<%--content in-lined for better performance --%>
			<c:choose>
				<c:when test="${empty model.document}">
					<tag:pagenotfound />
				</c:when>
				<c:otherwise>
					
					<div class="inner">
						<c:if test="${not empty model.document.introduction }">
							<p class="intro">
								<c:out value="${model.document.introduction }" />
							</p>
						</c:if>
					</div>
						
					<tag:resultsummary paginator="${model.paginator}" />
			
					<c:forEach var="item" items="${model.items}">
						<hst:link var="link" hippobean="${item}" />
						<article class="well well-large">
							<hst:cmseditlink hippobean="${item}" />
							<h3>
								<a href="${link}">${fn:escapeXml(item.title)}</a>
							</h3>
							<c:if test="${hst:isReadable(item, 'releaseDate.time')}">
								<p class="badge badge-info">
									<fmt:formatDate value="${item.releaseDate.time}" type="both" dateStyle="medium" timeStyle="short" />
								</p>
							</c:if>
							<p>${fn:escapeXml(item.introduction)}</p>
						</article>
					</c:forEach>
					
				  	<div class="paginator-style">
			 			<tag:simplepaginator paginator="${model.paginator}"/>
					</div>
			
				</c:otherwise>
			</c:choose>

			<hst:include ref="contentBottom" />
		</div>
		<aside class="span2">
			<hst:include ref="rightTop" />
			<hst:include ref="right" />
			<hst:include ref="rightBottom" />
		</aside>
        <div class="header span2"></div>
  	</div>
	<hst:include ref="bottom-container" />
</div>
