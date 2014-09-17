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
          <section class="overzicht col-md-9">
            
            <h1 class="hidden"><c:out value="${model.document.title}"></c:out> </h1> 
            <hst:include ref="contentTop" />
            
            <%-- <tag:rssReader document="${model.document}"/> --%>
                
            <div class="overzichtlijst">
              <c:forEach var="item" items="${model.items}">
              
                <hst:link var="link" hippobean="${item}" />
                <article class="media clearfix">
                  <hst:cmseditlink hippobean="${item}" />
                  
                  <c:set var="image" value=""/>
                  <c:if test="${not empty tag:getFirstFlexibleBlockImage(item) }">
                    <div class="image-space">
                      <hst:link var="image" hippobean="${tag:getFirstFlexibleBlockImage(item).image.listImageMedium}" />  
                    </div>
                  </c:if>
                
                  <a href="${link}">
                    <!-- afmeting afbeelding: 100x100 -->
                    <figure class="media-object pull-left">
                      <c:if test="${not empty image}">
                        <img alt="${item.title }" title="${item.title }" src="${image }" />
                      </c:if>
                    </figure>
                    <tag:renderDate document="${item}" dateClass="datum start"/>
                    <%-- a content type addition, (end date for events probably)  and after it
                    modify the renderDate tag and uncomment the following line
                    <tag:renderDate document="${item}" dateClass="datum start"/>
                    --%>
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