<%@ tag language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<%@ attribute name="highLightedItem" type="hslbeans.WebPage" required="true" %>

<c:choose>
  <c:when test="${pageContext.request.requestContext.contentBean['class'].name == 'org.hippoecm.hst.content.beans.standard.facetnavigation.HippoFacetSubNavigation'}">
    <article class="highlighted col-md-9"></article>
  </c:when>
  <c:when test="${pageContext.request.requestContext.contentBean['class'].name == 'hslbeans.MedewerkersOverviewPage'}">
    <article class="highlighted col-md-9 medewerkers"></article>
  </c:when>
  <c:otherwise>
      <c:if test="${not empty highLightedItem}">
        <article class="highlighted col-md-9">
          <hst:cmseditlink hippobean="${highLightedItem}" />
          <hst:link hippobean="${highLightedItem }" var="link" />
          <div class="row">
          <a href="${link }" title="${fn:escapeXml(highLightedItem.title)}">
            <div class="media-body col-md-5">
              <h1 class="media-heading"><c:out value="${highLightedItem.title}"/></h1>
              <tag:renderDate document="${highLightedItem}" dateClass="datum start large"/>
              <p><c:out value="${highLightedItem.introduction}"/></p>
            </div>    
            </a>    
          </div>
        </article>
      </c:if>
  </c:otherwise>
</c:choose>
