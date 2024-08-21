<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/admin/tags/edit/${tag.id}" var="editTag"/>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Chỉnh sửa tag</h1>
    </div>
</div>

<c:if test="${errors != null}">
    <c:forEach  var="error" items="${errors}">
        <div class="alert alert-danger">
            ${error.message}
        </div>
    </c:forEach>
</c:if>

<form:form id="editTagForm" method="post" modelAttribute="tag" action="${editTag}">
    <form:hidden path="id"/>

    <div class="form-group">
        <form:label path="name" cssClass="form-label">Tên tag</form:label>
        <form:input type="text" name="name" path="name" cssClass="form-control"/><br/>
    </div>

    <div class="form-group">
        <form:label path="description" cssClass="form-label">Mô tả</form:label>
        <form:input type="text" name="description" path="description" cssClass="form-control"/><br/>
    </div>

    <div class="form-group d-flex align-items-center">
        <form:label path="active" cssClass="form-label">Active:</form:label>
        <form:checkbox path="active" checked="${active}" class="ms-2"/>
    </div>

    <input type="submit" value="Cập nhật" class="mt-3"/>
</form:form>
