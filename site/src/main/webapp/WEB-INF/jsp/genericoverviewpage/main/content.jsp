<%@ page contentType="text/html; charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<section class="overzicht col-md-9">
   
   <hst:cmseditlink hippobean="${document}" />
   <h1 class="hidden"><c:out value="${document.title}"></c:out> </h1>
   <hst:include ref="contentTop" />
   
   <tag:rssReader document="${document}"/>
   
   <div class="overzichtlijst">
    <c:forEach var="item" items="${items}">
    
      <hst:link var="link" hippobean="${item}" />
      <article class="media clearfix">
        <hst:cmseditlink hippobean="${item}" />
        <c:set var="image" value=""/>
        
        <c:if test="${not empty tag:getFirstFlexibleBlockImage(item) }">
          <hst:link var="image" hippobean="${tag:getFirstFlexibleBlockImage(item).image.listImageMedium}" />  
        </c:if>
              
        <a href="${link}">
          
          <!-- afmeting afbeelding: 100x100 -->
          <figure class="media-object pull-left">
            <c:if test="${not empty image}">
              <img alt="${item.title }" title="${item.title }" src="${image }" />
            </c:if>
          </figure>
  
          <c:if test="${not pageContext.request.requestContext.contentBean['class'].name == 'hslbeans.MedewerkersOverviewPage' }">
            <tag:renderDate document="${item}" dateClass="datum start"/>
          </c:if>
  
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

                      
