const endpoint = fetch("http://localhost:8080/Amazon/details");

endpoint
    .then((response) => {
        return response.json();
    })
    .then((res) => {
        document.body.innerHTML = `<section class="section container" id="contact">
						<h1>Product Details</h1>
						<div id=${res.id} class="detail">
							<div>Title: ${res.title}</div>
							<div>Description: ${res.description}</div>
							<div>Price: ${res.amount}</div>
							<div>Image: ${res.image}</div>
						</div>
						<button id="btn-edit" class="waves-effect waves-light btn-small yellow">Edit product</button>
						<button id="btn-delete" class="waves-effect waves-light btn-small red">Delete product</button>
					</section>`;

        const editBtn = document.getElementById("btn-edit");
        editBtn.addEventListener("click", () => {
            window.location.href =
                "http://localhost:8080/Amazon/public/views/updateProduct.html";
        });

        const deleteBtn = document.getElementById("btn-delete");

        deleteBtn.addEventListener("click", () => {
            const id = res.id;

            const endpoint = "http://localhost:8080/Amazon/deleteProduct";

            const data = {
                method: "POST",
                body: JSON.stringify(parseInt(id)),
            };

            fetch(endpoint, data)
                .then((res) => {
                    if (res.status == 200) {
                        window.location.href =
                            "http://localhost:8080/Amazon/public/views/dashboard.html";
                    } else {
                        window.alert("Product can't be deleted");
                    }
                    console.log(res);
                })
                .catch((err) => {
                    console.log(err);
                });
        });
    });
