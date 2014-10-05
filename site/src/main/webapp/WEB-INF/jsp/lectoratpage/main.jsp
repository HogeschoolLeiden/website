<%@ page contentType="text/html; charset=UTF-8" language="java"
  trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>


<hst:headContribution keyHint="swiper">
  <script type="text/javascript" src="<hst:link path="/js/kees/vendor/idangerous.swiper.js"/>"></script>
</hst:headContribution>

<hst:headContribution keyHint="swiper.progress">
  <script type="text/javascript" src="<hst:link path="/js/kees/vendor/idangerous.swiper.progress.min.js"/>"></script>
</hst:headContribution>  

<div id="main" role="main" class="lectoraat"> 
            
  <c:choose>
    <c:when test="${empty document}">
       <tag:pagenotfound />
    </c:when>
    <c:otherwise>
      <h1 class="hidden"><c:out value="${document.title}"/></h1>
      <tag:carrousel document="${document}"/>
    </c:otherwise>
  </c:choose>
  
  <div class="container">  
    <div class="row">
      
      <section class="col-md-9 detail-content">
        <hst:include ref="content"/>
      </section>

      <hst:include ref="left" />
          
    </div>
    <hst:include ref="bottom-container" />
  </div>
  
  
</div>
