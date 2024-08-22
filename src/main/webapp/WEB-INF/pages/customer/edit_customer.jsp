<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/admin/customers/edit/${customer.id}" var="editCustomer"/>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Chỉnh sửa khách hàng</h1>
    </div>
</div>

<c:if test="${errors != null}">
    <c:forEach var="error" items="${errors}">
        <div class="alert alert-danger">
            ${error.message}
        </div>
    </c:forEach>
</c:if>

<form:form id="editCustomerForm" method="post" modelAttribute="customer" action="${editCustomer}" acceptCharset="UTF-8">
    <form:hidden path="id"/>

    <div class="form-group">
        <form:label path="firstName" cssClass="form-label">First name</form:label>
        <form:input type="text" name="firstName" path="firstName" placeholder="Nhập tên danh mục" cssClass="form-control"/><br/>
    </div>
    
    <div class="form-group d-flex align-items-center">
        <form:label path="active" cssClass="form-label">Active:</form:label>
        <form:checkbox path="active" checked="${category.active}" class="ms-2"/>
    </div>

    <input type="submit" value="Cập nhật" class="mt-3"/>
</form:form>
