const apiRoutes = require("./people");

const constructorMethod = app => {
    app.use("/api", apiRoutes);

    app.use("*", (req, res) => {
        res.status(404).json({ error: "Not found" });
    });
};

module.exports = constructorMethod;