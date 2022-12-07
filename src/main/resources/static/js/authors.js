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
                        styleDisplay = "\"display: none\""
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
                  <button class="delete_button" style=${styleDisplay} onclick="deletePicture(${e.id})">delete</button>
            </div>`
                })
                let authors = document.getElementById('authors')
                authors.innerHTML = str;
            })
        })
}