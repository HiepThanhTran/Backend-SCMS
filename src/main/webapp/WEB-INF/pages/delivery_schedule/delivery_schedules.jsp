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
    <table id="categoryTable" class="table table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Ngày giao hàng</th>
            <th>Phương thức giao hàng</th>
            <th>Ngày tạo</th>
            <th>Ngày cập nhập</th>
            <th>Active</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="delivery_schedule" items="${deliverySchedules}">
            <tr id="item${delivery_schedule.id}">
                <td>${delivery_schedule.id}</td>
                <td>${delivery_schedule.scheduledDate}</td>
                <td>${delivery_schedule.method.name}</td>
                <td>
                    <fmt:parseDate value="${ delivery_schedule.createdAt }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
                    <fmt:formatDate pattern="dd.MM.yyyy" value="${ parsedDateTime }"/>
                </td>
                <td>
                    <c:if test="${ delivery_schedule.updatedAt != null }">
                        <fmt:parseDate value="${ delivery_schedule.updatedAt }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedUpdatedDateTime" type="both"/>
                        <fmt:formatDate pattern="dd.MM.yyyy" value="${ parsedUpdatedDateTime }"/>
                    </c:if>
                    <c:if test="${ delivery_schedule.updatedAt == null }">
                        Chưa cập nhập
                    </c:if>
                </td>
                <td>${delivery_schedule.active}</td>
                <td>
                    <a class="btn btn-primary btn-sm" href="<c:url value="/admin/delivery-schedules/edit/${delivery_schedule.id}"/>">
                        <i class='bx bxs-edit'></i>
                    </a>

                    <c:url value="/admin/delivery-schedules/delete/${delivery_schedule.id}" var="deleteDeliverySchedule"/>
                    <button class="btn btn-danger btn-sm" onclick="deleteItem('${deleteDeliverySchedule}', ${delivery_schedule.id})">
                        <i class='bx bx-x'></i>
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
