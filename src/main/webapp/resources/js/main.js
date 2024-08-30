$(document).ready(() => {
    setTimeout(() => {
        $('#loadingModal').fadeOut('slow');
    });

    // Popover
    const popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'))
    const popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
        return new bootstrap.Popover(popoverTriggerEl)
    });
    const ids = ['#popoverTotalAmount', '#popoverOrders', '#popoverProducts'];
    ids.forEach(id => {
        const element = document.querySelector(id);
        if (element) {
            new bootstrap.Popover(element, {
                container: element.closest('.card'),
            });
        }
    });
});

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
                },
                error: function (xhr, status, error) {
                    console.error(error);
                    Swal.fire(
                        'Xóa dữ liệu thất bại!',
                        'Dữ liệu đang được liên kết với thực thể khác.',
                        'error'
                    );
                },
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

const getMondayAndSunday = (date) => {
    const dayOfWeek = date.getDay(); // Lấy ngày trong tuần (0 = Chủ Nhật, 1 = Thứ Hai, ..., 6 = Thứ Bảy)

    const monday = new Date(date);
    // Ngày thứ Hai là ngày trong tháng - (dayOfWeek + 6) % 7 (Chênh lệch ngày trong tuần với ngày trong tháng)
    monday.setDate(date.getDate() - (dayOfWeek + 6) % 7);

    const sunday = new Date(date);
    // Chủ Nhật là ngày thứ Hai + 6
    sunday.setDate(monday.getDate() + 6);

    // Trả về ngày thứ Hai và Chủ Nhật
    return {
        monday: monday,
        sunday: sunday
    };
}