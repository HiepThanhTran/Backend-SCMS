<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Danh sách hóa đơn</h1>
    </div>
</div>

<div class="container mt-4">
    <table id="table" class="table table-striped display nowrap">
        <thead>
        <tr>
            <th>ID</th>
            <th>Tổng tiền</th>
            <th>Trạng thái</th>
            <th>Người dùng</th>
            <th>Ngày tạo</th>
            <th>Ngày cập nhập</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${invoices}" var="invoice">
            <tr id="item${invoice.id}">
                <td>${invoice.id}</td>
                <td>
                    <fmt:formatNumber value="${invoice.totalAmount}" type="currency" currencySymbol="₫" groupingUsed="true"
                                      var="formattedTotalAmount"/>
                        ${formattedTotalAmount}
                </td>
                <td>
                    <c:choose>
                        <c:when test="${ invoice.paid }">
                            <span class="badge bg-success">Đã thanh toán</span>
                        </c:when>
                        <c:when test="${ !invoice.paid }">
                            <span class="badge bg-danger">Chưa thanh toán</span>
                        </c:when>
                    </c:choose>
                </td>
                <td>${invoice.user.username}</td>
                <td>
                    <fmt:parseDate value="${ invoice.createdAt }" pattern="yyyy-MM-dd HH:mm:ss" var="parsedDateTime" type="both"/>
                    <fmt:formatDate pattern="dd-MM-yyyy" value="${ parsedDateTime }"/>
                </td>
                <td>
                    <c:if test="${ invoice.updatedAt != null }">
                        <fmt:parseDate value="${ invoice.updatedAt }" pattern="yyyy-MM-dd HH:mm:ss" var="parsedUpdatedDateTime" type="both"/>
                        <fmt:formatDate pattern="dd-MM-yyyy" value="${ parsedUpdatedDateTime }"/>
                    </c:if>
                    <c:if test="${ invoice.updatedAt == null }">
                        Chưa cập nhập
                    </c:if>
                </td>
                <td>
                    <a class="btn btn-primary btn-sm" href="<c:url value="/admin/invoices/edit/${invoice.id}"/>">
                        <i class='bx bxs-edit'></i>
                    </a>

                    <c:url value="/admin/invoices/delete/${invoice.id}" var="deleteInvoice"/>
                    <button class="btn btn-danger btn-sm" onclick="deleteItem('${deleteInvoice}', ${invoice.id})">
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
            columns: [null, {searchable: false}, null, null, {searchable: false}, {searchable: false}, {searchable: false}],
            language: {
                url: "https://cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Vietnamese.json"
            },
        });
    });
</script>