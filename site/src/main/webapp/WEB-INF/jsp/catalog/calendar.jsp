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

<hst:headContribution keyHint="fullCalendarCss">
<link rel='stylesheet' href='<hst:link path="/css/fullcalendar/fullcalendar.css" />' />
</hst:headContribution>
<hst:headContribution keyHint="momentJs">
	<script src="<hst:link path="/js/moment.min.js" />" type="text/javascript"></script>
</hst:headContribution>
<hst:headContribution keyHint="fullCalendarJs">
	<script src="<hst:link path="/js/fullcalendar/fullcalendar.min.js" />" type="text/javascript"></script>
</hst:headContribution>


<div class="catalog relatedNews">
  <hst:defineObjects />
  <c:set var="isCmsRequest" value="${hstRequest.requestContext.cmsRequest}" />

  <c:if test="${(empty model.items or fn:length(model.items) eq 0) and not empty webMasterMessage and isCmsRequest}">
  	<p class="error-message"><fmt:message key="${webMasterMessage}" ></fmt:message></p>
  </c:if>

    <div class="widget-title">
      <h2><c:out value="${model.paramInfo.widgetTitle}" escapeXml="true" /></h2>
    </div>
    <div id='calendar'></div>
<hst:resourceURL  var="resouceUrl" />
<script type="text/javascript">
$(document).ready(function() {

    // page is now ready, initialize the calendar...

    $('#calendar').fullCalendar({
    	weekends: true,
    	events: '${resouceUrl}',
		eventClick: function(calEvent, jsEvent, view) {
	        window.location.href = calEvent.link;
	    }
 			
    });

});
</script>
</div>
