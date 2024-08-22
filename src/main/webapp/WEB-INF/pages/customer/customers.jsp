<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Danh sách khách hàng</h1>
        <a href="<c:url value="/admin/customers/add"/>" class="list__icon-add">
            <i class='bx bxs-user-plus'></i>
        </a>
    </div>
</div>

<div class="container mt-4">
    <table id="table" class="table table-striped">
        <thead>
            <tr>
                <th>ID</th>
                <th>Họ và tên</th>
                <th>Giới tinh</th>
                <th>Địa chỉ</th>
                <th>Số điện thoại</th>
                <th>Ngày tạo</th>
                <th>Ngày cập nhập</th>
                <th>Active</th>
                <th>Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="customer" items="${customers}">
                <tr id="item${customer.id}">
                    <td>${customer.id}</td>
                    <td>${customer.firstName}</td>
                    <td>
                        <c:if test="${customer.gender == true}">
                            Nữ
                        </c:if>
                        <c:if test="${customer.gender == false}">
                            Nam
                        </c:if>
                    </td>
                    <td>${customer.address}</td>
                    <td>${customer.phone}</td>
                    <td>
                        <fmt:parseDate value="${ customer.createdAt }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
                        <fmt:formatDate pattern="dd.MM.yyyy" value="${ parsedDateTime }"/>
                    </td>
                    <td>
                        <c:if test="${ customer.updatedAt != null }">
                            <fmt:parseDate value="${ customer.updatedAt }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedUpdatedDateTime" type="both"/>
                            <fmt:formatDate pattern="dd.MM.yyyy" value="${ parsedUpdatedDateTime }"/>
                        </c:if>
                        <c:if test="${ customer.updatedAt == null }">
                            Chưa cập nhập
                        </c:if>
                    </td>
                    <td>${customer.active}</td>
                    <td>
                        <a class="btn btn-primary btn-sm" href="<c:url value="/admin/customers/edit/${customer.id}"/>">
                            <i class='bx bxs-edit'></i>
                        </a>

                        <c:url value="/admin/customers/delete/${customer.id}" var="deleteCustomer"/>
                        <button class="btn btn-danger btn-sm" onclick="deleteItem('${deleteCustomer}', ${customer.id})">
                            <i class='bx bx-x'></i>
                        </button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
