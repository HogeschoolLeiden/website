<%@ page contentType="text/html; charset=UTF-8" language="java"
  trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<c:set var="mainClass">
  <c:choose>
    <c:when test="${tag:isSubclassOfWebPage(document) and 
              hst:isReadable(document, 'headerImage') and 
              not empty document.headerImage}">
        <c:out value="detail"/>
    </c:when>
    <c:otherwise>
        <c:out value="landing"/>
    </c:otherwise>
  </c:choose>
</c:set>

<div id="main" role="main" class="${mainClass}"> 
    
  <tag:renderBackLink sitemapRefId="${paramInfo.overviewSitemapRefId}"/>
          
  <tag:headerImage document="${document}"/>
        
  <div class="container">
    <%-- <hst:include ref="top-container" />      --%>
    <div class="row">
              
        <hst:include ref="content"/>
      
      <hst:include ref="left" />
      
    </div>
    <hst:include ref="bottom-container" />
  </div>
  
  
</div>
