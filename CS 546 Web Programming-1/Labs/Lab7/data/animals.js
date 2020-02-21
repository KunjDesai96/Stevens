//Reference taken from lecture notes.
const mongoCollections = require("../config/mongoCollections");
const animals = mongoCollections.animals;
const postData = mongoCollections.posts;
// Creating a variable for ObjectId
var ObjectId = require('mongodb').ObjectId;

//Implemetation of create function.
const create = async function create(name, animalType) {
    if (!name || typeof name !== 'string') throw "You must provide a valid string name for your animal";

    if (!animalType || typeof animalType !== 'string') throw "You must provide a valid string animal type for your animal";

    const animalCollection = await animals();

    let newAnimal = {
        name: name,
        animalType: animalType,
        likes: [],
        posts: []
    }

    const insertInfo = await animalCollection.insertOne(newAnimal);

    if (insertInfo.insertedCount === 0) throw "Could not add animal";

    const id = insertInfo.insertedId.toString();

    const animalObject = await this.get(id);

    return animalObject;

}

//Implementation of getAll() function.
const getAll = async function getAll() {
    const animalCollection = await animals();

    const animalAll = await animalCollection.find({}).toArray();

    const resultArr = [];
    for (element of animalAll) {
        resultArr.push(await this.get(element._id.toString()));
    }
    return resultArr;
}

//Implementation  of get(id) function.
const get = async function get(id) {
    if (!id || typeof id !== 'string' || !ObjectId.isValid(id)) throw "You must provide a valid string id to search for";
    var o_id = new ObjectId(id);
    const animnalCollection = await animals();
    const postCollection = await postData();
    const animalOne = await animnalCollection.findOne({ _id: o_id });
    var post_o_id = [], postObjArr = [];
    var like_o_id = [], likeObjArr = [];
    var animalPostArr = animalOne.posts;
    var animalLikeArr = animalOne.likes;

    animalPostArr.forEach(element => {
        post_o_id.push(new ObjectId(element.postId.toString()));
    });
    animalLikeArr.forEach(element => {
        like_o_id.push(new ObjectId(element.postId.toString()));
    });
    for (element of post_o_id) {
        const postByAnimal = await postCollection.findOne({ _id: element });
        const postObj = { "_id": postByAnimal._id, "title": postByAnimal.title };
        postObjArr.push(postObj);
    }
    for (element of like_o_id) {
        const postByAnimal = await postCollection.findOne({ _id: element });
        const postObj = { "_id": postByAnimal._id, "title": postByAnimal.title };
        likeObjArr.push(postObj);
    }
    if (animalOne === null) throw "No animal with that id";

    const result =
    {
        "_id": animalOne._id,
        "name": animalOne.name,
        "animalType": animalOne.animalType,
        "likes": likeObjArr,
        "posts": postObjArr
    }
    return result;
}

//Implemetation of remove(id) funtion.
const remove = async function remove(id) {
    if (!id || typeof id !== 'string' || !ObjectId.isValid(id)) throw "You must provide a valid string id to remove for";

    var o_id = new ObjectId(id);
    const animalCollection = await animals();
    const postCollection = await postData();
    const deletedAnimal = await get(id);
    const postArr = deletedAnimal.posts;
    for (element of postArr) {
        await postCollection.findOneAndDelete({ _id: element._id });
    }
    const deleteInfo = await animalCollection.findOneAndDelete({ _id: o_id });
    if (deleteInfo.value === null) {
        throw `Could not delete animal with id of ${id}`;
    }
    const deleteStatus =
    {
        "deleted": true,
        "data": deletedAnimal
    }
    return deleteStatus;
}

//Implementation of rename(id, newName) function.
const rename = async function rename(id, newName, newType) {
    if (!id || typeof id !== 'string' || !ObjectId.isValid(id)) throw "You must provide a valid string id for renaming";
    var recentAnimal = await this.get(id);
    if (!newName) {
        newName = recentAnimal.name;
    }
    if (!newType) {
        newType = recentAnimal.animalType;
    }

    if (typeof newName !== 'string') throw "You must provide a valid string name to repalce with";
    if (typeof newType !== 'string') throw "You must provide a valid string animal type to repalce with";
    const animalCollection = await animals();

    var o_id = new ObjectId(id);
    let updatedAnimal = {
        name: newName,
        animalType: newType,
        likes: recentAnimal.likes,
        posts: recentAnimal.posts
    };

    const updatedInfo = await animalCollection.replaceOne(
        { _id: o_id },
        updatedAnimal
    );
    if (updatedInfo.modifiedCount === 0) {
        throw "could not update  successfully";
    }

    return await this.get(id);

}




const addPostToUser = async function addPostToAnimal(animalId, postId) {
    const animnalCollection = await animals();
    var o_id = new ObjectId(animalId);
    const updateInfo = await animnalCollection.updateOne(
        { _id: o_id },
        { $addToSet: { posts: { postId } } }
    );

    if (!updateInfo.matchedCount && !updateInfo.modifiedCount) throw 'Update failed';

    return await this.get(animalId);
}
const removePostFromAnimal = async function removePostFromAnimal(animalId, removeID) {
    const animalCollection = await animals();
    const updateInfo = await animalCollection.updateOne({ _id: animalId }, { $pull: { posts: { postId: removeID.toString() } } });
    if (!updateInfo.matchedCount && !updateInfo.modifiedCount) throw 'Update failed';

    return await this.get(animalId.toString());
}


const addLiketoAnimal = async function addLiketoAnimal(animalId, postId) {
    const animnalCollection = await animals();
    var o_id = new ObjectId(animalId);
    const animalOne = await this.get(animalId);
    var flag = 0;

    animalOne.likes.forEach(element => {
        if (postId === element._id.toString()) {
            flag = 1;
        }
    });
    if (flag == 1) {
        return true;
    }
    else {
        const updateInfo = await animnalCollection.updateOne(
            { _id: o_id },
            { $addToSet: { likes: { postId } } }
        );

        if (!updateInfo.matchedCount && !updateInfo.modifiedCount) throw 'Update failed';

        return true;
    }

}

const removeLikeFromAnimal = async function removePostFromAnimal(animalId, removeID) {
    const animalCollection = await animals();
    const animalOne = await this.get(animalId.toString());
    var flag = 0;

    animalOne.likes.forEach(element => {
        if (removeID.toString() === element._id.toString()) {
            flag = 1;
        }
    });
    if (flag == 1) {
        const updateInfo = await animalCollection.updateOne({ _id: new ObjectId(animalId) }, { $pull: { likes: { postId: removeID.toString()} } });
        if (!updateInfo.matchedCount && !updateInfo.modifiedCount) throw 'Update failed';

        return true;
    }
    else {
        return true
    }

}
//exporting funtions
module.exports = {
    create,
    getAll,
    get,
    remove,
    rename,
    addPostToUser,
    removePostFromAnimal,
    addLiketoAnimal,
    removeLikeFromAnimal

}




