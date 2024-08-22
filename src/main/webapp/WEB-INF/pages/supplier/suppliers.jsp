<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Danh sách nhà cung cấp</h1>
        <a href="<c:url value="/admin/suppliers/add"/>" class="list__icon-add">
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
            <th>Số điện thoại</th>
            <th>Liên hệ</th>
            <th>Ngày tạo</th>
            <th>Ngày cập nhập</th>
            <th>Active</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="supplier" items="${suppliers}">
            <tr id="item${supplier.id}">
                <td>${supplier.id}</td>
                <td>${supplier.name}</td>
                <td>${supplier.address}</td>
                <td>${supplier.phone}</td>
                <td>${supplier.contactInfo}</td>
                <td>
                    <fmt:parseDate value="${ supplier.createdAt }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
                    <fmt:formatDate pattern="dd.MM.yyyy" value="${ parsedDateTime }"/>
                </td>
                <td>
                    <c:if test="${ supplier.updatedAt != null }">
                        <fmt:parseDate value="${ supplier.updatedAt }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedUpdatedDateTime" type="both"/>
                        <fmt:formatDate pattern="dd.MM.yyyy" value="${ parsedUpdatedDateTime }"/>
                    </c:if>
                    <c:if test="${ supplier.updatedAt == null }">
                        Chưa cập nhập
                    </c:if>
                </td>
                <td>${supplier.active}</td>
                <td>
                    <a class="btn btn-primary btn-sm" href="<c:url value="/admin/suppliers/edit/${supplier.id}"/>">
                        <i class='bx bxs-edit'></i>
                    </a>

                    <c:url value="/admin/categories/delete/${category.id}" var="deleteCategory"/>
                    <button class="btn btn-danger btn-sm" onclick="deleteItem('${deleteCategory}', ${category.id})">
                        <i class='bx bx-x'></i>
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>