<%@ page contentType="text/html; charset=UTF-8" language="java"
	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<hst:setBundle basename="nl.hsleiden.general.Messages" />
<hst:resourceURL fullyQualified="true" var="resourceUrl"/>
<script type="text/javascript">
$.getJSON( "${resourceUrl}", function( data ) {
	  alert(data);
	});
</script>
<div class="search-box">

	<fmt:message var="submitText" key="search.submit.text" />
	<hst:link var="link" siteMapItemRefId="search" />
	<form class="navbar-search form-search" action="${link}" method="get">
		<p>
		
			<input type="text" name="q" class="search-query input-xlarge"
				placeholder="${fn:escapeXml(submitText)}" required="required" ${not empty model.query? 'value=\"' : ''}${model.query}${not empty model.query? '\"' : ''} }/>
			<button class="btn btn-primary inline" type="submit"
				value="${submitText}"><c:out value="${submitText}"/></button>
		</p>
	</form>

</div>
<div class="clear"></div>