<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/admin/shipments/edit/${shipment.id}" var="editShipment"/>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Chỉnh sửa đơn vận chuyển</h1>
    </div>
</div>

<c:if test="${errors != null}">
    <c:forEach var="error" items="${errors}">
        <div class="alert alert-danger">
                ${error.message}
        </div>
    </c:forEach>
</c:if>

<form:form id="editShipmentForm" method="post" modelAttribute="shipment" action="${editShipment}">
    <form:hidden path="id"/>

    <div class="form-group mb-1">
        <form:label path="cost" cssClass="form-label">Chi phí vận chuyển</form:label>
        <form:input type="number" name="cost" path="cost" placeholder="Nhập chi phí vận chuyển" cssClass="form-control"/>
    </div>

    <div class="form-group">
        <form:label path="note" cssClass="form-label">Ghi chú (Nếu có)</form:label>
        <form:input type="text" name="note" path="note" placeholder="Nhập ghi chú" cssClass="form-control"/>
    </div>

    <div class="form-group">
        <form:label path="shipper" cssClass="form-label mt-3">Người vận chuyển</form:label>
        <form:select path="shipper" cssClass="w-100 mb-3">
            <form:option value="" label="Chọn người vận chuyển"/>
            <form:options items="${shippers}" itemValue="id" itemLabel="name"/>
        </form:select>
    </div>

    <div class="form-group">
        <form:label path="warehouse" cssClass="form-label mt-3">Nhà kho</form:label>
        <form:select path="warehouse" cssClass="w-100 mb-3">
            <form:option value="" label="Chọn nhà kho"/>
            <form:options items="${warehouses}" itemValue="id" itemLabel="name"/>
        </form:select>
    </div>

    <div class="form-group">
        <form:label path="deliverySchedule" cssClass="form-label mt-3">Lịch giao hàng</form:label>
        <form:select path="deliverySchedule" cssClass="w-100 mb-3">
            <form:option value="" label="Chọn lịch giao hàng"/>
            <form:options items="${deliverySchedules}" itemValue="id" itemLabel="scheduledDate"/>
        </form:select>
    </div>

    <div class="form-group d-flex align-items-center">
        <form:label path="active" cssClass="form-label">Active:</form:label>
        <form:checkbox path="active" checked="${active}" class="ms-2"/>
    </div>

    <input type="submit" value="Cập nhật"/>
</form:form>