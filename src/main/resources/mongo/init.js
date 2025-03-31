db.createUser({
    user: "root",
    pwd: "NixJavaCourse2025",
    roles: [
        {role: "userAdminAnyDatabase", db: "admin"},
        {role: "readWriteAnyDatabase", db: "admin"},
        {role: "dbAdminAnyDatabase", db: "admin"}
    ]
});
db.createCollection("orders");
