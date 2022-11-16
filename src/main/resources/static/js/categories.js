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
            let table = document.getElementById('categoriesTable')
            let str = ''
            //console.log(data);
            data.forEach(e => {
                str += `<tr> <td>${e.name}</td><td>${e.info}</td><td><button class="delete_button" data-email="${e.id}">delete</button></td></tr>`
            })
            table.innerHTML = str;
        })

    // document.addEventListener('click', (e) => {
    //     if (e.target.classList.contains('delete_button')) {
    //         const element = e.target;
    //         const email = element.dataset.email;
    //         if (confirm("Are you sure you want to delete user " + email + "?")) {
    //             deleteUser(email);
    //         }
    //     }
    // });
}