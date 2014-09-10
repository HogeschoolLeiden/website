<%@ tag language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<hst:setBundle basename="nl.hsleiden.general.Messages"/>

<div class="overzichtlijst">
  <c:forEach var="item" items="${items}">
    
    <hst:link var="link" hippobean="${item}" />
    
    <article class="media clearfix">
      <hst:cmseditlink hippobean="${item}" />
      <c:set var="image" value=""/>
      
      <c:if test="${not empty item.headerImage }">
          <hst:link var="image" hippobean="${item.headerImage.listImageMedium}" />  
      </c:if>
    
      <a href="${link}">
        <!-- afmeting afbeelding: 100x100 -->
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
 