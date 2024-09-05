$(document).ready(async () => {
    $('#revenueTable').DataTable({
        "pageLength": 5,
        "lengthMenu": [[5, 10, 15, 20, 25, -1], [5, 10, 15, 20, 25, "Tất cả"]],
        language: {
            url: "https://cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Vietnamese.json"
        },
    });

    $("#yearpicker").yearpicker({
        year: new Date().getFullYear(),
        startYear: 1900,
        endYear: 2100,
        onSelect: async (year) => await fetchData()
    });

    await fetchData();
});

const fetchData = async () => {
    const filter = document.getElementById('filter').value;
    const year = document.getElementById("yearpicker").value;

    showPreLoading();
    try {
        const revenueChartData = await axios.get(`${contextPath}/api/statistics/revenue/${filter}/?year=${year}`);
        updateRevenueChart(revenueChartData.data);

        const productData = await axios.get(`${contextPath}/api/statistics/revenue/${filter}/products/?year=${year}`);
        updateTable(productData.data);
        updateProductChart(productData.data);

        const categoryData = await axios.get(`${contextPath}/api/statistics/revenue/${filter}/categories/?year=${year}`);
        updateCategoryChart(categoryData.data);
    } catch (error) {
        console.error('Error loading statistics revenue data:', error);
    } finally {
        hidePreLoading();
    }
}

let revenueChartInstance;

const updateRevenueChart = (chartData) => {
    const ctx = document.getElementById('revenueChart').getContext('2d');
    const filter = document.getElementById('filter').value;
    const title = filter === 'month' ? 'tháng' : 'quý';

    if (revenueChartInstance) {
        revenueChartInstance.destroy();
    }

    revenueChartInstance = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: chartData.labels,
            datasets: [{
                label: 'Doanh thu',
                data: chartData.data,
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1
            }]
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
                    text: `Doanh thu theo ${title}`,
                    font: {
                        size: 32
                    }
                },
                legend: {
                    display: false,
                },
                tooltip: {
                    usePointStyle: true,
                    callbacks: {
                        label: (tooltipItem) => {
                            let label = tooltipItem.dataset.label || '';

                            if (label) {
                                label += ': ';
                            }
                            if (tooltipItem.parsed.y !== null) {
                                label += tooltipItem.parsed.y.toLocaleString('vi-VN') + ' VNĐ';
                            }

                            return label;
                        }
                    }
                },
            },
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}

const updateTable = (tableData) => {
    const table = $('#revenueTable').DataTable();
    table.clear().draw();

    tableData.forEach(function (item) {
        table.row.add([
            item.productId,
            item.productName,
            item.time,
            item.revenue.toLocaleString('vi-VN', {currency: "VND", style: "currency"})
        ]).draw();
    });
}

let productChartInstance;

const updateProductChart = (chartData) => {
    const ctx = document.getElementById('productChart').getContext('2d');

    if (productChartInstance) {
        productChartInstance.destroy();
    }

    chartData.sort((a, b) => b.revenue - a.revenue);

    const topN = 10;
    const topData = chartData.slice(0, topN);

    const labels = [];
    const data = [];
    const colors = [];

    topData.forEach((item) => {
        labels.push(item.productName);
        data.push(item.revenue);
        colors.push(generateRandomColor().color);
    });

    productChartInstance = new Chart(ctx, {
        type: 'pie',
        data: {
            labels: labels,
            datasets: [{
                label: 'Doanh thu',
                data: data,
                borderWidth: 1,
                backgroundColor: colors
            }]
        },
        options: {
            responsive: true,
            plugins: {
                title: {
                    display: true,
                    text: 'Top ' + topN + ' sản phẩm có doanh thu cao nhất',
                    font: {
                        size: 20
                    }
                },
                legend: {
                    position: 'bottom',
                    labels: {
                        font: {
                            size: 16
                        }
                    }
                },
                tooltip: {
                    usePointStyle: true,
                    callbacks: {
                        label: (tooltipItem) => {
                            let label = tooltipItem.label || '';
                            let value = tooltipItem.raw || 0;

                            if (label) {
                                label += ': ';
                            }
                            label += value.toLocaleString('vi-VN') + ' VNĐ';

                            return label;
                        }
                    }
                },
            },
            scales: {
                x: {
                    display: false,
                },
                y: {
                    display: false,
                },
            },
        }
    });
}

let categoryChartInstance;

const updateCategoryChart = (chartData) => {
    const ctx = document.getElementById('categoryChart').getContext('2d');

    if (categoryChartInstance) {
        categoryChartInstance.destroy();
    }

    const labels = [];
    const data = [];
    const colors = [];

    chartData.forEach((item) => {
        labels.push(item.categoryName);
        data.push(item.revenue);
        colors.push(generateRandomColor().color);
    });

    categoryChartInstance = new Chart(ctx, {
        type: 'pie',
        data: {
            labels: labels,
            datasets: [{
                label: 'Doanh thu',
                data: data,
                borderWidth: 1,
                backgroundColor: colors
            }]
        },
        options: {
            responsive: true,
            plugins: {
                title: {
                    display: true,
                    text: 'Top 10 danh mục có doanh thu cao nhất',
                    font: {
                        size: 20
                    }
                },
                legend: {
                    position: 'bottom',
                    labels: {
                        font: {
                            size: 16
                        }
                    }
                },
                tooltip: {
                    usePointStyle: true,
                    callbacks: {
                        label: (tooltipItem) => {
                            let label = tooltipItem.label || '';
                            let value = tooltipItem.raw || 0;

                            if (label) {
                                label += ': ';
                            }
                            label += value.toLocaleString('vi-VN') + ' VNĐ';

                            return label;
                        }
                    }
                },
            },
            scales: {
                x: {
                    display: false,
                },
                y: {
                    display: false,
                },
            },
        }
    });
}