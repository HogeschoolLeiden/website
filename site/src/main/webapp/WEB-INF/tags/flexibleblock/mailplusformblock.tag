<%@tag description="display image in header width" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<hst:setBundle basename="nl.hsleiden.general.Messages"/>

<%@ attribute name="content" rtexprvalue="true" required="true" type="hslbeans.MailPlusForm" %>
 
  <c:if test="${not empty content.formId and not empty content.reactUrl}">
  
    <div class="mailplusform">
    
    <c:set var="reactUrl">
      <c:out value="${content.reactUrl}"></c:out>
    </c:set>
    
    <%--
      // ######################### //
      //  HEADER CONTRIBUTIONS     //
      // ######################### //
    --%>

    <hst:headContribution keyHint="mpfJqueryUICss">
      <link rel="stylesheet" href="http://static.mailplus.nl/jq//css/ui-lightness/jquery-ui-1.8.18.custom.css" />
    </hst:headContribution>
    
    <hst:headContribution keyHint="mpfReact" category="scripts">
      <script type="text/javascript" src="${reactUrl}"></script>  
    </hst:headContribution> 
    <hst:headContribution keyHint="mpfValidation" category="scripts">
      <script type="text/javascript" src="http://static.mailplus.nl/jq//jquery.validate.min.js" language='javascript'></script>
    </hst:headContribution>
    
    
 
    <div id="${content.formId}"></div>
    
    </div>
  </c:if>