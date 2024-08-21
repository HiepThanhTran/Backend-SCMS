<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Danh sách hàng tồn kho</h1>
        <a href="<c:url value="/admin/inventories/add"/>" class="list__icon-add">
            <i class='bx bxs-plus-circle'></i>
        </a>
    </div>
</div>

<div class="container mt-4">
    <table id="categoryTable" class="table table-striped">
        <thead>
            <tr>
                <th>ID</th>
                <th>Tên</th>
                <th>Kho</th>
                <th>Ngày tạo</th>
                <th>Ngày cập nhập</th>
                <th>Active</th>
                <th>Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="inventory" items="${inventories}">
                <tr id="item${inventory.id}">
                    <td>${inventory.id}</td>
                    <td>${inventory.name}</td>
                    <td>${inventory.warehouse.name}</td>
                    <td>
                        <fmt:parseDate value="${ inventory.createdAt }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
                        <fmt:formatDate pattern="dd.MM.yyyy" value="${ parsedDateTime }"/>
                    </td>
                    <td>
                        <c:if test="${ inventory.updatedAt != null }">
                            <fmt:parseDate value="${ inventory.updatedAt }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedUpdatedDateTime" type="both"/>
                            <fmt:formatDate pattern="dd.MM.yyyy" value="${ parsedUpdatedDateTime }"/>
                        </c:if>
                        <c:if test="${ inventory.updatedAt == null }">
                            Chưa cập nhập
                        </c:if>
                    </td>
                    <td>${inventory.active}</td>
                    <td>
                        <a class="btn btn-primary btn-sm" href="<c:url value="/admin/inventories/edit/${inventory.id}"/>">
                            <i class='bx bxs-edit'></i>
                        </a>

                        <c:url value="/admin/inventories/delete/${inventory.id}" var="deleteInventory"/>
                        <button class="btn btn-danger btn-sm" onclick="deleteItem('${deleteInventory}', ${inventory.id})">
                            <i class='bx bx-x'></i>
                        </button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>