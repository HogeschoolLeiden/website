<%@tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<hst:setBundle basename="nl.hsleiden.coockielaw.Messages" />

<div class="row disclaimer" id="row-disclaimer">
  <div class="container disclaimer">
    <div id="site_message" class="info_message">
      <div id="message_content">
        <i class="fa fa-info-circle fa-3x"></i>
        <fmt:message key="infoLink.sitemap" var="infoSitemap"/>
        <p><fmt:message key="use.cookies.message" /><br>
           <hst:link var="infoLink" path="${infoSitemap}" mount="hsl"/>
           <a href="${infoLink}"><fmt:message key="infoLink.message" /></a>
        </p>
      </div>
      <div id="actions">
        <span class="btn fa fa-times-circle-o fa-lg" onclick="setCookieAllowRemoveMessage()">
          <fmt:message key="accept.cookie.close" />  
        </span>
        <span class="btn fa fa-times-circle-o fa-lg" onclick="setCookieRefuseRemoveMessage()">
          <fmt:message key="refuse.cookie.close" />  
        </span>
      </div>
    </div>
  </div>
</div>