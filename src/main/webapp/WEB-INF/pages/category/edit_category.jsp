<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/admin/categories/edit/${category.id}" var="editCategory"/>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Chỉnh sửa danh mục</h1>
    </div>
</div>

<form:form id="editCategoryForm" method="post" modelAttribute="category" action="${editCategory}">
    <form:hidden path="id"/>

    <!-- Label and input for name -->
    <div class="form-group">
        <form:label path="name" cssClass="form-label">Tên danh mục</form:label>
        <form:input id="name" type="text" name="name" path="name" cssClass="form-control"/><br/>
    </div>

    <!-- Label and input for description -->
    <div class="form-group">
        <form:label path="description" cssClass="form-label">Mô tả</form:label>
        <form:input type="text" name="description" path="description" cssClass="form-control"/><br/>
    </div>

    <!-- Label and radio buttons for active -->
    <div class="form-group d-flex align-items-center">
        <form:label path="active" cssClass="form-label">Active:</form:label>
        <form:checkbox path="active" checked="${active}" class="ms-2"/>
    </div>

    <input type="submit" value="Cập nhật" class="mt-3"/>
</form:form>
