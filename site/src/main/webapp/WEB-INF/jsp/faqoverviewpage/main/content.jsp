<%@ page contentType="text/html; charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<c:if test="${tag:isSubclassOfWebPage(document)}">
    <div class="inner">
      <c:if test="${not empty document.introduction }">
        <p class="intro">
          <c:out value="${document.introduction }" />
        </p>
      </c:if>
    </div>
</c:if>
        
<c:forEach var="item" items="${items}">
    <hst:link var="link" hippobean="${item}"/>
    <article class="well well-large">
        <hst:cmseditlink hippobean="${item}"/>
        <h3><a href="${link}">${fn:escapeXml(item.title)}</a></h3>
        <c:if test="${hst:isReadable(item, 'releaseDate.time')}">
            <p class="badge badge-info">
              <c:out value="use property for COLLAPSE / EXPAND link"></c:out>
            </p>
        </c:if>
        <tag:flexibleblock content="${item.flexibleblock }"/>
  </article>
</c:forEach>
<tag:toolbox document="${document }" />

<div class="paginator-style">
  <opw:simplepaginator paginator="${paginator}"/>
</div>