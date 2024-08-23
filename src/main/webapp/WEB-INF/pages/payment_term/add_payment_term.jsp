<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/admin/payment-terms/add" var="editpaymentTerms"/>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Thêm tồn kho</h1>
    </div>
</div>

<c:if test="${errors != null}">
    <c:forEach var="error" items="${errors}">
        <div class="alert alert-danger">
                ${error.message}
        </div>
    </c:forEach>
</c:if>

<form:form id="addpaymentTermsForm" method="post" modelAttribute="paymentTerms" action="${editpaymentTerms}">
    <div class="form-group">
        <form:label path="discountDays" cssClass="form-label">Số ngày hưởng chiết khấu</form:label>
        <form:input type="number" name="discountDays" path="discountDays" placeholder="Nhập số ngày hưởng chiết khấu" cssClass="form-control"/>
    </div>

    <div class="form-group mt-3">
        <form:label path="discountPercentage" cssClass="form-label">Phần trăm chiết khấu (nếu có)</form:label>
        <form:input type="number" name="discountPercentage" path="discountPercentage" placeholder="Nhập phần trăm chiết khấu (nếu có)" cssClass="form-control"/>
    </div>

    <div class="form-group mt-3">
        <form:label path="type" cssClass="form-label">Loại thanh toán</form:label><br/>
        <form:select path="type" cssClass="w-100 mb-3">
            <form:option value="" label="Chọn loại thanh toán"/>
            <c:forEach items="${paymentTermTypes}" var="paymentTermsType">
                <form:option value="${paymentTermsType['key']}" label="${paymentTermsType.value}"/>
            </c:forEach>
        </form:select>
    </div>

    <div class="form-group">
        <form:label path="supplier" cssClass="form-label">Nhà cung cấp</form:label>
        <form:select path="supplier" cssClass="w-100 mb-3">
            <form:option value="" label="Chọn nhà cung cấp"/>
            <form:options items="${suppliers}" itemValue="id" itemLabel="name"/>
        </form:select>
    </div>

    <input type="submit" value="Thêm mới"/>
</form:form>