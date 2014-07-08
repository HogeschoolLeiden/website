<%@ page contentType="text/html; charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
  
<hst:headContribution category="scripts">
  <script src="<hst:link path="/js/accordian.js"/>" type="text/javascript"></script>
</hst:headContribution>

<tag:overviewIntrodution doc="${document}" ></tag:overviewIntrodution>

<c:forEach var="item" items="${items}">
    <article class="well well-large faq">
        <hst:cmseditlink hippobean="${item}"/>
        <div class="accordian">
          <div class="title">
            <h3><c:out value="${item.title}"/></h3>
          </div>
        </div>
        <c:if test="${hst:isReadable(item, 'releaseDate.time')}">
            <p class="badge badge-info">
              <c:out value=" "></c:out>
            </p>
        </c:if>
        <tag:flexibleblock content="${item.flexibleblock }"/>
  </article>
</c:forEach>
<tag:toolbox document="${document }" />

<div class="paginator-style">
  <opw:simplepaginator paginator="${paginator}"/>
</div>