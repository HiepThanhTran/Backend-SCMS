<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url value="/admin/schedules/edit/${deliverySchedule.id}" var="editDeliverySchedule"/>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Chỉnh sửa lịch giao hàng</h1>
    </div>
</div>

<c:if test="${errors != null}">
    <c:forEach var="error" items="${errors}">
        <div class="alert alert-danger">
                ${error.message}
        </div>
    </c:forEach>
</c:if>

<form:form id="addInventoryForm" method="post" modelAttribute="deliverySchedule" action="${editDeliverySchedule}">
    <form:hidden path="id"/>

    <div class="form-group">
        <fmt:parseDate value="${ deliverySchedule.scheduledDate }" pattern="yyyy-MM-dd" var="parsedDateTime"/>
        <fmt:formatDate pattern="yyyy-MM-dd" value="${ parsedDateTime }" var="scheduledDate"/>

        <form:label path="scheduledDate" cssClass="form-label">Ngày giao hàng</form:label>
        <form:input type="date" value="${scheduledDate}" name="scheduledDate" path="scheduledDate" cssClass="form-control"/>
    </div>

    <div class="form-group">
        <form:label path="method" cssClass="form-label mt-3">Phương thức giao hàng</form:label><br/>
        <form:select path="method" cssClass="w-100 mb-3">
            <form:option value="" label="Chọn phương thức giao hàng"/>
            <c:forEach items="${deliveryMethods}" var="method">
                <form:option value="${method['key']}" label="${method.value}"/>
            </c:forEach>
        </form:select>
    </div>

    <div class="form-group d-flex align-items-center">
        <form:label path="active" cssClass="form-label">Active:</form:label>
        <form:checkbox path="active" checked="${active}" class="ms-2"/>
    </div>

    <div id="ordersSection" class="form-group">
        <label class="form-label mt-3">Nhãn</label><br/>
        <div id="ordersContainer">
            <div class="order-row">
                <button type="button" class="btn btn-success" onclick="addOrderRow()">+</button>
            </div>
            <c:forEach var="unit" items="${scheduleOrders}">
                <div style="display: flex; align-items: center; justify-content: center" class="order-row">
                    <select name="orderIds" class="form-control mt-3 mb-2 me-3">
                        <c:forEach var="optionOrder" items="${orders}">
                            <option value="${optionOrder.id}"
                                    <c:if test="${optionOrder.id == unit.id}">selected</c:if>>
                                    ${optionOrder.orderNumber}
                            </option>
                        </c:forEach>
                    </select>
                    <button type="button" class="btn btn-danger mt-2" onclick="removeOrderRow(this)">-</button>
                </div>
            </c:forEach>
        </div>
    </div>

    <input type="submit" class="mt-3" value="Cập nhật"/>
</form:form>

<script>
    function addOrderRow() {
        const container = document.getElementById('ordersContainer');
        const row = document.createElement('div');
        row.className = 'order-row';
        row.style.display = 'flex';
        row.style.alignItems = 'center';
        row.style.justifyContent = 'center';
        row.innerHTML = `
            <select name="orderIds" class="form-control mt-3 mb-2 me-3">
                <c:forEach var="order" items="${orders}">
                    <option value="${order.id}">${order.orderNumber}</option>
                </c:forEach>
            </select>
            <button type="button" class="btn btn-danger mt-2" onclick="removeOrderRow(this)">-</button>
        `;
        container.appendChild(row);
    }

    function removeOrderRow(button) {
        button.parentElement.remove();
    }
</script>