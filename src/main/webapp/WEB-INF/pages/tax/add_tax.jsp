<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/admin/taxs/add" var="addTax"/>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Thêm thuế</h1>
    </div>
</div>

<c:if test="${errors != null}">
    <c:forEach var="error" items="${errors}">
        <div class="alert alert-danger">
                ${error.message}
        </div>
    </c:forEach>
</c:if>

<form:form id="addTaxForm" method="post" modelAttribute="tax" action="${addTax}">
    <div class="form-group">
        <form:label path="rate" cssClass="form-label">Tỉ lệ thuế</form:label>
        <form:input type="text" name="rate" path="rate" placeholder="Nhập tỉ lệ thuế" cssClass="form-control"/><br/>
    </div>

    <div class="form-group">
        <form:label path="region" cssClass="form-label">Vùng</form:label>
        <form:input type="text" name="region" path="region" placeholder="Nhập vùng" cssClass="form-control"/><br/>
    </div>

    <input type="submit" value="Thêm mới"/>
</form:form>
