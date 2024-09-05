$(document).ready(async () => {
    $("#recentlyOrderTable").DataTable({
        info: false,
        paging: false,
        ordering: false,
        searching: false,
        language: {
            url: "https://cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Vietnamese.json",
            zeroRecords: "Không có đơn hàng nào gần đây."
        },
    });

    totalAmountChart();
    totalOrdersChart();
    totalProductsChart();
    dashboardChart();

    // Cập nhật dữ liệu mỗi 1 phút
    // setInterval(async () => {
    //     try {
    //         const response = await axios.get(`${contextPath}/api/statistics/revenue/last-week`);
    //         updateUI(response.data);
    //     } catch (error) {
    //         console.error("Error fetching data:", error);
    //     }
    // }, 60000);

    // Cập nhật dữ liệu mỗi 5 giây
    // const fetchLatestData = async () => {
    //     try {
    //         const response = await axios.get(`${contextPath}/api/statistics/revenue/last-week`);
    //         updateUI(response.data);
    //
    //         await fetchLatestData();
    //     } catch (error) {
    //         console.error("Error fetching data:", error);
    //         setTimeout(fetchLatestData, 5000);
    //     }
    // }
    // await fetchLatestData();
});

const generateChart = (ctx, type, data, color) => {
    return new Chart(ctx, {
        type: type,
        data: {
            datasets: [{
                data: [data, 100 - data < 0 ? 0 : 100 - data],
                borderWidth: 0,
                backgroundColor: ["#f6f6f9", color],
                borderRadius: 20,
                cutout: "80%",
            }]
        },
        plugins: [centerTextInDoughnut],
        options: {
            responsive: true,
            plugins: {
                tooltip: {
                    enabled: false,
                },
            },
        },
    })
}

const centerTextInDoughnut = {
    beforeDraw: (chart) => {
        const {ctx, data} = chart;
        const xCenter = chart.getDatasetMeta(0).data[0].x;
        const yCenter = chart.getDatasetMeta(0).data[0].y;

        ctx.save();

        ctx.font = "bold 20px Arial";
        ctx.textAlign = "center";
        ctx.textBaseline = "middle";

        ctx.shadowColor = "rgba(255, 255, 255, 0.8)";
        ctx.shadowBlur = 20;
        ctx.shadowOffsetX = 1;
        ctx.shadowOffsetY = 1;

        ctx.fillStyle = "rgba(255, 255, 255, 0.8)";
        ctx.fillText(data.datasets[0].data[0].toFixed(2) + "%", xCenter, yCenter);

        ctx.restore();
    },
};

const totalAmountChart = () => {
    const totalAmountCurrentWeekNum = parseFloat(totalAmountCurrentWeek);
    const totalAmountLastWeekNum = parseFloat(totalAmountLastWeek);
    let dataTotalAmountChart = totalAmountCurrentWeekNum / totalAmountLastWeekNum * 100;
    const totalAmountChart = document.getElementById("totalAmountChart").getContext("2d")

    dataTotalAmountChart =  isNaN(dataTotalAmountChart) ? 0 : dataTotalAmountChart;

    const infoPercentageRevenue = document.getElementById("info-per-revenue");
    if (dataTotalAmountChart > 100) {
        infoPercentageRevenue.innerText = "Tăng " + (dataTotalAmountChart - 100).toFixed(2) + "% so với tuần trước";
    } else {
        infoPercentageRevenue.innerText = "Giảm " + (100 - dataTotalAmountChart).toFixed(2) + "% so với tuần trước";
    }

    return generateChart(totalAmountChart, "doughnut", dataTotalAmountChart, "#d35f67")
}

const totalOrdersChart = () => {
    const totalOrdersCurrentWeekNum = parseFloat(totalOrdersCurrentWeek);
    const totalOrdersLastWeekNum = parseFloat(totalOrdersLastWeek);
    let dataTotalOrdersChart = totalOrdersCurrentWeekNum / totalOrdersLastWeekNum * 100;
    const totalOrdersChart = document.getElementById("totalOrdersChart").getContext("2d")

    dataTotalOrdersChart = isNaN(dataTotalOrdersChart) ? 0 : dataTotalOrdersChart;

    const infoPercentageOrders = document.getElementById("info-per-orders");
    if (dataTotalOrdersChart > 100) {
        infoPercentageOrders.innerText = "Tăng " + (dataTotalOrdersChart - 100).toFixed(2) + "% so với tuần trước";
    } else {
        infoPercentageOrders.innerText = "Giảm " + (100 - dataTotalOrdersChart).toFixed(2) + "% so với tuần trước";
    }

    return generateChart(totalOrdersChart, "doughnut", dataTotalOrdersChart, "#277fc4")
}

const totalProductsChart = () => {
    const totalProductsCurrentWeekNum = parseFloat(totalProductsCurrentWeek);
    const totalProductsLastWeekNum = parseFloat(totalProductsLastWeek);
    let dataTotalProductsChart = totalProductsCurrentWeekNum / totalProductsLastWeekNum * 100;
    const totalProductsChart = document.getElementById("totalProductsChart").getContext("2d")

    dataTotalProductsChart = isNaN(dataTotalProductsChart) ? 0 : dataTotalProductsChart;

    const infoPercentageProducts = document.getElementById("info-per-products");
    if (dataTotalProductsChart > 100) {
        infoPercentageProducts.innerText = "Tăng " + (dataTotalProductsChart - 100).toFixed(2) + "% so với tuần trước";
    } else {
        infoPercentageProducts.innerText = "Giảm " + (100 - dataTotalProductsChart).toFixed(2) + "% so với tuần trước";
    }


    return generateChart(totalProductsChart, "doughnut", dataTotalProductsChart, "#24b4a1")
}

const dashboardChart = () => {
    const dashboardChart = document.getElementById("dashboardChart").getContext("2d");
    const formatter = new Intl.DateTimeFormat('vi', {day: '2-digit', month: '2-digit', year: 'numeric'});
    const {monday, sunday} = getMondayAndSunday(new Date());
    const formattedMonday = formatter.format(monday);
    const formattedSunday = formatter.format(sunday);

    return new Chart(dashboardChart, {
        type: "line",
        data: {
            labels: ["Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ nhật"],
            datasets: [{
                label: "Doanh thu",
                data: dataDashboardChart,
                borderColor: "#007bff",
                yAxisID: "y",
                borderWidth: 2,
                fill: true,
                tension: 0.4,
                pointStyle: "star",
                pointRadius: 6,
            },]
        },
        options: {
            responsive: true,
            interaction: {
                intersect: false,
                mode: "index",
            },
            plugins: {
                title: {
                    display: true,
                    text: `Doanh thu các ngày trong tuần này (${formattedMonday} - ${formattedSunday})`,
                    font: {
                        size: 24,
                    }
                },
                legend: {
                    display: false,
                },
                tooltip: {
                    usePointStyle: true,
                    callbacks: {
                        title: (tooltipItem) => {
                            const day = tooltipItem[0].dataIndex + 2 === 8 ? "chủ nhật" : "thứ " + (tooltipItem[0].dataIndex + 2);
                            return "Doanh thu " + day;
                        },
                        label: (tooltipItem) => {
                            let label = tooltipItem.dataset.label || "";

                            if (label) {
                                label += ": ";
                            }

                            if (tooltipItem.parsed.y !== null) {
                                label += tooltipItem.parsed.y.toLocaleString('vi-VN') + " VNĐ";
                            }

                            return label;
                        }
                    }
                },
            },
        },
    });
}

