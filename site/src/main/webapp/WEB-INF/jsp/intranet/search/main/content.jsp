<%@ include file="/WEB-INF/jspf/htmlTags.jspf"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>


<c:if test="${not empty searchText}">
	<script type="text/javascript">
	    _gaq.push(['_trackEvent', 'Search' , '${searchText}']); 
	</script>
</c:if>
 
 <div id="maincontent" >
 <section class="section-search">
    <hst:link var="searchUrl" path="/zoeken" />
    <form class="main-search search-form" name="main-search" action="${searchUrl}" method="GET">
        <input type="search" class="search-input" name="query" placeholder="Zoeken in praktische informatie" value="${searchText}">
        <img class="search-magnify" src="<hst:link path="/img/intranet/search.png"/>" data-form="main-search" alt="zoeken">
        
        <c:if test="${not hideFilter}">
	        <div class="filter-container">
	            <%@include file="/WEB-INF/jsp/intranet/common/filter.jsp" %>
	        </div>
        </c:if>
    </form>
    <c:if test="${not empty foundDocuments }">
          <p class="meta-results">
              Resultaten ${paginator.startIndex} t/m ${paginator.endIndex} van ${paginator.totalItems}
          </p>
    </c:if>
</section>
                      
<c:choose>
	<c:when test="${empty message}">
            <div id="searchresult" />
            <section class="seach-results">
                
                <c:forEach var="item" items="${foundDocuments.items}">
                    
                    <section class="search-result">
                        
                        <c:set var="metadataGroup" value="${item.metadata.infoForGroup}" />
                        <c:if test="${not empty metadataGroup}">
                             <span class="search-result-meta">
                                 <c:forEach var="metadataItem" items="${metadataGroup.items}" varStatus="status"> 
                                    <hst:link var="metadataItemUrl" path="${metadataItem.path}" />
                                    <a href="${metadataItemUrl}" title="toon alles voor ${fn:escapeXml(metadataItem.title)}">${fn:escapeXml(metadataItem.title)}</a>${(not status.last)?', ':''}                                      
                                 </c:forEach>
                             </span>
                        </c:if>
                         
                        <c:set var="document" value="${item.document}" />
                        
                        <c:choose>

	                        <c:when test="${not empty document}">
	                            
	                            <%-- found item is a regular document --%>
                         
                                <hst:link var="detailUrl" hippobean="${document}"/>  
                                
                                <a href="${detailUrl}" class="search-result-title">${fn:escapeXml(document.title)}</a>
                             
                                <c:if test="${not empty document.introduction}">
                                    <p title="${fn:escapeXml(document.introduction)}">
                                        <hs:abbreviate maxLength="250" text="${fn:escapeXml(document.introduction)}"/>
                                    </p>
                                </c:if>

	                                              
	                        </c:when>
	                        <c:otherwise>
	                            
                                <%-- found item is an asset --%>
                                
                                <hst:link var="assetUrl" hippobean="${item.attachment.document}"/>
                                
                                <span class="icon ${item.attachment.type}"></span>
                                <a href="${assetUrl}" class="search-result-title" onClick="trackOutboundLink(this, 'Outbound Links', '${assetUrl}'); return false;">${fn:escapeXml(item.attachment.title)}</a>

                                <c:if test="${fn:length(item.referingDocuments) gt 0 }">
                                    <p>Deze bijlage is gekoppeld aan:
	                                   <c:forEach var="document" items="${item.referingDocuments}" varStatus="status">
	                                       <hst:link var="detailUrl" hippobean="${document}"/>
	                                       <a href="${detailUrl}" title="${fn:escapeXml(document.introduction)}">${fn:escapeXml(document.title)}</a> ${(not status.last)?', ':''} 
	                                   </c:forEach>
                                    </p>
                                </c:if>
	                           
	                        </c:otherwise>
	                        
                        </c:choose>
	                
	               </section>
                
                </c:forEach>
                
                <tag:pagination paginator="${paginator}" searchText="${searchText}" department="${selectedDepartment}" training="${selectedTraining}"/>

            </section>        

	</c:when>
	<c:otherwise>
	
		 <section class="seach-results">
		      <section class="search-result">
		          <p>${message}</p>
		      </section>
		 </section>
		 
	</c:otherwise>
</c:choose>
</div>