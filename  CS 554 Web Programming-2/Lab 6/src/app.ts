import * as express from "express";
import * as bodyParser from "body-parser";
var urlVisisted = [];
import { TasksRoutes } from "./routes/tasks";

class App {
  public app: express.Application;
  public taskRoutes: TasksRoutes = new TasksRoutes();
  public urlVisisted = [];
  constructor() {
    this.app = express(); //run the express instance and store in app
    this.config();
    this.taskRoutes.routes(this.app);
  }

  Logger = (req: express.Request, res: express.Response, next: Function) => {
    //middleware here
    if (urlVisisted[req.originalUrl]) urlVisisted[req.originalUrl] += 1;
    else urlVisisted[req.originalUrl] = 1;
    console.log("\nInforamtion using first middleware: \n");
    console.log("Request Body: " + JSON.stringify(req.body));
    console.log("URL path: " + req.originalUrl);
    console.log("HTTP verb: " + req.method);
    next();
  };

  Tracker = (req: express.Request, res: express.Response, next: Function) => {
    //middleware here
    console.log("\nInforamtion using second middleware: \n");
    console.log(
      "URL " +
        req.originalUrl +
        " has been requested for " +
        urlVisisted[req.originalUrl] +
        " times."
    );
    next();
  };

  private config(): void {
    // support application/json type post data
    this.app.use(bodyParser.json());
    //support application/x-www-form-urlencoded post data
    this.app.use(
      bodyParser.urlencoded({
        extended: false,
      })
    );
    this.app.use(this.Logger);
    this.app.use(this.Tracker);
  }
}

export default new App().app;
