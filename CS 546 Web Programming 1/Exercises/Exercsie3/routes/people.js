const express = require("express");
const router = express.Router();
const data = require("../data");
const peopletData = data.people;

router.get("/:id", async (req, res) => {
  try {
    const person = await peopletData.getPersonById(req.params.id);
    res.json(person);
  } catch (e) {
    res.status(404).json({ message: "Post not found" });
  }
});

router.get("/", async (req, res) => {
  try {
    const peopleList = await peopletData.getPeople();
    res.json(peopleList);
  } catch (e) {
    res.status(500).send();
  }
});

router.post("/", async (req, res) => {
  // Not implemented
  res.status(501).send();
});

module.exports = router;