const axios = require('axios');

//getting data from people.json
async function getPeople() {
    const { data } = await axios.get('https://gist.githubusercontent.com/robherley/5112d73f5c69a632ef3ae9b7b3073f78/raw/24a7e1453e65a26a8aa12cd0fb266ed9679816aa/people.json');
    return data;
}

//getting data from work.json
async function getWork() {
    const { data } = await axios.get('https://gist.githubusercontent.com/robherley/61d560338443ba2a01cde3ad0cac6492/raw/8ea1be9d6adebd4bfd6cf4cc6b02ad8c5b1ca751/work.json');
    return data;
}

//checking name for whereDoTheyWork()
async function checkName(firstName, lastName) {
    let people = await getPeople();
    let ssn = null;
    people.forEach(element => {
        if (element.firstName === firstName && element.lastName === lastName) {
            ssn = element.ssn;
        }
    });
    if (ssn == null) {
        throw `Please enter valid name.`;
    }
    else {
        return ssn;
    }
}

//checking ip for findTheHacker()
async function checkIp(ip, workData) {
    var ssn ;

    if (/^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/.test(ip)) 
    {
        workData.forEach(element => {
            if(element.ip === ip)
            {
                ssn =  element.ssn;
            }
        });
    }
    else
    {
        throw `Please enter valid IP.`;
    }
    if(ssn)
    {
        return ssn;
    }
    else
    {
        throw `Entered IP address does not match.`;
    }  
}

//Implemetation of whereDoTheyWork()
const whereDoTheyWork = async function whereDoTheyWork(firstName, lastName) {
    if(!firstName || !lastName)
    {
        throw `Invalid arguments.`;
    }
    firstName = firstName.charAt(0).toUpperCase() + firstName.slice(1).toLowerCase();
    lastName = lastName.charAt(0).toUpperCase() + lastName.slice(1).toLowerCase();
    let ssn = await checkName(firstName, lastName);
    let workData = await getWork();
    let workInfo = {};
    
    workData.forEach(element => {
        if (element.ssn === ssn) {
            workInfo["jobTitle"] = element.jobTitle;
            workInfo["company"] = element.company;
            workInfo["willBeFired"] = element.willBeFired;
        }
    });
    if (workInfo["willBeFired"] === true) {
        return firstName + " " + lastName + " - " + workInfo["jobTitle"] + " at " + workInfo["company"] + ". They will be fired."
    }
    else {
        return firstName + " " + lastName + " - " + workInfo["jobTitle"] + " at " + workInfo["company"] + ". They will not be fired."
    }
}


//Implementation of findTheHacker()
const findTheHacker = async function findTheHacker(ip)
{
    let workData = await getWork();
    let people = await getPeople();
    const ssn = await checkIp(ip, workData);
    let string = null;
    people.forEach(element =>{
        if(element.ssn === ssn)
        {
            string  =  element.firstName + " " + element.lastName + " is the hacker!";
        }
    });
    return string;
}

//exporting functions
module.exports =
    {
        whereDoTheyWork,
        findTheHacker
    }