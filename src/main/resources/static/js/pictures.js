async function findPictures() {
    let search = document.getElementById("search").value;
    await fetch("/allpictures/search", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
        body: search
    })
        .then(res => res.json())
        .then(data => {
            let table = document.getElementById('pictures')
            let str = ''
            console.log(data);
            data.forEach(e => {
                str += `<div class="card">
                <img src="data:image/png;base64,${e.pictureBytes}">
                <div class="container">
                    <h3><b>${e.name}</b></h3>
                    <p>${e.author.name}</p>
                    <p>category: ${e.category.name}</p>
                    <p>year: ${e.year}</p>
                    <p>info: ${e.info}</p>
                  </div>
                  <button class="delete_button admin" onclick="deletePicture(${e.id})">delete</button>
                  <button class="add_button" onclick="addPictureToCollection(${e.id})">add to my collection</button>
            </div>`
            })
            table.innerHTML = str;})
}

async function deletePicture(id) {
    if (confirm("Are you sure you want to delete picture " + id + "?")) {
        await fetch("/admin/pictures/delete/" + id, {
            method: "DELETE",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        })
            .then(res => res.json())
            .then(res => {
                if (res.status >= 400 && res.status <= 500) {
                    console.log(res.status);
                } else {
                    alert(`Picture ${id} was deleted successfully`);
                    window.location.reload();
                }
            })
    }
}

async function addPictureToCollection(id) {
    alert("Picture id " + id);
    let collectionName = document.getElementById("collectionName").value;
    alert("collection name: " + collectionName);
    if (collectionName.length == 0) {
        alert("input your collection!")
    } else {
        await fetch("/user/addtocollection/" + id, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            },
            body: collectionName
        })
            .then(res => console.log(res.text())) //????
            .then(res => {
                if (res.status >= 400 && res.status <= 500) {
                    //console.log(res.status);
                } else {
                    alert(`Picture ${id} was added to collection successfully`);
                }
            })
    }
}

async function getPicturesList() {
    await fetch("/allpictures", {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    })
        .then(response => response.json())
        .then(data => {
            let str = ''
            let styleDisplay = "";
            console.log(data);
                fetch(`/isAdmin/`, {
                    headers: {'Authorization': `Bearer ${localStorage.getItem('token')}`}
                }).then(response => response.json())
                    .then(isAdmin => {
                        if(!isAdmin) {
                            styleDisplay = "style = \"display: none\""
                            console.log(styleDisplay)
                        }
            }).then(() => {
                    console.log(styleDisplay)
                    data.forEach(e => {
                        str += `<div class="card">
                <img src="data:image/png;base64,${e.pictureBytes}">
                <div class="container">
                    <h3><b>${e.name}</b></h3>
                    <p>${e.author.name}</p>
                    <p>category: ${e.category.name}</p>
                    <p>year: ${e.year}</p>
                    <p>info: ${e.info}</p>
                  </div>
                  <button class="delete_button" ${styleDisplay} onclick="deletePicture(${e.id})">delete</button>
                  <button class="add_button" onclick="addPictureToCollection(${e.id})">add to my collection</button>
            </div>`
                    })
                    let pictures = document.getElementById('pictures')
                    pictures.innerHTML = str;
                })
        })
}

async function resetSearch() {
    document.getElementById("search").value = ""
    await getPicturesList();
}

