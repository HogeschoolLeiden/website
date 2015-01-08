<%@tag trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<%@ attribute name="content" type="java.util.List" rtexprvalue="true" required="true" %>
<%@ attribute name="document" type="org.hippoecm.hst.content.beans.standard.HippoBean" rtexprvalue="true" required="true"%>
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
		</c:when>
		<c:when test="${block['class'].name == 'nl.hsleiden.beans.YoutubeBean' }">
			<tag:youtubeblock content="${block }"/>
		</c:when>
		<c:when test="${block['class'].name == 'hslbeans.Documents' }">
			<tag:downloadsblock content="${block }"/>
		</c:when>
		<c:when test="${block['class'].name == 'hslbeans.Quote' }">
			<tag:quoteblock content="${block }"/>
		</c:when>
		<c:when test="${block['class'].name == 'hslbeans.ExternalLinks' }">
			<tag:externallinksblock content="${block }"/>
		</c:when>
		<c:when test="${block['class'].name == 'hslbeans.Fotogallery' }">
			<tag:fotogalleryblock content="${block }"/>
		</c:when>
		<c:when test="${block['class'].name == 'hslbeans.MailPlusForm' }">
			<tag:mailplusformblock content="${block }"/>
		</c:when>
		<c:when test="${block['class'].name == 'hslbeans.Testimonial' }">
			<tag:testimonialblock content="${block }"/>
		</c:when>
		<c:when test="${block['class'].name == 'hslbeans.FbLikeShare' }">
			<tag:facebookShareBlock document="${document}"/>
		</c:when>
		<c:when test="${block['class'].name == 'hslbeans.Html' }">
			<tag:htmlBlock content="${block}"/>
		</c:when>
	</c:choose>
	<c:set var="tagcount" value="${tagcount + 1 }" />
</c:forEach>