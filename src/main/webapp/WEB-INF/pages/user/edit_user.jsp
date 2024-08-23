<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<c:url value="/admin/users/edit/${user.id}" var="editShipper"/>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Chỉnh sửa người dùng</h1>
    </div>
</div>

<c:if test="${errors != null}">
    <c:forEach var="error" items="${errors}">
        <div class="alert alert-danger">
                ${error.message}
        </div>
    </c:forEach>
</c:if>

<form:form id="editUserForm" method="post" enctype="multipart/form-data" modelAttribute="user" action="${editShipper}">
    <form:hidden path="id"/>
    <form:hidden path="avatar"/>
    <form:hidden path="password"/>

    <div class="form-group">
        <form:label path="email" cssClass="form-label mt-3">Email</form:label><br/>
        <form:input cssClass="w-100" id="email" type="email" name="firemailstName" path="email" placeholder="Nhập email"/>
    </div>

    <div class="form-group">
        <form:label path="username" cssClass="form-label mt-3">Tên đăng nhập</form:label><br/>
        <form:input cssClass="w-100" id="username" type="text" name="username" path="username" placeholder="Nhập tên đăng nhập"/>
    </div>

    <div class="mb-1 mt-3">
        <label for="file" class="form-label">Ảnh đại diện:</label>
        <form:input path="file" type="file" accept=".jpg,.png" class="form-control" id="file" name="file"/>
        <c:if test="${user.avatar != null}">
            <img class="mt-3" src="${user.avatar}" alt="${user.avatar}" width="300"/>
        </c:if>
    </div>

    <div class="form-group">
        <form:label path="userRole" cssClass="form-label mt-3">Vai trò</form:label><br/>
        <form:select path="userRole" cssClass="w-100 mb-3">
            <form:option value="" label="Chọn vai trò"/>
            <c:forEach items="${userRoles}" var="role">
                <form:option value="${role['key']}" label="${role.value}"/>
            </c:forEach>
        </form:select>
    </div>

    <div class="form-group d-flex align-items-center mb-1">
        <form:label path="confirm" cssClass="form-label">Confirm:</form:label>
        <form:checkbox path="confirm" checked="${confirm}" class="ms-2"/>
    </div>

    <div class="form-group d-flex align-items-center">
        <form:label path="active" cssClass="form-label">Active:</form:label>
        <form:checkbox path="active" checked="${active}" class="ms-2"/>
    </div>

    <input class="mt-3" type="submit" value="Cập nhật"/>
</form:form>
