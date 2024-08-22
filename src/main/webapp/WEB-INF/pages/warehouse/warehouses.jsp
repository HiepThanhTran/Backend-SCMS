<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Danh sách kho</h1>
        <a href="<c:url value="/admin/warehouses/add"/>" class="list__icon-add">
            <i class='bx bxs-plus-circle'></i>
        </a>
    </div>
</div>

<div class="container mt-4">
    <table id="table" class="table table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Tên</th>
            <th>Địa chỉ</th>
            <th>Dung tích</th>
            <th>Giá</th>
            <th>Ngày tạo</th>
            <th>Ngày cập nhập</th>
            <th>Active</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="warehouse" items="${warehouses}">
            <tr id="item${warehouse.id}">
                <td>${warehouse.id}</td>
                <td>${warehouse.name}</td>
                <td>${warehouse.location}</td>
                <td>${warehouse.capacity}</td>
                <td>${warehouse.cost}</td>
                <td>
                    <fmt:parseDate value="${ warehouse.createdAt }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
                    <fmt:formatDate pattern="dd.MM.yyyy" value="${ parsedDateTime }"/>
                </td>
                <td>
                    <c:if test="${ warehouse.updatedAt != null }">
                        <fmt:parseDate value="${ warehouse.updatedAt }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedUpdatedDateTime" type="both"/>
                        <fmt:formatDate pattern="dd.MM.yyyy" value="${ parsedUpdatedDateTime }"/>
                    </c:if>
                    <c:if test="${ warehouse.updatedAt == null }">
                        Chưa cập nhập
                    </c:if>
                </td>
                <td>${warehouse.active}</td>
                <td>
                    <a class="btn btn-primary btn-sm" href="<c:url value="/admin/warehouses/edit/${warehouse.id}"/>">
                        <i class='bx bxs-edit'></i>
                    </a>

                    <c:url value="/admin/warehouses/delete/${warehouse.id}" var="deleteWarehouses"/>
                    <button class="btn btn-danger btn-sm" onclick="deleteItem('${deleteWarehouses}', ${warehouse.id})">
                        <i class='bx bx-x'></i>
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>