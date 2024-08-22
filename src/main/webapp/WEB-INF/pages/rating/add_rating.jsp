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
        <label class="form-label">Đánh giá</label>
        <form:input type="numberDecimal" name="rating" path="rating" placeholder="Nhập đánh giá" cssClass="form-control"/><br/>
    </div>

    <div class="form-group">
        <label class="form-label">Nội dung</label>
        <form:input type="text" name="content" path="content" placeholder="Nhập nội dung" cssClass="form-control"/><br/>
    </div>
    <input type="submit" value="Thêm"/>
</form:form>