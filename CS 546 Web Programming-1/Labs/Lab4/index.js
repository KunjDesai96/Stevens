const animal = require("./data/animal");
const connection = require("./config/mongoConnection");
 
// Implemetation of main()
async function main()
{
    try {
        console.log(await animal.create("Sasha","Dog"));
    } catch (error) {
        console.error(error);
    }

    try {
        await animal.create("Lucy","Dog");
    } catch (error) {
        console.error(error);
    }

    try {
        console.log(await animal.getAll());
    } catch (error) {
        console.error(error);
    }
    try {
        console.log(await animal.create("Duke","Walrus"));
    } catch (error) {
        console.error(error);
    }

    try {
        console.log(await animal.rename("5d95392c45ae51027b7086a2","Sashita"));
    } catch (error) {
        console.error(error);
    }

    try {
        console.log(await animal.remove("5d95392c45ae51027b7086a3"));
    } catch (error) {
        console.error(error);
    }

    try {
        console.log(await animal.getAll());
    } catch (error) {
        console.error(error);
    }
    const db = await connection();
    await db.serverConfig.close();
}

main();