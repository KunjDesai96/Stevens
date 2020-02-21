const words ={
    programming: "The action or process of writing computer programs.",
    charisma: "A personal magic of leadership arousing special popular loyalty or enthusiasm for a public figure (such as a political leader)",
    sleuth: "To act as a detective : search for information",
    foray: "A sudden or irregular invasion or attack for war or spoils : raid",
    adjudicate: "to make an official decision about who is right in (a dispute) : to settle judicially"
}

const checkInput = function checkInput(str)
{
    if(typeof str === 'string')
    {
        return str;
    }
    else
    {
        throw "It is not a string."
    }
}

const lookupDefinition = function lookupDefinition(str)
{
        checkInput(str);
        if(words[str] !== undefined)
        {
            return words[str];
        }
        else
        {
            throw "Definition not found"
        }
  
}

const getWord = function getWord(str){
    checkInput(str);
    let getWord = Object.keys(words).find(key => words[key] === str);
    if (getWord == undefined){
        throw "Word not found"
    }
    return getWord

}

module.exports ={
    lookupDefinition,
    getWord
};