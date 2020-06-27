const logoutBtn = () => {
    const endpoint = "http://localhost:8080/Amazon/logout";

    const data = {
        method: "POST",
        headers: new Headers()
    };

    fetch(endpoint, data)
        .then((res) => {
            if (res.status == 200) {
                window.location.href = "http://localhost:8080/Amazon/index.html";
            } else {
                window.alert("Can't logout");
            }
            console.log(res);
        })
        .catch((err) => {
            console.log(err);
        });
};

let user = "";
var data = [];

const endpoint = fetch("http://localhost:8080/Amazon/dashboard");

endpoint.then(response => {
    return response.json();
})
    .then(people => {
        data = people;
        document.getElementById("data").innerHTML += data.map(user =>
            `<div class="container">
                <div class="row">
                    <div class="col s12 l6">
                        <div id=${user.id} class="card detail">
                            <div class="card-image">
                                <img src="" alt="">
                            </div>
                            <div class="content">
                                <span class="card-title">${user.title}</span>
                                <p>${user.description}</p>
                                <span>${user.amount}</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>`
        ).join('');

        const detail = document.querySelectorAll(".detail");

        for (let i = 0; i < detail.length; i++) {
            detail[i].addEventListener("click", () => {
                const endpoint = "http://localhost:8080/Amazon/details";
                const id = detail[i].id;
                const data = {
                    method: "POST",
                    body: JSON.stringify(parseInt(id))
                };
                fetch(endpoint, data)
                    .then((res) => {
                        if (res.status == 200) {
                            window.location.href = "http://localhost:8080/Amazon/public/views/details.html";
                        } else {
                            window.alert("You are not connected to internet");
                        }
                    })
                    .catch((err) => {
                        console.log(err);
                    });
            });
        };
    });