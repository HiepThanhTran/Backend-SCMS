$(document).ready(() => {
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
});
