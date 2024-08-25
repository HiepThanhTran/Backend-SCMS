<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<h2 id="warehouseHeader" class="mb-4">Báo cáo về tình trạng hiện tại của các nhà kho</h2>
<hr>
<table id="warehouseTable" class="table table-bordered table-hover display">
    <thead>
    <tr>
        <th>ID</th>
        <th>Tên nhà kho</th>
        <th>Dung tích nhà kho</th>
        <th>Dung tích còn lại</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${warehouseCapacityReport}" var="warehouse" varStatus="loop">
        <tr data-index="${loop.index}" data-id="${warehouse.warehouseId}">
            <td>${warehouse.warehouseId}</td>
            <td>${warehouse.warehouseName}</td>
            <td>${String.format("%,.3f", warehouse.warehouseCapacity)}</td>
            <c:choose>
                <c:when test="${warehouse.remainingCapacity < 0}">
                    <td><span class="badge bg-danger">Vượt hạn mức</span></td>
                </c:when>
                <c:when test="${warehouse.remainingCapacity == 0}">
                    <td><span class="badge bg-warning">Đã đầy</span></td>
                </c:when>
                <c:otherwise>
                    <td>${String.format("%,.3f", warehouse.remainingCapacity)}</td>
                </c:otherwise>
            </c:choose>
        </tr>
    </c:forEach>
    </tbody>
</table>

<h2 id="inventoryHeader" class="mt-5 mb-4">Tình trạng chi tiết của các tồn kho</h2>
<hr>
<table id="inventoryTable" class="table table-bordered table-hover display">
    <thead>
    <tr>
        <th>ID</th>
        <th>Tên tồn kho</th>
        <th>Số lượng hiện tại</th>
    </tr>
    </thead>
    <tbody>
    <%--Dữ liệu tồn kho sẽ được tải vào đây--%>
    </tbody>
</table>

<h2 id="chartHeader" class="mt-5 mb-4">Biểu đồ thống kê sản phẩm theo hạn sử dụng</h2>
<hr>
<div class="row justify-content-center">
    <div class="col-12 w-50">
        <canvas id="expiryDateChart"></canvas>
    </div>
</div>
<hr>

<div class="row">
    <h1 class="text-primary">Các sản phẩm của <span id="productHeader"></span></h1>
    <div class="col-md-6 col-12">
        <h2 id="productExpiringSoonHeader" class="mt-5 mb-4"><span class="text-warning bg-secondary p-3 rounded-pill">Sắp hết hạn</span></h2>
        <hr>
        <table id="productExpiringSoonTable" class="table table-bordered table-hover compact">
            <thead>
            <tr>
                <th>ID</th>
                <th>Tên sản phẩm</th>
                <th>Đơn vị</th>
                <th>Số lượng hiện tại</th>
                <th>Ngày hết hạn</th>
            </tr>
            </thead>
            <tbody>
            <%--Dữ liệu sản phẩm sẽ được tải vào đây--%>
            </tbody>
        </table>
    </div>
    <div class="col-md-6 col-12">
        <h2 id="productExpiredHeader" class="mt-5 mb-4"><span class="text-danger bg-secondary p-3 rounded-pill">Đã hết hạn</span></h2>
        <hr>
        <table id="productExpiredTable" class="table table-bordered table-hover compact">
            <thead>
            <tr>
                <th>ID</th>
                <th>Tên sản phẩm</th>
                <th class="noFilter">Đơn vị</th>
                <th class="noFilter">Số lượng hiện tại</th>
                <th class="noFilter">Ngày hết hạn</th>
            </tr>
            </thead>
            <tbody>
            <%--Dữ liệu sản phẩm sẽ được tải vào đây--%>
            </tbody>
        </table>
    </div>
</div>

<script src="<c:url value="/js/analytics.js"/>"></script>
<script>
    const chartCanvas = document.getElementById('expiryDateChart').getContext('2d');

    // Khởi tạo biểu đồ với dữ liệu rỗng
    const chartInstance = new Chart(chartCanvas, {
        type: 'polarArea',
        data: {
            labels: [],
            datasets: [{
                label: 'Số lượng',
                data: [],
                borderWidth: 1,
                hoverOffset: 4,
                backgroundColor: [
                    'rgb(75, 192, 192)',
                    'rgb(255, 99, 132)',
                    'rgb(255, 205, 86)',
                ],
            }]
        },
        options: {
            plugins: {
                title: {
                    display: true,
                    text: 'Thống kê sản phẩm theo hạn sử dụng',
                    font: {
                        size: 20
                    }
                },
                legend: {
                    labels: {
                        font: {
                            size: 16
                        }
                    }
                },
            },
            scales: {
                r: {
                    title: {
                        display: true,
                        text: 'Thống kê sản phẩm theo hạn sử dụng',
                        font: {
                            size: 20
                        }
                    },
                    ticks: {
                        display: false,
                    },
                    grid: {
                        display: true
                    },
                }
            },
        },
    });

    const warehouseData = ${fn:length(warehouseCapacityReport)};
    const contextPath = "${pageContext.request.contextPath}";
</script>