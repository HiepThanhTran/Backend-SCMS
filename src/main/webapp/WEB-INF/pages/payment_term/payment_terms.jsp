<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Danh sách phương thức thanh toán</h1>
        <a href="<c:url value="/admin/payment_terms/add"/>" class="list__icon-add">
            <i class='bx bxs-plus-circle'></i>
        </a>
    </div>
</div>


<div class="container mt-4">
    <table class="table table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Số ngày hưởng CK</th>
            <th>Phần trăm CK</th>
            <th>Hình thức</th>
            <th>Shipper</th>
            <th>Ngày tạo</th>
            <th>Ngày cập nhập</th>
            <th>Active</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="payment_term" items="${payment_terms}">
            <tr id="item${payment_term.id}">
                <td>${payment_term.id}</td>
                <td>${payment_term.discountDays}</td>
                <td>${payment_term.discountPercentage}</td>
                <td>
                <td>
                    <c:if test="${payment_term.supplier != null}">
                        ${payment_term.shipper.name}
                    </c:if>
                    <c:if test="${payment_term.supplier == null}">
                        Không có shipper
                    </c:if>
                </td>
                </td>
                <td>
                    <fmt:parseDate value="${ payment_term.createdAt }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
                    <fmt:formatDate pattern="dd.MM.yyyy" value="${ parsedDateTime }"/>
                </td>
                <td>
                    <c:if test="${ payment_term.updatedAt != null }">
                        <fmt:parseDate value="${ payment_term.updatedAt }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedUpdatedDateTime" type="both"/>
                        <fmt:formatDate pattern="dd.MM.yyyy" value="${ parsedUpdatedDateTime }"/>
                    </c:if>
                    <c:if test="${ payment_term.updatedAt == null }">
                        Chưa cập nhập
                    </c:if>
                </td>
                <td>${payment_term.active}</td>
                <td>
                    <a class="btn btn-primary btn-sm" href="<c:url value="/admin/payment_terms/edit/${payment_term.id}"/>">
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