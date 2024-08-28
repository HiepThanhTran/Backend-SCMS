<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/admin/orders/add" var="addOrder"/>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Thêm đơn hàng</h1>
    </div>
</div>

<c:if test="${errors != null}">
    <c:forEach var="error" items="${errors}">
        <div class="alert alert-danger">
                ${error.message}
        </div>
    </c:forEach>
</c:if>

<form:form id="addOrderForm" method="post" modelAttribute="order" action="${addOrder}">
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

    <div style="display: none;" id="inventoryInput" class="form-group">
        <label for="inventoryId" class="form-label mt-3">Tồn kho</label><br/>
        <select id="inventoryId" name="inventoryId" class="w-100 mb-3">
            <option value="" label="Chọn tồn kho"></option>
            <c:forEach items="${inventories}" var="inventory">
                <option value="${inventory.id}" label="${inventory.name}"></option>
            </c:forEach>
        </select>
    </div>

    <div class="form-group mt-3">
        <form:label path="expectedDelivery" cssClass="form-label">Ngày giao hàng dự kiến</form:label>
        <form:input type="date" name="expectedDelivery" path="expectedDelivery" cssClass="form-control"/>
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

            </tbody>
        </table>
    </div>

    <input type="submit" class="mt-3" value="Thêm mới"/>
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

            `<input type="number" name="quantities" value="1" class="form-control">`,

            `<button type="button" class="btn btn-danger" onclick="deleteProductRow(this)">-</button>`
        ]);

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