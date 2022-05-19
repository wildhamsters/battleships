document.getElementById("form").addEventListener("submit", function(e){
  e.preventDefault()
});

function register() {
    var username = document.getElementById("username");
    var email = document.getElementById("email");
    var password = document.getElementById("password");

    var form = document.getElementById("form");
    var formData = new FormData(form);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/registration");
    xhr.send(formData);

    xhr.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 201) {
            clearInput(username);
            clearInput(email);
            clearInput(password);
            console.log(xhr.response);
            window.location.replace(xhr.responseText);
        } else if (this.readyState == 4 && this.status == 400) {
           console.log(xhr.response);
           alert(xhr.responseText);
       }
    };
}

function clearInput(element) {
    element.value="";
}
