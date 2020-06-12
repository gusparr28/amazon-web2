const productForm = document.getElementyById("product-form");
const inputFile = document.getElementById("input-file");

function sendFile() {
	productForm.addEventListener("submit", function(e) {
		e.preventDefault();
	
		const endpoint = "http://localhost:8080/Amazon/dashboard";
		const formData = new FormData();

		formData.append("inputFile", inputFile.files[0]);
	
		fetch(endpoint, {
			method: "post",
			body: formData
		}).then().
		catch(console.error);
	});
}


