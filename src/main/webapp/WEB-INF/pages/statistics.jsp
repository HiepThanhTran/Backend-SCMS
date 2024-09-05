<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="container px-4 py-5" id="hanging-icons">
    <div class="row g-4 py-5 row-cols-1 row-cols-lg-3">
        <div class="col d-flex align-items-start">
            <div class="d-flex flex-column justify-content-between h-100">
                <h2 class="h4 d-flex align-items-center">
                    <i class="bx bx-line-chart" style="font-size: 3rem; margin-right: 12px;"></i>
                    Thống kê hiệu suất các nhà cung cấp
                </h2>
                <p>Xem thống kê về hiệu suất của nhà cung cấp dựa trên các chỉ số như chất lượng, giao hàng đúng hạn và giá cả.</p>
                <a href="<c:url value="/admin/statistics/supplier-performance"/>" class="btn btn-outline-primary">
                    Xem chi tiết
                </a>
            </div>
        </div>
        <div class="col d-flex align-items-start">
            <div class="d-flex flex-column justify-content-between h-100">
                <h2 class="h4 d-flex align-items-center">
                    <i class="bx bx-stats" style="font-size: 3rem; margin-right: 12px;"></i>
                    Thống kê doanh thu
                </h2>
                <p>Xem thống kê về doanh thu theo các điều kiện khác nhau (tháng, năm, quý, sản phẩm, loại, ...)</p>
                <a href="<c:url value="/admin/statistics/revenue"/>" class="btn btn-outline-primary">
                    Xem chi tiết
                </a>
            </div>
        </div>
        <div class="col d-flex align-items-start">
            <div class="d-flex flex-column justify-content-between h-100">
                <h2 class="h4 d-flex align-items-center">
                    <i class="bx bx-pie-chart" style="font-size: 3rem; margin-right: 12px;"></i>
                    Báo cáo tình trạng tồn kho
                </h2>
                <p>Báo cáo chi tiết về tình trạng tồn kho, bao gồm mức tồn kho hiện tại, hàng hóa sắp hết hạn và hàng hóa hết hạn.</p>
                <a href="<c:url value="/admin/inventory-report"/>" class="btn btn-outline-primary">
                    Xem chi tiết
                </a>
            </div>
        </div>
    </div>
</div>