$(document).ready(() => {
    const searchInput = document.getElementById('search-input');
    const searchResults = document.getElementById('search-results');

    searchInput.addEventListener('input', function () {
        const query = this.value.trim().toLowerCase();
        searchResults.innerHTML = '';

        if (query !== '') {
            const filteredItems = items.filter(item => item.value.toLowerCase().includes(query));

            if (filteredItems.length > 0) {
                searchResults.style.display = 'block';
                filteredItems.forEach(item => {
                    const li = document.createElement('li');
                    const a = document.createElement('a');
                    a.setAttribute('href', `${contextPath}/admin/${item.key}`);
                    a.style.color = 'black';
                    a.textContent = item.value;
                    li.appendChild(a);
                    li.classList.add('list-group-item');
                    searchResults.appendChild(li);
                });
            } else {
                searchResults.style.display = 'none';
            }
        } else {
            searchResults.style.display = 'none';
        }
    });

    searchInput.addEventListener('keydown', function (event) {
        if (event.key === 'Enter') {
            event.preventDefault();
        }
    });
})