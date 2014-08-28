<%@ page contentType="text/html; charset=UTF-8" language="java"
  trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>

<c:choose>
  <c:when test="${empty document}">
    <tag:pagenotfound />
  </c:when>
  <c:otherwise>

    <div class="container">
      <div class="row">
        <article class="col-md-9 detail-content">

          <hst:cmseditlink hippobean="${document}" />

          <div class="row">
            <div class="col-md-8 col-sm-8 contentarea">
              <div class="content">

                <h1>
                  <c:out value="${document.title }" escapeXml="true" />
                </h1>
                <%-- 		   
                <h2>
    			 <c:out value="${document.subtitle }" escapeXml="true" />
    		    </h2> 
                --%>

                <p class="intro">
                  <c:out value="${document.introduction }" />
                </p>

                <tag:flexibleblock content="${document.flexibleblock }" />

                <tag:toolbox document="${document }" />

              </div>
            </div>
          </div>

        </article>

      </div>
    </div>
  </c:otherwise>
</c:choose>
