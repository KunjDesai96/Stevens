import { dbcollection } from "../config/mongoCollection";

const tasks = dbcollection.getCollectionFn("tasks");
const comments = dbcollection.getCollectionFn("comments");
var ObjectId = require("mongodb").ObjectId;

class Tasks {
  createTask = async function createTask(title, description, hoursEstimated) {
    if (!title || typeof title != "string") throw "Please enter valid title";
    if (!description || typeof description != "string")
      throw "Please enter valid description";
    if (!hoursEstimated || typeof hoursEstimated != "number")
      throw "Please enter valid hoursEstimated";

    const tasksCollection = await tasks();

    let newTask = {
      title: title,
      description: description,
      hoursEstimated: hoursEstimated,
      completed: false,
      comments: [],
    };

    const insertInfo = await tasksCollection.insertOne(newTask);

    if (insertInfo.insertedCount === 0) throw "Could not add task";

    return newTask;
  };

  getTaskByID = async function getTaskByID(id) {
    if (!id || typeof id !== "string" || !ObjectId.isValid(id))
      throw "You must provide a valid post id.";

    var o_id = new ObjectId(id);
    const tasksCollection = await tasks();

    const oneTask = await tasksCollection.findOne({ _id: o_id });

    if (oneTask === null) throw "No task with that id";

    return oneTask;
  };

  getAllTasks = async function getAllTasks() {
    const tasksCollection = await tasks();
    const allTasks = await tasksCollection.find({}).toArray();
    const result = [];
    let element: any;
    for (element of allTasks) {
      result.push(await this.getTaskByID(element._id.toString()));
    }
    return result;
  };

  createComment = async function createComment(id, name, comment) {
    if (!id || typeof id !== "string" || !ObjectId.isValid(id))
      throw "You must provide a valid task id.";
    if (!name || typeof name != "string") throw "Please enter valid name";
    if (!comment || typeof comment != "string")
      throw "Please enter valid comment";

    const tasksCollection = await tasks();
    const commentsCollection = await comments();

    let newComment = {
      name: name,
      comment: comment,
    };

    const insertInfo = await commentsCollection.insertOne(newComment);

    if (insertInfo.insertedCount === 0) throw "Could not add comment";

    var o_id = new ObjectId(id);

    const updateInfo = await tasksCollection.update(
      { _id: o_id },
      { $push: { comments: newComment } }
    );

    if (!updateInfo.result.n && !updateInfo.result.nModified)
      throw "Update failed";

    return newComment;
  };

  getCommentByID = async function getCommentByID(id) {
    if (!id || typeof id !== "string" || !ObjectId.isValid(id))
      throw "You must provide a valid post id.";

    var o_id = new ObjectId(id);
    const commentsCollection = await comments();

    const oneComment = await commentsCollection.findOne({ _id: o_id });

    if (oneComment === null) throw "No task with that id";

    return oneComment;
  };

  updateTaskByPut = async function updateTaskByPut(
    id,
    title,
    description,
    hoursEstimated,
    completed
  ) {
    if (!id || typeof id !== "string" || !ObjectId.isValid(id))
      throw "You must provide a valid post id.";
    if (!title || typeof title != "string") throw "Please enter valid title";
    if (!description || typeof description != "string")
      throw "Please enter valid description";
    if (!hoursEstimated || typeof hoursEstimated != "number")
      throw "Please enter valid hoursEstimated";
    if (completed === undefined || typeof completed != "boolean")
      throw "Please enter valid completed value";

    const tasksCollection = await tasks();
    var o_id = new ObjectId(id);
    let recentTask = await this.getTaskByID(id);

    let updateTask = {
      title: title,
      description: description,
      hoursEstimated: hoursEstimated,
      completed: completed,
      comments: recentTask.comments,
    };

    const updatedInfo = await tasksCollection.replaceOne(
      { _id: o_id },
      updateTask
    );

    if (updatedInfo.modifiedCount === 0) {
      throw "could not update  successfully";
    }
    let recentNewTask = await this.getTaskByID(id);
    return recentNewTask;
  };

  deleteComment = async function deleteComment(taskid, commentid) {
    if (!taskid || typeof taskid !== "string" || !ObjectId.isValid(taskid))
      throw "You must provide a valid task id.";
    if (
      !commentid ||
      typeof commentid !== "string" ||
      !ObjectId.isValid(commentid)
    )
      throw "You must provide a valid comment id.";

    var t_o_id = new ObjectId(taskid);
    var c_o_id = new ObjectId(commentid);

    const tasksCollection = await tasks();
    const commentsCollection = await comments();

    const deletedComment = await this.getCommentByID(commentid);

    const deleteInfo = await commentsCollection.findOneAndDelete({
      _id: c_o_id,
    });
    if (deleteInfo.value === null) {
      throw `Could not delete animal with id`;
    }

    const updateInfo = await tasksCollection.updateOne(
      { _id: t_o_id },
      { $pull: { comments: { _id: c_o_id } } }
    );
    if (!updateInfo.modifiedCount) throw "Update failed";

    return deletedComment;
  };
}

export default new Tasks();
