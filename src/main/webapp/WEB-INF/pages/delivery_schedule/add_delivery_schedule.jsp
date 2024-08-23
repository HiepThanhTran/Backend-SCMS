<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/admin/schedules/add" var="addDeliverySchedule"/>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Thêm lịch giao hàng</h1>
    </div>
</div>

<c:if test="${errors != null}">
    <c:forEach var="error" items="${errors}">
        <div class="alert alert-danger">
                ${error.message}
        </div>
    </c:forEach>
</c:if>

<form:form id="addDeliveryScheduleForm" method="post" modelAttribute="deliverySchedule" action="${addDeliverySchedule}">
    <div class="form-group">
        <form:label path="scheduledDate" cssClass="form-label">Ngày giao hàng</form:label>
        <form:input type="date" name="scheduledDate" path="scheduledDate" placeholder="Nhập ngày giao hàng" cssClass="form-control"/>
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

    <input type="submit" value="Thêm mới"/>
</form:form>