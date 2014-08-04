<%@ page contentType="text/html; charset=UTF-8" language="java"
  trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>

<c:set var="position">
  <c:choose>
    <c:when test="${hst:isReadable(model.info, 'horizontal') and model.info.horizontal}">horizontal</c:when>
    <c:otherwise>vertical</c:otherwise>
  </c:choose>
</c:set>

<div class="catalog twitter ${position}">
    <h2><c:out value="${model.info.title}"/></h2>
    <c:forEach items="${model.tweets}" var="tweet">
        <hr/>
        <div class="tweet">
            <div class="image">
                <img src="${tweet.user.profileImageURL}" alt="profile image" />
            </div>
            <div class="user">
                <a href="http://www.twitter.com/${tweet.user.screenName}" target="_BLANK" class="username">${tweet.user.name}</a>
                <a href="http://www.twitter.com/${tweet.user.screenName}" target="_BLANK" class="screen-name">@${tweet.user.screenName}</a>
            </div>
            <span class="date"><fmt:formatDate value="${tweet.createdAt}" pattern="dd MMM"/></span> <br/>
            <span class="text">${tweet.text}</span>
            <div class="action">
                <span><a href="https://twitter.com/intent/tweet?in_reply_to=${tweet.id}" target="_BLANK">reply</a></span> ·
                <span><a href="https://twitter.com/intent/retweet?tweet_id=${tweet.id}" target="_BLANK">retweet</a></span> ·
                <span><a href="https://twitter.com/intent/favorite?tweet_id=${tweet.id}" target="_BLANK">favorite</a></span>
            </div>
        </div>
    </c:forEach>
    <hr/>
    <c:if test="${not empty model.info.followText and not empty model.info.from}">
        <div class="button-block">
            <a href="http://www.twitter.com/${model.info.from}" target="_BLANK">${model.info.followText}</a>
        </div>
    </c:if>
</div>