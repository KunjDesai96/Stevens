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

  //Checking "index" for lexIndex()
  function checkIndex(index, data)
  {
      if(!Number.isInteger(index))
      {
        throw `Please enter a valid index numebr.`
      }
      else if(index < 0 || index > data.length -1 )
      {
        throw `Given index is out of bound.`
      }
      else
      {
          return index;
      }
  }
//function to return total vowels
  function vowels(arrayelement)
  {
    const vowels = ["a", "e", "i", "o", "u"]
    var count = 0 ;
    for (let char of arrayelement.toLowerCase())
    {
      if (vowels.includes(char)) 
      {
         count++;
      }
    }
    return count;
  }

  //function to return total consonants
  function consonants(arrayelement)
  {
    const vowels = ["a", "e", "i", "o", "u"]
    var count = 0 ;
    for (let char of arrayelement.toLowerCase())
    {
      if (!vowels.includes(char)) 
      {
         count++;
      }
    }
    return count;
  }

  //getPersonById() implemetation
  const getPersonById = async function getPersonById(id)
  {
    let data =  await getPeople();
    checkID(id,data);
    let name;
    data.forEach(element => {
        if(element.id === id)
        {
            name = element.firstName +" "+ element.lastName;
        }
    });
    return name;
  } 

//lexIndex() implementation
const lexIndex = async function lexIndex(index)
{
    let data =  await getPeople();
    checkIndex(index,data);
    let name;
    data.sort(function(a,b)
    {
        return a.lastName.localeCompare(b.lastName);  
    })
    name = data[index].firstName + " " + data[index].lastName;
    return name;
}

//firstNameMetrics() implementation
const firstNameMetrics = async function firstNameMetrics()
{
  let data =  await getPeople();
  let result = {};
  let totalLetters = 0;
  let totalVowels = 0;
  let totalConsonants = 0;
  let longestName;
  let arrayName = [];
  data.forEach(element => {
    arrayName.push(element.firstName);
  });
  arrayName.forEach(arrayelement => {
    totalLetters = totalLetters + arrayelement.length;
    totalVowels = totalVowels + vowels(arrayelement);
    totalConsonants = totalConsonants + consonants(arrayelement); 
  });
  longestName = arrayName.sort(function (a, b) { return b.length - a.length; })[0];  
  shortestName = arrayName.sort(function(a,b) { return a.length - b.length})[0];
  result["totalLetters"] = totalLetters;
  result["totalVowels"] = totalVowels;
  result["totalConsonants"] = totalConsonants;
  result["longestName"] = longestName;
  result["shortestName"] = shortestName;
  return result;
}

//exporting functions
module.exports = 
{
  getPersonById,
  lexIndex,
  firstNameMetrics
}