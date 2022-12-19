async function addCollection() {
    let name = document.getElementById("name").value;
    if (name.length == 0) {
        alert("Enter name of the collection!")
    } else {
        await fetch("/user/addcollection/", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            },
            body: name
        })
            .then(res => res.status === 200 ? alert("collection added successfully") : alert("smth went wrong"))
        window.location.reload();
    }
}

async function deleteCollection(name) {
    if (confirm("Are you sure you want to delete collection " + name + "?")) {
        await fetch("/user/collections/delete/" + name, {
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
                    alert(`Collection ${name} was deleted successfully`);
                }
            })
        window.location.reload();
    }
}

async function deletePictureFromCollection(pictureId, collectionId) {
    if (confirm("Are you sure you want to delete picture " + pictureId + "?")) {
        await fetch("/user/collections/deletepicture/" + pictureId, {
            method: "DELETE",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            },
            body: collectionId
        })
            .then(res => res.json())
            .then(res => {
                if (res.status >= 400 && res.status <= 500) {
                    console.log(res.status);
                } else {
                    alert(`Picture ${pictureId} was deleted successfully`);
                }
            })
        window.location.reload();
    }
}

async function getCollectionsNamesList() {
    await fetch("/user/collectionsnames/", {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            let collectionsNames = document.getElementById('collectionsNames')
            let str = ''
            data.forEach(el => {
                str += `<div>${el} <button class="delete_button" onclick="deleteCollection('${el}')">delete</button></div>`
            })
            collectionsNames.innerHTML = str;
        })
}

async function getCollectionsList() {
    await fetch("/user/collections/", {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    })
        .then(response => response.json())
        .then(data => {
            let str = '';
            console.log(data);
            let collections = document.getElementById('collections')
            if (data[0].id != null) {
            data.forEach(el => {
                str += `<h2>${el.name}</h2><div style="display: grid; grid-template-columns: repeat(4, 1fr);">`
                el.pictures.forEach(e => {
                    str += `<div class="card">
                <img src="data:image/png;base64,${e.pictureBytes}">
                <div class="container">
                    <h3><b>${e.name}</b></h3>
                    <p>${e.author.name}</p>
                    <p>category: ${e.category.name}</p>
                    <p>year: ${e.year}</p>
                    <p>info: ${e.info}</p>
                  </div>
                  <button class="delete_button" onclick="deletePictureFromCollection(${e.id}, ${el.id})">delete</button>
            </div>`
                })
                str += '</div>'
            })}
            collections.innerHTML = str;
        })
}

