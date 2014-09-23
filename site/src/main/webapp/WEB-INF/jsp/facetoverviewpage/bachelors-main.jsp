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
    <tag:headerImage document="${model.document}"/>
  </c:if>
    
  <%-- <hst:include ref="top-container" /> --%>
  <div class="container">
	<div class="row">
       
      <c:choose>
        <c:when test="${empty model.document}">
          <tag:pagenotfound />
        </c:when>
        <c:otherwise>
          <section class="overzicht col-md-9">
            
            <h1 class="hidden"><c:out value="${model.document.title}"></c:out>  </h1> 
 
              <tag:facetednavigation-bachelors facetnav="${model.facetBean}" labels="${model.labels}" enableSearch="true"/>
                       
              <tag:rssReader document="${model.document}"/>
                  
              <div class="overzichtlijst">
                <c:forEach var="item" items="${model.items}">
                
                  <hst:link var="link" hippobean="${item}" />
                  <article class="media clearfix study">
                    <hst:cmseditlink hippobean="${item}" />
                    
                    <c:set var="image" value=""/>
                    <c:if test="${not empty item.icon }">
                      <div class="image-space">
                        <hst:link var="image" hippobean="${item.icon.listImageMedium}" />  
                      </div>
                    </c:if>
                  
                    <a href="${link}">
                      <!-- afmeting afbeelding: 100x100 -->
                      <figure class="media-object pull-left">
                        <c:if test="${not empty image}">
                          <img alt="${item.title }" title="${item.title }" src="${image }" />
                        </c:if>
                      </figure>

                      <%-- a content type addition, (end date for events probably)  and after it
                      modify the renderDate tag and uncomment the following line
                      <tag:renderDate document="${item}" dateClass="datum start"/>
                      --%>
                      
                      <div class="media-body">
                        <h1 class="media-heading"><c:out value="${item.title }"/></h1>
                        <p><c:out value="${item.introduction }"/></p>
                        <ul class="tags">
                          <li><c:out value="${item.educationtags[0] }"/></li>
                          <li><c:out value="${item.formtag.firstItem.label }"/></li>
                        </ul>   
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
          
          <aside class="col-md-3 col-sm-3 aside">
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
