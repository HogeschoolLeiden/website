<%@tag trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tag"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<%@ attribute name="content" rtexprvalue="true" required="true" type="hslbeans.Html"%>

<hst:setBundle basename="nl.hsleiden.sharebox.Messages" />

<c:if test="${not empty content.cssDocument.css and not empty content.htmlDocument.html}">
  <hst:headContribution category="extraCss">
    ${content.cssDocument.extraCss}
  </hst:headContribution>
</c:if>

<c:if test="${not empty content.htmlDocument.html}">
  <div class="free html">
      ${content.htmlDocument.html}
  </div>
</c:if>

