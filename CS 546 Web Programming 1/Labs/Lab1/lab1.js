const questionOne = function questionOne(arr) {
    // Implement question 1 here
    let sum = 0;                                    //variable to store the sum
    let square = 0 ;                                //variable to store square 
      arr.forEach(element => {
          square = element * element;               //squaring each element of array
          sum = sum + square;                       // adding square of each element to the sum 
      });
     return sum;
}

const questionTwo = function questionTwo(num) { 
    // Implement question 2 here
        if(num < 1)  
        {
            return 0;                           // returning '0' if number is less than '1' 
        }
        else if(num == 1)  
        {
            return num;                         //returnin number itself ('1') if number is '1'
        }
        else
        {
            return questionTwo(num - 1) + questionTwo(num - 2);  // recursion to get the sum value for number greater than '1'
        }
}

const questionThree = function questionThree(text) {
    // Implement question 3 here
    text = text.toLowerCase();                  // converting the given "text" to lowercase so that we can compare vowel characters in lowercase.
    text = text.toString();                     //convertisng the given "text" to string so that we can use "charAt()" funtion.
    let count = 0;                              // variable to count the occurances of vowels in given "text"
    for(let i = 0; i<text.length; i++)          //loop to run the cursor from '0' to the end of the given "text".
    {
        if(text.charAt(i) === 'a' || text.charAt(i) === 'e' || text.charAt(i) === 'i' ||text.charAt(i) === 'o' ||text.charAt(i) === 'u')
        {
            count++;                            //increasing the count whenever vowel occurs in the given "text".
        }
    }
    return count;
}

const questionFour = function questionFour(num) {
    // Implement question 4 here
   if(num < 0)  
   {
    return 'NaN';                           //returning 'NaN' if the number is less than '0'
   }
    if(num ==  1 || num == 0) 
   {
        return 1;                           //returning '1' if the number is '1' or '0'.
   }
   else 
   {
       return num * questionFour(num -1);   //recursion to get the factorial for numbers greatar than '1'
   }
}

//creating an module.exports to access the funtions in other file
module.exports = {
    firstName: "Kunj", 
    lastName: "Desai", 
    studentId: "10444511",
    questionOne,
    questionTwo,
    questionThree,
    questionFour
};