<%@ include file="/WEB-INF/jspf/htmlTags.jspf"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>

<%@include file="/WEB-INF/jsp/intranet/common/content.jsp" %>

<section class="sections clearfix">
	<section class="sections-column wide">
	    
	    <c:forEach var="group" items="${assets.groups}">
	    
		    <section class="column-box">
		           <div class="column-box-header">
		                <span title="${fn:escapeXml(group.title)}"><hs:abbreviate maxLength="40" text="${fn:escapeXml(group.title)}"/></span>
		                <img src="<hst:link path="/img/arrow_down.png"/>" class="arrow down" alt="">
		           </div>
		           <div class="box-content">
		              <ul class="categories">
		                  <c:forEach var="asset" items="${group.assets}">
		                      <hst:link var="detailUrl" hippobean="${asset.document}"/>
		                      <li>
		                            <a href="${detailUrl}" title="${fn:escapeXml(asset.title)}">
		                                ${fn:escapeXml(asset.title)}
		                            </a>
		                      </li>
		                  </c:forEach>
		              </ul>
		          </div>
		      </section>
	      
	     </c:forEach>
	                  
	</section>
</section>

<%@include file="/WEB-INF/jsp/intranet/common/metadata.jsp" %>   
