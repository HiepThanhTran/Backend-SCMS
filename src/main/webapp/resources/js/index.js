/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

//Js code to Sidebar

document.addEventListener('DOMContentLoaded', function () {
    const items = document.querySelectorAll('.sidebar-item');


    items.forEach(item => {
        item.addEventListener('click', function () {
            items.forEach(el => el.classList.remove('active'));

            this.classList.add('active');
        });
    });
});