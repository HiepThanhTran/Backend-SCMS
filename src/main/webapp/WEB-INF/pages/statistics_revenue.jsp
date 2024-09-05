<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<h2 class="mb-4 mt-5 text-center">Thống kê doanh thu</h2>
<hr>
<div class="filters">
    <label for="yearpicker">Chọn năm:</label>
    <input style="border-width: 0; padding: 4px 12px 4px 12px; border-radius: 8px; max-width: 66px;"
           type="text"
           id="yearpicker"
           class="yearpicker"
           value=""
           readonly>

    <label for="filter">Lọc theo:</label>
    <select id="filter" onchange="fetchData()" style="border-width: 0; padding: 8px; background: #fff; border-radius: 8px">
        <option value="month">Tháng</option>
        <option value="quarter">Quý</option>
    </select>
</div>

<canvas id="revenueChart" class="mb-5" width="900" height="380"></canvas>

<h2 class="mb-4 mt-5 text-center">Bảng thống kê doanh thu từng sản phẩm</h2>
<hr/>
<table id="revenueTable" class="display">
    <thead>
    <tr>
        <th>ID</th>
        <th>Tên sản phẩm</th>
        <th>Thời gian</th>
        <th>Doanh thu</th>
    </tr>
    </thead>
    <tbody>
    </tbody>
</table>

<h2 class="mb-4 mt-5 text-center">Biểu đồ thống kê doanh thu theo sản phẩm và loại sản phẩm</h2>
<hr/>
<div class="row">
    <div class="col-md-6 col-12">
        <canvas id="productChart"></canvas>
    </div>
    <div class="col-md-6 col-12">
        <canvas id="categoryChart"></canvas>
    </div>
</div>

<script src="<c:url value="/js/statisticsrevenue.js"/>"></script>