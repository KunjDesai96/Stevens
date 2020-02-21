const express = require("express");
const router = express.Router();

router.get("/", async(req, res) =>{
    
    const story = {
        "storyTitle": "The Automated Society",
        "story": "This is basically a story that was sparked off by my indignation at seeing Odeon cinemas employ a person just to make a little tear in everyone's tickets. That's all the person did, tear tickets.This ticket-tearer wasn't stupid, and she got paid for this. So what's wrong with that? \n I try to explain why I feel it's wrong, and I also talk about the Next Industrial Revolution, when the service industry will be mechanised and computerised. During the first Industrial Revolution, all the farmers and agricultural workers went into secondary (processing) and tertiary (service) industries. Where will all the service industry workers go now, and will they get trampled on, just like the workers did during the first Industrial Revolution?"
    }

    res.json(story);
});

module.exports = router;