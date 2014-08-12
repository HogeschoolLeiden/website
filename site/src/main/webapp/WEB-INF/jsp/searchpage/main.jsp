<%@ page contentType="text/html; charset=UTF-8" language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>
<%@ taglib prefix="esl" uri="http://open-web.nl/hippo/elasticsearch"%>

<hst:setBundle basename="nl.hsleiden.general.Messages"/>

<div class="container-fluid">
	<%-- <hst:include ref="top-container" />     --%>
  	<div class="row-fluid">
		<nav class="span2">
<div id="searchresult-category-container">
	<div id="facets">
			<h4><fmt:message key="refine.results" /></h4>
			<div class="form">
				<hst:link siteMapItemRefId="search" var="searchPageUrl" navigationStateful="false"/>
				<esl:facets facets="${model.facets}" searchPageUrl="${searchPageUrl}" facetsCssClass="fieldset" />
			</div>
		</div>
</div>
			
		</nav>
		<div class="span8">
			<hst:include ref="content" />
			${model.searchResults.totalHits}<br/>
			<c:forEach items="${model.searchResults.hits}" var="hit">
				<c:set var="item" value="${hit.bean}"/>
				<hst:link hippobean="${hit.bean}" var="link"/>
				<article class="well well-large">
				  <hst:cmseditlink hippobean="${item}" />
        
                  <c:if test="${not empty tag:getFirstFlexibleBlockImage(item) }">
                    <div class="image-space">
                      <hst:link var="image" hippobean="${tag:getFirstFlexibleBlockImage(item).image.paragraphImage}" />
                      <img alt="${item.title }" title="${item.title }" src="${image }" />
                    </div>
                  </c:if>

                  <div ${not empty tag:getFirstFlexibleBlockImage(item) ? 'class="list-item-content"' : 'class="item-content"' } >
    			    <h3>
    				  <a href="${link}"><c:out value="${item.title}"/></a>
    				</h3>
              
                    <c:choose>
                      <c:when test="${item['class'].name=='hslbeans.EventPage' }">
  					   <c:if test="${hst:isReadable(item, 'eventDate.time')}">
  					     <p class="badge badge-info">
  						   <fmt:formatDate value="${item.eventDate.time}" type="both" dateStyle="medium" timeStyle="short" />
  					     </p>
  					   </c:if>
                      </c:when>
                      <c:otherwise>
                        <c:if test="${hst:isReadable(item, 'releaseDate.time')}">
                          <p class="badge badge-info">
                            <fmt:formatDate value="${item.releaseDate.time}" type="both" dateStyle="medium" timeStyle="short" />
                          </p>
                        </c:if>
                      </c:otherwise>
                    </c:choose>
  			       
        			<p><c:out value="${item.introduction}"/></p>
                  </div>
                  
                  <c:if test="${not empty tag:getFirstFlexibleBlockImage(item) }">
                    <div class="clear"></div>
                  </c:if>
				</article>
			</c:forEach>
			<opw:simplepaginator paginator="${model.paginator}"/>
		</div>
		<aside class="span2">
			<hst:include ref="rightTop" />
			<hst:include ref="right" />
			<hst:include ref="rightBottom" />
		</aside>
  	</div>
  	<hst:include ref="bottom-container" />
</div>
