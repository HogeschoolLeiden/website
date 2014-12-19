<%@ page contentType="text/html; charset=UTF-8" language="java"
  trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>

<hst:setBundle basename="nl.hsleiden.channelmanager.Messages, nl.hsleiden.widget.Messages"/>

<hst:defineObjects />
<c:set var="isCmsRequest" value="${hstRequest.requestContext.cmsRequest}" />

<c:if test="${(empty model.info.account or not empty webMasterMessage) and isCmsRequest}">
	<p class="error-message"><fmt:message key="${webMasterMessage}" /></p>
</c:if>

<c:if test="${not empty model.info.account and empty webMasterMessage and model.info.postsLimit > 0}">
  <hst:headContribution keyHint="dynamicWidth">
    <script type="text/javascript" src="<hst:link path="/js/dynamic-width.js"/>"></script>
  </hst:headContribution>
    
  <section class="catalog facebook posts">
    <div id="fb-root"></div> 
    <script>(function(d, s, id) { var js, fjs = d.getElementsByTagName(s)[0]; if (d.getElementById(id)) return; js = d.createElement(s); js.id = id; js.src = "//connect.facebook.net/en_US/all.js#xfbml=1"; fjs.parentNode.insertBefore(js, fjs); }(document, 'script', 'facebook-jssdk'));</script>  
    
    <c:forEach items="${model.postIDs }" var="postID">
    
      <div class="fb-post" data-href="https://www.facebook.com/${model.info.account}/posts/${postID}">
         
        <div class="fb-xfbml-parse-ignore">
          <a href="https://www.facebook.com/${model.info.account}/posts/${postID}">Post</a> 
          by  <a href="https://www.facebook.com/${model.info.account}"> ${model.info.account} </a>.
        </div>
      </div>
    </c:forEach>
   
  </section>
  <div class="clearfix"></div>
</c:if>
