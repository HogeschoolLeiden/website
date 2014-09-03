<%@ tag language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<%@ attribute name="facetnav" required="true" type="org.hippoecm.hst.content.beans.standard.HippoFacetNavigationBean"%>
<%@ attribute name="labels" required="false" type="java.util.Map"%>

<hst:setBundle basename="nl.hsleiden.general.Messages"/>

<%-- <c:set var="facetnavtitle">
  <fmt:message key="refine.results" />
</c:set> --%>

<div class="filter filter-collapse collapse clearfix algemeenfilter">
  <c:if test="${facetnav.count gt 0}">

<%--     <h4><c:out value="${facetnavtitle}" escapeXml="true"/></h4> --%>

    <c:forEach var="facet" items="${facetnav.folders}">
	  <c:if test="${facet.resultSet.count > 0}">
		  
        <section class="filtergroup col-md-3">
		  <h2><c:out value="${facet.name}" escapeXml="true" /></h2>
		  <c:if test="${not empty facet.folders}">
              
            <div class="filterlist">
                
              <c:forEach items="${facet.folders}" var="item">
			        
                <c:set var="inputID">
                  <c:out value="${facet.name}"/>
                  <c:out value="-"/>
                  <c:out value="${item.name}"/>
                </c:set>
                    
                <c:choose>
                  <c:when test="${item.leaf and item.count gt 0}">
                  <%-- what to do with this 
                      <hst:facetnavigationlink remove="${item}" current="${facetnav}" var="removeLink" />
                      --%>
                    <div class="checkbox">
                      <input type="checkbox" id="${inputID}" value="${item.name}" name="${facet.name}" checked>
                      <label for="${inputID}">
                        <c:out value="${labels[item.name]}" default="${item.name}" escapeXml="true" />
                      </label>
                    </div>
                  </c:when>
                  <c:otherwise>
                    <div class="checkbox">
                      <input type="checkbox" id="${inputID}" value="${item.name}" name="${facet.name}">
                      <label for="${inputID}">
                        <c:out value="${labels[item.name]}" default="${item.name}" escapeXml="true" />
                      </label>
                    </div>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
                  
            </div>
              
          </c:if>  
        </section>
               
	  </c:if>
	</c:forEach>
  
    <section class="filtergroup col-md-3">
      <h2>Filter op trefwoord</h2>                                              <%-- use property --%>
      <div class="filterlist">
        <form role="form" class="form">
              <div class="form-group">
                <input type="text" class="form-control" placeholder="">
              </div>
              <button class="btn fa fa-search no-radius" type="submit">
                <span>Zoeken</span>                                             <%-- use property --%>
              </button> 
         </form>
      </div>
    </section>
          
  </c:if>
</div>
