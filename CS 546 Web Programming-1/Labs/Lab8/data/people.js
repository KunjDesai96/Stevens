const axios = require('axios');

//getting data from people.json
async function getPeople(){
    const { data } = await axios.get('https://gist.githubusercontent.com/robherley/5112d73f5c69a632ef3ae9b7b3073f78/raw/24a7e1453e65a26a8aa12cd0fb266ed9679816aa/people.json');
    return data;
  }

  // Checking "id" for getPersonById()
  function checkID(id,data)
  {
      if(!Number.isInteger(id))
      {
        throw `Please enter a valid id.`
      }
      else if(id < 1 || id > data.length )
      {
        throw `Given id is out of bound.`
      }
      else
      {
          return id;
      }
  }

  //getPersonById() implemetation
  const getPersonById = async function getPersonById(id)
  {
    let data =  await getPeople();
    checkID(id,data);
    var result;
    data.forEach(element => {
        if(element.id === id)
        {
          result = element;
        }
    });
    return result;
  } 

  const getPersonByName =  async function getPersonByName(name)
  {
    if(!name || typeof name !== "string") throw "Please enter valid name."
    let data = await getPeople();
    let regex = new RegExp([".*", name, ".*"].join(""), "i");
    let nameArr = [];
    let count =  0;
   
    data.forEach(element => {
      const fullName = element.firstName +" "+ element.lastName;
      if((element.firstName.match(regex) || element.lastName.match(regex) || fullName === name)&& count <20)
      {
        const result = {
          "id": element.id,
          "personName": element.firstName + " " + element.lastName
        }
        nameArr.push(result);
        count = count + 1;
      }
    });
    return nameArr;
  }


//exporting functions
module.exports = 
{
  getPersonById,
  getPersonByName
}