function requestPagesMenu() {
    let request = new XMLHttpRequest();
    request.open("GET", "/user/pages/data")
    request.send();
    request.addEventListener('load', function () {
        let data = JSON.parse(request.response);
        console.log(data);
    });
}