<%@ page contentType="text/html; charset=UTF-8" language="java"
  trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>

<hst:setBundle basename="nl.hsleiden.channelmanager.Messages, nl.hsleiden.widget.Messages"/>


<%-- 
lost position
 
<c:set var="position">
  <c:choose>
    <c:when test="${hst:isReadable(model.paramInfo, 'horizontal') and model.paramInfo.horizontal}">
      <c:choose>
        <c:when test="${fn:length(model.tweets) eq 2}">horizontal2</c:when>
        <c:otherwise>horizontal</c:otherwise>
      </c:choose>
    </c:when>
    <c:otherwise>vertical</c:otherwise>
  </c:choose>
</c:set> 

--%>

<hst:defineObjects />
  <c:set var="isCmsRequest" value="${hstRequest.requestContext.cmsRequest}" />

  <c:if test="${(empty model.tweets or fn:length(model.tweets) eq 0) and not empty webMasterMessage and isCmsRequest}">
    <p class="error-message"><fmt:message key="${webMasterMessage}" /></p>
  </c:if>
  
<c:if test="${fn:length(model.tweets)>0}">
      
    <%-- <h2><c:out value="${model.paramInfo.title}"/></h2> --%>
    <c:forEach items="${model.tweets}" var="tweet" varStatus="loop">
      <div class="col-sm-4">
        <article class="twitteritem">
          <figure>
            <img src="${tweet.status.user.profileImageURL}" alt="profile image" /> <%-- get it from properties --%> 
          </figure>
          <h2><a href="http://www.twitter.com/${tweet.status.user.screenName}" target="_BLANK" class="username">${tweet.status.user.name}</a></h2>
          <h3><a href="http://www.twitter.com/${tweet.status.user.screenName}" target="_BLANK" class="screen-name">@${tweet.status.user.screenName}</a></h3>
          <span class="date"><fmt:formatDate value="${tweet.status.createdAt}" pattern="dd MMM"/></span> <br/>
          <p>${tweet.text}</p>
          <div class="action">
            <span><a href="https://twitter.com/intent/tweet?in_reply_to=${tweet.status.id}" target="_BLANK">reply</a></span> ·
            <span><a href="https://twitter.com/intent/retweet?tweet_id=${tweet.status.id}" target="_BLANK">retweet</a></span> ·
            <span><a href="https://twitter.com/intent/favorite?tweet_id=${tweet.status.id}" target="_BLANK">favorite</a></span>
          </div>
        </article>
      </div>
    </c:forEach>

    <c:if test="${not empty model.paramInfo.followText and not empty model.paramInfo.from}">
        <div class="button-block">
            <a href="http://www.twitter.com/${model.paramInfo.from}" target="_BLANK">${model.paramInfo.followText}</a>
        </div>
    </c:if>
 
</c:if>
