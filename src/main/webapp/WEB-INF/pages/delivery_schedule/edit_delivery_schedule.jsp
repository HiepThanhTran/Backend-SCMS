<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url value="/admin/delivery-schedules/edit/${deliverySchedule.id}" var="editDeliverySchedule"/>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Chỉnh sửa lịch giao hàng</h1>
    </div>
</div>

<c:if test="${errors != null}">
    <c:forEach var="error" items="${errors}">
        <div class="alert alert-danger">
                ${error.message}
        </div>
    </c:forEach>
</c:if>

<form:form id="addInventoryForm" method="post" modelAttribute="deliverySchedule" action="${editDeliverySchedule}">
    <form:hidden path="id"/>

    <div class="form-group">
        <fmt:parseDate value="${ deliverySchedule.scheduledDate }" pattern="yyyy-MM-dd" var="parsedDateTime"/>
        <fmt:formatDate pattern="yyyy-MM-dd" value="${ parsedDateTime }" var="scheduledDate"/>

        <form:label path="scheduledDate" cssClass="form-label">Ngày giao hàng</form:label>
        <form:input type="date" value="${scheduledDate}" name="scheduledDate" path="scheduledDate" cssClass="form-control"/>
    </div>

    <div class="form-group">
        <form:label path="method" cssClass="form-label mt-3">Phương thức giao hàng</form:label><br/>
        <form:select path="method" cssClass="w-100 mb-3">
            <form:option value="" label="Chọn phương thức giao hàng"/>
            <c:forEach items="${deliveryMethods}" var="method">
                <form:option value="${method['key']}" label="${method.value}"/>
            </c:forEach>
        </form:select>
    </div>

    <div class="form-group d-flex align-items-center">
        <form:label path="active" cssClass="form-label">Active:</form:label>
        <form:checkbox path="active" checked="${active}" class="ms-2"/>
    </div>

    <input type="submit" value="Cập nhật"/>
</form:form>