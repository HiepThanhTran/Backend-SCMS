<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/admin/units/edit/${unit.id}" var="editUnit"/>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Chỉnh sửa đơn vị sản phẩm</h1>
    </div>
</div>

<c:if test="${errors != null}">
    <c:forEach var="error" items="${errors}">
        <div class="alert alert-danger">
                ${error.message}
        </div>
    </c:forEach>
</c:if>

<form:form id="editUnitForm" method="post" modelAttribute="unit" action="${editUnit}">
    <form:hidden path="id"/>

    <div class="form-group">
        <form:label path="name" cssClass="form-label">Đơn vị sản phẩm</form:label>
        <form:input type="text" name="name" path="name" placeholder="Nhập tên đơn vị sản phẩm" cssClass="form-control"/><br/>
    </div>

    <div class="form-group">
        <form:label path="abbreviation" cssClass="form-label">Ký hiệu đơn vị</form:label>
        <form:input type="text" name="abbreviation" path="abbreviation" placeholder="Nhập ký hiệu đơn vị" cssClass="form-control"/><br/>
    </div>

    <div class="form-group d-flex align-items-center">
        <form:label path="active" cssClass="form-label">Active:</form:label>
        <form:checkbox path="active" checked="${active}" class="ms-2"/>
    </div>

    <input type="submit" value="Cập nhật" class="mt-3"/>
</form:form>
