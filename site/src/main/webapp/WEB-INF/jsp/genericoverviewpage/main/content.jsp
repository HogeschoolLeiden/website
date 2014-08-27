<%@ page contentType="text/html; charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<tag:overviewIntrodution doc="${document }"></tag:overviewIntrodution>

<c:if test="${hst:isReadable(document, 'rssItem') && not empty document.rssItem }">
  <c:set var="rssLink">
    <hst:link hippobean="${document.rssItem}"/>
  </c:set>
  <hst:headContribution keyHint="rssItem">
    <link title="${document.rssItem.title}" rel="alternate" type="application/rss+xml" href="${rssLink}"/>
  </hst:headContribution>
</c:if>
                      
<c:forEach var="item" items="${items}">
    <hst:link var="link" hippobean="${item}"/>
    <article class="well well-large">
        <hst:cmseditlink hippobean="${item}"/>
        <h3><a href="${link}"><c:out value="${item.title}"/></a></h3>
        <c:if test="${hst:isReadable(item, 'releaseDate.time')}">
            <p class="badge badge-info">
              <fmt:formatDate value="${item.releaseDate.time}" type="both" dateStyle="medium" timeStyle="short"/>
            </p>
        </c:if>
        <p><c:out value="${item.introduction}"/></p>
  </article>
</c:forEach>

<tag:toolbox document="${document }" />

<div class="paginator-style">
  <opw:simplepaginator paginator="${paginator}" namespaced="false"/>
</div>
