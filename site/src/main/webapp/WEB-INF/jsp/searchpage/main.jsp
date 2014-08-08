<%@ page contentType="text/html; charset=UTF-8" language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<div class="container-fluid">
	<%-- <hst:include ref="top-container" />     --%>
  	<div class="row-fluid">
		<nav class="span2">
<div id="searchresult-category-container">
	<div id="facets">
			<hst:link siteMapItemRefId="search" var="searchPage" navigationStateful="false"/>
			<h4>refine results:</h4>
			<div class="form">
				<div class="fieldset">
					<c:forEach items="${model.facets.categories}" var="category">
					<h5 class="facetName"><c:out value="${category.title}"/></h5>
						<c:forEach items="${category.items}" var="item">
							<c:set var="facetLink" value="${searchPage}${item.url}?${pageContext.request.queryString }"></c:set>
							<c:choose>
							<c:when test="${not item.selected}">
								<div class="input">
									<a href="${facetLink}"><c:out value="${item.label}"/></a> (${item.count})
								</div>
							</c:when>
							<c:otherwise>
								<div class="input selected">
									<span><c:out value="${item.label}"/></span> 
									<a href="${facetLink}" class="removeLink"></a>
									</div>
							</c:otherwise>
							</c:choose>
						</c:forEach>	
					</c:forEach> 
					
					
				</div>
		
			</div>
		</div>
</div>
			
		</nav>
		<div class="span8">
			<hst:include ref="content" />
			${model}
		</div>
		<aside class="span2">
			<hst:include ref="rightTop" />
			<hst:include ref="right" />
			<hst:include ref="rightBottom" />
		</aside>
  	</div>
  	<hst:include ref="bottom-container" />
</div>
