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
            //console.log(data);
                data.forEach(e => {
                    str += `<tr> <td>${e.email}</td><td>${e.username}</td><td>${e.role.roleName}</td><td><button class="delete_button" data-email="${e.email}">delete</button></td></tr>`
                })
                table.innerHTML = str;
        })

    document.addEventListener('click', (e) => {
        if (e.target.classList.contains('delete_button')) {
            const element = e.target;
            const email = element.dataset.email;
            if (confirm("Are you sure you want to delete user " + email + "?")) {
                deleteUser(email);
            }
        }
    });
}

function deleteUser(email) {
    //console.log("delete user: email: " + email);

    fetch("/admin/users/delete/" + email, {
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
            console.log(res.status);
        } else {
            alert(`User ${email} was deleted successfully`);
        }
    })
}

async function addAuthor() {
    let token = localStorage.getItem("token");
    let authorName = document.getElementById("authorName").value;
    let authorInfo = document.getElementById("authorInfo").value;
    if (authorName.length != 0 && authorInfo.length != 0) {
        await fetch("/admin/addauthor", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify({name: authorName, info: authorInfo})
        })
            .then(res => res.status === 200 ? alert("author added successfully") : alert("smth went wrong"))
    } else {
        alert("Please, fill all inputs")
    }
}

async function addCategory() {
    let token = localStorage.getItem("token");
    let categoryName = document.getElementById("categoryName").value;
    let categoryInfo = document.getElementById("categoryInfo").value;
    if (categoryName.length != 0 && categoryInfo.length != 0) {
        await fetch("/admin/addcategory", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify({name: categoryName, info: categoryInfo})
        })
            .then(res => res.status === 200 ? alert("category added successfully") : alert("smth went wrong"))
    } else {
        alert("Please, fill all inputs")
    }
}

async function addPicture() {
    let token = localStorage.getItem("token");
    let pictureName = document.getElementById("pictureName").value;
    let pictureAuthorName = document.getElementById("pictureAuthorName").value;
    let pictureCategoryName = document.getElementById("pictureCategoryName").value;
    let pictureYear = document.getElementById("pictureYear").value;
    let pictureInfo = document.getElementById("pictureInfo").value;
    let picturePicture =
        "D:\\University\\javaproject\\src\\main\\resources\\static\\images\\" + document.getElementById("picturePicture").value.split('\\')[2];
    console.log(picturePicture)
    if (pictureName.length != 0 &&
        pictureAuthorName.length != 0 &&
        pictureCategoryName.length != 0 &&
        pictureYear.length != 0 &&
        pictureInfo.length != 0 &&
        picturePicture.length != 0) {
        if (!isNaN(pictureYear)) {
            await fetch("/admin/addpicture", {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify({
                    name: pictureName,
                    authorName: pictureAuthorName,
                    categoryName: pictureCategoryName,
                    year: pictureYear,
                    info: pictureInfo,
                    picturePath: picturePicture
                })
            })
                .then(res => res.status === 200 ? alert("picture added successfully") : alert("smth went wrong"))
        } else {
            alert("year must be a number")
        }
    } else {
        alert("Please, fill all inputs")
    }
}