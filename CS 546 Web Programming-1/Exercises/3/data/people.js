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
    const newid = parseInt(id);
    let data =  await getPeople();
    checkID(newid,data);
    let name;
    data.forEach(element => {
        if(element.id === newid)
        {
            name = element.firstName +" "+ element.lastName;
        }
    });
    return name;
  } 

//exporting functions
module.exports = 
{
  getPersonById,
  getPeople
}