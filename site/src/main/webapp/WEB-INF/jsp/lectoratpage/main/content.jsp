<%@ page contentType="text/html; charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<section class="col-md-9 detail-content">
  <div class="row">
    <div class="col-md-8 col-sm-8 contentarea">               
      <div class="overzichtlijst">
        
        <hst:cmseditlink hippobean="${document}" />
        <h1><span><c:out value="${document.title }" /></span></h1>
        <hst:include ref="contentTop" />
        
        <%-- 
        <h2> <c:out value="${document.subtitle }" escapeXml="true" />  </h2> 
        --%>
        
        
        <tag:listItems document="${document}"/>
        
        <%--
        <div class="row lectorat">
        <c:if test="${hst:isReadable(document, 'releaseDate.time') and hst:isReadable(document, 'introduction')}">
          <tag:renderDate document="${document}"/>
          <p class="intro">
            <c:out value="${document.introduction }" />
          </p>
        </c:if>
        </div>

        
        <tag:toolbox document="${document }" /> 
        --%>
        
        
      </div>
    </div>
    
    <aside class="col-md-4 col-sm-4 aside">
        <hst:include ref="rightTop" />
        <hst:include ref="right" />
        <hst:include ref="rightBottom" />
    </aside>

    
  </div>
  
  <div class="row border-top">
    <hst:include ref="contentBottom" /> 
  </div>
  
</section>
       

