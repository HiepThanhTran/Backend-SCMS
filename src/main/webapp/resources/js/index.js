// Table Categories
$(document).ready(function () {
    $('#categoryTable').DataTable();
});

// Delete item
function deleteItem(endpoint, itemId) {
    if (confirm("Bạn chắc chắn xóa không?") === true) {
        fetch(endpoint, {
            method: "delete"
        }).then(res => {
            console.log(res);
            if (res.status === 204 || res.status === 405) {
                let d = document.getElementById(`item${itemId}`);
                d.style.display = "none";
            } else {
                alert("Xóa không thành công!");
            }
        });
    }
}
