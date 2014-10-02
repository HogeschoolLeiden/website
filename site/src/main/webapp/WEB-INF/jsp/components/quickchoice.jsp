<%@ page contentType="text/html; charset=UTF-8" language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<hst:setBundle basename="nl.hsleiden.general.Messages, nl.hsleiden.widget.Messages"/>
      
<section class="snelkiezer blok colorbg paars medium">
  <h1><fmt:message key="quick.choice.title"/></h1>
  <c:forEach var="facet" items="${model.facetBean.folders}">
    <c:if test="${facet.name eq model.paramInfo.firstFacetName }">
      <div class="interesse col-xs-6 col-sm-8 col-md-8">
        <tag:rendefFacetGroup facet="${facet}" labels="${model.labels}"/>
      </div>
    </c:if>
    <c:if test="${facet.name eq model.paramInfo.secondFacetName }">
      <div class="vorm col-xs-6 col-sm-4 col-md-4">
         <tag:rendefFacetGroup facet="${facet}" labels="${model.labels}"/>
      </div>
    </c:if>
  </c:forEach>
      
  <div class="zoeken col-xs-12 col-sm-12 ">
    <hst:link var="link" siteMapItemRefId="bachelors" />
    <form role="form" class="form" action="${link}" method="get">
    <label for="zoekfilter"><fmt:message key="search.in.bachelors"/></label>
      <div class="form-group">
        <input type="text" class="form-control" id="zoekfilter" name="q" data-cip-id="zoekfilter">
      </div>
      <button class="btn fa fa-search no-radius" type="submit"><span>Zoek</span></button>
     </form>
  </div>
  
</section>
        
    
