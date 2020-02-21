//to check the input type of string
function chechInputType(string)
{
    if(typeof string === 'string')
    {
        return string;
    }
    else
    {
        throw `Please insert string type data.`;
    }
}

//to check if empty string is passed 
function checkEmptyString(string)
{
    if(string === undefined || string === "" || !string)
    {
        throw `Please insert string.`
    }
    else
    {
        return string;
    }
}

//capitalize function implementation
const capitalize = function capitalize(string)
{
    chechInputType(string);
    checkEmptyString(string);
    string = string.toLowerCase();
    string = string.charAt(0).toUpperCase() + string.substring(1);
    return string;
}

//repeat function implementation
const repeat = function repeat(string,num)
{
    chechInputType(string);
    checkEmptyString(string);
    var newString = "";
    if(!Number.isInteger(num) || num < 0 || num === undefined)
    {
        throw `Enter valid number.`
    }
    else
    {
        for(let i=0;i< num;i++)
        {
            newString = newString + string;
        }
        return newString;
    }
}

//countChars funtion implementation
const countChars = function countChars(string)
{
    chechInputType(string);
    checkEmptyString(string);
    let count =1;
    const result = {};
    string = string.split('').sort().join('');
    for(let i = 0;i< string.length;i++)
    {
        if(string.charAt(i) === string.charAt(i+1))
        {
            count ++;
        }
        else
        {
            result[JSON.stringify(string.charAt(i))] = count;
            count = 1;
        }
    }
    return result;
}

//exporting functions to use in other files.
module.exports = {
    capitalize,
    repeat,
    countChars
};