const express = require("express");
const app = express();
const configRoutes = require("./routes");

var bodyParser = require("body-parser");
app.use(bodyParser.json());
var urlVisisted = [];
app.use(function (req, res, next) {
  if (req.originalUrl in urlVisisted) urlVisisted[req.originalUrl] += 1;
  else urlVisisted[req.originalUrl] = 1;

  console.log("Request Body: " + JSON.stringify(req.body));
  console.log("URL path: " + req.originalUrl);
  console.log("HTTP verb: " + req.method);
  next();
});

app.use(function (req, res, next) {
  console.log(
    "URL " +
      req.originalUrl +
      " has been requested for " +
      urlVisisted[req.originalUrl] +
      " times."
  );
  console.log(urlVisisted);
  next();
});

configRoutes(app);
app.listen(3000, () => {
  console.log("We've now got a server!");
  console.log("Your routes will be running on http://localhost:3000");
});
