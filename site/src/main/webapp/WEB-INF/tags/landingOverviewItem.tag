<%@tag trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tag"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<%@ attribute name="item" rtexprvalue="true" required="true" type="nl.hsleiden.beans.OverviewBean"%>

<hst:setBundle basename="nl.hsleiden.sharebox.Messages" />

<div class="overviewItem">
  <h2><c:out value="${item.overviewBean.title}"/></h2>
  <hst:link var="link" hippobean="${item.highLighted}" />
  
  <a class="highlighted link" href="${link}" title="${item.highLighted.title}">
     <img src="<hst:link hippobean="${item.highLighted.headerImage.paragraphImage }" />" 
          alt="<c:out value="${item.highLighted.title }" escapeXml="true" />" 
          title="<c:out value="${item.highLighted.title }" escapeXml="true" />" />
  </a>
  
  
  <div class="title">
    <a href="${link}" title="${item.highLighted.title}">
     <span>
       <c:out value="${item.highLighted.title}"/>
     </span>  
    </a>
  </div>
  
  <div class="hr"><hr /></div>
  
  <c:if test="${not empty item.highLighted.releaseDate.time }">
    <p class="highLighted introduction">
      <fmt:formatDate value="${item.highLighted.releaseDate.time}" type="date" dateStyle="long"/>
    </p>
  </c:if>
 
  <p class="highLighted introduction">
    <c:out value="${item.highLighted.introduction }" />
  </p>
  
  <div class="hr"><hr /></div>
 
  <tag:topmenu menuItem="${item.menuItem}"/>
  
  <div class="hr"><hr /></div>
    
</div>

