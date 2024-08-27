<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="row mt-4">
    <div class="col-md-4 col-12">
        <div style="background: #c37eff" class="card h-100">
            <div class="card-body d-flex justify-content-between align-items-center">
                <div class="info">
                    <h3 class="glow">Doanh thu</h3>
                    <h1 class="glow">---</h1>
                </div>

                <div class="progresss">
                    <canvas style="background: #c37eff" width="120" height="120" id="chart1"></canvas>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-4 col-12">
        <div style="background: #ff9d9c" class="card h-100">
            <div class="card-body d-flex justify-content-between align-items-center">
                <div class="info">
                    <h3 class="glow">---</h3>
                    <h1 class="glow">---</h1>
                </div>

                <div class="progresss">
                    <canvas style="background: #ff9d9c" width="120" height="120" id="chart2"></canvas>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-4 col-12">
        <div style="background: #ffc96c" class="card h-100">
            <div class="card-body d-flex justify-content-between align-items-center">
                <div class="info">
                    <h3 class="glow">---</h3>
                    <h1 class="glow">---</h1>
                </div>

                <div class="progresss">
                    <canvas style="background: #ffc96c" width="120" height="120" id="chart3"></canvas>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row mt-5">
    <div class="col-md-6 col-12">
        <div class="card h-100 text-center">
            <div class="card-body">
                <h5 class="card-title">Thống kê</h5>
                <p class="card-text">Xem thống kê về hoạt động hệ thống.</p>
                <a href="<c:url value="admin/statistics"/>" class="btn btn-outline-primary">Xem chi tiết</a>
            </div>
        </div>
    </div>
    <div class="col-md-6 col-12">
        <div class="card h-100 text-center">
            <div class="card-body">
                <h5 class="card-title">Cấu hình hệ thống</h5>
                <p class="card-text">Thiết lập và quản lý cấu hình hệ thống một cách dễ dàng.</p>
                <a href="#" class="btn btn-outline-primary">Xem chi tiết</a>
            </div>
        </div>
    </div>
</div>

<div class="row mt-4">
    <h2 class="mb-4">Đơn hàng gần đây</h2>
    <hr>
    <table id="recentlyOrderTable" class="table table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Mã đơn hàng</th>
            <th>Loại đơn hàng</th>
            <th>Ngày đặt</th>
            <th>Trạng thái</th>
            <th>Người đặt</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${recentOrders}" var="order" varStatus="loop">
            <tr data-id="${order.id}" data-index="${loop.index}">
                <td>${order.id}</td>
                <td>${order.orderNumber}</td>
                <td>${order.type.getDisplayName()}</td>
                <td>
                    <fmt:parseDate value="${ order.createdAt }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
                    <fmt:formatDate pattern="dd-MM-yyyy HH:mm:ss" value="${ parsedDateTime }"/>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${ order.status == 'PENDING' }">
                            <span class="badge bg-warning">${order.status.getDisplayName()}</span>
                        </c:when>
                        <c:when test="${ order.status == 'CONFIRMED' }">
                            <span class="badge bg-info">${order.status.getDisplayName()}</span>
                        </c:when>
                        <c:when test="${ order.status == 'SHIPPED' }">
                            <span class="badge bg-primary">${order.status.getDisplayName()}</span>
                        </c:when>
                        <c:when test="${ order.status == 'DELIVERED' }">
                            <span class="badge bg-success">${order.status.getDisplayName()}</span>
                        </c:when>
                        <c:when test="${ order.status == 'CANCELLED' }">
                            <span class="badge bg-danger">${order.status.getDisplayName()}</span>
                        </c:when>
                        <c:when test="${ order.status == 'RETURNED' }">
                            <span class="badge bg-info">${order.status.getDisplayName()}</span>
                        </c:when>
                    </c:choose>
                </td>
                <td>${order.user.username}</td>
                <td>
                    <button type="button" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#staticBackdrop">Xem chi tiết</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- Modal -->
<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="staticBackdropLabel">Chi tiết đơn hàng</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                ...
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<script src="<c:url value="/js/dashboard.js"/>"></script>
<script>
    const centerTextInDoughnut = {
        beforeDraw: (chart) => {
            const { ctx, data } = chart;
            const xCenter = chart.getDatasetMeta(0).data[0].x;
            const yCenter = chart.getDatasetMeta(0).data[0].y;

            ctx.save();

            ctx.font = "bold 20px Arial";
            ctx.textAlign = "center";
            ctx.textBaseline = "middle";

            ctx.shadowColor = 'rgba(255, 255, 255, 0.8)';
            ctx.shadowBlur = 20;
            ctx.shadowOffsetX = 1;
            ctx.shadowOffsetY = 1;

            ctx.fillStyle = 'rgba(255, 255, 255, 0.8)';
            ctx.fillText(data.datasets[0].data[0] + "%", xCenter, yCenter);

            ctx.restore();
        },
    };

    const generateChart = (ctx, type, data, color) => {
        new Chart(ctx, {
            type: type,
            data: {
                datasets: [{
                    data: [data, 100 - data],
                    borderWidth: 0,
                    backgroundColor: ["#f6f6f9", color],
                    borderRadius: 20,
                    cutout: "80%",
                }]
            },
            plugins: [centerTextInDoughnut],
            options: {
                responsive: true,
                plugins: {
                    tooltip: {
                        enabled: false,
                    },
                },
            },
        })
    }

    const chart1 = document.getElementById('chart1').getContext('2d')
    generateChart(ctx = chart1, type = 'doughnut', data = 14, color = "#a64db5")

    const chart2 = document.getElementById('chart2').getContext('2d')
    generateChart(ctx = chart2, type = 'doughnut', data = 29, color = "#e67c7b")

    const chart3 = document.getElementById('chart3').getContext('2d')
    generateChart(ctx = chart3, type = 'doughnut', data = 67, color = "#f1a340")
</script>