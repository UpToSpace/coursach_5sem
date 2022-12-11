async function deleteAuthor(id) {
    if (confirm("Are you sure you want to delete author " + id + "?")) {
        await fetch("/admin/authors/delete/" + id, {
            method: "DELETE",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        })
            .then(res => res.json())
            .then(res => {
                if (res.status >= 400 && res.status <= 500) {
                    //console.log(res.status);
                } else {
                    alert(`Author ${id} was deleted successfully`);
                    window.location.reload();
                }
            })
    }
}

async function getAuthorsList() {
    let token = localStorage.getItem("token");
    await fetch("/allauthors", {
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
                    if(!isAdmin) {
                        styleDisplay = "style=\"display: none\""
                        console.log(styleDisplay)
                    }
                }).then(() => {
                console.log(styleDisplay)
                data.forEach(e => {
                    str += `<div>
                <div class="container">
                    <h3><b>${e.name}</b></h3>
                    <p>info: ${e.info}</p>
                  </div>
                  <button class="delete_button" ${styleDisplay} onclick="deleteAuthor(${e.id})">delete</button>
            </div>`
                })
                let authors = document.getElementById('authors')
                authors.innerHTML = str;
            })
        })
}