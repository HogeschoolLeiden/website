<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
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


<hst:headContributions categoryIncludes="componentsCss" xhtml="true" />
<hst:headContributions categoryIncludes="globalJavascript" xhtml="true" />

<hst:headContribution keyHint="blogstyling">
  <link rel="stylesheet" href="<hst:link  path="/css/bootstrap.css"/>"
    type="text/css" />
</hst:headContribution>

<div class="container-fluid">
  <div class="row">
    <div class="col-md-9">
      <hst:include ref="detailcontainer" />
    </div>
    <div class="col-md-3">
      <hst:include ref="sidebarcontainer" />
    </div>
  </div>
</div>