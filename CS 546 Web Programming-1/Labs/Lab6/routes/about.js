const express = require("express");
const router = express.Router();


router.get('/', async (req,res) =>{

    const about ={
        "name": "Kunj Desai",
        "cwid": "10444511",
        "biography": "I am from India and I did my bachelors in Computer Engineering. After that I worked in an IT firm for a year and then I decided to do my Masters' in Computer Science which brought me here at Stevens Institute of Technology.\n My personal experince says that a user interface is like a joke. If you have to explain it, it not that good.",
        "favoriteShows": ["Walking dead", "Stranger Thing", "Agents of Shield", "Riverdale"],
        "hobbies": ["Cricket", "Traveling", "Reading","Gymming"]
    }
    res.json(about);
});

module.exports = router;