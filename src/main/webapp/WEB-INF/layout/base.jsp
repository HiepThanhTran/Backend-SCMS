<%--
  Created by IntelliJ IDEA.
  User: devlin
  Date: 8/16/24
  Time: 10:37 PM
  To change this template use File | Settings | File Templates.
--%>

<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>
            <tiles:insertAttribute name="title"/>
        </title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <%--    <script src="<c:url value="/js/script.js" />"></script>--%>

        <link href="<c:url value="/css/styles.css" />" rel="stylesheet"/>
       <script src="<c:url value='/js/index.js' />"></script>
    </head>

    <body>

        <tiles:insertAttribute name="header"/>

        <div class="container">
            <div class="row">
                <div class="col-3">
                    <tiles:insertAttribute name="sidebar"/>
                </div>

                <div class="col-9">
                    <tiles:insertAttribute name="content"/>
                </div>
            </div>
        </div>

        <tiles:insertAttribute name="footer"/>
    </body>

</html>
