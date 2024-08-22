<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-expand-sm navbar-dark navbar-custom">
    <div class="container-fluid">
        <a class="navbar-brand navbar-custom__logo" href="<c:url value="/admin" />">SCM Admin</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="collapsibleNavbar">
            <ul class="navbar-nav navbar-custome__menu w-100">
                <li class="nav-item navbar-custome__menu--item">
                    <a class="nav-link" href="<c:url value="/" />">Trang chủ</a>
                </li>
                <li class="nav-item navbar-custome__menu--item">
                    <a class="nav-link" href="<c:url value="/admin" />">Dashboard</a>
                </li>
                <s:authorize access="!isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/login" />">Đăng nhập</a>
                    </li>
                </s:authorize>
                <s:authorize access="hasAnyRole('ADMIN')">
                    <li class="nav-item navbar-custome__menu--item">
                        <a class="nav-link" href="<c:url value="/admin/statistics" />">Thống kê</a>
                    </li>
                    <li class="nav-item navbar-custome__menu--item">
                        <a class="nav-link" href="<c:url value="/admin/admin/analytics" />">Phân tích</a>
                    </li>
                </s:authorize>
                <div class="navbar-nav ms-auto">
                    <s:authorize access="hasAnyRole('ADMIN')">
                        <li class="nav-item me-2">
                            <a class="nav-link" href="<c:url value="/" />">
                                Xin chào <s:authentication property="principal.username"/>!
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="btn btn-danger" href="<c:url value="/logout" />">Đăng xuất</a>
                        </li>
                    </s:authorize>
                </div>
            </ul>
        </div>
    </div>
</nav>
