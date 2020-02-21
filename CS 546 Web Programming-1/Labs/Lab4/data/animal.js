//Reference taken from lecture notes.
const mongoCollections = require("../config/mongoCollections");
const animals = mongoCollections.animals;
// Creating a variable for ObjectId
var ObjectId = require('mongodb').ObjectId;     


//Implemetation of create function.
async function create(name, animalType)
{
    if(!name || typeof name !== 'string') throw "You must provide a valid string name for your animal";

    if(!animalType || typeof animalType !== 'string') throw "You must provide a valid string animal type for your animal";

    const animalCollection = await animals();
    
    let newAnimal = {
        name,
        animalType
    }

    const insertInfo = await animalCollection.insertOne(newAnimal);

    if(insertInfo.insertedCount === 0) throw "Could not add animal";

    const id = insertInfo.insertedId.toString();

    const animalObject = await this.get(id);

    return animalObject;

}

//Implementation of getAll() function.
const getAll = async function getAll()
{
    const animalCollection = await animals();

    const animalAll = await animalCollection.find({}).toArray();

    return animalAll;
}

//Implementation  of get(id) function.
const get = async function get(id)
{
    if (!id || typeof id !== 'string'|| !ObjectId.isValid(id)) throw "You must provide a valid string id to search for";
    var o_id = new ObjectId(id);
    const animnalCollection = await animals();

    const animalOne = await animnalCollection.findOne({_id:o_id});
    if(animalOne === null) throw "No animal with that id";

    return animalOne;
}


//Implemetation of remove(id) funtion.
const remove = async function remove(id)
{
    if (!id || typeof id !== 'string' || !ObjectId.isValid(id)) throw "You must provide a valid string id to remove for";

    var o_id = new ObjectId(id);
    const animalCollection = await animals();
    const deleteInfo = await animalCollection.findOneAndDelete({_id:o_id});
    if (deleteInfo.value === null) {
        throw `Could not delete animal with id of ${id}`;
      }
    return deleteInfo.value;
}

//Implementation of rename(id, newName) function.
const rename = async function rename(id, newName)
{
    if(!id || typeof id !== 'string' || !ObjectId.isValid(id)) throw "You must provide a valid string id for renaming";
    if(!newName || typeof newName !== 'string') throw "You must provide a valid string name to repalce with";

    var recentAnimal = await this.get(id);
    const animalCollection = await animals();

    let updateInfo = await animalCollection.updateOne({_id:recentAnimal._id}, {$set: { "name" : newName }});

    if(updateInfo.modifiedCount === 0)
    {
        throw `Could not update animal name with id of ${id}`;
    }

    return await this.get(id);
}

//exporting funtions
module.exports = {
    create,
    getAll,
    get,
    remove,
    rename
}