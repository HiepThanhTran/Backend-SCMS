<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/admin/units/add" var="addUnit"/>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Thêm đơn vị sản phẩm</h1>
    </div>
</div>

<c:if test="${errors != null}">
    <c:forEach var="error" items="${errors}">
        <div class="alert alert-danger">
                ${error.message}
        </div>
    </c:forEach>
</c:if>

<form:form id="addUnitForm" method="post" modelAttribute="unit" action="${addTax}">
    <div class="form-group">
        <form:label path="name" cssClass="form-label">Đơn vị sản phẩm</form:label>
        <form:input type="text" name="name" path="name" placeholder="Nhập tên đơn vị sản phẩm" cssClass="form-control"/><br/>
    </div>

    <div class="form-group">
        <form:label path="abbreviation" cssClass="form-label">Ký hiệu đơn vị</form:label>
        <form:input type="text" name="abbreviation" path="abbreviation" placeholder="Nhập ký hiệu đơn vị" cssClass="form-control"/><br/>
    </div>

    <input type="submit" value="Thêm"/>
</form:form>