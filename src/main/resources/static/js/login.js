async function logUser(data) {
    return await fetch("/login", {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json'
        }
    });
}

async function login() {
    let password = document.getElementById("password").value;
    let email = document.getElementById("email").value;
    let data = {email: email, password: password};
    //alert(email + password);
    let result = await logUser(data);
    //alert(result);
    if (result.ok) {
        let body = await result.text();
        let info = JSON.parse(body);
        //alert("ok " + info)
        localStorage.setItem('token', info['token']);
        //alert('token ' + info['token']);
        window.location.replace(window.location.origin);
    } else {
        alert('theres no user with this parameters');
    }
}