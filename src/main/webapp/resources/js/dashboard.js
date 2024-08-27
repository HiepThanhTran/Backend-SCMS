$(document).ready(async () => {
    $("#recentlyOrderTable").DataTable({
        info: false,
        paging: false,
        ordering: false,
        searching: false,
        language: {
            url: "https://cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Vietnamese.json",
            zeroRecords: 'Không có đơn hàng nào gần đây.'
        },
    });

    setInterval(async () => {
        try {
            const response = await axios.get(`${contextPath}/api/statistics/revenue/last-24-hours`);
            updateUI(response.data);
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    }, 60000);

    // const fetchLatestData = async () => {
    //     try {
    //         const response = await axios.get(`${contextPath}/api/statistics/revenue/last-24-hours`);
    //         updateUI(response.data);
    //
    //         await fetchLatestData();
    //     } catch (error) {
    //         console.error('Error fetching data:', error);
    //         setTimeout(fetchLatestData, 5000);
    //     }
    // }
    // await fetchLatestData();
});

const updateUI = (data) => {
    const newRevenue = data.revenue;
    const newOrderCount = data.orderCount;

    console.info('New revenue:', newRevenue);
    console.info('New order count:', newOrderCount);

    $('.totalRevenues').text(newRevenue.toLocaleString('vi-VN', {style: 'currency', currency: 'VND'}));
    $('.totalOrders').text(newOrderCount);

    revenueLast24HoursNum = parseFloat(newRevenue);
    dataChart1 = revenueLast24HoursNum / revenueLastWeekNum * 100;
    chart1Instance.data.datasets[0].data = [dataChart1, 100 - dataChart1];
    chart1Instance.update();

    let orderCountLast24HoursNum = parseFloat(newOrderCount);
    dataChart2 = orderCountLast24HoursNum / orderCountLastWeekNum * 100;
    chart2Instance.data.datasets[0].data = [dataChart2, 100 - dataChart2];
    chart2Instance.update();
}
