<%@ page contentType="text/html; charset=UTF-8" language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<hst:setBundle basename="nl.hsleiden.general.Messages"/>

<%-- <hst:headContribution category="scripts">
  <script src="//s7.addthis.com/js/300/addthis_widget.js#pubid=ra-53c2b4d4115b0ba9" type="text/javascript"></script>
</hst:headContribution> --%>

<hst:setBundle basename="nl.hsleiden.general.Messages"/>

<div class="container-fluid">
  <div class="footer row-fluid">
    <div class="header span8">
  	     <div class="navbar">
  		   <hst:include ref="footerMenu" />
  	     </div>
  	     <div class="service">
  		   <hst:include ref="footerServiceMenu" />
  	     </div>
         <div class="follow_toolbox">
          <p>Social media</p>
          <tag:follow/>
         </div>
  	   </div>
    </div>
</div>