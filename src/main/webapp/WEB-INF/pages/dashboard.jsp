<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="row mt-4">
    <div class="col-md-4 col-12">
        <fmt:formatNumber value="${revenueLastWeek.totalAmount}" type="currency" currencySymbol="₫" groupingUsed="true"
                          var="formattedTotalAmountLastWeek"/>
        <div style="background: var(--color-chart1)" class="card h-100" id="popoverTotalAmount"
             data-bs-toggle="popover"
             title="Tổng doanh thu"
             data-bs-placement="bottom"
             data-bs-trigger="hover"
             data-bs-content="Tổng doanh thu của tuần trước: ${formattedTotalAmountLastWeek}.">
            <div class="card-body d-flex justify-content-between align-items-center">
                <fmt:formatNumber value="${revenueCurrentWeek.totalAmount}" type="currency" currencySymbol="₫" groupingUsed="true"
                                  var="formattedTotalAmountCurrentWeek"/>
                <div class="info d-flex flex-column justify-content-between h-100">
                    <div>
                        <h3 class="glow d-flex align-items-center">
                            <i class='bx bx-coin' style="margin-right: 8px"></i>
                            Doanh thu
                        </h3>
                        <p class="glow"><span class="totalRevenues">${formattedTotalAmountCurrentWeek}</span></p>
                    </div>
                    <p style="margin-bottom: 0;" class="text-light" id="info-per-revenue"></p>
                </div>

                <div class="progresss">
                    <canvas id="totalAmountChart" style="background: var(--color-chart1)" width="120" height="120">
                    </canvas>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-4 col-12">
        <div style="background: var(--color-chart2)" class="card h-100" id="popoverOrders"
             data-bs-toggle="popover"
             title="Tổng đơn hàng"
             data-bs-placement="bottom"
             data-bs-trigger="hover"
             data-bs-content="Tổng đơn hàng của tuần trước: ${revenueLastWeek.totalOrders} đơn.">
            <div class="card-body d-flex justify-content-between align-items-center">
                <div class="info d-flex flex-column justify-content-between h-100">
                    <div>
                        <h3 class="glow d-flex align-items-center">
                            <i class='bx bx-package' style="margin-right: 8px"></i>
                            Đơn hàng
                        </h3>
                        <p class="glow"><span class="totalOrders">${revenueCurrentWeek.totalOrders}</span></p>
                    </div>
                    <p style="margin-bottom: 0;" class="text-light" id="info-per-orders"></p>
                </div>

                <div class="progresss">
                    <canvas id="totalOrdersChart" style="background: var(--color-chart2)" width="120" height="120">
                    </canvas>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-4 col-12">
        <div style="background: var(--color-chart3)" class="card h-100" id="popoverProducts"
             data-bs-toggle="popover"
             title="Tổng sản phẩm bán được"
             data-bs-placement="bottom"
             data-bs-trigger="hover"
             data-bs-content="Tổng sản phẩm bán được của tuần trước: ${revenueLastWeek.totalProducts} sản phẩm.">
            <div class="card-body d-flex justify-content-between align-items-center">
                <div class="info d-flex flex-column justify-content-between h-100">
                    <div>
                        <h3 class="glow d-flex align-items-center">
                            <i class='bx bx-spa' style="margin-right: 8px"></i>
                            Sản phẩm
                        </h3>
                        <p class="glow"><span class="totalOrders">${revenueCurrentWeek.totalProducts}</span></p>
                    </div>
                    <p style="margin-bottom: 0;" class="text-light" id="info-per-products"></p>
                </div>

                <div class="progresss">
                    <canvas id="totalProductsChart" style="background: var(--color-chart3)" width="120" height="120">
                    </canvas>
                </div>
            </div>
        </div>
    </div>
</div>

<div style="margin-top: 32px; padding: 0 20px;" class="card">
    <canvas class="my-4 w-100" id="dashboardChart" width="900" height="380"></canvas>
</div>

<div class="row mt-4">
    <h2 class="mb-4 d-flex align-items-center">
        <i class='bx bx-box' style="font-size: 3rem; margin-right: 12px"></i>
        Đơn hàng gần đây
    </h2>
    <hr>
    <table id="recentlyOrderTable" class="table table-striped display nowrap">
        <thead>
        <tr>
            <th>ID</th>
            <th>Mã đơn hàng</th>
            <th>Loại đơn hàng</th>
            <th>Ngày đặt</th>
            <th>Trạng thái</th>
            <th>Người đặt</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${recentlyOrders}" var="order" varStatus="loop">
            <tr data-id="${order.id}" data-index="${loop.index}">
                <td>${order.id}</td>
                <td>${order.orderNumber}</td>
                <td>
                    <c:choose>
                        <c:when test="${ order.type == 'INBOUND' }">
                            <span class="badge bg-info">${order.type.getDisplayName()}</span>
                        </c:when>
                        <c:when test="${ order.type == 'OUTBOUND' }">
                            <span class="badge bg-primary">${order.type.getDisplayName()}</span>
                        </c:when>
                    </c:choose>
                </td>
                <td>
                    <fmt:parseDate value="${ order.createdAt }" pattern="yyyy-MM-dd HH:mm:ss" var="parsedDateTime" type="both"/>
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
                    <a href="<c:url value="/admin/orders/edit/${order.id}"/>" type="button" class="btn btn-outline-primary">Xem chi tiết</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script src="<c:url value="/js/dashboard.js"/>"></script>
<script>
    const totalAmountCurrentWeek = '${revenueCurrentWeek.totalAmount}';
    const totalAmountLastWeek = '${revenueLastWeek.totalAmount}';

    const totalOrdersCurrentWeek = '${revenueCurrentWeek.totalOrders}';
    const totalOrdersLastWeek = '${revenueLastWeek.totalOrders}';

    const totalProductsCurrentWeek = '${revenueCurrentWeek.totalProducts}';
    const totalProductsLastWeek = '${revenueLastWeek.totalProducts}';

    const dataDashboardChart = [<c:forEach items="${revenueCurrentWeek.details}" var="detail">'${detail.amount}', </c:forEach>]
</script>