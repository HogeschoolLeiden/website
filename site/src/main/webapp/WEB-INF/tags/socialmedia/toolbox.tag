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
<fmt:message key="page.forward" var="forward"/>

<c:if test="${hst:isReadable(document, 'share') && document.share}">

  <c:set var="url">
    <hst:link hippobean="${document }" fullyQualified="true" />
  </c:set>

  <hst:headContribution category="metadata" keyHint="url">
    <meta property="og:url" content="${url}"/>
  </hst:headContribution>

  <hst:headContribution category="metadata" keyHint="description">
    <meta property="og:description" content="${document.introduction}"/>
  </hst:headContribution>

<div class="share clearfix">

  <%-- 
  enable after css
  <c:if test="${not empty document}">
      <hst:link mount="pdf" var="pdfLink"/>
      <a  class="pdf" id="pdflink" title="${pagePdf}" 
          href="${pdfLink}?${pageContext.request.queryString}">
        <c:out value="${pagePdf}"/>
      </a>

  </c:if> 
  --%>
  
  <a class="fa fa-facebook-square col-md-4 col-sm-4 col-xs-12" id="tool-facebook" title="${pageFacebook}" 
       href="https://www.facebook.com/sharer/sharer.php?u=${url }&amp;t=<c:out value="${document.title }" />"
       target="_blank">
       <span><c:out value="${pageFacebook}"/></span>
  </a>
  
  <c:url var="googlePlusUrl" value="https://plus.google.com/share">
	<c:param name="url" value="${url}"/>
  </c:url>
  <a class="fa fa-google-plus col-md-4 col-sm-4 col-xs-12" id="tool-googleplus" title="${googleplus}"
         href="${fn:escapeXml(googlePlusUrl)}" target="_blank">
         <span><c:out value="${googleplus}"/></span>
  </a>
  
   <%-- 
  enable after css
      <c:url var="linkedInUrl" value="http://www.linkedin.com/shareArticle">
        <c:param name="mini" value="true"/>
        <c:param name="url" value="${url}"/>
        <c:param name="title" value="${fn:substring(document.title,0,250)}"/>
        <c:param name="source" value="Surf"/>
        <c:if test="${hst:isReadable(document, 'introduction')}">
          <c:param name="summary" value="${fn:substring(document.introduction,0, 256)}"/>
        </c:if>
      </c:url>
        <a class="linkedin" id="tool-linkedin" title="${linkedin}" 
           href="${fn:escapeXml(linkedInUrl)}" target="_blank"><c:out value="${linkedin}"/>
        </a>
     --%>
    
    <a class="fa fa-twitter col-md-4 col-sm-4 col-xs-12" id="tool-twitter" title="${pageTwitter}" 
       href="http://twitter.com/home?status=<c:out value="${document.title }" /> - ${url }" 
       target="_blank">
       <span><c:out value="${pageTwitter}"/></span>
    </a>
  
  <%-- 
  enable after css
  <c:set var="email">
    mailto:?subject=<fmt:message key="forward.mail.subject"/>&body=<fmt:message key="forward.mail.body"/>${url }
  </c:set>
    <a class="email" title="${forward}" 
       href="<c:out value="${email }" escapeXml="true" />">
       <c:out value="${forward}"/>     
    </a>
  --%>
  
</div>
</c:if>