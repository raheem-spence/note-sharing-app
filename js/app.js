const ul = document.getElementById('course-list')

ul.addEventListener('click', (event) => {
    const parentElement = event.target;

    const clickedLi = parentElement.closest('li');

});