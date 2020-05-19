const express = require('express');
const bluebird = require('bluebird');
const redis = require('redis');


const app = express();
const configRoutes = require('./routes');


bluebird.promisifyAll(redis.RedisClient.prototype);
bluebird.promisifyAll(redis.Multi.prototype);

configRoutes(app);

app.listen(3000, () => {
    console.log("We've now got a server!");
    console.log('Your routes will be running on http://localhost:3000');
});