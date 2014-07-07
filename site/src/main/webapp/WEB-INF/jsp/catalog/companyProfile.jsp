<%@ page contentType="text/html; charset=UTF-8" language="java"
	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>
<div class="linkedin-profile">
<c:if test="${not empty model.parameterInfo.companyProfileId}">
	<hst:headContribution keyHint="linkedin.platform">
		<script src="//platform.linkedin.com/in.js" type="text/javascript"></script>
	</hst:headContribution>
	<script type="IN/CompanyProfile" data-id="${model.parameterInfo.companyProfileId}" data-format="inline"></script>
</c:if>
</div>