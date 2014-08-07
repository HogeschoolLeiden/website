<%@tag trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<%@ attribute name="content" type="java.util.List" rtexprvalue="true" required="true" %>
<%@ attribute name="event" type="java.lang.Boolean" rtexprvalue="true" required="false" %>
<%@ attribute name="flexibleblockid" type="java.lang.String" rtexprvalue="true" required="false" %>

<c:set var="tagcount" value="0" />

<c:forEach var="block" items="${content }">
	<c:choose>
		<c:when test="${block['class'].name == 'hslbeans.Paragraph' }">
			<tag:paragraphblock content="${block}"/>
		</c:when>
		<c:when test="${block['class'].name == 'hslbeans.Image' }">
			<tag:imageblock content="${block }" event="${event}"/>
      		<div class="hr"><hr /></div>
		</c:when>
		<c:when test="${block['class'].name == 'nl.hsleiden.beans.YoutubeBean' }">
			<tag:youtubeblock content="${block }"/>
      		<div class="hr"><hr /></div>
		</c:when>
		<c:when test="${block['class'].name == 'hslbeans.Documents' }">
			<tag:downloadsblock content="${block }"/>
      		<div class="hr"><hr /></div>
		</c:when>
		<c:when test="${block['class'].name == 'hslbeans.Quote' }">
			<tag:quoteblock content="${block }"/>
      		<div class="hr"><hr /></div>
		</c:when>
		<c:when test="${block['class'].name == 'hslbeans.ExternalLinks' }">
			<tag:externallinksblock content="${block }"/>
      		<div class="hr"><hr /></div>
		</c:when>
	</c:choose>
	<c:set var="tagcount" value="${tagcount + 1 }" />
</c:forEach>