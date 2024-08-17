<%--
  Created by IntelliJ IDEA.
  User: devlin
  Date: 8/16/24
  Time: 10:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">NexusChain Admin</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="collapsibleNavbar">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="#">Trang chủ</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="#" />">Danh sách người dùng</a>
                </li>
<%--                <s:authorize access="!isAuthenticated()">--%>
<%--                    <li class="nav-item">--%>
<%--                        <a class="nav-link" href="<c:url value="/login" />">Đăng nhập</a>--%>
<%--                    </li>--%>
<%--                </s:authorize>--%>
<%--                <s:authorize access="isAuthenticated()">--%>
<%--                    <li class="nav-item">--%>
<%--                        <a class="nav-link" href="<c:url value="/" />">--%>
<%--                            Welcome <s:authentication property="principal.username" />!--%>
<%--                        </a>--%>
<%--                    </li>--%>
<%--                    <li class="nav-item">--%>
<%--                        <a class="nav-link" href="<c:url value="/logout" />">Đăng xuất</a>--%>
<%--                    </li>--%>
<%--                </s:authorize>--%>
            </ul>
        </div>
    </div>
</nav>
