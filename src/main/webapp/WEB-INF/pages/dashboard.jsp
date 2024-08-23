<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>

<div class="jumbotron bg-light p-5 rounded">
    <h1 class="display-4">Chào mừng đến với SCMS Admin</h1>
    <p class="lead">Trang quản lý dành cho quản trị viên.</p>
    <hr class="my-4">
</div>

<div class="row mt-5">
    <div class="col-md-4">
        <div class="card h-100 text-center">
            <div class="card-body">
                <h5 class="card-title">Thống kê</h5>
                <p class="card-text">Xem thống kê về hoạt động hệ thống.</p>
                <a href="<c:url value="admin/statistics"/>" class="btn btn-outline-primary">Xem chi tiết</a>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <div class="card h-100 text-center">
            <div class="card-body">
                <h5 class="card-title">Phân tích</h5>
                <p class="card-text">Phân tích các sự thay đổi trong hệ thống.</p>
                <a href="<c:url value="admin/analytics"/>" class="btn btn-outline-primary">Xem chi tiết</a>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <div class="card h-100 text-center">
            <div class="card-body">
                <h5 class="card-title">Cấu hình hệ thống</h5>
                <p class="card-text">Thiết lập và quản lý cấu hình hệ thống một cách dễ dàng.</p>
                <a href="#" class="btn btn-outline-primary">Xem chi tiết</a>
            </div>
        </div>
    </div>
</div>
