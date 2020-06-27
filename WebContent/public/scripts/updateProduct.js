const endpoint = fetch("http://localhost:8080/Amazon/details");

endpoint.then(response => {
    return response.json();
}).then(product => {
    console.log(product.id);
    document.getElementById("id-product").value = product.id;
    document.getElementById("title").value = product.title;
    document.getElementById("description").value = product.description;
    document.getElementById("amount").value = product.amount;
});