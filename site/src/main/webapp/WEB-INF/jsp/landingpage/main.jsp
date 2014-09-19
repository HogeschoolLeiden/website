<%@ page contentType="text/html; charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<div id="main" role="main" class="landing"> 
      
<%-- <hst:include ref="contentTop" /> --%>
      
  <div class="container">
    
    <%-- <hst:include ref="top-container" />      --%>
    
    <div class="row">
      
      <hst:cmseditlink hippobean="${document}" />
      
      <c:choose>
        <c:when test="${tag:getSitemapConfigParameter(pageContext.request, 'columnNr') eq '3' }">

          <hst:include ref="content" />
          
          <aside class="col-sm-6 col-md-3 aside">
            <hst:include ref="rightTop" />
            <hst:include ref="rightBottom" />
          </aside>
          
        </c:when>
        <c:when test="${tag:getSitemapConfigParameter(pageContext.request, 'columnNr') eq '4' }">
          <hst:include ref="content" />
        </c:when>
        <c:otherwise>
            <h1>Landing pages can have only 3 or 4 content columns</h1>
        </c:otherwise>
      </c:choose>
            
    </div>
    
    <hst:include ref="contentBottom" />
    
  </div>
</div>
