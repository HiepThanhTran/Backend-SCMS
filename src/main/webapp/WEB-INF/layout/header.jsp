<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header class="p-3 bg-dark text-white sticky-top">
    <div class="container-fluid">
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
            <a class="navbar-brand navbar-custom__logo d-flex align-items-center" href="<c:url value="/" />">
                <img src="<c:url value="/images/logo.png" />" alt="Logo" width="30" height="30" style="margin-right: 12px"
                     class="d-inline-block align-text-top">
                HARMONY ADMIN
            </a>

            <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                <li class="nav-item navbar-custome__menu--item">
                    <a class="nav-link px-2 text-secondary" href="<c:url value="/" />">Trang chủ</a>
                </li>
                <s:authorize access="hasAnyRole('ADMIN')">
                    <li class="nav-item navbar-custome__menu--item">
                        <a class="nav-link px-2" href="<c:url value="/admin/statistics" />">Thống kê</a>
                    </li>
                </s:authorize>
            </ul>

            <form class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3">
                <input id="search-input" type="search" class="form-control form-control-dark" placeholder="Tìm kiếm..." aria-label="Search">
                <ul id="search-results" class="list-group mt-2" style="display: none;"></ul>
            </form>

            <ul style="margin-bottom: 0; padding-left: 0" class="text-end d-flex align-items-center">
                <s:authorize access="!isAuthenticated()">
                    <li class="nav-item ms-auto">
                        <a class="btn btn-outline-light me-2" href="<c:url value="/login" />">Đăng nhập</a>
                    </li>
                </s:authorize>
                <s:authorize access="isAuthenticated()">
                    <li class="nav-item me-2 ms-auto">
                        <a class="nav-link px-2 text-white" href="javascript:void(0)">
                            Xin chào <s:authentication property="principal.username"/>!
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="btn btn-outline-danger" href="<c:url value="/logout" />">Đăng xuất</a>
                    </li>
                </s:authorize>
            </ul>
        </div>
    </div>
</header>

<script src="<c:url value="/js/header.js"/>"></script>
<script>
    const items = [];
    <c:forEach items="${entities}" var="entity">
    items.push({
        key: '${entity.key}',
        value: '${entity.value}'
    })
    </c:forEach>
</script>