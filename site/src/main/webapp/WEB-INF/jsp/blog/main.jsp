<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>
<%@ taglib prefix='opw' uri="http://open-web.nl/hippo/prototype"%>
<%--
  Copyright 2014 Hippo B.V. (http://www.onehippo.com)

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  --%>

<div id="main" role="main" class="detail">

  
  <tag:headerImage document="${document}"/>

  <div class="container">
    <div class="row">
    <article class="col-md-9 detail-content">
      
      <div class="row">
        
        <div class="col-md-8 col-sm-8 contentarea">
          <div class="content">
      
            <hst:include ref="detailcontainer" />
       
          </div>
        </div>
        
        <aside class="col-md-4 col-sm-4 aside">
          <hst:include ref="sidebarcontainer" /> 
        </aside>
      
      </div>
    </article>
    
    <hst:include ref="leftTop" /> 
    <hst:include ref="left"/>
    <hst:include ref="leftBottom" />
    
    </div>
  </div>
</div>