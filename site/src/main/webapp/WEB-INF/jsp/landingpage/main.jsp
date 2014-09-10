<%@ page contentType="text/html; charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<div class="container-fluid">
  <div class="row-fluid">
    <div class="header span8">
      
      <hst:include ref="contentTop" />
      
      <hst:cmseditlink hippobean="${document}" />
      
      <c:choose>
        <c:when test="${tag:getSitemapConfigParameter(pageContext.request, 'columnNr') eq '3' }">
          <div class="threeFourth">
            <h1><c:out value="${document.title}"/></h1>
            <hst:include ref="content" />
          </div>
          
          <div class="widgets column">
            <hst:include ref="rightTop" />
            <hst:include ref="rightBottom" />
          </div>
          
          <div class="clear"></div>
        </c:when>
        <c:when test="${tag:getSitemapConfigParameter(pageContext.request, 'columnNr') eq '4' }">
          <div class="fourFourth">
            <h1><c:out value="${document.title}"/></h1>
            <hst:include ref="content"/>
          </div>
        </c:when>
        <c:otherwise></c:otherwise>
      </c:choose>
            
      <hst:include ref="contentBottom" />
      
    </div>
  </div>
</div>
