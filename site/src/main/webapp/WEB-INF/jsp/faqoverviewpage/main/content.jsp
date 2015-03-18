<%@ page contentType="text/html; charset=UTF-8" language="java" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="opw" uri="http://open-web.nl/hippo/prototype"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
  
<section class="col-md-9 detail-content">
  <div class="row">
    <div class="col-md-8 col-sm-8 contentarea">    
    
    <div class="content">
    
      <hst:cmseditlink hippobean="${document}" />
      <h1 class="title"><c:out value="${document.title}"></c:out> </h1>
      <p class="intro"><c:out value="${document.introduction }"/></p>
     
      <hst:include ref="contentTop" />
    
      <c:forEach var="item" items="${items}">
          
          <article class="media clearfix white">
              <hst:cmseditlink hippobean="${item}"/>
              
              <div class="media-body accordian">
                <a href="#" title="expand" class="expOrColl">
                  <img src="<hst:link path="images/faq-arrow-right.png"/>" alt="expand" title="expand" class="faqimg"/>
                  <h2 class="media-heading"><c:out value="${item.title }"/></h2>
                </a>
              </div>
              
              <div class="faq item hidden">
                <tag:flexibleblock content="${item.faqflexibleblock }" document="${document}"/>
              </div>
             

          </article>
      </c:forEach>
      
    </div>
    
    <div class="paginator-style">
      <opw:simplepaginator paginator="${paginator}" namespaced="false"/>
      <tag:relNextPrev paginator="${paginator}"/>
    </div>

    </div>
    
    <aside class="col-md-4 col-sm-4 aside">
       <hst:include ref="rightTop" />
       <hst:include ref="right" />
       <hst:include ref="rightBottom" />
    </aside>
  </div>
    
  <div class="row">
     <hst:include ref="contentBottom" /> 
  </div>
  
</section>
