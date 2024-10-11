<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title><tiles:insertAttribute name="title"/> | FlowChain</title>

    <!--===============================================================================================-->
    <link rel="apple-touch-icon" sizes="180x180" href="<c:url value="/images/icons/apple-touch-icon.png"/>">
    <link rel="icon" type="image/png" sizes="32x32" href="<c:url value="/images/icons/favicon-32x32.png"/>">
    <link rel="icon" type="image/png" sizes="16x16" href="<c:url value="/images/icons/favicon-16x16.png"/>">
    <link rel="manifest" href="<c:url value="/images/icons/site.webmanifest"/>">
    <!--===============================================================================================-->
    <%--Box Icons--%>
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <!--===============================================================================================-->
    <%--FontAwesome--%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"
          integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <!--===============================================================================================-->
    <%--Bootstrap 5--%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <!--===============================================================================================-->
    <%--Swal--%>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <!--===============================================================================================-->
    <%--ChartJS--%>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-zoom"></script>
    <!--===============================================================================================-->
    <%--Moment.js--%>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.30.1/moment.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.30.1/moment-with-locales.min.js"></script>
    <!--===============================================================================================-->
    <%--Axios--%>
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
    <!--===============================================================================================-->
    <%--jQuery--%>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <!--===============================================================================================-->
    <!-- Moment Js -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script>
    <!--===============================================================================================-->
    <%--DataTables--%>
    <script src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.min.js"></script>
    <link rel="stylesheet" href="https://cdn.datatables.net/1.12.1/css/jquery.dataTables.min.css">
    <!--===============================================================================================-->
    <link href="<c:url value="/css/yearpicker.css" />" rel="stylesheet"/>
    <link href="<c:url value="/css/preloading.css" />" rel="stylesheet"/>
    <link href="<c:url value="/css/backtotop.css" />" rel="stylesheet"/>
    <link href="<c:url value="/css/header.css" />" rel="stylesheet"/>
    <link href="<c:url value="/css/footer.css" />" rel="stylesheet"/>
    <link href="<c:url value="/css/sidebar.css" />" rel="stylesheet"/>
    <link href="<c:url value="/css/dashboard.css" />" rel="stylesheet"/>
    <link href="<c:url value="/css/main.css" />" rel="stylesheet"/>
    <!--===============================================================================================-->
    <script src="<c:url value="/js/yearpicker.js" />"></script>
    <script src="<c:url value="/js/backtotop.js" />"></script>
    <script src="<c:url value="/js/main.js" />"></script>
</head>

<body>

<div class="wrapper">
    <tiles:insertAttribute name="header"/>

    <div class="container-fluid pb-5">
        <div class="row">
            <div class="col-3">
                <tiles:insertAttribute name="sidebar"/>
            </div>

            <div class="col-9">
                <tiles:insertAttribute name="content"/>
            </div>
        </div>
    </div>

    <%--    <tiles:insertAttribute name="footer"/>--%>
</div>

<button id="back-to-top"><i class="fa-solid fa-angle-up"></i></button>

<tiles:insertAttribute name="preloading"/>

<script>
    const contextPath = "${pageContext.request.contextPath}";
</script>

</body>

</html>
