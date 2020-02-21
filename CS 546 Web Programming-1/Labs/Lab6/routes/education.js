const express = require("express");
const router = express.Router();

router.get("/", async(req, res) =>{

    const education = [{
        "schoolName": "Parth Concept School",
        "degree": "High School Diploma",
        "favoriteClass": "My favorite class in this school was Mathematics class because I always loved solving problems.",
        "favoriteMemory": "One of my memory for this school is that we always use to have surprise test but one day our principal came and announced that we were having a surprise picnic."
    },
    {
        "schoolName": "Institue of Technology and Management",
        "degree": "Bachelor's Degree",
        "favoriteClass": "One of my favorite class was Theory of Computation as I learned a lot of things and also, I topped that class.",
        "favoriteMemory": "One of my memory for this school is that we took part in Hackathon event and we won the first prise making a face detection software."
    },
    {
        "schoolName": "Stevens Institute of Technology",
        "degree": "Masters' Degree",
        "favoriteClass": "My favorite class is Web Programming because I am going to learn how to make a full stack website.",
        "favoriteMemory": "One of my memory for this school is that I got 100% in one of the courses."
    }]

    res.json(education);
});

module.exports = router;