const axios = require('axios');

//getting data from people.json
async function getPeople(){
    const { data } = await axios.get('https://gist.githubusercontent.com/robherley/5112d73f5c69a632ef3ae9b7b3073f78/raw/24a7e1453e65a26a8aa12cd0fb266ed9679816aa/people.json');
    return data;
}

//getting data from weather.json
async function getWeather()
{
    const { data } = await axios.get('https://gist.githubusercontent.com/robherley/1b950dc4fbe9d5209de4a0be7d503801/raw/eee79bf85970b8b2b80771a66182aa488f1d7f29/weather.json');
    return data;
}

//checking names for shouldTheyGoOutside()
async function checkName(firstName,lastName)
{
    let people = await getPeople();
    let zipCode = null;
    people.forEach(element => {
       if(element.firstName === firstName && element.lastName === lastName)
       {
          zipCode = element.zip;
       }
    });
    if(zipCode == null )
    {
        throw `Please enter valid name.`;
    }
    else
    {
       return zipCode;
    }
}

//Implemetation of shouldTheyGoOutside()
const shouldTheyGoOutside = async function shouldTheyGoOutside(firstName, lastName)
{
    if(!firstName || !lastName)
    {
        throw `Invalid arguments.`;
    }
    firstName = firstName.charAt(0).toUpperCase() + firstName.slice(1).toLowerCase();
    lastName = lastName.charAt(0).toUpperCase() + lastName.slice(1).toLowerCase();
    let zipCode = await checkName(firstName,lastName);
    let weather = await getWeather();
    let checkFlag = 0;
    
    weather.forEach(element => {
        if(element.zip === zipCode && element.temp >= 34 )
        {
            checkFlag = 1;
        }
    });
    if(checkFlag == 1)
    {
        return "Yes, " + firstName +" should go outside."
    }
    else
    {
        return "No, " + firstName +" should not go outside."
    }
}

//exporting function
module.exports = 
{
    shouldTheyGoOutside
}