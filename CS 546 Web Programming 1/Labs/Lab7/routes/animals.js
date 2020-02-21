const express = require('express');
const router = express.Router();
const animalsData = require('../data/animals');

router.get('/:id', async (req, res) => {
    try {
        let animal = await animalsData.get(req.params.id);
        res.status(200).json(animal);
    } catch (e) {
        res.status(404).json({ error: 'Animal not found' });
    }
});

router.get('/', async (req, res) => {
    try {
        let animalsList = await animalsData.getAll()
        res.json(animalsList);
    } catch (e) {
        res.sendStatus(500);
    }
});

router.post('/', async (req, res) => {
    let animalInfo = req.body;

    if (!animalInfo) {
        res.status(400).json({ error: 'You must provide data to create a user' });
        return;
    }

    if (!animalInfo.name) {
        res.status(400).json({ error: 'You must provide a name' });
        return;
    }

    if (!animalInfo.animalType) {
        res.status(400).json({ error: 'You must provide an animalType' });
        return;
    }

    try {
        const newAnimal = await animalsData.create(animalInfo.name, animalInfo.animalType);
        res.status(200).json(newAnimal);
    } catch (e) {
        res.sendStatus(500);
    }
});

router.put('/:id', async (req, res) => {
    let animalInfo = req.body;

    if (!animalInfo) {
        res.status(400).json({ error: 'You must provide data to update an animal.' });
        return;
    }

    if (!animalInfo.newName && !animalInfo.newType) {
        res.status(400).json({ error: 'You must provide either name or animal type' });
        return;
    }

    try {
        await animalsData.get(req.params.id);
    } catch (e) {
        res.status(404).json({ error: 'Animal not found' });
        return;
    }
    try {
        const updatedUser = await animalsData.rename(req.params.id, animalInfo.newName, animalInfo.newType);
        res.json(updatedUser);
    } catch (e) {
        res.sendStatus(500);
    }
});

router.delete('/:id', async (req, res) => {
    try {
      await animalsData.get(req.params.id);
    } catch (e) {
        res.status(404).json({ error: 'Animal not found' });
        return;
    }

    try {
        const removedData = await animalsData.remove(req.params.id);
        res.json(removedData);
    } catch (e) {
        res.sendStatus(500);
    }
});

module.exports = router;