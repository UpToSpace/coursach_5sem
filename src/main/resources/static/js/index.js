let getMenuLogin = () => {
    let login = document.getElementById("login")
    if (localStorage.getItem('token') == null) {
        login.innerHTML = `<a href="/login">Log in / Registration</a>`
    } else {
        login.innerHTML = `<a href="/profile">Profile</a>`;
    }
}