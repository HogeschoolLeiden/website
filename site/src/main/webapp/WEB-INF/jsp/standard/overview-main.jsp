<%@ page contentType="text/html; charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<hst:setBundle basename="nl.hsleiden.general.Messages"/>

<div id="main" role="main" class="">
    
  <c:if test="${tag:isSubclassOfWebPage(document)}">
    <tag:headerImage document="${document}"/>
  </c:if>
    
  <%-- <hst:include ref="top-container" /> --%>
  <div class="container">
  <div class="row">

    <c:if test="${pageContext.request.requestContext.contentBean['class'].name == 'hslbeans.OverviewPage'}">
        <tag:highlightedItem highLightedItem="${document.highLightedItem }"/>
      </c:if>
       
      <c:choose>
        <c:when test="${empty document}">
          <tag:pagenotfound />
        </c:when>
        <c:otherwise>
          <hst:include ref="content"/>
        </c:otherwise>
      </c:choose>

    <hst:include ref="leftTop" /> 
    <hst:include ref="left"/>
    <hst:include ref="leftBottom" />

  </div>
  </div>
  <hst:include ref="bottom-container" />
</div>