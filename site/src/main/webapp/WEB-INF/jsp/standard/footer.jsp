<%@ page contentType="text/html; charset=UTF-8" language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>

<hst:setBundle basename="nl.hsleiden.general.Messages"/>

<hst:headContribution category="scripts">
  <script src="//s7.addthis.com/js/300/addthis_widget.js#pubid=ra-53c2b4d4115b0ba9" type="text/javascript"></script>
</hst:headContribution>

<hst:setBundle basename="nl.hsleiden.general.Messages"/>

<div class="container-fluid">
  <div class="row-fluid">
    <div class="span2"></div>
    <div class="span8">
  	   <div class="footer">
  	     <div class="navbar">
  		   <hst:include ref="footerServiceMenu" />
  	     </div>
         <div class="addthis_horizontal_follow_toolbox"></div>
  	   </div>
    </div>
    <div class="span2"></div>
  </div>
</div>