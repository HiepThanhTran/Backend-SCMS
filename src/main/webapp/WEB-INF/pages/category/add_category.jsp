<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/admin/categories/add" var="addCategory" />

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Thêm danh mục</h1>
    </div>
</div>

<c:if test="${errors != null}">
    <c:forEach  var="error" items="${errors}">
        <div class="alert alert-danger">
            ${error.message}
        </div>
    </c:forEach>
</c:if>

<form:form id="addCategoryForm" method="post" modelAttribute="category" action="${addCategory}">
    <form:input id="name" type="text" name="name" path="name" placeholder="Nhập tên danh mục" />
    <form:input type="text" name="description" path="description" placeholder="Mô tả" />
    <input type="submit" value="Thêm"/>
</form:form>
