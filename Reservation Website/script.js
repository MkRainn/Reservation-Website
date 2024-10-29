let currentIndex = 0;
const images = document.querySelectorAll('.carousel-images img');
const dots = document.querySelectorAll('.carousel-dots .dot');

function showSlide(index) {
    if (index >= images.length) currentIndex = 0;
    else if (index < 0) currentIndex = images.length - 1;
    else currentIndex = index;

    const offset = -currentIndex * 100;
    document.querySelector('.carousel-images').style.transform = `translateX(${offset}%)`;

    dots.forEach(dot => dot.classList.remove('active'));
    dots[currentIndex].classList.add('active');
}


document.querySelector('.next').addEventListener('click', () => showSlide(currentIndex + 1));
document.querySelector('.prev').addEventListener('click', () => showSlide(currentIndex - 1));


dots.forEach(dot => {
    dot.addEventListener('click', (e) => showSlide(parseInt(e.target.dataset.index)));
});


showSlide(currentIndex);
