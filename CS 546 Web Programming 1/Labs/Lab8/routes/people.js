const express = require('express');
const router = express.Router();
const data = require('../data');
const peopleData = data.people;

router.get('/', async (req,res) => {
    res.render('people/new', {title : "People Finder"});
});

router.post('/search', async(req,res)  =>{
    const bodyObj = req.body;
    let errors = [];
    if(!bodyObj.personName)
    {
        errors.push("No name provided");
    }

    if (errors.length > 0) {
        res.status(400).render('people/error', {title: "Error"});
        return;
      }

    try {
        const  peopleFound = await  peopleData.getPersonByName(bodyObj.personName);
        res.render('people/data',{peopleFound: peopleFound, title : "People Found", name: bodyObj.personName } );    
    } catch (error) {
        res.status(404).json({error: error});
    }
   
})

router.get('/details/:id', async(req, res) => {
    const personId = Number(req.params.id); 
    try {
        const personDetails = await peopleData.getPersonById(personId);
        const personName = personDetails.firstName + " " + personDetails.lastName;
        res.render('people/person',{personDetails: personDetails, title: "Person Found", personName: personName});
    } catch (error) {
        res.status(404).json({error: error});
    }
 
})


module.exports = router;