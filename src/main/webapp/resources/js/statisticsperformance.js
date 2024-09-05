$(document).ready(async () => {
    $("#supplierPerformanceTable").DataTable({
        columns: [null, null],
        "pageLength": 5,
        "lengthMenu": [[5, 10, 15, 20, 25, -1], [5, 10, 15, 20, 25, "Tất cả"]],
        language: {
            url: "https://cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Vietnamese.json"
        },
    });

    $("#ratingTable").DataTable({
        columns: [null, null, {searchable: false}, null, null],
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
        onSelect: async (year) => {
            const selectedRow = document.querySelector("#supplierPerformanceTable tbody tr.selected-item");
            if (selectedRow) {
                const supplierId = selectedRow.getAttribute("data-id");
                await loadSupplierPerformanceData(supplierId);
                await loadRatingData(supplierId);
            }
        }
    });

    if (supplierPerformanceLength > 0) {
        const table = document.querySelector('#supplierPerformanceTable');
        table.addEventListener('click', async (event) => {
            const target = event.target.closest('tbody > tr');
            if (target) {
                await selectSupplier(target);
            }
        });

        const firstRow = document.querySelector("#supplierPerformanceTable tbody tr[data-index='0']");
        await selectSupplier(firstRow);
    }
});

const selectSupplier = async (row) => {
    const allRows = document.querySelectorAll("#supplierPerformanceTable tbody tr");
    allRows.forEach(r => r.classList.remove("selected-item"));

    row.classList.add("selected-item");
    const supplierName = row.querySelector("td:nth-child(2)").textContent;
    const ratingHeader = document.querySelector("#ratingHeader");
    ratingHeader.parentElement.style.display = "block";
    ratingHeader.textContent = supplierName;

    const supplierId = row.getAttribute("data-id");
    showPreLoading();
    await loadSupplierPerformanceData(supplierId);
    await loadRatingData(supplierId);
    hidePreLoading();
}

const loadSupplierPerformanceData = async (supplierId) => {
    const year = document.querySelector("#yearpicker").value;
    try {
        const response = await axios.get(`${contextPath}/api/statistics/supplier/${supplierId}/performance?year=${year}`);
        updateChart(response.data);
    } catch (error) {
        console.error('Error loading supplier statistics performance data:', error);
    }
}

const loadRatingData = async (supplierId) => {
    const year = document.querySelector("#yearpicker").value;
    try {
        const response = await axios.get(`${contextPath}/api/ratings/?supplierId=${supplierId}&year=${year}`);
        updateRatingTable(response.data);
    } catch (error) {
        console.error('Error loading supplier ratings data:', error);
    }
}

const updateChart = (data) => {
    if (data === "") {
        resetChart();
        return;
    }

    if (revenueChartInstance) {
        const datasetsCost = data.cost.sort((a, b) => a.month - b.month).map(item => item.averageRating)
        const datasetsQuality = data.quality.sort((a, b) => a.month - b.month).map(item => item.averageRating)
        const datasetsDelivery = data.timelyDelivery.sort((a, b) => a.month - b.month).map(item => item.averageRating)

        revenueChartInstance.data.datasets[0].data = datasetsQuality;
        revenueChartInstance.data.datasets[1].data = datasetsDelivery;
        revenueChartInstance.data.datasets[2].data = datasetsCost;
        revenueChartInstance.update();
    }
}

const updateRatingTable = (data) => {
    const table = $("#ratingTable").DataTable();
    table.clear();

    data.forEach((rating, index) => {
        const rowNode = table.row.add([
            rating.id,
            rating.criteria,
            `${rating.rating} sao`,
            rating.content,
            rating.user.username
        ]).node();

        rowNode.setAttribute('data-index', index);
        rowNode.setAttribute('data-id', rating.id);
    });

    table.draw();
}

const resetChart = () => {
    revenueChartInstance.data.datasets[0].data = [];
    revenueChartInstance.data.datasets[1].data = [];
    revenueChartInstance.data.datasets[2].data = [];
    revenueChartInstance.update();
};