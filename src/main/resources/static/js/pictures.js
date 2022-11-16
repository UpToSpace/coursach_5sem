async function deletePicture(id) {
    if (confirm("Are you sure you want to delete picture " + id + "?")) {
        await fetch("/admin/pictures/delete/" + id, {
            method: "DELETE",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        })
            .then(res => console.log(res.text())) //????
            .then(res => {
                if (res.status >= 400 && res.status <= 500) {
                    //console.log(res.status);
                } else {
                    alert(`Picture ${id} was deleted successfully`);
                }
            })
    }
}

async function addPictureToCollection(id) { //add to collection
    await fetch("/user/addpicture", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(res => console.log(res.text())) //????
        .then(res => {
            if (res.status >= 400 && res.status <= 500) {
                //console.log(res.status);
            } else {
                alert(`Picture ${id} was added successfully`);
            }
        })
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
            let table = document.getElementById('pictures')
            let str = ''
            console.log(data);
            data.forEach(e => {
                str += `<tr> <td>${e.name}</td><td>${e.author.name}</td><td>${e.category.name}</td><td>${e.year}</td><td>${e.info}</td>
<td><button class="delete_button" onclick="deletePicture(${e.id})" style="visibility: visible">delete</button></td>
<td><button class="add_button" onclick="addPictureToCollection(${e.id})">add to my collection</button></td></tr>`
            })
            table.innerHTML = str;
        })

    // document.addEventListener('click', (e) => {
    //     if (e.target.classList.contains('delete_button')) {
    //         const element = e.target;
    //         const id = element.dataset.id;
    //         if (confirm("Are you sure you want to delete picture " + id + "?")) {
    //             deletePicture(id);
    //         }
    //     }

    //     if (e.target.classList.contains('add_button')) {
    //         const element = e.target;
    //         const id = element.dataset.id;
    //         addPicture(id);
    //     }
    // });
}

//
// $(window).on('load',
// getPicturesList()
// );
