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

<div id="main" role="main" class="">

  <c:if test="${tag:isSubclassOfWebPage(model.document)}">
    <tag:headerImage document="${model.document}" large="large"/>
  </c:if>
  
  <div class="container">
    <div class="row">
    
      <section class="overzicht col-md-9">
          
          <hst:include ref="contentTop" />
          
          <div class="overzichtlijst">
            <h3>
              <opw:public-parameter parameterName="q" var="query"/>
              <fmt:message key="found.results.message" >
                <fmt:param value="${model.searchResults.totalHits}"/>
                <fmt:param value="${fn:escapeXml(query)}"/>
              </fmt:message>
            </h3>
            
            <c:forEach items="${model.searchResults.hits}" var="hit">
              <c:set var="item" value="${hit.bean}"/>
              <hst:link hippobean="${hit.bean}" var="link"/>
                
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
      
      <div class="filter filter-collapse collapse clearfix algemeenfilter">
        <hst:include ref="leftTop" />
        <%-- <h4><fmt:message key="refine.results" /></h4> --%>
         <hst:link siteMapItemRefId="search" var="searchPageUrl" navigationStateful="false"/>
         <tag:facetedelasticsearch facets="${model.facets}" searchPageUrl="${searchPageUrl}" facetItemCssClass="checkbox"  facetsCssClass="fieldset" />
      </div>

    </div>
  </div>
  <hst:include ref="bottom-container" />
</div>

