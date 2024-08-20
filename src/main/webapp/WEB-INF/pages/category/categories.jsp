<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Danh sách danh mục</h1>
        <a href="<c:url value="/admin/categories/add"/>" class="list__icon-add">
            <i class='bx bxs-cart-add'></i>
        </a>
    </div>
</div>

<div class="container mt-4">
    <table id="categoryTable" class="table table-striped">
        <thead>
            <tr>
                <th>ID</th>
                <th>Tên</th>
                <th>Mô tả</th>
                <th>Ngày tạo</th>
                <th>Active</th>
                <th>Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="category" items="${categories}">
                <tr id="item${category.id}">
                    <td>${category.id}</td>
                    <td>${category.name}</td>
                    <td>${category.description}</td>
                    <td>
                        <fmt:parseDate value="${ category.createdAt }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                        <fmt:formatDate pattern="dd.MM.yyyy" value="${ parsedDateTime }" />
                    </td>
                    <td>${category.active}</td>
                    <td>
                        <a class="btn btn-primary btn-sm" href="<c:url value="/admin/categories/edit/${category.id}"/>">
                            <i class='bx bxs-edit'></i>
                        </a>

                        <a class="btn btn-danger btn-sm" href="#" 
                           onclick="deleteItem('<c:url value="/admin/categories/delete/${category.id}"/>', ${category.id})">
                            <i class='bx bx-x'></i>
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
