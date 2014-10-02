<%@ page contentType="text/html; charset=UTF-8" language="java"
  trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>


<hst:headContribution keyHint="swiper">
  <script type="text/javascript" src="<hst:link path="/js/kees/vendor/idangerous.swiper.js"/>"></script>
</hst:headContribution>

<hst:headContribution keyHint="swiper.progress">
  <script type="text/javascript" src="<hst:link path="/js/kees/vendor/idangerous.swiper.progress.min.js"/>"></script>
</hst:headContribution>  

<div id="main" role="main" class="home"> 
            
  <c:choose>
    <c:when test="${empty document}">
       <tag:pagenotfound />
    </c:when>
    <c:otherwise>
      <hst:include ref="content"/>
    </c:otherwise>
  </c:choose>
  
  <div class="container">  
    <div class="row">
      
      <div class="col-xs-12 col-md-6">
               
        <div><div> <%-- delete these two --%>
        
        <%-- make this a peace a component --%>
        <section class="snelkiezer blok colorbg paars medium">
          <h1>Vind je bacheloropleiding</h1>
          <div class="interesse col-xs-6 col-sm-8 col-md-8">
            <h2>Interessegebied</h2>
            <ul>
              <li><a href="" class="btn">Onderwijs</a></li>
              <li><a href="" class="btn">Social Work &amp; Toegepaste Psychologie</a></li>
              <li><a href="" class="btn">Management &amp; Bedrijf</a></li>
              <li><a href="" class="btn">Techniek</a></li>
              <li><a href="" class="btn">Zorg</a></li>
            </ul>
          </div>
          <div class="vorm col-xs-6 col-sm-4 col-md-4">
            <h2>Vorm</h2>
            <ul>
              <li><a href="" class="btn">Voltijd</a></li>
              <li><a href="" class="btn">Deeltijd</a></li>
              <li><a href="" class="btn">Duaal</a></li>
            </ul>
          </div>
          <div class="zoeken col-xs-12 col-sm-12 ">
            <form role="form" class="form">
            <label for="zoekfilter">Zoeken in bacheloropleidingen</label>
                  <div class="form-group">
                    <input type="text" class="form-control" id="zoekfilter" name="zoekfilter" data-cip-id="zoekfilter">
                  </div>
                  <button class="btn fa fa-search no-radius" type="submit"><span>Zoeken</span></button>
             </form>
          </div>
        </section>

        </div></div> <%-- delete these two --%>
        
        <hst:include ref="leftTop" />
        <hst:include ref="leftBottom" />
        
      </div>
      
      <div class="col-xs-6 col-sm-6 col-md-3 ">
        <hst:include ref="contentTop" />
        <hst:include ref="contentBottom" />
      </div>
    
      <div class="col-xs-6 col-sm-6 col-md-3 ">
        <hst:include ref="rightTop" />
        <hst:include ref="rightBottom" />
      </div>
    
    </div>
    <hst:include ref="bottom-container" />
  </div>
  
  
</div>
