<%@tag description="Render elastic search facets" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>

<%@ attribute name="facets" required="true" type="nl.openweb.elastic.bean.FacetsBean" rtexprvalue="true"%>
<%@ attribute name="searchPageUrl" required="true" type="java.lang.String" rtexprvalue="true"%>
<%@ attribute name="facetsCssClass" required="false" type="java.lang.String" rtexprvalue="true"%>
<%@ attribute name="facetItemCssClass" required="false" type="java.lang.String" rtexprvalue="true"%>
<%@ attribute name="selectedItemCssClass" required="false" type="java.lang.String" rtexprvalue="true"%>
<%@ attribute name="facetNameCssClass" required="false" type="java.lang.String" rtexprvalue="true"%>

<c:if test="${empty facetsCssClass}">
  <c:set var="facetsCssClass" value="facets"/>
</c:if>
<c:if test="${empty facetItemCssClass}">
  <c:set var="facetItemCssClass" value="item"/>
</c:if>
<c:if test="${empty selectedItemCssClass}">
  <c:set var="selectedItemCssClass" value="selected"/>
</c:if>
<c:if test="${empty facetNameCssClass}">
  <c:set var="facetNameCssClass" value="facetName"/>
</c:if>
<c:set var="queryParameters"><opw:remove-query-param  item="page"/></c:set>


<div class="${facetsCssClass}">
     
  <c:forEach items="${facets.categories}" var="category">
    
    <c:if test="${not empty category.items && fn:length(category.items) gt 0 }">
    
      <section class="filtergroup col-md-3">
      
        <h2 class="${facetNameCssClass}"><c:out value="${category.title}"/></h2>
        <div class="filterlist">
          
          <c:forEach items="${category.items}" var="item">
            <c:set var="facetLink" value="${searchPageUrl}${item.url}${queryParameters}"></c:set>
            <c:choose>
              <c:when test="${not item.selected}">
              
                <a href="${facetLink}" rel="nofollow">
                  <div class="${facetItemCssClass}">
                    <img  class="cbimg" src="<hst:link path="/images/checkbox-unchecked.png"/>" 
                          alt="<fmt:message key="checkbox.unchecked" />" title="<fmt:message key="checkbox.unchecked" />"/>
                    <c:out value="${item.label}" escapeXml="true" />
                  </div>
                </a>

              </c:when>
              <c:otherwise>

                <a href="${facetLink}" rel="nofollow">
                  <div class="${facetItemCssClass} ${' '} ${selectedItemCssClass}">
                    <img  class="cbimg" src="<hst:link path="/images/checkbox-checked.png"/>" 
                          alt="<fmt:message key="checkbox.checked" />" title="<fmt:message key="checkbox.checked" />"/>
                    <c:out value="${item.label}" escapeXml="true" />
                  </div>
                </a>
                
              </c:otherwise>
            </c:choose>
          </c:forEach>
        
        </div>
      </section>
      
    </c:if> 
  </c:forEach> 
</div>

