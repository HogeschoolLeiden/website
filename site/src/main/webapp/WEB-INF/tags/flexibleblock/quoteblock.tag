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
  <c:out value="${content.quoteParameters.name}" />
</c:set>
<c:set var="quoteText">
  <c:out value="${content.quoteParameters.quoteText }" />
</c:set>
<c:set var="completeQuote">
  <c:out value="${content.quoteParameters.name }" />
  <c:out value=": " />
  <c:out value="${content.quoteParameters.quoteText }" />
</c:set>

<hst:link var="image" hippobean="${content.quoteParameters.image.listImageSmall }" />
                
<blockquote class="media">
  <c:if test="${not empty image}">
      <c:choose>
        <c:when test="${content.quoteParameters.position eq 'right' }">
          <img class="pull-right" alt="${content.quoteParameters.name }" src="${image }" 
              title="${content.quoteParameters.name }"/>
        </c:when>
        <c:otherwise>
          <img class="pull-left" alt="${content.quoteParameters.name }" src="${image }" 
              title="${content.quoteParameters.name }"/>
        </c:otherwise>
      </c:choose>
  </c:if>

  <div class="media-body">
    <h2 class="media-heading">
      <c:out value="${quoteText }"></c:out>
      <span></span>
    </h2>
    
    <h3>
      <c:out value="${quoteAuthor }" />
    </h3>

    <c:if test="${content.shareParameters.twitter or content.shareParameters.linkedin }">
    <div class="share">
    <ul>
      <c:if test="${content.shareParameters.linkedin}">
      
      <c:url var="linkedinQuoteUrl" value="http://www.linkedin.com/shareArticle">
          <c:param name="mini" value="true"/>
          <c:param name="url" value="${url}"/>
          <c:param name="title" value="${quoteAuthor}"/>
          <c:param name="summary" value="${completeQuote}"/>
        </c:url>
        
      <li>
       <a class="linkedin" title="${linkedin}" target="_blank" href="${fn:escapeXml(linkedinQuoteUrl)}">
           <c:out value="${linkedin}" /> 
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
        
        <c:url var="twQuoteUrl" value="http://twitter.com/home">
          <c:param name="status" value="${twitterQuote}"/>
        </c:url>
  
        <li>
          <a class="twitter" target="_blank" title="${twitter}" href="${fn:escapeXml(twQuoteUrl)}" > 
             <c:out value="${twitter}" />
          </a>
        </li>
      </c:if>
    </ul>
    </div>
    </c:if>

  </div>
  <div class="clear"></div>
</blockquote>

