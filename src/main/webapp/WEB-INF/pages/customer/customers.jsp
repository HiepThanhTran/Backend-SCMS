<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container list">
    <div class="d-flex justify-content-between align-items-center">
        <h1 class="text-center list__title">Danh sách khách hàng</h1>
        <a href="<c:url value="/admin/customers/add"/>" class="list__icon-add">
            <i class='bx bxs-user-plus'></i>
        </a>
    </div>
</div>

