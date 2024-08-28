<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/admin/ratings/add" var="addRating"/>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Thêm đánh giá</h1>
    </div>
</div>

<c:if test="${errors != null}">
    <c:forEach var="error" items="${errors}">
        <div class="alert alert-danger">
                ${error.message}
        </div>
    </c:forEach>
</c:if>

<form:form id="addRatingForm" method="post" modelAttribute="rating" action="${addRating}">
    <div class="form-group">
        <form:label class="form-label" path="rating">Đánh giá</form:label>
        <form:input type="numberDecimal" name="rating" path="rating" placeholder="Nhập đánh giá" cssClass="form-control"/><br/>
    </div>

    <div class="form-group">
        <form:label class="form-label" path="comment">Nội dung</form:label>
        <form:input type="text" name="comment" path="comment" cssClass="form-control"/><br/>
    </div>

    <div class="form-group">
        <form:label path="criteria" cssClass="form-label">Tiêu chí đánh giá</form:label><br/>
        <form:select path="criteria" cssClass="w-100 mb-3">
            <form:option value="" label="Chọn tiêu chí đánh giá"/>
            <c:forEach items="${criteriaTypes}" var="criteriaType">
                <form:option value="${criteriaType['key']}" label="${criteriaType.value}"/>
            </c:forEach>
        </form:select>
    </div>

    <div class="form-group">
        <form:label path="supplier" cssClass="form-label mt-3">Nhà cung cấp</form:label><br/>
        <form:select path="supplier" cssClass="w-100 mb-3">
            <form:option value="" label="Chọn nhà cung cấp"/>
            <form:options items="${suppliers}" itemValue="id" itemLabel="name"/>
        </form:select>
    </div>

    <div class="form-group">
        <form:label path="user" cssClass="form-label mt-3">Người đánh giá</form:label><br/>
        <form:select path="user" cssClass="w-100 mb-3">
            <form:option value="" label="Chọn người đánh giá"/>
            <form:options items="${users}" itemValue="id" itemLabel="username"/>
        </form:select>
    </div>

    <input type="submit" value="Thêm mới"/>
</form:form>