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
<%@ attribute name="enableSearch" required="false" type="java.lang.Boolean"%>

<hst:setBundle basename="nl.hsleiden.general.Messages"/>

<%-- <c:set var="facetnavtitle">
  <fmt:message key="refine.results" />
</c:set> --%>

<div class="studiefilter clearfix filter-collapse collapse collapsed in">
    
  <c:if test="${facetnav.count gt 0}">

<%--     <h4><c:out value="${facetnavtitle}" escapeXml="true"/></h4> --%>

    <c:forEach var="facet" items="${facetnav.folders}">
	  <c:if test="${facet.resultSet.count > 0}">
		  
        <div class="col-md-4 col-sm-6">
		  <h2><c:out value="${facet.name}" escapeXml="true" /></h2>
		  <c:if test="${not empty facet.folders}">
              
              <ul>                
              <c:forEach items="${facet.folders}" var="item">
			                    
                <c:choose>
                  <c:when test="${item.leaf and item.count gt 0}">
                   
                    <hst:facetnavigationlink remove="${item}" current="${facetnav}" var="removeLink" />
                    
                    <li>  
                      <a href="${removeLink}">
                        <img  class="cbimg" src="<hst:link path="/images/bachelor-checkbox-checked.png"/>" 
                              alt="<fmt:message key="checkbox.checked" />" title="<fmt:message key="checkbox.checked" />"/>
                        <span>
                          <c:out value="${labels[item.name]}" default="${item.name}" escapeXml="true" />
                        </span>
                      </a>
                    </li>
                  
                  </c:when>
                  <c:otherwise>
                    
                    <c:if test="${item.count > 0 }">
                      
                      <hst:link var="link" hippobean="${item}" navigationStateful="true"/>
                      
                      <li>
                        <a href="${link}">
                          <img  class="cbimg" src="<hst:link path="/images/bachelor-checkbox-unchecked.png"/>" 
                                alt="<fmt:message key="checkbox.unchecked" />" title="<fmt:message key="checkbox.unchecked" />"/>
                          <span>
                            <c:out value="${labels[item.name]}" default="${item.name}" escapeXml="true" />
                          </span>
                        </a>
                      </li>
                      
                    </c:if>
                  
                  </c:otherwise>
                </c:choose>
              </c:forEach>
                  
            </ul>
              
          </c:if>  
        </div>
               
	  </c:if>
	</c:forEach>
  
    <c:if test="${enableSearch}">
      <div class="col-md-4 col-sm-12">
        <fmt:message var="submitText" key="search.filter.keywords"/>
        <h2><c:out value="${submitText}" /></h2>
        <ul>
		  <li>
          <form role="form" class="form" method="get">
 
                <%-- escape js --%>
                  <input type="text" name="q" class="form-control" placeholder=""
                    ${not empty model.query? 'value=\"' : ''} ${fn:escapeXml(model.query)} ${not empty model.query? '\"' : ''}>

                <button class="btn fa fa-search no-radius" type="submit">
                  <span><c:out value="${submitText}" /></span>                            
                </button> 
           </form>
           </li>
         </ul>
      </div>
    </c:if> 
          
  </c:if>
    
</div>
