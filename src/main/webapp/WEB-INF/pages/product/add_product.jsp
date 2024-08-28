<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/admin/products/add" var="editProduct"/>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Thêm sản phẩm</h1>
    </div>
</div>

<c:if test="${errors != null}">
    <c:forEach var="error" items="${errors}">
        <div class="alert alert-danger">
                ${error.message}
        </div>
    </c:forEach>
</c:if>

<form:form id="addProductForm" enctype="multipart/form-data" method="post" modelAttribute="product" action="${editProduct}">
    <div class="form-group">
        <label class="form-label">Tên sản phẩm</label>
        <form:input type="text" name="name" path="name" placeholder="Nhập tên sản phẩm" cssClass="form-control"/><br/>
    </div>

    <div class="form-group">
        <label class="form-label">Mô tả</label>
        <form:input type="text" name="description" path="description" placeholder="Nhập mô tả" cssClass="form-control"/><br/>
    </div>

    <div class="form-group">
        <label class="form-label">Giá sản phẩm</label>
        <form:input type="text" name="price" path="price" placeholder="Nhập giá sản phẩm" cssClass="form-control"/><br/>
    </div>

    <div class="mb-1">
        <label for="file" class="form-label">Ảnh sản phẩm:</label>
        <form:input path="file" type="file" accept=".jpg,.png" class="form-control" id="file" name="file"/>
    </div>

    <div class="form-group">
        <form:label path="expiryDate" cssClass="form-label">Ngày hết hạn</form:label>
        <form:input type="date" name="expiryDate" path="expiryDate" placeholder="Nhập ngày hết hạn" cssClass="form-control"/>
    </div>

    <div class="form-group">
        <form:label path="unit" cssClass="form-label mt-3">Đơn vị sản phẩm</form:label><br/>
        <form:select path="unit" cssClass="w-100 mb-3">
            <form:option value="" label="Chọn đơn vị sản phẩm"/>
            <form:options items="${units}" itemValue="id" itemLabel="name"/>
        </form:select>
    </div>

    <div class="form-group">
        <form:label path="category" cssClass="form-label mt-3">Danh mục</form:label><br/>
        <form:select path="category" cssClass="w-100 mt-1">
            <form:option value="" label="Chọn danh mục"/>
            <form:options items="${categories}" itemValue="id" itemLabel="name"/>
        </form:select>
    </div>

    <div id="tagsSection" class="form-group">
        <label class="form-label mt-3">Nhãn</label><br/>
        <div id="tagsContainer">
            <div class="tag-row">
                <button type="button" class="btn btn-success" onclick="addTagRow()">+</button>
            </div>
        </div>
    </div>

    <input class="mt-3" type="submit" value="Thêm mới"/>
</form:form>

<script>
    function addTagRow() {
        const container = document.getElementById('tagsContainer');
        const row = document.createElement('div');
        row.className = 'tag-row';
        row.style.display = 'flex';
        row.style.alignItems = 'center';
        row.style.justifyContent = 'center';
        row.innerHTML = `
            <select name="tagIds" class="form-control mt-3 mb-2 me-3">
                <c:forEach var="unit" items="${tags}">
                    <option value="${unit.id}">${unit.name}</option>
                </c:forEach>
            </select>
            <button type="button" class="btn btn-danger mt-2" onclick="removeTagRow(this)">-</button>
        `;
        container.appendChild(row);
    }

    function removeTagRow(button) {
        button.parentElement.remove();
    }
</script>