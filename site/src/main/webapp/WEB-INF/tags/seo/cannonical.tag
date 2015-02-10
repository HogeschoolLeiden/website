<%@tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype" %>

<hst:link var="canonicalLink" canonical="true"/>  
<hst:headContribution keyHint="cannonicalLink">
  <link rel="canonical" href="${canonicalLink}"/>
</hst:headContribution>