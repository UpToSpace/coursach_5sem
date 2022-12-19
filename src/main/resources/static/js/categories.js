async function deleteCategory(id) {
    if (confirm("Are you sure you want to delete category " + id + "?")) {
        await fetch("/admin/categories/delete/" + id, {
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
                    alert(`Category ${id} was deleted successfully`);
                    window.location.reload();
                }
            })
    }
}

async function getCategoriesList() {
    let token = localStorage.getItem("token");
    await fetch("/allcategories", {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => response.json())
        .then(data => {
            let str = ''
            let styleDisplay = "";
            fetch(`/isAdmin/`, {
                headers: {'Authorization': `Bearer ${localStorage.getItem('token')}`}
            }).then(response => response.json())
                .then(isAdmin => {
                    if (!isAdmin) {
                        styleDisplay = "style = \"display: none\""
                        console.log(styleDisplay)
                    }
                }).then(() => {
                console.log(styleDisplay)
                data.forEach(e => {
                    str += `<div class="card">
                <div class="container">
                    <h3><b>${e.name}</b></h3>
                    <p>${e.info}</p>
                  </div>
                  <button class="delete_button" ${styleDisplay} onclick="deleteCategory(${e.id})">delete</button>
            </div>`
                })
                let authors = document.getElementById('categories')
                authors.innerHTML = str;
            })
        })
}