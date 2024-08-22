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
    <form:input id="firstName" type="text" name="firstName" path="firstName" placeholder="Nhập tên"/>
    <form:input type="text" name="middleName" path="middleName" placeholder="Nhập tên đệm"/>
    <form:input type="text" name="lastName" path="lastName" placeholder="Nhập họ"/>
    <form:input type="text" name="address" path="address" placeholder="Nhập địa chỉ"/>
    <form:input type="tel" name="phone" path="phone" placeholder="Nhập số điện thoại"/>

    <label for="gender">Giới tính</label>
    <div class="gender-group">
        <form:radiobutton path="gender" value="true" label="Nữ"/>
        <form:radiobutton path="gender" value="false" label="Nam"/>
    </div>

    <label for="dateOfBirth">Ngày sinh</label>
    <form:input type="date" name="dateOfBirth" path="dateOfBirth" placeholder="Chọn ngày sinh"/>

    <input type="submit" value="Thêm"/>
</form:form>
