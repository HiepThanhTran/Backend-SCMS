<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Danh sách người dùng</h1>
    </div>
</div>

<div class="container mt-4">
    <table id="table" class="table table-striped display nowrap">
        <thead>
        <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Vai trò</th>
            <th>Xác thực</th>
            <th>Lần cuối đăng nhập</th>
            <th>Ngày tạo</th>
            <th>Ngày cập nhập</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users}">
            <tr id="item${user.id}">
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.userRole.getDisplayName()}</td>
                <td>
                    <c:if test="${ user.confirm == true }">
                        <span class="badge bg-success">Đã xác thực</span>
                    </c:if>
                    <c:if test="${ user.confirm == false }">
                        <span class="badge bg-danger">Chưa xác thực</span>
                    </c:if>
                </td>
                <td>
                    <c:if test="${ user.lastLogin != null }">
                        <fmt:parseDate value="${ user.lastLogin }" pattern="yyyy-MM-dd HH:mm:ss" var="parsedLastLoginDateTime" type="both"/>
                        <fmt:formatDate pattern="dd-MM-yyyy" value="${ parsedLastLoginDateTime }"/>
                    </c:if>
                    <c:if test="${ user.lastLogin == null }">
                        Chưa đăng nhập
                    </c:if>
                </td>
                <td>
                    <fmt:parseDate value="${ user.createdAt }" pattern="yyyy-MM-dd HH:mm:ss" var="parsedDateTime" type="both"/>
                    <fmt:formatDate pattern="dd-MM-yyyy" value="${ parsedDateTime }"/>
                </td>
                <td>
                    <c:if test="${ user.updatedAt != null }">
                        <fmt:parseDate value="${ user.updatedAt }" pattern="yyyy-MM-dd HH:mm:ss" var="parsedUpdatedDateTime" type="both"/>
                        <fmt:formatDate pattern="dd-MM-yyyy" value="${ parsedUpdatedDateTime }"/>
                    </c:if>
                    <c:if test="${ user.updatedAt == null }">
                        Chưa cập nhập
                    </c:if>
                </td>
                <td>
                    <a class="btn btn-primary btn-sm" href="<c:url value="/admin/users/edit/${user.id}"/>">
                        <i class='bx bxs-edit'></i>
                    </a>

                    <c:url value="/admin/users/delete/${user.id}" var="deleteCustomer"/>
                    <button class="btn btn-danger btn-sm" onclick="deleteItem('${deleteCustomer}', ${user.id})">
                        <i class='bx bx-x'></i>
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script>
    $(document).ready(function () {
        $('#table').DataTable({
            columns: [null, null, null, {searchable: false}, {searchable: false}, {searchable: false}, {searchable: false}, {searchable: false}],
            language: {
                url: "https://cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Vietnamese.json"
            },
        });
    });
</script>