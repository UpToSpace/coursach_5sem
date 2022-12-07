let getMenuLogin = () => {
    let login = '';
    if (localStorage.getItem('token') == null) {
        login = `<div class="login" id="login"><a href="/login">Log in / Registration</a></div>`
    } else {
        login = `<div class="login" id="login"><a href="/profile">Profile</a></div>`;
    }
    document.getElementsByTagName("header")[0].innerHTML += login;
}

const checkIsAdmin = async () => {
    const token = window.localStorage.getItem("token");
    const response = await fetch(`/isAdmin`,
        {
            headers: {'Authorization': `Bearer ${token}`}
        });

    const isAdmin = await response.json();

    if (!isAdmin || response.status === 403) {
        alert("////403 Forbidden////");
        window.location.assign('/');
    } else {
        //document.querySelector(".admin").classList.remove("d-none");
    }
}

function showMenuAdmin() {
    const menu = document.querySelector(".menu");
    const token = window.localStorage.getItem("token");
    if(token) {
        fetch(`/isAdmin/`, {
            headers: {'Authorization': `Bearer ${token}`}
        }).then(response => response.json())
            .then(isAdmin => {
                if(isAdmin) {
                    const admin = document.createElement("a");
                    admin.href = "/admin";
                    admin.innerHTML = "Admin";
                    menu.appendChild(admin);
                } else {
                    //console.log(document.querySelector(".admin").classList.remove("admin"))
                    if (document.querySelector(".admin")) {
                        console.log(document.querySelector(".admin"));
                        document.querySelector(".admin").classList.remove("admin")
                    }
                    //document.querySelector(".admin").classList.add("admin");
                }
            })
    }
}