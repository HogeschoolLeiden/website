<%@ tag trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<%@ attribute name="document" required="true" type="org.hippoecm.hst.content.beans.standard.HippoBean" rtexprvalue="true"%>

<fmt:message key="page.facebook"  var="pageFacebook"/>
<fmt:message key="page.twitter" var="pageTwitter"/>
<ul class="toolbox">
  <c:set var="url">
    <hst:link hippobean="${document }" fullyQualified="true" />
  </c:set>
  <li>
    <a class="print" id="tool-print-page" title="<fmt:message key="page.print" />" 
       href="<hst:link hippobean="${document }" />">
          <fmt:message key="page.print" />
    </a>
  </li>
  <li>
    <a class="facebook" id="tool-facebook" title="<fmt:message key="page.facebook"/>" 
       href="https://www.facebook.com/sharer/sharer.php?u=${url }&amp;t=<c:out value="${document.title }" />" 
       target="_blank"><c:out value="${pageFacebook}"/>
    </a>
  </li>
  <li>
    <a class="twitter" id="tool-twitter" title="<fmt:message key="page.twitter" />" 
       href="http://twitter.com/home?status=<c:out value="${document.title }" /> - ${url }" 
       target="_blank">
       <c:out value="${pageTwitter}"/>
    </a>
  </li>
  <c:set var="email">
    mailto:?subject=Ter kennisneming doorgestuurd&body=De informatie op de volgende pagina is mogelijk voor u van belang:%0D%0A${url }
  </c:set>
  <li>
    <a class="email" title="<fmt:message key="page.forward" />" 
       href="<c:out value="${email }" escapeXml="true" />">
       <fmt:message key="page.forward" />
    </a>
  </li>
</ul>