const arrayUtils = require("./arrayUtils");
const stringUtils = require("./stringUtils");
const objUtils = require("./objUtils");


console.log("==================== head function ==========================");
try{
    console.log(arrayUtils.head([1, 2, 3]));
    console.log("head passed successfully.");
}
catch(e){
    console.error("head failed test case : " + e);
}
try{
    console.log(arrayUtils.head(["1",2,3]));
    console.error("head did not error");
}
catch(e){
    console.error("head failed successfully : " + e);
}

console.log("==================== remove function ========================");
try {
    console.log(arrayUtils.remove([5, 6, 7], 1));
    console.log("remove passed succesfully.");
} catch (e) {
    console.error("remove failed test case : " + e);
}

try {
    console.log(arrayUtils.remove([1,2,3],-2));
    console.error("remove did not occur.");
} catch (e) {
    console.error("remove failed successfully : " + e);
}


console.log("==================== countChars function ====================");
try {
    console.log(stringUtils.countChars("Hello, the pie is in the oven"));
    console.log("countChars passed succesfully.");
} catch (e) {
    console.error("countChars failed test case :  " + e);
}

try {
    console.log(stringUtils.countChars(""));
    console.error("countChars did not occur.");
} catch (e) {
    console.error("countChars failed successfully : " + e);
}

console.log("==================== mapValues function =====================");
try {
    console.log(objUtils.mapValues({ a: 1, b: 2, c: 3 }, n => n+1)); 
    console.log("mapValues passed succesfully.");
} catch (e) {
    console.error("mapValues failed test case :  " + e);
}

try {
    console.log(objUtils.mapValues({ a: 1, b: 2, c: 3 },2)); 
    console.error("mapValues did not occur.");
} catch (e) {
    console.error("mapValues failed successfully : " + e);
}

