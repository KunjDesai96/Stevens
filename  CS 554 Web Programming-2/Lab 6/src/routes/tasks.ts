import { Request, Response } from "express";
import * as express from "express";
const router = express.Router();
import tasksData from "../data/tasks";
export class TasksRoutes {
  public routes(app): void {
    //received the express instance from app.ts file
    app.use("/api", router);
    app.use(router);
    app.use("*", (req: Request, res: Response) => {
      res.status(404).json({ error: "Not found" });
    });

    router.get("/tasks/:id", async (req: Request, res: Response) => {
      try {
        const task = await tasksData.getTaskByID(req.params.id);
        res.status(200).json(task);
      } catch (e) {
        res.status(404).json({ message: "Task not found" });
      }
    });

    router.get("/tasks", async (req: Request, res: Response) => {
      try {
        const allPosts = await tasksData.getAllTasks();
        let postsLength: number = allPosts.length;
        var finalArr = [];
        if (req.query.skip) {
          let skip: number = parseInt(req.query.skip.toString());
          if (postsLength < skip) {
            finalArr = [];
          } else {
            for (var i = skip; i < postsLength && finalArr.length < 100; i++) {
              finalArr.push(allPosts[i]);
            }
          }
        } else if (req.query.take) {
          let take: number = parseInt(req.query.take.toString());
          if (take > postsLength) {
            for (var i = 0; i < postsLength; i++) {
              if (i < 100) {
                finalArr.push(allPosts[i]);
              }
            }
          } else {
            for (var j = 0; j < take && finalArr.length < 100; j++) {
              finalArr.push(allPosts[j]);
            }
          }
        } else {
          for (var i = 0; i < postsLength && i < 20; i++) {
            finalArr.push(allPosts[i]);
          }
        }

        res.status(200).json(finalArr);
      } catch (e) {
        res.status(500).send();
      }
    });

    router.post("/tasks", async (req: Request, res: Response) => {
      let taskInfo = req.body;

      if (!taskInfo) {
        res
          .status(400)
          .json({ error: "You must provide data to create a task" });
        return;
      }

      if (!taskInfo.title) {
        res.status(400).json({ error: "You must provide a title" });
        return;
      }

      if (!taskInfo.description) {
        res.status(400).json({ error: "You must provide a description" });
        return;
      }

      if (!taskInfo.hoursEstimated) {
        res.status(400).json({ error: "You must provide hoursEstimated" });
        return;
      }

      try {
        const newTask = await tasksData.createTask(
          taskInfo.title,
          taskInfo.description,
          taskInfo.hoursEstimated
        );
        res.status(200).json(newTask);
      } catch (e) {
        res.sendStatus(500);
      }
    });

    router.put("/tasks/:id", async (req: Request, res: Response) => {
      let newtaskInfo = req.body;

      if (!newtaskInfo) {
        res
          .status(400)
          .json({ error: "You must provide data to create a task" });
        return;
      }

      if (!newtaskInfo.title) {
        res.status(400).json({ error: "You must provide a title" });
        return;
      }

      if (!newtaskInfo.description) {
        res.status(400).json({ error: "You must provide a description" });
        return;
      }

      if (!newtaskInfo.hoursEstimated) {
        res.status(400).json({ error: "You must provide hoursEstimated" });
        return;
      }

      if (newtaskInfo.completed === undefined) {
        res.status(400).json({ error: "You must provide completed field" });
        return;
      }
      try {
        const newTask = await tasksData.updateTaskByPut(
          req.params.id,
          newtaskInfo.title,
          newtaskInfo.description,
          newtaskInfo.hoursEstimated,
          newtaskInfo.completed
        );
        res.status(200).json(newTask);
      } catch (e) {
        res.sendStatus(500);
      }
    });

    router.patch("/tasks/:id", async (req: Request, res: Response) => {
      let newtaskInfo = req.body;
      let currentTaskInfo = {
        title: undefined,
        description: undefined,
        hoursEstimated: undefined,
        completed: undefined,
      };
      try {
        currentTaskInfo = await tasksData.getTaskByID(req.params.id);
      } catch (e) {
        res.status(404).json({ error: "Task not found" });
        return;
      }
      if (!newtaskInfo) {
        res
          .status(400)
          .json({ error: "You must provide data to create a task" });
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
        const newTask = await tasksData.updateTaskByPut(
          req.params.id,
          newtaskInfo.title,
          newtaskInfo.description,
          newtaskInfo.hoursEstimated,
          newtaskInfo.completed
        );
        res.status(200).json(newTask);
      } catch (e) {
        res.sendStatus(500);
      }
    });

    router.post("/tasks/:id/comments", async (req: Request, res: Response) => {
      const commentInfo = req.body;

      if (!commentInfo) {
        res
          .status(400)
          .json({ error: "You must provide data to create a task" });
        return;
      }

      if (!req.params.id) {
        res.status(400).json({ error: "You must provide an id" });
        return;
      }

      if (!commentInfo.name) {
        res.status(400).json({ error: "You must provide a name" });
        return;
      }

      if (!commentInfo.comment) {
        res.status(400).json({ error: "You must provide a comment" });
        return;
      }

      try {
        const newComment = await tasksData.createComment(
          req.params.id,
          commentInfo.name,
          commentInfo.comment
        );
        res.status(200).json(newComment);
      } catch (e) {
        res.status(400).json({ error: e });
      }
    });

    router.delete(
      "/tasks/:taskId/:commentId",
      async (req: Request, res: Response) => {
        try {
          await tasksData.getCommentByID(req.params.commentId);
        } catch (e) {
          res.status(404).json({ error: "Comment not found" });
          return;
        }
        try {
          const deletedData = await tasksData.deleteComment(
            req.params.taskId,
            req.params.commentId
          );
          res.json(deletedData);
        } catch (e) {
          res.status(500).json({ error: e });
        }
      }
    );
  }
}
