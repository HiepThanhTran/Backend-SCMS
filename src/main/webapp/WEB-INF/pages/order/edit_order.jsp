<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url value="/admin/orders/edit/${order.id}" var="editOrder"/>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Chỉnh sửa đơn hàng</h1>
    </div>
</div>

<c:if test="${errors != null}">
    <c:forEach var="error" items="${errors}">
        <div class="alert alert-danger">
                ${error.message}
        </div>
    </c:forEach>
</c:if>

<form:form id="editOrderForm" method="post" modelAttribute="order" action="${editOrder}">
    <form:hidden path="id"/>
    
    <div class="form-group">
        <form:label path="type" cssClass="form-label mt-3">Loại đơn hàng</form:label><br/>
        <form:select path="type" cssClass="w-100 mb-3" id="typeSelect">
            <form:option value="" label="Chọn loại đơn hàng"/>
            <c:forEach items="${orderTypes}" var="type">
                <form:option value="${type['key']}" label="${type.value}"/>
            </c:forEach>
        </form:select>
    </div>

    <div class="form-group">
        <form:label path="status" cssClass="form-label mt-3">Trạng thái đơn hàng</form:label><br/>
        <form:select path="status" cssClass="w-100 mb-3">
            <form:option value="" label="Chọn trạng thái đơn hàng"/>
            <c:forEach items="${orderStatus}" var="status">
                <form:option value="${status['key']}" label="${status.value}"/>
            </c:forEach>
        </form:select>
    </div>

    <div id="inventoryInput" class="form-group"
         <c:if test="${order.type == 'INBOUND'}">style="display: block" </c:if>
         <c:if test="${order.type == 'OUTBOUND'}">style="display: none" </c:if>>
        <label for="inventoryId" class="form-label mt-3">Tồn kho</label><br/>
        <select id="inventoryId" name="inventoryId" class="w-100 mb-3">
            <option value="" label="Chọn tồn kho"></option>
            <c:forEach items="${inventories}" var="inventory">
                <option value="${inventory.id}" label="${inventory.name}"
                        <c:if test="${inventory.id == orderDetails[0].inventoryDetails.inventory.id}">selected</c:if>>
                </option>
            </c:forEach>
        </select>
    </div>

    <div class="form-group mt-3">
        <fmt:parseDate value="${ order.expectedDelivery }" pattern="yyyy-MM-dd" var="parsedDateTime"/>
        <fmt:formatDate pattern="yyyy-MM-dd" value="${ parsedDateTime }" var="scheduledDate"/>

        <form:label path="expectedDelivery" cssClass="form-label">Ngày giao hàng dự kiến</form:label>
        <form:input type="date" value="${scheduledDate}" name="expectedDelivery" path="expectedDelivery" cssClass="form-control"/>
    </div>

    <div class="form-group">
        <form:label path="user" cssClass="form-label mt-3">Người đặt hàng</form:label><br/>
        <form:select path="user" cssClass="w-100 mb-3">
            <form:option value="" label="Chọn người đặt hàng"/>
            <form:options items="${users}" itemValue="id" itemLabel="username"/>
        </form:select>
    </div>

    <div class="form-group">
        <label class="form-label mt-3 mb-1">Sản phẩm</label><br/>
        <button type="button" onclick="addProductRow()" id="addProductBtn" class="btn btn-success mb-3">Thêm sản phẩm</button>
        <table style="box-shadow: none;" id="orderDetailsTable" class="table table-striped display nowrap">
            <thead>
            <tr>
                <th>Tên sản phẩm</th>
                <th>Số lượng</th>
                <th>Hành động</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${orderDetails}" var="orderDetail">
                <tr>
                    <td>
                        <select name="productIds" class="form-control">
                            <c:forEach items="${products}" var="product">
                                <option value="${product.id}" <c:if test="${product.id == orderDetail.product.id}">selected</c:if>>${product.name}</option>
                            </c:forEach>
                        </select>
                    </td>

                    <td>
                        <input type="number" name="quantities" class="form-control" value="${orderDetail.quantity}"/>
                    </td>

                    <td>
                        <button type="button" class="btn btn-danger" onclick="deleteProductRow(this)">-</button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <input type="submit" class="mt-3" value="Chỉnh sửa"/>
</form:form>

<script>
    $(document).ready(function () {
        $('#orderDetailsTable').DataTable({
            info: false,
            paging: false,
            ordering: false,
            searching: false,
            "columns": [
                {"width": "50%"},
                {"width": "50%"},
                null,
            ],
            language: {
                url: "https://cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Vietnamese.json",
                zeroRecords: "Chưa chọn sản phẩm nào."
            },
        });
    });

    function addProductRow() {
        const table = $('#orderDetailsTable').DataTable();

        table.row.add([
            `<select name="productIds" class="form-control">
                <c:forEach items="${products}" var="product">
                    <option value="${product.id}">${product.name}</option>
                </c:forEach>
            </select>`,

            `<input type="number" value="1" name="quantities" class="form-control">`,

            `<button type="button" class="btn btn-danger" onclick="deleteProductRow(this)">-</button>`
        ]).node();

        table.draw();
    }

    function deleteProductRow(button) {
        const table = $('#orderDetailsTable').DataTable();
        table.row($(button).parents('tr')).remove().draw();
    }

    const typeSelect = document.getElementById("typeSelect");
    typeSelect.addEventListener('change', () => {
        const inventoryId = document.getElementById('inventoryInput');
        if (typeSelect.value === 'INBOUND') {
            inventoryId.style.display = 'block';
        } else {
            inventoryId.style.display = 'none';
        }
    });
</script>