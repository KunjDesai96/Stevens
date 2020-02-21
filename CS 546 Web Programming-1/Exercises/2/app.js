const todoItems = require("./todo");
const connection = require("./config/mongoConnection");
let FirstId;
let secondID;
async function main()
{
    try {
        const data = await todoItems.createTask("Ponder Dinosaurs", "Has Anyone Really Been Far Even as Decided to Use Even Go Want to do Look More Like?");
        FirstId = data._id.toString();
        console.log(data);
    } catch (error) {
        console.log(error);
    }

    try {
        const data = await todoItems.createTask("Play Pokemon with Twitch TV", "Should we revive Helix?");
        secondID = data._id.toString();
        console.log(data);
    } catch (error) {
        console.log(error);
    }

    try {
        console.log(await todoItems.getAllTask());
    } catch (error) {
        console.log(error);
    }

    try {
        console.log(await todoItems.removeTask(FirstId));
    } catch (error) {
        console.log(error);
    }

    try {
        console.log(await todoItems.completeTask(secondID));
    } catch (error) {
        console.log(error);
    }
    const db = await connection();
    await db.serverConfig.close();
}
main()