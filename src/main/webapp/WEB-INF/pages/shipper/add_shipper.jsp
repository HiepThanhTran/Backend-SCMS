<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<c:url value="/admin/shippers/add" var="addShipper"/>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Thêm nhà cung cấp</h1>
    </div>
</div>

<c:if test="${errors != null}">
    <c:forEach var="error" items="${errors}">
        <div class="alert alert-danger">
                ${error.message}
        </div>
    </c:forEach>
</c:if>

<form:form id="addShipperForm" method="post" modelAttribute="shipper" action="${addShipper}">
    <form:hidden path="userRole" value="ROLE_SHIPPER"/>

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
        <form:label path="name" cssClass="form-label mt-3">Tên</form:label><br/>
        <form:input cssClass="w-100" id="name" type="text" name="name" path="name" placeholder="Nhập tên"/>
    </div>

    <div class="form-group">
        <form:label path="contactInfo" cssClass="form-label mt-3">Thông tin liên hệ</form:label><br/>
        <form:input cssClass="w-100" id="address" type="text" name="contactInfo" path="contactInfo" placeholder="Nhập thông tin liên hệ"/>
    </div>

    <input class="mt-3" type="submit" value="Thêm mới"/>
</form:form>
