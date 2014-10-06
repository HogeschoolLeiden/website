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

<div class="filter filter-collapse collapse clearfix algemeenfilter">
  
  
  <hst:include ref="leftTop" />
  
  <c:if test="${facetnav.count gt 0}">

    <c:forEach var="facet" items="${facetnav.folders}">
	  <c:if test="${facet.resultSet.count > 0}">
		  
        <section class="filtergroup col-md-3">
		  <%-- <h2><c:out value="${facet.name eq 'Vakgebied' ?  'Opleidingen' : facet.name}" escapeXml="true" /></h2> --%>
		  <h2><c:out value="${facet.name}" escapeXml="true" /></h2>
		  <c:if test="${not empty facet.folders}">
            <div class="filterlist">
              <c:if test="${facet.name eq 'Vakgebied'}" >
              	<c:forEach items="${facetnav.ancestorsAndSelf}" var="ancestor">
              		<c:if test="${ancestor.parentBean.name eq 'Opleidingen'}">
	               	<hst:facetnavigationlink removeList="${opw:removeListByCategory('Opleidingen', 'Vakgebied', facetnav)}" current="${facetnav}" var="removeLink" />
                      
                    <a href="${removeLink}">
                      <div class="checkbox">
                        <img  class="cbimg" src="<hst:link path="/images/checkbox-checked.png"/>" 
                              alt="<fmt:message key="checkbox.checked" />" title="<fmt:message key="checkbox.checked" />"/>
                        <c:out value="${labels[ancestor.name]}" default="${ancestor.name}" escapeXml="true" />
                      </div>
                    </a>
              		</c:if>
               	</c:forEach> 
              </c:if>  
              <c:forEach items="${facet.folders}" var="item">
			                    
                <c:set var="divClass">
                  <c:choose>
                    <c:when test="${facet.name eq 'Vakgebied' }">
                      <c:out value="checkbox drilled"></c:out>
                    </c:when>
                    <c:otherwise>
                      <c:out value="checkbox"></c:out>                        
                    </c:otherwise>
                  </c:choose>
                </c:set>
                
                <c:choose>
                  <c:when test="${item.leaf and item.count gt 0}">
                    <hst:facetnavigationlink remove="${item}" current="${facetnav}" var="removeLink" />
                      
                    <a href="${removeLink}">
                      <div class="${divClass }">
                        <img  class="cbimg" src="<hst:link path="/images/checkbox-checked.png"/>" 
                              alt="<fmt:message key="checkbox.checked" />" title="<fmt:message key="checkbox.checked" />"/>
                        <c:out value="${labels[item.name]}" default="${item.name}" escapeXml="true" />
                      </div>
                    </a>
                  
                  </c:when>
                  <c:otherwise>
                    <c:if test="${item.count > 0 }">
                      
                      <hst:link var="link" hippobean="${item}" navigationStateful="true"/>
                      
                      <a href="${link}">
                        <div class="${divClass }">
                          <img  class="cbimg" src="<hst:link path="/images/checkbox-unchecked.png"/>" 
                                alt="<fmt:message key="checkbox.unchecked" />" title="<fmt:message key="checkbox.unchecked" />"/>
                          <c:out value="${labels[item.name]}" default="${item.name}" escapeXml="true" />
                        </div>
                      </a>
                    
                    </c:if>
                  
                  </c:otherwise>
                </c:choose>
              </c:forEach>
                  
            </div>
              
          </c:if>  
        </section>
               
	  </c:if>
	</c:forEach>
  
    <c:if test="${enableSearch}">
      <section class="filtergroup col-md-3">
        <fmt:message var="submitText" key="search.filter.keywords"/>
        <h2><c:out value="${submitText}" /></h2>
        <div class="filterlist">
          <form role="form" class="form" method="get">
                <div class="form-group">
                <%-- escape js --%>
                  <input type="text" name="q" class="form-control" placeholder=""
                    ${not empty model.query? 'value=\"' : ''} ${fn:escapeXml(model.query)} ${not empty model.query? '\"' : ''}>
                </div>
                <button class="btn fa fa-search no-radius" type="submit">
                  <span><c:out value="${submitText}" /></span>                            
                </button> 
           </form>
        </div>
      </section>
    </c:if> 
          
  </c:if>
    
</div>
