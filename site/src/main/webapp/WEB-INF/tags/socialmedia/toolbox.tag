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

<fmt:message key="page.facebook"  var="pageFacebook"/>
<fmt:message key="page.googleplus"  var="googleplus"/>
<fmt:message key="page.linkedin"  var="linkedin"/>
<fmt:message key="page.twitter" var="pageTwitter"/>
<fmt:message key="page.pdf" var="pagePdf"/>
<fmt:message key="page.print" var="pagePrint"/>
<fmt:message key="page.forward" var="forward"/>

<c:if test="${hst:isReadable(document, 'share') && document.share}">

  <c:set var="url">
    <hst:link hippobean="${document }" fullyQualified="true" />
  </c:set>

  <c:set var="description">
    <c:out value="${document.introduction}" escapeXml="true"/>
  </c:set>

  <hst:headContribution category="metadata" keyHint="url">
    <meta property="og:url" content="${url}"/>
  </hst:headContribution>

  <hst:headContribution category="metadata" keyHint="description">
    <meta property="og:description" content="${description}"/>
  </hst:headContribution>

<div class="share clearfix">
  
  <%-- FACEBOOK --%>
  <c:url var="fbUrl" value="https://www.facebook.com/sharer/sharer.php">
    <c:param name="u" value="${url}"/>
    <c:param name="t" value="=${document.title}"/>
  </c:url>
  
  <a class="fa fa-facebook-square col-md-4 col-sm-4 col-xs-12" rel="nofollow"
      title="${pageFacebook}" target="_blank" href="${fn:escapeXml(fbUrl)}" >
       <span><c:out value="${pageFacebook}"/></span>
  </a>
  
  <%-- TWITTER --%>
  <c:url var="twUrl" value="https://twitter.com/home">
    <c:param name="status" value="${document.title } - ${url}"/>
  </c:url>
        
  <a class="fa fa-twitter col-md-4 col-sm-4 col-xs-12" title="${pageTwitter}" target="_blank"
     href="${fn:escapeXml(twUrl)}" rel="nofollow">
     <span><c:out value="${pageTwitter}"/></span>
  </a>
  
  <%-- GOOGLE PLUS --%>
  <c:url var="googlePlusUrl" value="https://plus.google.com/share">
	<c:param name="url" value="${url}"/>
  </c:url>
  <a class="fa fa-google-plus col-md-4 col-sm-4 col-xs-12" title="${googleplus}"
         href="${fn:escapeXml(googlePlusUrl)}" target="_blank" rel="nofollow">
         <span><c:out value="${googleplus}"/></span>
  </a>
  
  <%-- LINKED IN --%>
  <c:url var="linkedInUrl" value="https://www.linkedin.com/shareArticle">
    <c:param name="mini" value="true"/>
    <c:param name="url" value="${url}"/>
    <c:param name="title" value="${fn:substring(document.title,0,250)}"/>
    <c:param name="source" value="HsLeiden"/>
    <c:if test="${hst:isReadable(document, 'introduction')}">
      <c:param name="summary" value="${fn:substring(document.introduction,0, 256)}"/>
    </c:if>
  </c:url>
  <a class="fa fa-linkedin col-md-4 col-sm-4 col-xs-12" title="${linkedin}" rel="nofollow"
     href="${fn:escapeXml(linkedInUrl)}" target="_blank"><span><c:out value="${linkedin}"/></span>
  </a>

  <%-- EMAIL --%>
  <c:set var="email">
    mailto:?subject=<fmt:message key="forward.mail.subject"/>&body=<fmt:message key="forward.mail.body"/>${fn:replace(url, ' ', '%20')}
  </c:set>
  <a class="fa fa-envelope col-md-4 col-sm-4 col-xs-12" title="${forward}" rel="nofollow"
     href="<c:out value="${fn:replace(email, ' ', '%20')}" escapeXml="true"/>">
     <span><c:out value="${forward}"/></span>     
  </a>
  
  <%-- PRINT --%>
  <a class="fa fa-print col-md-4 col-sm-4 col-xs-12" title="${pagePrint}" href="#" onclick="window.print();return false;">
     <span>${pagePrint}</span>
  </a> 
    
</div>
</c:if>