<%@ page contentType="text/html; charset=UTF-8" language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<hst:setBundle basename="nl.hsleiden.channelmanager.Messages, nl.hsleiden.widget.Messages"/>

<hst:defineObjects/>
<c:choose>
<c:when test="${not empty model.paramInfo.forumShortname}">
	<hst:headContribution keyHint="component.disqus.js" category="scripts">
	<script type="text/javascript" async="async" src="http://${model.paramInfo.forumShortname}.disqus.com/embed.js"></script>
	</hst:headContribution>
	
	<div id="disqus_thread"></div>
	<noscript>Please enable JavaScript to view the comments.</noscript>
</c:when>
<c:when test="${empty model.paramInfo.forumShortname and hstRequest.requestContext.cmsRequest}">
	<c:choose>
		<c:when test="${not empty webMasterMessage}">
			<p style="color: red"><fmt:message key="${webMasterMessage}" /></p>
		</c:when>
		<c:otherwise>
			<p style="color: red"><fmt:message key="webmaster.disqus.forumname"/></p>
		</c:otherwise>
	</c:choose>
</c:when>
</c:choose>