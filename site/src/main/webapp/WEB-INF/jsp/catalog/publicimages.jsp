<%@ page contentType="text/html; charset=UTF-8" language="java"
  trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<%-- <%@ attribute name="content" rtexprvalue="true" required="true" type="nl.hsleiden.beans.PublicImagesBean" %> --%>

<c:if test="${not empty model.items and fn:length(model.items) > 0 }">
  <hst:headContribution keyHint="ppinit">
    <script type="text/javascript" src="<hst:link path="/js/pretty-photo-inizialization.js"/>" charset="utf-8"></script>
  </hst:headContribution>

  <hst:headContribution keyHint="jquery">
    <script type="text/javascript" src="<hst:link path="/js/jquery-1.9.1.min.js"/>" charset="utf-8"></script>
  </hst:headContribution>
  
  <hst:headContribution keyHint="jqueryUICss">
    <link rel="stylesheet" href="<hst:link path="/css/prettyPhoto.css"/>" type="text/css" media="screen" />
  </hst:headContribution>
  
  <hst:headContribution keyHint="prettyPhoto">
    <script type="text/javascript" src="<hst:link path="/js/jquery.prettyPhoto.js"/>" charset="utf-8"></script>
  </hst:headContribution>
  
  <div class="images${model.info.imagesPerRow}">
    
    <c:forEach items="${model.items}" var="image" varStatus="loop">
      
      <div class="singleImage">
        <div class="singleImagePadding">

          <figure class="fexibleblock image">
          
            <hst:link fullyQualified="true" hippobean="${image.wideImage }" var="imageLink"></hst:link>
                  
            <a href="${imageLink }" rel="prettyPhoto" class="pin-it-button">
              <img  src="${imageLink}" alt="${fn:escapeXml(image.alt) }" title="${fn:escapeXml(image.alt) }"/>
            </a>
                      
          </figure>
       
        </div>        
      </div>
      
    </c:forEach>
        
  </div>
   
  <div class="pager-wrapper">
      <opw:componentParameterName name="page" var="pageParameterName"/>
      <opw:simplepaginator paginator="${model.paginator}" pageParamerter="${pageParameterName}"/>
  </div>
  
</c:if>