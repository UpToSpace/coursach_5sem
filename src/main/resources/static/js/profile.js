async function getUserInfo() {
    let token = localStorage.getItem("token");
    await fetch("/user/profile", {
        method: "GET",
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
        .then(res => res.json())
        .then(data => {
            document.getElementById("profile_info")
            document.getElementById("password").innerHTML = data[0].password;
            document.getElementById("email").innerHTML = data[0].email;
            document.getElementById("username").innerHTML = data[0].username;
            document.getElementById("role").innerHTML = data[0].role.roleName;
            //console.log(data[0])
        })
}

async function changeUserData(data) {
    let token = localStorage.getItem("token");
    return await fetch("/user/updateprofile", {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    });
}


async function updateUser() {
    let password = document.getElementById("newPassword").value;
    let username = document.getElementById("newUsername").value;
    let mes = document.getElementById("message");
    let data = {username: username, password: password};
    let result = await changeUserData(data);
    if (result.ok) {
        // let body = await result.text();
        // let info = JSON.parse(body);
        // localStorage.setItem('token', info['token']);
        // window.location.replace(window.location.origin);
    } else {
        mes.innerHTML = 'Error occured';
    }
    window.location.reload();
}