<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url value="/admin/products/edit/${product.id}" var="editProduct"/>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Chỉnh sửa sản phẩm</h1>
    </div>
</div>

<c:if test="${errors != null}">
    <c:forEach var="error" items="${errors}">
        <div class="alert alert-danger">
                ${error.message}
        </div>
    </c:forEach>
</c:if>

<form:form id="editProductForm" enctype="multipart/form-data" method="post" modelAttribute="product" action="${editProduct}">
    <form:hidden path="id"/>

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
        <c:if test="${product.image != null}">
            <img class="mt-3" src="${product.image}" alt="${product.image}" width="300"/>
        </c:if>
    </div>

    <div class="form-group">
        <fmt:parseDate value="${ product.expiryDate }" pattern="yyyy-MM-dd" var="parsedDateTime"/>
        <fmt:formatDate pattern="yyyy-MM-dd" value="${ parsedDateTime }" var="expiryDate"/>

        <form:label path="expiryDate" cssClass="form-label">Ngày hết hạn</form:label>
        <form:input type="date" value="${expiryDate}" name="expiryDate" path="expiryDate" placeholder="Nhập ngày hết hạn" cssClass="form-control"/>
    </div>

    <div class="form-group">
        <form:label path="supplier" cssClass="form-label mt-3">Nhà cung cấp</form:label><br/>
        <form:select path="supplier" cssClass="w-100 mt-1">
            <form:option value="" label="Chọn nhà cung cấp"/>
            <form:options items="${suppliers}" itemValue="id" itemLabel="name"/>
        </form:select>
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

    <div class="form-group d-flex align-items-center mt-3">
        <form:label path="active" cssClass="form-label">Active:</form:label>
        <form:checkbox path="active" checked="${active}" class="ms-2"/>
    </div>

    <div id="tagsSection" class="form-group">
        <label class="form-label mt-3">Nhãn</label><br/>
        <div id="tagsContainer">
            <div class="tag-row">
                <button type="button" class="btn btn-success" onclick="addTagRow()">+</button>
            </div>
            <c:forEach var="tag" items="${productTags}">
                <div style="display: flex; align-items: center; justify-content: center" class="tag-row">
                    <select name="tagIds" class="form-control mt-3 mb-2 me-3">
                        <c:forEach var="optionTag" items="${tags}">
                            <option value="${optionTag.id}"
                                    <c:if test="${optionTag.id == tag.id}">selected</c:if>>
                                    ${optionTag.name}
                            </option>
                        </c:forEach>
                    </select>
                    <button type="button" class="btn btn-danger mt-2" onclick="removeTagRow(this)">-</button>
                </div>
            </c:forEach>
        </div>
    </div>

    <input class="mt-3" type="submit" value="Cập nhật"/>
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