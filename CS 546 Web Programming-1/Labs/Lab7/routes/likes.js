const express = require('express');
const router = express.Router();
const animalsData = require('../data/animals');
const postsData = require('../data/posts')

router.post('/:id', async (req, res) => {
    var animalId;
    var postId;
    try {
        animalId = req.params.id; 
        await animalsData.get(animalId);

    } catch (e) {
        res.status(404).json({ error: 'Animal not found' });
    }

    try{
        postId = req.query.postId;
        console.log(postId);
        await postsData.getPostById(postId);
    }catch (e) {
        res.status(404).json({ error: 'Post not found' });
    }

    try
    {
        await  animalsData.addLiketoAnimal(animalId, postId);
        res.sendStatus(200);
    }
    catch (e) {
        res.status(404).json({ error: 'Like not added' });
    }


});

router.delete('/:id', async (req, res) => {
    var animalId;
    var postId;
    try {
        animalId = req.params.id;
        await animalsData.get(animalId);

    } catch (e) {
        res.status(404).json({ error: 'Animal not found' });
    }

    try{    
        postId = req.query.postId;
        await postsData.getPostById(postId);
    }catch (e) {
        res.status(404).json({ error: 'Animal not found' });
    }

    try
    {
        await  animalsData.removeLikeFromAnimal(animalId, postId);
        res.sendStatus(200);
    }
    catch (e) {
        res.status(404).json({ error: 'Like not added' });
    }


});

module.exports = router;