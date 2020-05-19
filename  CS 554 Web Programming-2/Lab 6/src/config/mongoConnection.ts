import * as mongodb from "mongodb";

export class dbconnection {
  public static MongoClient: mongodb.MongoClient;

  public static client: mongodb.MongoClient;

  public static connect(url: string) {
    return new Promise((resolve, reject) => {
      mongodb.MongoClient.connect(
        url,
        { useNewUrlParser: true },
        (err, client: mongodb.MongoClient) => {
          if (err) {
            reject(err);
          } else {
            dbconnection.client = client;
            resolve(client);
          }
        }
      );
    });
  }
}
