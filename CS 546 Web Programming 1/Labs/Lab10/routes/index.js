const userRoutes = require("./users");
const constructorMethod = app => {
    app.use(function(request, response, next) {
        if (!request.session.logged) {
            request.session.authenticated = "Non-Authenticated User";
        }
        console.log("[" + new Date().toUTCString() + "]: " + request.method + " " + request.originalUrl + " (" + request.session.authenticated + ")");
        next();
    })
    app.use("/", userRoutes);
    app.use("*", (req, res) => {
        res.status(404).json({ error: "Not found" });
    });
};

module.exports = constructorMethod;