function requestPagesMenu() {
    let request = new XMLHttpRequest();
    request.open("GET", "/user/pages/data")
    request.send();
    request.addEventListener('load', function () {
        let data = JSON.parse(request.response);
        createDropMenu(data);
        refreshStateDropMenu();
    });
}

function createDropMenu(pages) {
    const menuElement = document.getElementById("pagesMenu_");
    pages.forEach(function (item) {
        if (item.type !== 'about' && item.type !== 'main') {
            let href = (item.type === 'additional' ?
                `/user/${item.type}/${item.id}` : `/user/${item.type}`);
            menuElement.insertAdjacentHTML('beforeend',
                `<li><a class="dropdown-item" href="${href}">${item.title}</a></li>`
            );
        }
    });
    menuElement.insertAdjacentHTML('afterbegin', `<li><a class="dropdown-item" href="/user/news">Новини</a></li>`)
}

function refreshStateDropMenu() {
    let menuItems = document.querySelectorAll('.dropdown-item');
    const mainButton = document.getElementById('dropdownMenuButton');
    menuItems.forEach(function (item) {
        if (window.location.pathname === item.getAttribute('href')) {
            const newHref = item.getAttribute('href');
            const newText = item.textContent;
            const oldHref = mainButton.getAttribute('href');
            const oldText = mainButton.textContent;
            mainButton.href = newHref;
            mainButton.textContent = newText;
            item.href = oldHref;
            item.textContent = oldText;
        }
    });
}

function createSlidersImage(galleries, myCarousel) {
    let indicatorsElement = document.getElementById(`indicators_${myCarousel.getAttribute('data-index')}`);
    let imageSlidersElement = document.getElementById(`imageSliders_${myCarousel.getAttribute('data-index')}`);
    galleries.forEach(function (element, index) {
        const activeClass = index === 0 ? 'active' : '';
        indicatorsElement.insertAdjacentHTML('beforeend', `<li data-bs-target="#${myCarousel.id}"
                 data-bs-slide-to="${index}" class="${activeClass}"></li>`);
        imageSlidersElement.insertAdjacentHTML('beforeend', `<div class="carousel-item ${activeClass}">
                                            <img class="d-block w-100 sharesPage" src="${element.pathToImage}"
                                                 alt="error" style="max-width: 100%;height: 400px;"/>
                                        </div>`);
    });
}

function requestBanners() {
    let request = new XMLHttpRequest();
    request.open("GET", "/user/banners/data")
    request.send();
    request.addEventListener('load', function () {
        let data = JSON.parse(request.response);
        data.forEach(function (item) {
            if (item.type === 'shareAndNew') {
                const myCarousel = document.getElementById('carouselExample');
                if (myCarousel) {
                    buildCarousel(item, myCarousel);
                }
            } else if (item.type === 'main') {
                const myCarousel = document.getElementById('carouselMain');
                if (myCarousel) {
                    buildCarousel(item, myCarousel);
                }
            }
        });
    });
}

function buildCarousel(item, myCarousel) {
    createSlidersImage(item.bannersImagesDTOS, myCarousel);
    new bootstrap.Carousel(myCarousel, {
        interval: item.rotationSpeed * 1000,
        ride: 'carousel'
    });
}

function requestPageOfMain() {
    let request = new XMLHttpRequest();
    request.open('GET', "/user/main/data")
    request.send();
    request.addEventListener('load', function () {
        let data = JSON.parse(request.response);
        document.getElementById("phone_1").textContent = (data.phone1 === null ? 'null' : data.phone1);
        document.getElementById("phone_2").textContent = (data.phone2 === null ? 'null' : data.phone2);
        let seoTextElement = document.getElementById("seoText_");
        if (seoTextElement) {
            seoTextElement.textContent = data.seoText;
        }
    });
}