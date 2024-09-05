<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<h2 class="mb-4 mt-5 text-center">Thống kê hiệu suất của nhà cung cấp</h2>
<hr>
<label class="text-dark h5 mb-4" for="yearpicker">Chọn năm:</label>
<input style="border-width: 0; padding: 4px 12px 4px 12px; border-radius: 8px; max-width: 66px;"
       type="text"
       id="yearpicker"
       class="yearpicker"
       value=""
       readonly>

<div class="row">
    <div style="margin-top: 12px;" class="col-md-4 col-12">
        <table id="supplierPerformanceTable" class="table table-bordered table-hover display nowrap">
            <thead>
            <tr>
                <th style="padding: 12px !important;">Id</th>
                <th style="padding: 12px !important;">Tên nhà cung cấp</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${suppliers}" var="supplier" varStatus="loop">
                <tr style="cursor: pointer" data-index="${loop.index}" data-id="${supplier.id}">
                    <td style="padding: 12px !important;">${supplier.id}</td>
                    <td style="padding: 12px !important;">${supplier.name}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="col-md-8 col-12">
        <canvas id="supplierPerformanceChart"></canvas>
    </div>
</div>
<hr>

<h2 class="mt-5 text-center" style="display: none">Danh sách đánh giá của <span id="ratingHeader"></span></h2>
<div class="row">
    <div class="col-md-12 col-12">
        <table id="ratingTable" class="table table-bordered table-hover display nowrap">
            <thead>
            <tr>
                <th>Id</th>
                <th>Tiêu chí đánh giá</th>
                <th>Điểm đánh giá</th>
                <th>Nội dung</th>
                <th>Người đánh giá</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>

<script src="<c:url value="/js/statisticsperformance.js"/>"></script>
<script>
    const supplierPerformanceLength = ${fn:length(suppliers)};

    // Khởi tạo biểu đồ với dữ liệu rỗng
    const chartCanvas = document.getElementById('supplierPerformanceChart').getContext('2d');
    const chartInstance = new Chart(chartCanvas, {
        type: 'line',
        data: {
            labels: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6',
                'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'],
            datasets: [
                {
                    label: 'Chất lượng sản phẩm',
                    data: [],
                    borderColor: 'rgb(75,192,128)',
                    yAxisID: 'y',
                    borderWidth: 2,
                    fill: true,
                    tension: 0.4,
                    pointStyle: 'triangle',
                    pointRadius: 6,
                },
                {
                    label: 'Giao hàng đúng hạn',
                    data: [],
                    borderColor: 'rgb(255, 99, 132)',
                    yAxisID: 'y',
                    borderWidth: 2,
                    fill: true,
                    tension: 0.4,
                    pointStyle: 'star',
                    pointRadius: 6,
                },
                {
                    label: 'Giá cả sản phẩm',
                    data: [],
                    borderColor: 'rgb(75, 192, 192)',
                    yAxisID: 'y',
                    borderWidth: 2,
                    fill: true,
                    tension: 0.4,
                    pointStyle: 'circle',
                    pointRadius: 6,
                },
            ]
        },
        options: {
            responsive: true,
            interaction: {
                intersect: false,
                mode: 'index',
            },
            plugins: {
                title: {
                    display: true,
                    text: 'Điểm đánh giá trung bình từng tháng theo các tiêu chí',
                    font: {
                        size: 20
                    }
                },
                legend: {
                    labels: {
                        font: {
                            size: 16
                        }
                    }
                },
                tooltip: {
                    usePointStyle: true,
                    callbacks: {
                        title: (tooltipItem) => {
                            return "Điểm đánh giá trung bình tháng " + (tooltipItem[0].dataIndex + 1);
                        },
                        label: (tooltipItem) => {
                            let label = tooltipItem.dataset.label || '';

                            if (label) {
                                label += ': ';
                            }
                            if (tooltipItem.parsed.y !== null) {
                                label += tooltipItem.parsed.y + ' điểm';
                            }
                            return label;
                        }
                    }
                },
            },
            scales: {
                y: {
                    beginAtZero: true,
                    display: true,
                    max: 6,
                    title: {
                        display: true,
                        text: 'Điểm đánh giá',
                        color: '#191',
                        font: {
                            family: 'Times',
                            size: 20,
                            style: 'normal',
                            lineHeight: 1.2
                        },
                        padding: {top: 30, left: 0, right: 0, bottom: 0}
                    }
                },
            },
        },
    });
</script>
