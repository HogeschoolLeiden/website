<!doctype html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="hst" uri="http://www.hippoecm.org/jsp/hst/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="tag" uri="/WEB-INF/tags/tags.tld"%>

<%@ page isErrorPage="true" %>
<% response.setStatus(400); %>

<html lang="en">
<head>
  <meta charset="utf-8"/>
  <title>400 error</title>
</head>
<body>
<h1>Bad request!!!</h1>
<p>The request cannot be fulfilled due to bad syntax.</p>
</body>
</html>