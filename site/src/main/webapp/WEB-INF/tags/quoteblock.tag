<%@tag trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tag"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<%@ attribute name="content" rtexprvalue="true" required="true" type="hslbeans.Quote"%>

<hst:setBundle basename="nl.hsleiden.sharebox.Messages" />

<fmt:message key="quote.facebook" var="pageFacebook" />
<fmt:message key="quote.googleplus" var="googleplus" />
<fmt:message key="quote.twitter" var="pageTwitter" />

<c:set var="url"><hst:link hippobean="${document }" fullyQualified="true" /></c:set>
<c:set var="quoteAuthor">
  <c:out value="${content.quoteParameters.name }" />
  <c:out value=" : " />
</c:set>
<c:set var="quoteText">
  <c:out value="${content.quoteParameters.quoteText }" />
</c:set>
<c:set var="completeQuote">
  <c:out value="${content.quoteParameters.name }" />
  <c:out value=" : " />
  <c:out value="${content.quoteParameters.quoteText }" />
</c:set>

<hst:link var="image" hippobean="${content.quoteParameters.image.listImageLarge }" />

<div class="quote item-with-image">
  <div class="quote image-space">
    <img alt="${item.title }" src="${image }" />
  </div>

  <div class="quote itemContent">

    <div class="author">
      <c:out value="${quoteAuthor }" />
    </div>
    
    <div class="text">
      <c:out value="${quoteText }" />
    </div>


    <c:if test="${content.shareParameters.twitter or content.shareParameters.facebook }">
    <div class="share">
    <ul>
      <c:if test="${content.shareParameters.facebook}">
      <li>
        <a class="facebook" id="tool-facebook" title="${pageFacebook}"
           href="https://www.facebook.com/sharer/sharer.php?s=100&p[summary]=${completeQuote}&p[images][0]=${image}" 
           target="_blank"><c:out value="${pageFacebook}" /> 
        </a>
      </li>
      </c:if>
      <c:if test="${content.shareParameters.twitter}">
      <li>
        <a class="twitter" id="tool-twitter" title="${pageTwitter}"
           href="http://twitter.com/home?status=${completeQuote}"
           target="_blank"> <c:out value="${pageTwitter}" />
        </a>
      </li>
      </c:if>
    </ul>
    </div>
    </c:if>

  </div>
  <div class="clear"></div>
</div>

