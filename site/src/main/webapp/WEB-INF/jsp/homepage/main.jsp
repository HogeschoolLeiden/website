<%@ page contentType="text/html; charset=UTF-8" language="java"
  trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<div id="main" role="main" class="home"> 
            
  <div class="container">  
    <div class="row">
                   
      <c:choose>
        <c:when test="${empty document}">
           <tag:pagenotfound />
        </c:when>
        <c:otherwise>
          <hst:include ref="content"/>
        </c:otherwise>
      </c:choose>
      
      <div class="col-xs-12 col-md-6">
        <hst:include ref="leftTop" />
        <hst:include ref="leftBottom" />
      </div>
      
      <div class="col-xs-6 col-sm-6 col-md-3 ">
        <hst:include ref="contentTop" />
        <hst:include ref="contentBottom" />
      </div>
    
      <div class="col-xs-6 col-sm-6 col-md-3 ">
        <hst:include ref="rightTop" />
        <hst:include ref="rightBottom" />
      </div>
    
    </div>
    <hst:include ref="bottom-container" />
  </div>
  
  
</div>
