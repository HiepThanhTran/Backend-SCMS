<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%--<div class="jumbotron bg-light p-5 rounded">--%>
<%--    <h1 class="display-4">Chào mừng đến với SCMS ADMIN</h1>--%>
<%--    <p class="lead">Trang quản lý dành cho quản trị viên.</p>--%>
<%--    <hr class="my-4">--%>
<%--</div>--%>

<div class="row mt-4 justify-content-center">
    <div class="col-md-6">
        <div class="card h-100 text-center">
            <div class="card-body">
                <h5 class="card-title">Thống kê hiệu suất các nhà cung cấp</h5>
                <p class="card-text">Xem thống kê về hiệu suất của nhà cung cấp dựa trên các chỉ số như chất lượng, giao hàng đúng hạn và giá cả.</p>
                <a href="<c:url value="/admin/statistics/supplier/performance"/>" class="btn btn-outline-primary">Xem chi tiết</a>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="card h-100 text-center">
            <div class="card-body">
                <h5 class="card-title">Thống kê doanh thu các đơn hàng</h5>
                <p class="card-text">Xem thống kê về doanh thu của các đơn hàng theo tháng, năm, hoặc quý</p>
                <a href="<c:url value="/admin/statistics/revenue"/>" class="btn btn-outline-primary">Xem chi tiết</a>
            </div>
        </div>
    </div>
    <div class="col-md-6 mt-4">
        <div class="card h-100 text-center">
            <div class="card-body">
                <h5 class="card-title">Báo cáo tình trạng tồn kho</h5>
                <p class="card-text">Báo cáo chi tiết về tình trạng tồn kho, bao gồm mức tồn kho hiện tại, hàng hóa sắp hết hạn và hàng hóa hết hạn.</p>
                <a href="<c:url value="/admin/report/inventory"/>" class="btn btn-outline-primary">Xem chi tiết</a>
            </div>
        </div>
    </div>
</div>