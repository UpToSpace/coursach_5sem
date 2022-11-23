async function addCollection() {
    let name = document.getElementById("name").value;
    await fetch("/user/addcollection", {
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
            .then(res => console.log(res.text())) //????
            .then(res => {
                if (res.status >= 400 && res.status <= 500) {
                    //console.log(res.status);
                } else {
                    alert(`Picture ${pictureId} was deleted successfully`);
                }
            })
        window.location.reload();
    }
}

async function getCollectionsList() {
    await fetch("/user/collections", {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            let collections = document.getElementById('collections')
            let str = ''
            data.forEach(el => {
                str += `<h3>${el.name}</h3>`
                el.pictures.forEach(e => {
                    str += `<tr> <td>${e.name}</td><td>${e.author.name}</td><td>${e.category.name}</td><td>${e.year}</td><td>${e.info}</td>
<td><button class="delete_button" onclick="deletePictureFromCollection(${e.id}, ${el.id})">delete</button></td>
<td><img src="data:image/png;base64,${e.pictureBytes}"></td></tr>`
                })
            })
            collections.innerHTML = str;
        })
}

