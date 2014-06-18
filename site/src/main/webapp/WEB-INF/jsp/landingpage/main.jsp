<%@ page contentType="text/html; charset=UTF-8" language="java"	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>

<div class="container-fluid">
	<hst:include ref="top-container" />    
  	<div class="row-fluid">
        <div class="header span2"></div>
		<nav class="span2">
			<hst:include ref="leftTop" />
			<hst:include ref="left" />
			<hst:include ref="leftBottom" />
		</nav>
		<div class="span8">
			<hst:include ref="contentTop" />
			<hst:include ref="content" />
			<hst:include ref="contentBottom" />
		</div>
		<aside class="span2">
			<hst:include ref="rightTop" />
			<hst:include ref="right" />
			<hst:include ref="rightBottom" />
		</aside>
        <div class="header span2"></div>
  	</div>
	<hst:include ref="bottom-container" />
</div>
