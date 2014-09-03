<%@tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<%@ attribute name="document" type="hslbeans.OverviewPage" rtexprvalue="true" required="true"%>

<hst:setBundle basename="nl.hsleiden.widget.Messages" />

<c:if test="${hst:isReadable(document, 'rssItem') && not empty document.rssItem }">
  <c:set var="rssLink">
    <hst:link hippobean="${document.rssItem}"/>
  </c:set>
  <hst:headContribution keyHint="rssItem">
    <link title="${document.rssItem.title}" rel="alternate" type="application/rss+xml" href="${rssLink}"/>
  </hst:headContribution>
</c:if>


