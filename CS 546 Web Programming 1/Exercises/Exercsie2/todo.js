const mongoCollections = require("./config/mongoCollections");
const todoItems = mongoCollections.todoItems;
var ObjectId = require('mongodb').ObjectId;   

async function createTask(title, description)
{
    if(!title || typeof title !== 'string') throw "You must provide a valid string title";

    if(!description || typeof description !== 'string') throw "You must provide a valid string description";

    const todoCollections = await todoItems();

    let todoNew = 
    {
        title,
        description,
        completed: false,
        completedAt: null
    }

    const insertInfo = await todoCollections.insertOne(todoNew);

    if(insertInfo.insertedCount === 0) throw "Could not add todo";

    const id = insertInfo.insertedId.toString();

    const todoObject = await this.getTask(id);

    return todoObject;
}

async function getAllTask()
{
    const todoCollections = await todoItems();

    const todoAll = await todoCollections.find({}).toArray();

    return todoAll;
}

async function getTask(id)
{
    if (!id || typeof id !== 'string'|| !ObjectId.isValid(id)) throw "You must provide a valid string id to search for";
    var o_id = new ObjectId(id);
    const todoCollections = await todoItems();

    const todoOne = await todoCollections.findOne({_id:o_id});
    if(todoOne === null) throw "No animal with that id";

    return todoOne;
}

async function completeTask(taskId)
{
    var o_id = new ObjectId(taskId)
    let update =
    {
        $set:
        {
            completed: true,
            completedAt: new Date()
        }  
    };
    const todoCollections = await todoItems();
    const updateInfo = await todoCollections.updateOne({_id:o_id},update);

    if(updateInfo.modifiedCount === 0)
    {
        throw `Could not update todo name with id of ${taskId}`;
    }

    return this.getTask(taskId);
    
}


async function removeTask(id)
{
    if (!id || typeof id !== 'string' || !ObjectId.isValid(id)) throw "You must provide a valid string id to remove for";

    var o_id = new ObjectId(id);
    const todoCollections = await todoItems();
    const deleteInfo = await todoCollections.findOneAndDelete({_id:o_id});
    if (deleteInfo.value === null) {
        throw `Could not delete animal with id of ${id}`;
    }
    return deleteInfo.value;
}

module.exports = 
{
    createTask,
    getAllTask,
    getTask,
    completeTask,
    removeTask
}