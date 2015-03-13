<%@tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<%@ attribute name="document" rtexprvalue="true" required="true" type="org.hippoecm.hst.content.beans.standard.HippoBean" %>

<hst:link var="canonicalLink" hippobean="${document}" canonical="true" fullyQualified="true"/>
<hst:headContribution keyHint="cannonicalLink">
  <link rel="canonical" href="${canonicalLink}"/>
</hst:headContribution>