<%@ include file="/WEB-INF/jspf/htmlTags.jspf" %>
<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<section class="title">
    <p class="title-intro"><hst:html hippohtml="${welcomeText}"/></p>
</section>

<div id="maincontent" >
<section class="categories clearfix">
    <hst:include ref="most-read-students"/>
    <hst:include ref="most-read-service"/>
    <hst:include ref="most-read-employees"/>
</section>
</div>
