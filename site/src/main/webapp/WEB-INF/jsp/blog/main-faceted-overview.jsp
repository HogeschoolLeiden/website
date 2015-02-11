<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>
<%--
  Copyright 2014 Hippo B.V. (http://www.onehippo.com)

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  --%>

<hst:setBundle basename="nl.hsleiden.general.Messages"/>

<div id="main" role="main" class="">
    
  <c:if test="${tag:isSubclassOfWebPage(model.document)}">
    <tag:headerImage document="${model.document}"/>
  </c:if>
  
  <div class="container">
  <div class="row">
  
    <c:if test="${pageContext.request.requestContext.contentBean['class'].name == 'hslbeans.OverviewPage'}">
      <tag:highlightedItem highLightedItem="${model.document.highLightedItem }"/>
    </c:if>
  
    <c:choose>
        <c:when test="${empty model.document}">
          <tag:pagenotfound />
        </c:when>
        <c:otherwise>
          <section class="overzicht col-md-9 col-sm-8">
            
            <div class="overzichtlijst">
              
              <hst:cmseditlink hippobean="${model.document}" />
              <h1 class="title"><c:out value="${model.document.title}"></c:out> </h1>
              <p class="intro"><c:out value="${model.document.introduction }"/></p>
              <hst:include ref="contentTop" />
              
              <c:if test="${not empty frontEndMessage}">
                <h2 class="noQueryResults"><fmt:message key="${frontEndMessage}" />
                  <c:if test="${frontEndMessage eq 'facet.search.noresults'}">
                    <hst:link var="canonicalLink" canonical="true"/>  
                    <a href="${canonicalLink}">
                      <c:out value=" "/>
                      <fmt:message key="remove.query.param"/> 
                    </a>
                  </c:if>
                </h2>
              </c:if>
              
              <c:forEach var="item" items="${model.items}">
              
                <hst:link var="link" hippobean="${item}" />
                <article class="media clearfix">
                  <hst:cmseditlink hippobean="${item}" />
                  
                  <a href="${link}">
                    <%-- afmeting afbeelding: 100x100 --%>
                    <tag:renderListImage item="${item}"/>
                    
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