const express = require('express');
const router = express.Router();
const tasksData = require('../data/tasks');

router.get("/tasks/:id", async(req, res) => {
    try {
        const task = await tasksData.getTaskByID(req.params.id);
        res.status(200).json(task);
    } catch (e) {
        res.status(404).json({ message: "Task not found" });
    }
});

router.get("/tasks", async(req, res) => {

    try {
        const allPosts = await tasksData.getAllTasks();
        const finalResult = [];

        if (req.query.skip) {
            if (allPosts.length < req.query.skip) {
                finalResult = [];
            } else {
                for (var i = req.query.skip; i < allPosts.length && finalResult.length < 100; i++) {
                    finalResult.push(allPosts[i]);
                }
            }
        } else if (req.query.take) {
            if (req.query.take > allPosts.length) {
                for (element in allPosts) {
                    if (element < 100) {
                        finalResult.push(allPosts[element]);
                    }
                }
            } else {
                for (var j = 0; j < req.query.take && finalResult.length < 100; j++) {
                    finalResult.push(allPosts[j]);
                }
            }

        } else {
            if (allPosts.length < 20) {
                for (element in allPosts) {
                    finalResult.push(allPosts[element]);
                }
            } else {
                for (var k = 0; k < 20; k++) {
                    finalResult.push(allPosts[k]);
                }
            }

        }
        res.json(finalResult);
    } catch (e) {
        res.status(500).send();
    }

});


router.post('/tasks', async(req, res) => {

    let taskInfo = req.body;

    if (!taskInfo) {
        res.status(400).json({ error: 'You must provide data to create a task' });
        return;
    }

    if (!taskInfo.title) {
        res.status(400).json({ error: 'You must provide a title' });
        return;
    }

    if (!taskInfo.description) {
        res.status(400).json({ error: 'You must provide a description' });
        return;
    }

    if (!taskInfo.hoursEstimated) {
        res.status(400).json({ error: 'You must provide hoursEstimated' });
        return;
    }

    try {
        const newTask = await tasksData.createTask(taskInfo.title, taskInfo.description, taskInfo.hoursEstimated, taskInfo.completed);
        res.status(200).json(newTask);
    } catch (e) {
        res.sendStatus(500);
    }
});


router.put('/tasks/:id', async(req, res) => {

    let newtaskInfo = req.body;

    if (!newtaskInfo) {
        res.status(400).json({ error: 'You must provide data to create a task' });
        return;
    }

    if (!newtaskInfo.title) {
        res.status(400).json({ error: 'You must provide a title' });
        return;
    }

    if (!newtaskInfo.description) {
        res.status(400).json({ error: 'You must provide a description' });
        return;
    }

    if (!newtaskInfo.hoursEstimated) {
        res.status(400).json({ error: 'You must provide hoursEstimated' });
        return;
    }

    if (newtaskInfo.completed === undefined) {
        res.status(400).json({ error: 'You must provide completed field' });
        return;
    }
    try {
        const newTask = await tasksData.updateTaskByPut(req.params.id, newtaskInfo.title, newtaskInfo.description, newtaskInfo.hoursEstimated, newtaskInfo.completed);
        res.status(200).json(newTask);
    } catch (e) {
        res.sendStatus(500);
    }
});


router.patch('/tasks/:id', async(req, res) => {

    let newtaskInfo = req.body;
    let currentTaskInfo = {};
    try {
        currentTaskInfo = await tasksData.getTaskByID(req.params.id);

    } catch (e) {
        res.status(404).json({ error: 'Task not found' });
        return;
    }
    if (!newtaskInfo) {
        res.status(400).json({ error: 'You must provide data to create a task' });
        return;
    }

    if (!newtaskInfo.title) {
        newtaskInfo.title = currentTaskInfo.title;
    }

    if (!newtaskInfo.description) {
        newtaskInfo.description = currentTaskInfo.description;
    }

    if (!newtaskInfo.hoursEstimated) {
        newtaskInfo.hoursEstimated = currentTaskInfo.hoursEstimated;
    }

    if (newtaskInfo.completed === undefined) {
        newtaskInfo.completed = currentTaskInfo.completed;
    }
    try {
        const newTask = await tasksData.updateTaskByPut(req.params.id, newtaskInfo.title, newtaskInfo.description, newtaskInfo.hoursEstimated, newtaskInfo.completed);
        res.status(200).json(newTask);
    } catch (e) {
        res.sendStatus(500);
    }
});

router.post("/tasks/:id/comments", async(req, res) => {
    const commentInfo = req.body;

    if (!commentInfo) {
        res.status(400).json({ error: 'You must provide data to create a task' });
        return;
    }

    if (!req.params.id) {
        res.status(400).json({ error: 'You must provide an id' });
        return;
    }

    if (!commentInfo.name) {
        res.status(400).json({ error: 'You must provide a name' });
        return;
    }

    if (!commentInfo.comment) {
        res.status(400).json({ error: 'You must provide a comment' });
        return;
    }

    try {
        const newComment = await tasksData.createComment(req.params.id, commentInfo.name, commentInfo.comment);
        res.status(200).json(newComment);
    } catch (e) {
        res.status(400).json({ error: e });
    }
});

router.delete('/tasks/:taskId/:commentId', async(req, res) => {
    try {
        await tasksData.getCommentByID(req.params.commentId);
    } catch (e) {
        res.status(404).json({ error: 'Comment not found' });
        return;
    }
    try {
        const deletedData = await tasksData.deleteComment(req.params.taskId, req.params.commentId)
        res.json(deletedData);
    } catch (e) {
        res.status(500).json({ error: e });
    }
});
module.exports = router;