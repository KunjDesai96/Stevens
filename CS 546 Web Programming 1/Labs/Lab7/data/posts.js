//Reference taken from lecture notes.
const mongoCollections = require("../config/mongoCollections");
const post = mongoCollections.posts;
const animalsData = require("./animals");
// Creating a variable for ObjectId
var ObjectId = require('mongodb').ObjectId;   

// Creating a post
const addPost = async function addPost(title, author, content)
{
    if(!title || typeof title !== 'string') throw "You must enter a valid string title."
    if(!content || typeof content !== 'string') throw "You must enter a valid string content."
    if(!author || typeof author !== 'string'|| !ObjectId.isValid(author)) throw "You must provide a valid string author id.";

    const postCollection = await post();

    const newPostInfo = {
        title: title,
        author: author,
        content: content
    }

    const insertInfo = await postCollection.insertOne(newPostInfo);
    const newId = insertInfo.insertedId.toString();
    if (insertInfo.insertedCount === 0) throw "Could not add post";

    await animalsData.addPostToUser(author, newId, title);

    const newPost = await getPostById(newId); 

    return newPost;
    
    
}

/*
Reading post
*/

//Getting all post
const getAllPosts = async function getAllPosts()
{
    const postCollection = await post();

    const postsAll = await postCollection.find({}).toArray();

    const result = [];
    for(element of postsAll)
    {
      result.push(await getPostById(element._id.toString()));
    }
    return result;
}

//Getting post by id

const getPostById = async function getPostById(id)
{
    if(!id || typeof id !== 'string'|| !ObjectId.isValid(id)) throw "You must provide a valid post id.";

    var o_id = new ObjectId(id);
    const postCollection = await post();

    const postOne = await postCollection.findOne({_id:o_id});

    if(postOne === null) throw "No post with that id";

    const authorID = postOne.author.toString();
 
    const poster = await animalsData.get(authorID);

    const result = 
    {
      "_id": postOne._id,
      "title": postOne.title,
      "content": postOne.content,
      "author":
      {
        "_id": poster._id,
        "name": poster.name
      }
    };
  

    return result;

}



/* 
Update
*/

const updatePost = async function updatePost(id, newTitle, newContent)
{
    if(!id || typeof id !== 'string'|| !ObjectId.isValid(id)) throw "You must provide a valid string post id.";
    const recentPost = await this.getPostById(id);
    if(!newTitle)
    {
      newTitle = recentPost.title;
    }

    if(!newContent)
    {
      newContent = recentPost.content;
    }
    if(typeof newTitle !== 'string') throw "You must enter a valid string title."
    if(typeof newContent !== 'string') throw "You must enter a valid string content."

    const postCollection = await post();
    var o_id = new ObjectId(id);
    let updatedPost = {
        title: newTitle,
        author: recentPost.author._id.toString(),
        content: newContent
      };

      const updatedInfo = await postCollection.replaceOne(
        { _id: o_id },
        updatedPost
      );
      if (updatedInfo.modifiedCount === 0) {
        throw "could not update post successfully";
      }
  
      return await this.getPostById(id);
}
/*
Delete
*/
const removePost = async function removePost(id)
{
    if (!id || typeof id !== 'string' || !ObjectId.isValid(id)) throw "You must provide a valid string id to remove for";

    var o_id = new ObjectId(id);
    const postCollection = await post();
    const deletedPost = await this.getPostById(id);

    await animalsData.removePostFromAnimal(deletedPost.author._id, deletedPost._id);
    await animalsData.removeLikeFromAnimal(deletedPost.author._id, deletedPost._id);

  
    const deleteInfo = await postCollection.findOneAndDelete({_id:o_id});
    if (deleteInfo.value === null) {
        throw `Could not delete animal with id of ${id}`;
    }
    

    const resDelete = {
      "deleted": true,
      "data": deletedPost
    }
    return resDelete; 
}

//exporting funtions
module.exports = {
  addPost,
  getAllPosts,
  getPostById,
  updatePost,
  removePost,
  
}