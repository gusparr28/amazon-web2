const endpoint = fetch("http://localhost:8080/Amazon/update");

endpoint.then(response => {
    return response.json();
}).then(people => {
    document.getElementById("name").value = people.name;
    document.getElementById("lastName").value = people.lastName;
    document.getElementById("email").value = people.email;
    document.getElementById("country").value = people.country;
    document.getElementById("city").value = people.city
    document.getElementById("state").value = people.state;
    document.getElementById("street").value = people.street;
    document.getElementById("postalCode").value = people.postalCode;
    document.getElementById("phone").value = people.phone;
});
