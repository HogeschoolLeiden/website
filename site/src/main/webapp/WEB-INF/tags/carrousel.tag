<%@tag description="display carrousel in header width" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>

<%@ attribute name="document" rtexprvalue="true" required="true" type="org.hippoecm.hst.content.beans.standard.HippoBean" %>

<hst:headContribution keyHint="swiper">
  <script type="text/javascript" src="<hst:link path="/js/kees/vendor/idangerous.swiper.js"/>"></script>
</hst:headContribution>

<hst:headContribution keyHint="swiper.progress">
  <script type="text/javascript" src="<hst:link path="/js/kees/vendor/idangerous.swiper.progress.min.js"/>"></script>
</hst:headContribution>  

<div class="background large img swiper-container">
  <div class="swiper-wrapper">

    <c:forEach var="carrouselitem" items="${document.carrousel.carrouselitems }">
      <div class="img swiper-slide">

        <a href="<hst:link hippobean="${carrouselitem.document }" />" title="${carrouselitem.document.title}"> 
        
        <img src="<hst:link hippobean="${carrouselitem.image.detailBackground }" />"
          alt="<c:out value="${carrouselitem.document.title }" escapeXml="true" />"
          title="<c:out value="${carrouselitem.document.title }" escapeXml="true" />" />

          <div class="container">
            <div class="row">

              <section class="slide-content col-md-12">
                <div class="row">
                  <div class="col-md-4 col-md-offset-1 highlighted">
                    <article class="media-body slider-text">
                      <div class="contentarea">

                        <h1><c:out value="${carrouselitem.document.title }" /> </h1>
                        <tag:renderDate document="${carrouselitem.document }" dateClass="datum start large" />
                        <p><c:out value="${carrouselitem.document.introduction}"/></p>

                      </div>
                    </article>
                  </div>
                </div>
              </section>

            </div>
          </div>

        </a>
      </div>
    </c:forEach>

  </div>

  <div class="slider-pagination"></div>
  <div class="swipenav prev fa fa-angle-left"></div>
  <div class="swipenav next fa fa-angle-right"></div>     
</div>
