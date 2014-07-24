<%@ tag trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<hst:setBundle basename="nl.hsleiden.sharebox.Messages"/>

<fmt:message key="follow.facebook"  var="facebook"/>
<fmt:message key="follow.googleplus"  var="googleplus"/>
<fmt:message key="follow.linkedin"  var="linkedin"/>
<fmt:message key="follow.twitter" var="twitter"/>
<fmt:message key="follow.pinterest" var="pinterest"/>
<fmt:message key="follow.instagram" var="instagram"/>

<ul class="social follow">
<li>
  <a href="http://www.facebook.com/HSLeidenNL" target="_blank" title="${facebook}">
    <img src="<hst:link path="images/follow/facebook-icon.png"/>">
  </a>
</li>
<li>
  <a href="http://www.twitter.com/HSLeidenNL" target="_blank" title="${twitter}">
    <img src="<hst:link path="images/follow/twitter-icon.png"/>">
  </a>
</li>
<li>
  <a href="http://www.linkedin.com/company/hogeschool-leiden" target="_blank" title="${linkedin}">
    <img src="<hst:link path="images/follow/linkedin-icon.png"/>">
  </a>
</li>
<li>
  <a href="https://plus.google.com/+HSLeidenNL" rel="publisher" target="_blank" title="${googleplus}">
    <img src="<hst:link path="images/follow/googleplus-icon.png"/>">
  </a>
</li>
<li>
  <a href="https://instagram.com/hsleiden" target="_blank" title="${instagram}">
    <img src="<hst:link path="images/follow/instagram-icon.png"/>">
  </a>
</li>
<%-- <li>
  <a href="http://www.pinterest.com/ivanderent/hogeschool-leiden" target="_blank" title="${pinterest}">
    <img src="<hst:link path="images/follow/pinterest-icon.png"/>">
  </a>
</li> --%>
</ul>