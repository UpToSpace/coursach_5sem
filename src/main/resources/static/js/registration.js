async function regUser(data) {
    return await fetch("/registration", {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json'
        }
    });
}

async function register() {
    let password = document.getElementById("password").value;
    let email = document.getElementById("email").value;
    let username = document.getElementById("username").value;
    let mes = document.getElementById("message");
    if (password.length == 0 || email.length == 0 || username.length == 0) {
        alert("Please, enter information about yourself")
    } else {
        let data = {email: email, username: username, password: password};
        let result = await regUser(data);
        if (result.ok) {
            let body = await result.text();
            // let info = JSON.parse(body);
            // localStorage.setItem('token', info['token']);
            // window.location.replace(window.location.origin);
        } else {
            mes.innerHTML = 'Error occured';
        }
    }
}