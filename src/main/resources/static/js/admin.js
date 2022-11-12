async function getUsersList() {
    let token = localStorage.getItem("token");
    await fetch("/admin/users", {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => response.json())
        .then(data => {
                let table = document.getElementById('usersTable')
                let str = ''
                data.forEach(e => {
                    str += `<tr> <td>${e.email}</td><td>${e.username}</td><td>${e.role}</td></tr>`
                })
                table.innerHTML = str;
        })

    document.addEventListener('click', (e) => { // хз что тут творицца
        if (e.target.classList.contains('delete__button')) {
            const element = e.target;
            //const name = element.dataset.name;
            const id = element.dataset.name;
            console.log(id);

            deleteUser(id);
        }
    });
}

function deleteUser(id) { // хз что тут творицца
    alert(id);
    console.log("delete user: email: " + id);

    fetch("/admin/users/delete/{email}" + id, {
        method: "DELETE",
        headers: {
            "Content-Type": 'application/json; charset=utf-8',
            "Accept": 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    })
        .then(res => res.json())
        .then(res => {
        if (res.status >= 400 && res.status <= 500) {
            //console.log(res.status);
        } else {
            console.log("successful");
        }
    })
}

async function addAuthor() {
    let token = localStorage.getItem("token");
    await fetch("/admin/users", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        }
    })
}