const express = require('express');
const router = express.Router();
const redis = require('redis');
const client = redis.createClient();
const data = require("../data/data")
let lastestData = [];


router.get("/people/history/", async(req, res) => {
    let historyData = [];
    let currentLatestData = null;
    for (var i = 0; i < 20 && i < lastestData.length; i++) {
        currentLatestData = await client.getAsync(lastestData[i]);
        historyData.unshift(JSON.parse(currentLatestData));
    }
    res.status(200).json(historyData);
});

router.get("/people/:id", async(req, res) => {
    const id = Number(req.params.id);
    const cacheData = await client.getAsync(id);

    if (cacheData) {
        lastestData.push(id);
        res.status(200).json(JSON.parse(cacheData));
    } else {
        try {
            personData = await data.getById(id);
            lastestData.push(id);
            res.status(200).json(personData);
            await client.setAsync(id, JSON.stringify(personData));

        } catch (error) {
            res.status(404).json({ message: error.message });
        }

    }

});




module.exports = router;