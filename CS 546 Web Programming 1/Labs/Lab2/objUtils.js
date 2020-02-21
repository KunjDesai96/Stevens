//to check object type for args
function checkObject(args)
{
    for(var obj in args)
    {
        if(typeof args[obj] !== 'object' )
        {
            throw `Please enter an object type data.`
        }
    }
    return args;
}

//to check undefined objects in args
function checkUndefined(args)
{
    for(var obj in args)
    {
        if(typeof args[obj] == 'undefined')
        {
            throw `Object is undefined.`
        }
    }
    return args;
}

//to check minimum number of arguments passed
function checkLenght(args)
{
    if(args.length < 2)
    {
        throw `Please insert atleast 2 arguments`
    }
}

//implementation of extend function
const extend = function extend(...args)
{   
    checkObject(args);
    checkUndefined(args);
    checkLenght(args);
    var result = {};
    for(var i in args)
    {
        var obj = args[i];     
        for(var key in obj)
        {
            if(!result.hasOwnProperty(key))
            {
                result[key] = obj[key];
            }
        }  
    }
    return result;
}

//implementation of smush function
const smush = function smush(...args)
{
    checkObject(args);
    checkUndefined(args);
    checkLenght(args);
    var result = {};
    for(var i in args)
    {
        var obj = args[i];     
        for(var key in obj)
        {
            if(!result.hasOwnProperty(key))
            {
                result[key] = obj[key];
            }
            else
            {
                result[key] = obj[key];
            }
        }   
    }
    return result;
}

//implementation of mapValues function
const mapValues = function mapValues(object, func)
{
    if(typeof object != 'object' || Object.keys(object).length === 0)
    {
        throw `Please insert valid object.`
    }
    else if(typeof func != 'function')
    {
        throw `Please insert valid function.`
    }
    else
    {
        for(var i in object)
        {
            object[i] = func(object[i]);
        }
        return object;
    }
   
}

//exporting functions to use in other files.
module.exports = {
    extend,
    smush,
    mapValues
};