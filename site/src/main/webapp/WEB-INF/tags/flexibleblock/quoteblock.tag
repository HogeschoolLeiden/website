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

<fmt:message key="quote.facebook" var="facebook" />
<fmt:message key="quote.linkedin" var="linkedin" />
<fmt:message key="quote.googleplus" var="googleplus" />
<fmt:message key="quote.twitter" var="twitter" />

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
  <c:out value=": " />
  <c:out value="${content.quoteParameters.quoteText }" />
</c:set>

<hst:link var="image" hippobean="${content.quoteParameters.image.listImageLarge }" />

<div class="quote item-with-image">
  <c:if test="${not empty image}">
    <div class="quote image-space">
      <img alt="${item.title }" src="${image }" />
    </div>
  </c:if>

  <div ${empty image ? 'class="noimage quote itemContent"' : 'class="quote itemContent"' }>

    <div class="author">
      <c:out value="${quoteAuthor }" />
    </div>
    
    <div class="text">
      <c:out value="${quoteText }" />
    </div>


    <c:if test="${content.shareParameters.twitter or content.shareParameters.linkedin }">
    <div class="share">
    <ul>
      <c:if test="${content.shareParameters.linkedin}">
      <li>
       <a class="linkedin" title="${linkedin}"
           href="http://www.linkedin.com/shareArticle?mini=true&url=${url}&title=${quoteAuthor}&summary=${completeQuote}" 
           target="_blank"><c:out value="${linkedin}" /> 
        </a>
      </li>
      </c:if>
      <c:if test="${content.shareParameters.twitter}">
        <c:choose>
        <c:when test="${fn:length(completeQuote) > 140 }">
          <c:set var="twitterQuote" value="${fn:substring(completeQuote,0, 136)} ..."/>
        </c:when>
        <c:otherwise>
          <c:set var="twitterQuote" value="${completeQuote}"/>
        </c:otherwise>
        </c:choose>
        
      <li>
        <a class="twitter" title="${twitter}"
           href="http://twitter.com/home?status=${twitterQuote}"
           target="_blank"> <c:out value="${twitter}" />
        </a>
      </li>
      </c:if>
    </ul>
    </div>
    </c:if>

  </div>
  <div class="clear"></div>
</div>

