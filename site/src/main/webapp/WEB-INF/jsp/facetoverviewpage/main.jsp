<%@ page contentType="text/html; charset=UTF-8" language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<hst:setBundle basename="nl.hsleiden.general.Messages"/>

<div id="main" role="main" class="">
    
  <c:if test="${tag:isSubclassOfWebPage(model.document)}">
    <tag:headerImage document="${model.document}" large="large"/>
  </c:if>
    
  <%-- <hst:include ref="top-container" /> --%>
  <div class="container">
	<div class="row">
      
	  <c:if test="${pageContext.request.requestContext.contentBean['class'].name == 'hslbeans.OverviewPage' or 
                    pageContext.request.requestContext.contentBean['class'].name == 'hslbeans.NewsOverviewPage' or
                    pageContext.request.requestContext.contentBean['class'].name == 'hslbeans.EventsOverviewPage' or   
                    pageContext.request.requestContext.contentBean['class'].name == 'org.hippoecm.hst.content.beans.standard.facetnavigation.HippoFacetSubNavigation'}">   
        <tag:highlightedItem highLightedItem="${model.document.highLightedItem }"/>
      </c:if>
       
      <c:choose>
        <c:when test="${empty model.document}">
          <tag:pagenotfound />
        </c:when>
        <c:otherwise>
          <section class="overzicht col-md-9">
            
            <h1 class="hidden"><c:out value="${model.document.title}"></c:out> </h1> 
            <hst:include ref="contentTop" />
            
            <tag:rssReader document="${model.document}"/>
                
            <div class="overzichtlijst">
              
              <c:if test="${not empty frontEndMessage or not (fn:length(model.items)>0) }">
                <h2 class="noQueryResults"><fmt:message key="facet.search.noresults"/></h2>
              </c:if>
              
              <c:forEach var="item" items="${model.items}">
              
                <hst:link var="link" hippobean="${item}" />
                <article class="media clearfix">
                  <hst:cmseditlink hippobean="${item}" />
                  
                  <tag:renderListImage item="${item}"/>
                
                  <a href="${link}">
                    <%-- afmeting afbeelding: 100x100 --%>
                    <figure class="media-object pull-left">
                      <c:if test="${not empty image}">
                        <img alt="${item.title }" title="${item.title }" src="${image }" />
                      </c:if>
                    </figure>
                    
                    <tag:renderDate document="${item}" dateClass="datum start"/>
                    
                    <div class="media-body">
                      <h1 class="media-heading"><c:out value="${item.title }"/></h1>
                      <p><c:out value="${item.introduction }"/></p>
                    </div>   
                  </a>     

                </article>
              </c:forEach>
            </div>  
          
            <div class="paginator-style">
              <opw:simplepaginator paginator="${model.paginator}" namespaced="false"/>
            </div>
      
            <hst:include ref="contentBottom" />
            
          </section>
        </c:otherwise>
      </c:choose>

	  <tag:facetednavigation facetnav="${model.facetBean}" labels="${model.labels}" enableSearch="true"/>

	</div>
  </div>
  <hst:include ref="bottom-container" />
</div>
