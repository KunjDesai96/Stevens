import { dbconnection } from "./mongoConnection";
import settings = require("./settings.json");
var mongoConfig = settings.mongoConfig;

export class dbcollection {
  public static getCollectionFn = (collection) => {
    let _col = undefined;
    return async () => {
      await dbconnection.connect(mongoConfig.serverUrl);
      if (!_col) {
        _col = await dbconnection.client
          .db(mongoConfig.database)
          .collection(collection);
      }
      return _col;
    };
  };
}
