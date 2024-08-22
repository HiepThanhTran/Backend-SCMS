// Table
$(document).ready(function () {
    $('#table').DataTable({
        language: {
            url: "https://cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Vietnamese.json"
        }
    });
});

// Delete item
function deleteItem(endpoint, itemId) {
    if (confirm("Bạn chắc chắn xóa không?") === true) {
        fetch(endpoint, {
            method: "delete"
        }).then(res => {
            console.log(res);
            if (res.status === 204) {
                let d = document.getElementById(`item${itemId}`);
                d.style.display = "none";
            } else {
                alert("Xóa không thành công!");
            }
        });
    }
}