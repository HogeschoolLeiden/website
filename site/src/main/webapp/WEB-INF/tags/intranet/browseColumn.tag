<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<%@ attribute name="browseResult" type="nl.hinttech.hsleiden.pi.beans.BrowseResult" rtexprvalue="true"
  required="true" %>
<%@ attribute name="begin" type="java.lang.Integer" rtexprvalue="true"
  required="true" %>
<%@ attribute name="end" type="java.lang.Integer" rtexprvalue="true"
  required="true" %>

<section class="sections-column">
     
     
     
     
     <c:forEach var="group" items="${browseResult.groups}" begin="${begin}" end="${end}">

          <c:choose>
              <c:when test="${fn:length(group.documents) gt 1}">
                     
                  <section class="column-box">
                       <div class="column-box-header">
                            <span title="${fn:escapeXml(group.categoryTitle)}"><hs:abbreviate maxLength="38" text="${fn:escapeXml(group.categoryTitle)}"/></span>
                            <img src="<hst:link path="/img/intranet/arrow_down.png"/>" class="arrow down" alt="">
                       </div>
                       <div class="box-content">
                          <ul class="categories">
                              <c:forEach var="document" items="${group.documents}">
                                  <hst:link var="detailUrl" hippobean="${document}"/>
                                  <li>
	                                    <a href="${detailUrl}" title="${fn:escapeXml(document.introduction)}">
	                                        ${fn:escapeXml(document.title)}
	                                    </a>
                                  </li>
                              </c:forEach>
                          </ul>
                      </div>
                  </section>

                 </c:when>
                 <c:otherwise>
                     <c:set var="document" value="${group.documents[0]}" />
                     <hst:link var="detailUrl" hippobean="${document}"/>
                     <section class="sections-head">
                          <a href="${detailUrl}" title="${fn:escapeXml(document.introduction)}"><hs:abbreviate maxLength="38" text="${fn:escapeXml(group.categoryTitle)}"/></a>
                      </section>
                     
                 </c:otherwise>
             </c:choose>

     </c:forEach>
</section>