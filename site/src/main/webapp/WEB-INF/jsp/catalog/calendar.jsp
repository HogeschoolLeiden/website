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
	<script src="<hst:link path="/js/fullcalendar/fullcalendar.js" />" type="text/javascript"></script>
</hst:headContribution>
<hst:headContribution keyHint="fullCalendarLangJs">
	<script src="<hst:link path="/js/fullcalendar/lang/nl.js" />" type="text/javascript"></script>
</hst:headContribution>

<hst:defineObjects />
<c:set var="isCmsRequest" value="${hstRequest.requestContext.cmsRequest}" />

<c:if test="${(empty model.items or fn:length(model.items) eq 0) and not empty webMasterMessage and isCmsRequest}">
	<p class="error-message"><fmt:message key="${webMasterMessage}" ></fmt:message></p>
</c:if>

<c:if test="${empty webMasterMessage}">
  <section class="filtergroup col-md-3 calendar">
    <h2 class="hidden"><c:out value="${model.paramInfo.widgetTitle}" escapeXml="true" /></h2>
    <div id="datepicker" class="hasDatepicker"></div>
    
    <hst:resourceURL  var="resouceUrl" />
    <script type="text/javascript">
		$(document).ready(function() {
  		// page is now ready, initialize the calendar...
  			$('#datepicker').fullCalendar({
  				lang: '${pageContext.request.locale.language}',
            	weekends: true,
            	events: '${resouceUrl}',
    			eventClick: function(calEvent, jsEvent, view) {
    	        	window.location.href = calEvent.link;
      	    	},
            	header: {
            	    left:   '',
            	    center: 'prev,title,next',
                	right:  ''
            	}
        	});
    	});
  	</script>
    
    
    
  </section>

  <opw:public-parameter parameterName="qd" var="hasDateFilter"/>
  <c:choose>
    <c:when test="${not empty hasDateFilter}">
      <c:set var="noDateFilterRefID" value="${tag:getSitemapConfigParameter(pageContext.request, 'noDateFilterRefID')}"/>
      
      <section class="removeDateFilter">
        <c:set var="noParamRefId">
          <c:choose>
            <c:when test="${not empty noDateFilterRefID}">
              <c:out value="${noDateFilterRefID}"/>
            </c:when>
            <c:otherwise>
              <c:out value="agenda-future"/>
            </c:otherwise>
          </c:choose>
        </c:set>
        <hst:link siteMapItemRefId="${noParamRefId}" var="current" >
          <hst:param name="qd"/>
        </hst:link>
        <fmt:message key="remove.date.filter.message" var="removeFilterMessage"/>
        <a class="btn" title="${removeFilterMessage }" href="${current}">
          <span>${removeFilterMessage}</span>
        </a>
      </section>
    </c:when>
    <c:otherwise>
      <div class="clearfix"></div>
    </c:otherwise>
  </c:choose>
    

  

</c:if>  
