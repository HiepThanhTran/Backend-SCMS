<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/admin/warehouses/edit/${warehouse.id}" var="editWarehouse"/>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Chỉnh sửa kho</h1>
    </div>
</div>

<c:if test="${errors != null}">
    <c:forEach  var="error" items="${errors}">
        <div class="alert alert-danger">
            ${error.message}
        </div>
    </c:forEach>
</c:if>

<form:form id="editWarehouseForm" method="post" modelAttribute="warehouse" action="${editWarehouse}">
    <form:hidden path="id"/>

    <div class="form-group">
        <form:label path="name" cssClass="form-label">Tên kho</form:label>
        <form:input type="text" name="name" path="name" cssClass="form-control"/><br/>
    </div>

    <div class="form-group">
        <form:label path="location" cssClass="form-label">Địa chỉ kho</form:label>
        <form:input type="text" name="location" path="location" cssClass="form-control"/><br/>
    </div>

    <div class="form-group">
        <form:label path="capacity" cssClass="form-label">Dung tích</form:label>
        <form:input type="numberDecimal" name="capacity" path="capacity" cssClass="form-control"/><br/>
    </div>
    
     <div class="form-group">
        <form:label path="cost" cssClass="form-label">Giá</form:label>
        <form:input type="numberDecimal" name="cost" path="cost" cssClass="form-control"/><br/>
    </div>
    
    <div class="form-group d-flex align-items-center">
        <form:label path="active" cssClass="form-label">Active:</form:label>
        <form:checkbox path="active" checked="${active}" class="ms-2"/>
    </div>

    <input type="submit" value="Cập nhật" class="mt-3"/>
</form:form>
