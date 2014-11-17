<%@ page contentType="text/html; charset=UTF-8" language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<c:choose>
  <c:when test="${empty document}">
    <tag:pagenotfound/>
  </c:when>
  <c:otherwise>

    <article class="col-md-9 detail-content">
      <div class="row">
      
        <div class="col-md-8 col-sm-8 contentarea">
          <div class="content">
          
            <hst:cmseditlink hippobean="${document}"/>
            <c:if test="${hst:isReadable(document, 'title') }">
              <h1><c:out value="${document.title}"/></h1>
            </c:if>
  
            <hst:include ref="contentTop" />
            <hst:include ref="contentBottom" />
  
            <tag:toolbox document="${document }" />
  
          </div>
        </div>      
      
        <aside class="col-md-4 col-sm-4 aside">
          <hst:include ref="rightTop" />
          <hst:include ref="right" />
          <hst:include ref="rightBottom" />
        </aside>
      </div>
    
    </article>

  </c:otherwise>
</c:choose>