<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<c:url value="/admin/customers/add" var="addCustomer"/>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Thêm khách hàng</h1>
    </div>
</div>

<c:if test="${errors != null}">
    <c:forEach var="error" items="${errors}">
        <div class="alert alert-danger">
                ${error.message}
        </div>
    </c:forEach>
</c:if>

<form:form id="addCustomerForm" method="post" modelAttribute="customer" action="${addCustomer}">
    <form:hidden path="userRole" value="ROLE_CUSTOMER"/>

    <div class="form-group">
        <form:label path="email" cssClass="form-label mt-3">Email</form:label><br/>
        <form:input cssClass="w-100" id="email" type="email" name="firemailstName" path="email" placeholder="Nhập email"/>
    </div>

    <div class="form-group">
        <form:label path="username" cssClass="form-label mt-3">Tên đăng nhập</form:label><br/>
        <form:input cssClass="w-100" id="username" type="text" name="username" path="username" placeholder="Nhập tên đăng nhập"/>
    </div>

    <div class="form-group">
        <form:label path="password" cssClass="form-label mt-3">Mật khẩu</form:label><br/>
        <form:password cssClass="w-100" id="password" name="password" path="password" placeholder="Nhập mật khẩu"/>
    </div>

    <div class="form-group">
        <form:label path="firstName" cssClass="form-label mt-3">Tên</form:label><br/>
        <form:input cssClass="w-100" id="firstName" type="text" name="firstName" path="firstName" placeholder="Nhập tên"/>
    </div>

    <div class="form-group">
        <form:label path="middleName" cssClass="form-label mt-3">Tên đệm</form:label><br/>
        <form:input cssClass="w-100" id="middleNmae" type="text" name="middleName" path="middleName" placeholder="Nhập tên đệm"/>
    </div>

    <div class="form-group">
        <form:label path="lastName" cssClass="form-label mt-3">Họ</form:label><br/>
        <form:input cssClass="w-100" id="lastName" type="text" name="lastName" path="lastName" placeholder="Nhập họ"/>
    </div>

    <div class="form-group">
        <form:label path="address" cssClass="form-label mt-3">Địa chỉ</form:label><br/>
        <form:input cssClass="w-100" id="address" type="text" name="address" path="address" placeholder="Nhập địa chỉ"/>
    </div>

    <div class="form-group">
        <form:label path="phone" cssClass="form-label mt-3">Số điện thoại</form:label><br/>
        <form:input cssClass="w-100" id="phone" type="tel" name="phone" path="phone" placeholder="Nhập số điện thoại"/>
    </div>

    <form:label path="gender" cssClass="form-label mt-4">Giới tính</form:label>
    <div class="gender-group">
        <form:radiobutton path="gender" value="true" label="Nữ"/>
        <form:radiobutton path="gender" value="false" label="Nam"/>
    </div>

    <div class="form-group">
        <form:label path="dateOfBirth" cssClass="form-label">Ngày sinh</form:label>
        <form:input type="date" name="dateOfBirth" path="dateOfBirth" cssClass="form-control"/>
    </div>

    <input class="mt-3" type="submit" value="Thêm mới"/>
</form:form>
