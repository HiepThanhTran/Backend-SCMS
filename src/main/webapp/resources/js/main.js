const deleteItem = async (endpoint, itemId) => {
    Swal.fire({
        title: 'Bạn có chắc chắn muốn xóa không?',
        text: "Dữ liệu bị xóa sẽ không thể khôi phục lại được!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Có',
        cancelButtonText: 'Không'
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                url: endpoint,
                type: 'DELETE',
                success: function () {
                    $('#item' + itemId).remove();
                    Swal.fire(
                        'Đã xóa!',
                        'Dữ liệu đã được xóa thành công.',
                        'success'
                    )
                }
            });
        }
    });
}

const showPreLoading = () => {
    const loadingModal = document.getElementById('loadingModal');
    loadingModal.style.display = 'flex';
};

const hidePreLoading = () => {
    const loadingModal = document.getElementById('loadingModal');
    loadingModal.style.display = 'none';
};

const generateRandomColor = () => {
    const [r, g, b] = [Math.random() * 255, Math.random() * 255, Math.random() * 255];
    return {
        color: `rgba(${r}, ${g}, ${b}, 0.2)`,
        borderColor: `rgba(${r}, ${g}, ${b}, 1)`,
    };
};