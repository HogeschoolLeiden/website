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
<fmt:message key="follow.youtube" var="youtube"/>


<li class="facebook">
  <a href="http://www.facebook.com/HSLeidenNL" target="_blank" title="${facebook}" class="fa fa-facebook">
    <span>${facebook}</span>
  </a>
</li>

<li class="twitter">
  <a href="http://www.twitter.com/HSLeidenNL" target="_blank" title="${twitter}" class="fa fa-twitter">
    <span>${twitter}</span>
  </a>
</li>

<li class="googleplus">
  <a href="https://plus.google.com/+HSLeidenNL" rel="publisher" target="_blank" title="${googleplus}" class="fa fa-google-plus">
    <span>${googleplus}</span>
  </a>
</li>

<li class="linkedin">
  <a href="http://www.linkedin.com/company/hogeschool-leiden" target="_blank" title="${linkedin}" class="fa fa-linkedin">
    <span>${linkedin}</span>
  </a>
</li>
<li class="instagram">
  <a href="https://instagram.com/hsleiden" target="_blank" title="${instagram}" class="fa fa-instagram">
    <span>${instagram}</span>
  </a>
</li>

<li class="instagram">
  <a href="https://www.youtube.com/user/hogeschoolleiden" target="_blank" title="${youtube}" class="fa fa-youtube">
    <span>${youtube}</span>
  </a>
</li>

