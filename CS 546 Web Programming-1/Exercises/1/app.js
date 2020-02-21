const dic = require("./dictonary"); 

try{
    dic.lookupDefinition('xyz');
}
catch (e)
{
    console.log(e);
}
try{
    dic.lookupDefinition(1);
}
catch (e)
{
    console.log(e);
}
try{
    console.log(dic.lookupDefinition('foray'));
}
catch (e)
{
    console.log(e);
}
try{
    dic.getWord('xyz');
}
catch (e)
{
    console.log(e);
}
try{
    dic.getWord(1);
}
catch (e)
{
    console.log(e);
}
try{
    console.log(dic.getWord('A sudden or irregular invasion or attack for war or spoils : raid'));
}
catch (e)
{
    console.log(e);
}