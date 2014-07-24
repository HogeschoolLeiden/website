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
<ul class="toolbox">
  <c:set var="url">
    <hst:link hippobean="${document }" fullyQualified="true" />
  </c:set>
  <c:if test="${not empty document}">
    <li>
      <hst:link mount="pdf" var="pdfLink"/>
      <a  class="pdf" id="pdflink" title="${pagePdf}" 
          href="${pdfLink}?${pageContext.request.queryString}">
        <c:out value="${pagePdf}"/>
      </a>
    </li>
  </c:if>
  <li>
    <a class="facebook" id="tool-facebook" title="${pageFacebook}" 
       href="#" 
       target="_blank"><c:out value="${pageFacebook}"/>
    </a>
  </li>
  <li>
      <c:url var="googlePlusUrl" value="https://plus.google.com/share">
        <c:param name="url" value="${url}"/>
      </c:url>
      <a class="googleplus" id="tool-googleplus" title="${googleplus}"
         href="${fn:escapeXml(googlePlusUrl)}" target="_blank"><c:out value="${googleplus}"/>
      </a>
    </li>
    <li>
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
    </li>
  <li>
    <a class="twitter" id="tool-twitter" title="${pageTwitter}" 
       href="http://twitter.com/home?status=<c:out value="${document.title }" /> - ${url }" 
       target="_blank">
       <c:out value="${pageTwitter}"/>
    </a>
  </li>
  <c:set var="email">
    mailto:?subject=Ter kennisneming doorgestuurd&body=De informatie op de volgende pagina is mogelijk voor u van belang:%0D%0A${url }
  </c:set>
  <li>
    <a class="email" title="${forward}" 
       href="<c:out value="${email }" escapeXml="true" />">
       <c:out value="${forward}"/>     
    </a>
  </li>
</ul>
</c:if>