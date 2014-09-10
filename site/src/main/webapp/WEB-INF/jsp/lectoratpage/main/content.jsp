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
     
      <hst:cmseditlink hippobean="${document}" />
      <h1><span><c:out value="${document.title }" /></span></h1>
      <hst:include ref="contentTop" />
      
      <c:choose>
        <c:when test="${not (document.useFlexibleBlock) and fn:length(items)>0}">
          <tag:listItems/>
        </c:when>
        <c:otherwise>
          <div class="content">
          <p class="intro">
            <c:out value="${document.introduction }" />
          </p>
          <tag:flexibleblock content="${document.flexibleblock }" />
          <tag:toolbox document="${document }"/>
          </div>
        </c:otherwise>
      </c:choose>
                
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
       

