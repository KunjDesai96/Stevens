//to check data type of array
function chechInputType(array)
{
    if(Array.isArray(array))
    {
        return array;
    }
    else
    {
        throw `Please insert array type data.`;
    }
}

//to check same type of elements in array
function checkElementType(array)
{
    for(let i=0;i<array.length-1;i++)
    {
        if(typeof array[i] !== typeof array[i+1])
        {
            throw `Insert same type of data`;
        }

    }
    return array;
}

//to check empty array
function chechkEmptyArray(array)
{
    if(array.length > 0)
    {
        return array;
    }
    else
    {
        throw `Array is empty.`
    }
}

//implementation of head function
const head = function head(array){
    chechInputType(array);
    chechkEmptyArray(array);
    checkElementType(array)
    return array[0];
}

//implementation of last funtion
const last = function last(array){   
    chechInputType(array);
    chechkEmptyArray(array);
    checkElementType(array);
    return array[array.length -1];
}

//implementation of remove funtion
const remove = function remove(array,index){
    chechInputType(array);
    chechkEmptyArray(array);
    checkElementType(array);
    
    //checking index passed for removing element
    if(index<0 || index >= array.length || !Number.isInteger(index))
    {
        throw `Please insert valid array index.`
    }
    else
    {
        for (var i=0;i<array.length; i++) {
            if (i === index) {
                array.splice(i, 1);
            }
        }
    }        
    return array;
}

//implementation  of range function
const range = function range(end,value){

    //to check valid "end"
    if(end <= 0 || !end || !Number.isInteger(end))
    {
        throw `Please insert valid "end" value`
    }
    else
    {
        let newArr = new Array(end);
        if(!value)
        {
            for(let i =0; i<newArr.length;i++)
            {
                newArr[i]= i;
            }
        }
        else
        {
            for(let i =0; i<newArr.length;i++)
            {
                newArr[i]= value;
            }
        }
        return newArr;
    }
}

//implementation of countElements
const countElements = function countElements(array)
{
    chechInputType(array);
    chechkEmptyArray(array);
    let count =1;
    array.sort();
    const result = {};
    for(let i = 0;i< array.length;i++)
    {
        if(array[i] == array[i+1])
        {
            count ++;
        }
        else
        {       
            result[JSON.stringify(array[i])] = count;
            count = 1;
        }
    }
    return result;
}

//implementation of isEqual funtion 
const isEqual = function isEqual(array1, array2){   
    chechInputType(array1);
    chechInputType(array2);
    chechkEmptyArray(array1);
    chechkEmptyArray(array2);
    checkElementType(array1);
    checkElementType(array2);
    if(JSON.stringify(array1) === JSON.stringify(array2))
    {
        return true;
    }
    else
    {
        return false;
    }
}

//exporting functions to use in other files.
module.exports = {
    head,
    last,
    remove,
    range,
    countElements,
    isEqual
};