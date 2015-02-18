<%@ page contentType="text/html; charset=UTF-8" language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<hst:setBundle basename="nl.hsleiden.general.Messages"/>

<div id="main" role="main" class="vindjestudie">
    
  <c:if test="${tag:isSubclassOfWebPage(model.document)}">
    <tag:headerImage document="${model.document}" large="large" />
  </c:if>
    
  <div class="container">
	<div class="row">
       
      <c:choose>
        <c:when test="${empty model.document}">
          <tag:pagenotfound />
        </c:when>
        <c:otherwise>
          <section class="overzicht col-md-9">
            
              <tag:facetednavigation-bachelors facetnav="${model.facetBean}" labels="${model.labels}" enableSearch="true"/>
                       
              <tag:rssReader document="${model.document}"/>
                  
              <div class="overzichtlijst">
              
                <hst:cmseditlink hippobean="${model.document}" />
                <h1 class="title"><c:out value="${model.document.title}"></c:out> </h1>
                <p class="intro"><c:out value="${model.document.introduction }"/></p>
              
                <div class="paginator-style">
                  <opw:simplepaginator paginator="${model.paginator}" namespaced="false"/>
                </div>
                
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
                  <article class="media clearfix study">
                    <hst:cmseditlink hippobean="${item}" />
                    
                    <c:set var="image" value=""/>
                    <c:if test="${not empty item.icon }">
                        <hst:link var="image" hippobean="${item.icon.listImageMedium}" />  
                    </c:if>
                  
                    <a href="${link}">
                      <!-- afmeting afbeelding: 100x100 -->
                      <figure class="media-object pull-left">
                        <c:if test="${not empty image}">
                          <img alt="${item.title }" title="${item.title }" src="${image }" />
                        </c:if>
                      </figure>
                      
                      <div class="media-body">
                        <h1 class="media-heading"><c:out value="${item.title }"/></h1>
                        <p><c:out value="${item.introduction }"/></p>
                        <ul class="tags">
                          <li> 
                            <c:forEach items="${item.educationtagsLabels}" var="educationTagItem" varStatus="loop">
                              <c:out value="${educationTagItem}"/>
                              <c:if test="${not loop.last}">
                                <c:out value=" , "/>
                              </c:if>
                            </c:forEach>
                          </li>
                          <li>
                            <c:forEach items="${item.formtagLabels}" var="formTagItem"  varStatus="loop">
                              <c:out value="${formTagItem}"/>
                              <c:if test="${not loop.last}">
                                <c:out value=" , "/>
                              </c:if>
                            </c:forEach>
                          </li>
                        </ul>   
                      </div>
                    </a>     
  
                  </article>
                </c:forEach>

              <div class="paginator-style">
                <opw:simplepaginator paginator="${model.paginator}" namespaced="false"/>
              </div>

              </div>             
        
              <hst:include ref="contentBottom" />

          </section>
          
          <aside class="col-md-3 aside">
            <hst:include ref="rightTop" />
            <hst:include ref="right" />
            <hst:include ref="rightBottom" />
          </aside>
          
        </c:otherwise>
      </c:choose>

	</div>
  </div>
  <hst:include ref="bottom-container" />
</div>
