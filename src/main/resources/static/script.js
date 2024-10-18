function requestPagesMenu() {
    const contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
    let request = new XMLHttpRequest();
    request.open("GET", contextPath + "/user/pages/data")
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
                `${contextPath}/user/${item.type}/${item.id}` : `${contextPath}/user/${item.type}`);
            menuElement.insertAdjacentHTML('beforeend',
                `<li><a class="dropdown-item" href="${href}">${item.title}</a></li>`
            );
        }
    });
    menuElement.insertAdjacentHTML('afterbegin', `<li><a class="dropdown-item" href="${contextPath}/user/news">Новини</a></li>`)
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
                                            <img class="d-block w-100 sharesPage" src="${contextPath}${element.pathToImage}"
                                                 alt="error" style="max-width: 100%;height: 400px;"/>
                                        </div>`);
    });
}

function requestBanners() {
    let request = new XMLHttpRequest();
    request.open("GET", contextPath + "/user/banners/data")
    request.send();
    request.addEventListener('load', function () {
        let data = JSON.parse(request.response);
        console.log(data);
        data.forEach(function (item) {
            if (item.type === 'shareAndNew') {
                const myCarousel = document.getElementById('carouselExample');
                if (item.bannersImagesDTOS.length !== 0 && item.status === true && myCarousel) {
                    document.getElementById('banner-shareAndNew-default_').remove();
                    buildCarousel(item, myCarousel);
                } else if (item.bannersImagesDTOS.length !== 0 && item.status === false) {
                    myCarousel.remove();
                } else if (item.bannersImagesDTOS.length === 0) {
                    if (myCarousel) {
                        myCarousel.remove();
                    }
                }
            } else if (item.type === 'main') {
                const myCarousel = document.getElementById('carouselMain');
                if (item.bannersImagesDTOS.length !== 0 && item.status === true && myCarousel) {
                    const bannerDef = document.getElementById('banner-main-default_');
                    if (bannerDef) {
                        bannerDef.remove();
                    }
                    buildCarousel(item, myCarousel);
                } else if (item.bannersImagesDTOS.length !== 0 && item.status === false) {
                    myCarousel.remove();
                } else if (item.bannersImagesDTOS.length === 0) {
                    if (myCarousel) {
                        myCarousel.remove();
                    }
                }
            }
        });
        // deletingContainerCarousel(data);
    });
}

// function deletingContainerCarousel(banners) {
//     let pathname = window.location.pathname;
//     console.log(pathname);
//     const myCarouselShareAndNew = document.getElementById('carouselExample');
//     const myCarouselMain = document.getElementById('carouselMain');
//     if (pathname.includes('main')) {
//         console.log('enter in main block');
//         if (banners.length === 0) {
//             myCarouselMain.innerHTML = '';
//             myCarouselShareAndNew.innerHTML = '';
//         } else if (banners.length === 1) {
//             banners.forEach(function (item) {
//                 if (item.type === 'shareAndNew') {
//                     myCarouselMain.innerHTML = '';
//                 } else if (item.type === 'main') {
//                     myCarouselShareAndNew.innerHTML = '';
//                 }
//             });
//         }
//     } else {
//         console.log('enter in sharesAndNews block');
//         if (banners.length === 0) {
//             myCarouselShareAndNew.innerHTML = '';
//         }
//     }
// }

function buildCarousel(item, myCarousel) {
    createSlidersImage(item.bannersImagesDTOS, myCarousel);
    new bootstrap.Carousel(myCarousel, {
        interval: item.rotationSpeed * 1000,
        ride: 'carousel'
    });
}

function requestPageOfMain() {
    let request = new XMLHttpRequest();
    request.open('GET', contextPath + "/user/main/data")
    request.send();
    request.addEventListener('load', function () {
        let data = JSON.parse(request.response);
        document.getElementById("phone_1").textContent = (data.phone1 === null ? '+38 (0XX) XXX-XX-XX' : data.phone1);
        document.getElementById("phone_2").textContent = (data.phone2 === null ? '+38 (0XX) XXX-XX-XX' : data.phone2);
        let seoTextElement = document.getElementById("seoText_");
        if (seoTextElement) {
            seoTextElement.textContent = data.seoText;
        }
    });
}