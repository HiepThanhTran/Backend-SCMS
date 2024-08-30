<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url value="/admin/invoices/edit/${invoice.id}" var="editInvoice"/>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Chỉnh sửa hóa đơn</h1>
    </div>
</div>

<c:if test="${errors != null}">
    <c:forEach var="error" items="${errors}">
        <div class="alert alert-danger">
                ${error.message}
        </div>
    </c:forEach>
</c:if>

<form:form id="editInvoiceForm" method="post" modelAttribute="invoice" action="${editInvoice}">
    <form:hidden path="id"/>
    <form:hidden path="user"/>
    <form:hidden path="order"/>

    <div class="form-group mb-1">
        <form:label path="totalAmount" cssClass="form-label">Tổng tiền</form:label>
        <form:input type="number" name="totalAmount" path="totalAmount" placeholder="Nhập tổng tiền" cssClass="form-control"/>
    </div>

    <div class="form-group">
        <form:label path="tax" cssClass="form-label">Thuế</form:label><br/>
        <form:select path="tax" cssClass="w-100 mb-3">
            <form:option value="" label="Chọn thuế"/>
            <form:options items="${taxes}" itemValue="id" itemLabel="region"/>
        </form:select>
    </div>

    <div class="form-group d-flex align-items-center mt-3">
        <form:label path="paid" cssClass="form-label">Trạng thái thanh toán:</form:label>
        <form:checkbox path="paid" checked="${paid}" class="ms-2"/>
    </div>

    <div class="form-group d-flex align-items-center mt-3">
        <form:label path="active" cssClass="form-label">Active:</form:label>
        <form:checkbox path="active" checked="${active}" class="ms-2"/>
    </div>

    <input type="submit" class="mt-3" value="Chỉnh sửa"/>
</form:form>