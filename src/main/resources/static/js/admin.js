async function getUsersList() {
    let token = localStorage.getItem("token");
    return await fetch("/admin/users", {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        }
    });
}