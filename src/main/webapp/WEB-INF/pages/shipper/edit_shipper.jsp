<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<c:url value="/admin/shippers/edit/${shipper.id}" var="editShipper"/>

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

<form:form id="editShipperForm" method="post" modelAttribute="shipper" action="${editShipper}">
    <form:hidden path="id"/>
    <form:hidden path="user"/>

    <div class="form-group">
        <form:label path="name" cssClass="form-label mt-3">Tên</form:label><br/>
        <form:input cssClass="w-100" id="firstName" type="text" name="name" path="name" placeholder="Nhập tên"/>
    </div>

    <div class="form-group">
        <form:label path="contactInfo" cssClass="form-label mt-3">Thông tin liên hệ</form:label><br/>
        <form:input cssClass="w-100" id="address" type="text" name="contactInfo" path="contactInfo" placeholder="Nhập thông tin liên hệ"/>
    </div>

    <div class="form-group d-flex align-items-center mt-3">
        <form:label path="active" cssClass="form-label">Active:</form:label>
        <form:checkbox path="active" checked="${active}" class="ms-2"/>
    </div>

    <input class="mt-3" type="submit" value="Cập nhật"/>
</form:form>
