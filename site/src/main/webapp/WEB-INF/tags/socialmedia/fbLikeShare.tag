<%@ tag trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<hst:setBundle basename="nl.hsleiden.sharebox.Messages"/>

<%@ attribute name="document" required="true" type="org.hippoecm.hst.content.beans.standard.HippoBean" rtexprvalue="true"%>

<c:if test="${hst:isReadable(document, 'share') && document.share}">

  <c:set var="url">
    <hst:link hippobean="${document }" fullyQualified="true" />
  </c:set>
  
  <c:choose>
    <c:when test="${pageContext.request.locale.language eq 'en'}">
      <div class="fbLikeShare">

        <div id="fb-root"></div>
        <script>(function(d, s, id) {
            var js, fjs = d.getElementsByTagName(s)[0];
            if (d.getElementById(id)) return;
            js = d.createElement(s); js.id = id;
            js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&appId=1428653840757673&version=v2.0";
            fjs.parentNode.insertBefore(js, fjs);
          }(document, 'script', 'facebook-jssdk'));
        </script>
      
        <div class="fb-like" 
             data-href="${url}" 
             data-width="292" 
             data-layout="button_count" 
             data-action="like" 
             data-show-faces="true" 
             data-colorscheme="dark"
             data-share="true">
        </div>
        
      </div>
    </c:when>
    <c:otherwise>
      <div class="fbLikeShare">

        <div id="fb-root"></div>
        <script>(function(d, s, id) {
            var js, fjs = d.getElementsByTagName(s)[0];
            if (d.getElementById(id)) return;
            js = d.createElement(s); js.id = id;
            js.src = "//connect.facebook.net/nl_NL/sdk.js#xfbml=1&appId=1428653840757673&version=v2.0";
            fjs.parentNode.insertBefore(js, fjs);
          }(document, 'script', 'facebook-jssdk'));
        </script>
             
        <div class="fb-like" 
             data-href="${url}" 
             data-width="292" 
             data-layout="button_count" 
             data-action="like" 
             data-show-faces="true" 
             data-colorscheme="dark"
             data-share="true">
        </div>
       
      </div>
    </c:otherwise>
  </c:choose>
</c:if>