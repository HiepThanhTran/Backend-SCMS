let warehouseName = "";
let inventoryName = "";

$(document).ready(async () => {
    initializeDataTable('#warehouseTable', [null, null, {searchable: false}, {searchable: false}]);
    initializeDataTable('#inventoryTable', [null, null, {searchable: false}]);
    initializeDataTable('#productExpiringSoonTable', [null, null, {searchable: false}, {searchable: false}, {searchable: false}]);
    initializeDataTable('#productExpiredTable', [null, null, {searchable: false}, {searchable: false}, {searchable: false}]);

    if (warehouseLength > 0) {
        setupTableClickEvent('#warehouseTable', selectWarehouse);
        setupTableClickEvent('#inventoryTable', selectInventory);

        const firstRow = document.querySelector('#warehouseTable tbody tr[data-index="0"]');
        handleRowSelection('#warehouseTable', firstRow);
        warehouseName = getRowData(firstRow, 2);
        const warehouseId = firstRow.getAttribute('data-id');
        await loadInventoryData(warehouseId);
    }
});

const initializeDataTable = (selector, columnSettings) => {
    $(selector).DataTable({
        columns: columnSettings,
        "pageLength": 5,
        "lengthMenu": [[5, 10, 15, 20, 25, -1], [5, 10, 15, 20, 25, "Tất cả"]],
        language: {
            url: "https://cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Vietnamese.json"
        },
    });
};

const setupTableClickEvent = (tableSelector, callback) => {
    const table = document.querySelector(tableSelector);
    table.addEventListener('click', async (event) => {
        const target = event.target.closest('tbody > tr');
        if (target) {
            showPreLoading();
            await callback(target);
            hidePreLoading();
        }
    });
};

const selectWarehouse = async (row) => {
    handleRowSelection('#warehouseTable', row);
    warehouseName = getRowData(row, 2);
    const warehouseId = row.getAttribute('data-id');
    await loadInventoryData(warehouseId);
    scrollToElement('#inventoryHeader');
};

const selectInventory = async (row) => {
    handleRowSelection('#inventoryTable', row);
    inventoryName = getRowData(row, 2);
    const inventoryId = row.getAttribute('data-id');
    await loadProductData(inventoryId);
    scrollToElement('#chartHeader');
};

const handleRowSelection = (tableSelector, row) => {
    const allRows = document.querySelectorAll(`${tableSelector} tbody tr`);
    allRows.forEach(r => r.classList.remove('selected-item'));
    row.classList.add('selected-item');
};

const getRowData = (row, cellIndex) => row.querySelector(`td:nth-child(${cellIndex})`).textContent;

const scrollToElement = (selector) => {
    const element = document.querySelector(selector);
    if (element) element.scrollIntoView({behavior: 'smooth'});
};

const loadInventoryData = async (warehouseId) => {
    try {
        const inventories = await axios.get(`${contextPath}/api/statistics/inventory/report?warehouseId=${warehouseId}`);
        await updateTable('#inventoryTable', inventories.data, ['inventoryId', 'inventoryName', 'totalQuantity'], true);
        handleProductHeaderDisplay(inventories.data.length > 0, warehouseName, inventoryName);
    } catch (error) {
        console.error('Error loading inventory data:', error);
    }
};

const loadProductData = async (inventoryId) => {
    await loadProductExpiryDate(inventoryId);
    await loadProductTableData(inventoryId, '/expiring-soon', '#productExpiringSoonTable');
    await loadProductTableData(inventoryId, '/expired', '#productExpiredTable');
};

const loadProductExpiryDate = async (inventoryId) => {
    try {
        const response = await axios.get(`${contextPath}/api/statistics/inventory/${inventoryId}/report/product/expiry-date`);
        const labels = response.data.map(stat => stat.label);
        const data = response.data.map(stat => stat.value);

        updateChartInstance(labels, data, `Thống kê sản phẩm theo hạn sử dụng (${warehouseName} - ${inventoryName})`);
    } catch (error) {
        console.error('Error loading product expiry date:', error);
    }
};

const loadProductTableData = async (inventoryId, endpoint, tableSelector) => {
    try {
        const products = await axios.get(`${contextPath}/api/statistics/inventory/${inventoryId}/report/product${endpoint}`);
        await updateTable(tableSelector, products.data, ['productId', 'productName', 'productUnit', 'productQuantity', 'expiryDate']);
    } catch (error) {
        console.error(`Error loading product data from ${endpoint}:`, error);
    }
};

const updateTable = async (selector, data, fields, formatQuantity = false) => {
    const table = $(selector).DataTable();
    table.clear();

    data.forEach((item, index) => {
        const rowData = fields.map(field => {
            if (formatQuantity && field === 'totalQuantity') {
                return item[field].toLocaleString('vi-VN', {minimumFractionDigits: 3, maximumFractionDigits: 3});
            }
            return item[field];
        });

        const rowNode = table.row.add(rowData).node();
        rowNode.style.cursor = "pointer";
        rowNode.setAttribute('data-index', index);
        rowNode.setAttribute('data-id', item[fields[0]]);
    });

    table.draw();

    const firstRow = document.querySelector(`${selector} tbody tr[data-index="0"]`);
    if (selector === '#inventoryTable') await selectInventory(firstRow);
};

const updateChartInstance = (labels, data, titleText) => {
    if (revenueChartInstance) {
        revenueChartInstance.data.labels = labels;
        revenueChartInstance.data.datasets[0].data = data;
        revenueChartInstance.options.plugins.title.text = titleText;
        revenueChartInstance.options.scales.r.title.text = titleText;
        revenueChartInstance.update();
    }
};

const handleProductHeaderDisplay = (isVisible, warehouse, inventory) => {
    const productHeader = document.querySelector('#productHeader');
    productHeader.innerHTML = `${warehouse} - ${inventory}`;
    productHeader.parentElement.style.display = isVisible ? "block" : "none";

    if (!isVisible && revenueChartInstance) {
        resetChart();
    }
};

const resetChart = () => {
    revenueChartInstance.data.labels = [];
    revenueChartInstance.data.datasets[0].data = [];
    revenueChartInstance.options.plugins.title.text = 'Thống kê sản phẩm theo hạn sử dụng';
    revenueChartInstance.options.scales.r.title.text = 'Thống kê sản phẩm theo hạn sử dụng';
    revenueChartInstance.update();
};
