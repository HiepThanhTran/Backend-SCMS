<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Danh sách lịch giao hàng</h1>
        <a href="<c:url value="/admin/delivery-schedules/add"/>" class="list__icon-add">
            <i class='bx bxs-plus-circle'></i>
        </a>
    </div>
</div>

<div class="container mt-4">
    <table id="table" class="table table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Ngày giao hàng</th>
            <th>Phương thức giao hàng</th>
            <th>Ngày tạo</th>
            <th>Ngày cập nhập</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="deliverySchedule" items="${deliverySchedules}">
            <tr id="item${deliverySchedule.id}">
                <td>${deliverySchedule.id}</td>
                <td>
                    <fmt:parseDate value="${ deliverySchedule.scheduledDate }" pattern="yyyy-MM-dd" var="parsedDeliveryDate" type="date"/>
                    <fmt:formatDate pattern="dd-MM-yyyy" value="${ parsedDeliveryDate }"/>
                </td>
                <td>${deliverySchedule.method.getDisplayName()}</td>
                <td>
                    <fmt:parseDate value="${ deliverySchedule.createdAt }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
                    <fmt:formatDate pattern="dd-MM-yyyy" value="${ parsedDateTime }"/>
                </td>
                <td>
                    <c:if test="${ deliverySchedule.updatedAt != null }">
                        <fmt:parseDate value="${ deliverySchedule.updatedAt }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedUpdatedDateTime" type="both"/>
                        <fmt:formatDate pattern="dd-MM-yyyy" value="${ parsedUpdatedDateTime }"/>
                    </c:if>
                    <c:if test="${ deliverySchedule.updatedAt == null }">
                        Chưa cập nhập
                    </c:if>
                </td>
                <td>
                    <a class="btn btn-primary btn-sm" href="<c:url value="/admin/delivery-schedules/edit/${deliverySchedule.id}"/>">
                        <i class='bx bxs-edit'></i>
                    </a>

                    <c:url value="/admin/delivery-schedules/delete/${deliverySchedule.id}" var="deleteDeliverySchedule"/>
                    <button class="btn btn-danger btn-sm" onclick="deleteItem('${deleteDeliverySchedule}', ${deliverySchedule.id})">
                        <i class='bx bx-x'></i>
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
