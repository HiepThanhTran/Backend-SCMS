<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/admin/inventories/add" var="addInventory"/>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Thêm tồn kho</h1>
    </div>
</div>

<c:if test="${errors != null}">
    <c:forEach var="error" items="${errors}">
        <div class="alert alert-danger">
                ${error.message}
        </div>
    </c:forEach>
</c:if>

<form:form id="addInventoryForm" method="post" modelAttribute="inventory" action="${addInventory}">
    <div class="form-group">
        <form:label path="name" cssClass="form-label">Tên hàng tồn kho</form:label>
        <form:input type="text" name="name" path="name" placeholder="Nhập tên hàng tồn kho" cssClass="form-control"/>
    </div>

    <form:select path="warehouse">
        <form:option value="" label="Chọn kho"/>
        <form:options items="${warehouses}" itemValue="id" itemLabel="name"/>
    </form:select>

    <input type="submit" value="Thêm"/>
</form:form>