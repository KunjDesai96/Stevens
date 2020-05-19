import app from "./app";
const PORT = process.env.PORT || 3000;
// import { dbconnection } from "./config/mongoConnection";
// import settings = require("./config/settings.json");
// var mongoConfig = settings.mongoConfig;

app.listen(PORT, () => {
  console.log("We've now got a server with port " + PORT);
  console.log("Your routes will be running on https://localhost:3000");
});

// app.on("listening", async () => {
//   try {
//     await dbconnection.connect(mongoConfig.serverUrl);
//     console.log("DB Connection Done!");
//   } catch (err) {
//     console.log(err);
//   }
// });
