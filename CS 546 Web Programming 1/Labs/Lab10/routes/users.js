const express = require('express');
const router = express.Router();
const users = require("../data/users");
var bcrypt = require('bcryptjs');

router.get('/', async(req, res) => {
    if (!req.session.logged) {
        res.render('users/login', { title: "Login Page" });
    } else {
        res.redirect('/private');
    }
})

router.post('/login', async(req, res) => {
    var formUsername = req.body.username;
    var formPassword = req.body.password;
    var logged = false;
    var validUsername = false;
    var validPassword = false;
    req.session.logged = false;
    for (var i = 0; i < users.length; i++) {
        if ((formUsername) && formUsername === users[i].username) {
            validUsername = true;
            const checkPass = await bcrypt.compareSync(formPassword, users[i].hashedPassword);
            if (checkPass) {
                validPassword = true;
                //res.cookie("AuthCookie");
                req.session.logged = true;
                req.session.authenticated = "Authenticated User";
                req.session.userInfo = users[i];
                logged = true;
            }
        }
    }

    if (logged) {
        res.redirect('/private');
    } else {
        if (!validUsername) {
            res.status(401).render('users/login', { title: "Login failed", error: "Please enter valid user name and password." });
        } else {
            res.status(401).render('users/login', { title: "Login failed", error: "Please enter valid password." });
        }

    }
})

const middleware = (req, res, next) => {
    if (!req.session.logged) {
        res.status(403).render("users/login", { title: "Login failed", error: "Access denied." });
    } else {
        next();
    }
}
router.get('/private', middleware, async(req, res) => {
    try {
        res.render('users/private', { title: "Details Page", userInfo: req.session.userInfo });
    } catch (error) {
        res.status(403).render("user/private", { error: "Access denied." });
    }
})

router.get('/logout', async(req, res) => {
    res.clearCookie("AuthCookie");
    req.session.logged = false;
    req.session.authenticated = "Non-Authenticated User";
    res.render("users/logout", { title: 'Logout Page' });
});

module.exports = router;