const express = require("express");
const router = express.Router();
const data = require("../data");

router.get('/', async (req, res) => {
    const myInfo ={
        name: 'Kunj Desai',
        dob: '11/28',
        hometown: 'NJ'
    }
    res.json(myInfo);
});

module.exports = router;