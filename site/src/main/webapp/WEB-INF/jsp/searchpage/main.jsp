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
    
      <section class="overzicht col-md-9 col-sm-8">
          
          <hst:include ref="contentTop" />
          
          <div class="overzichtlijst">
            <h3>
              <opw:public-parameter parameterName="q" var="query"/>
              <c:choose>
                <c:when test="${not (model.searchResults.totalHits > 0)}">
                  <fmt:message key="no.results.found.message" >
                    <fmt:param value="${fn:escapeXml(query)}"/>
                  </fmt:message>
                </c:when>
                <c:otherwise>
                  <fmt:message key="found.results.message" >
                    <fmt:param value="${model.searchResults.totalHits}"/>
                    <fmt:param value="${fn:escapeXml(query)}"/>
                  </fmt:message>
                </c:otherwise>
              </c:choose> 
            </h3>
            
            <c:forEach items="${model.searchResults.hits}" var="hit">
              <c:set var="item" value="${hit.bean}"/>
              
              <c:if test="${tag:isSubclassOfWebPage(item)}">
                <hst:link hippobean="${hit.bean}" var="link"/>
                  
                <article class="media clearfix">
                  <hst:cmseditlink hippobean="${item}" />
                    
                  <a href="${link}">
                    <tag:renderListImage item="${item}"/>                  
                    <div class="media-body">
                      <h1 class="media-heading"><c:out value="${item.title }"/></h1>
                      <p><c:out value="${item.introduction }"/></p>
                    </div>   
                  </a>
                     
                </article>
              </c:if>
                
            </c:forEach>
          </div>
          
          <div class="paginator-style">
              <opw:simplepaginator paginator="${model.paginator}" namespaced="false"/>
          </div>
      
          <hst:include ref="contentBottom" />
          
      </section>
      
      <div class="filter filter-collapse collapse clearfix algemeenfilter sm-facet-display">
        <hst:include ref="leftTop" />
         <hst:link siteMapItemRefId="search" var="searchPageUrl" navigationStateful="false"/>
         <tag:facetedelasticsearch facets="${model.facets}" searchPageUrl="${searchPageUrl}" facetItemCssClass="checkbox"  facetsCssClass="fieldset" />
      </div>

    </div>
  </div>
  <hst:include ref="bottom-container" />
</div>

