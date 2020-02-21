
//importing files
const people =  require("./people");
const weather = require("./weather");
const work = require("./work");


//main funtion
async function main()
{
    try {
        console.log(await people.getPersonById("a"));
    } catch (error) {
        console.error(error);
    }

    try {
        console.log(await people.lexIndex());
    } catch (error) {
        console.error(error);
    }

    try {
        console.log(await people.firstNameMetrics());
    } catch (error) {
        console.log(error);
    }

    try {
        console.log(await weather.shouldTheyGoOutside());
    } catch (error) {
        console.error(error);
    }

    try {
        console.log(await work.whereDoTheyWork("Hank", "Tarling"));
    } catch (error) {
        console.error(error);
    }
    
    try {
        console.log(await work.findTheHacker("79.222.167.180"));
    } catch (error) {
        console.error(error);
    }
   
}

//call main()
main();