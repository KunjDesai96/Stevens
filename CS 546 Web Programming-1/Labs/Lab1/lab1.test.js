const lab1 = require("./lab1");  //importing module from lab1.js file

//**************************                  Information                  *******************************/
console.log("* Information *")
console.log("First name :"+ lab1.firstName);
console.log("Last name :"+lab1.lastName)
console.log("Student ID :"+lab1.studentId);
//**************************                End of Inforamtiuon            *******************************/

//**************************                 Test cases for questionOne             *******************************/
console.log("Outputs for questionOne : ");

console.log("Test case 1 :" +lab1.questionOne([1, 2, 3]));   //Test case 1 should output '14'

console.log("Test case 2 :" +lab1.questionOne([5, 3, 10]));  //Test case 2 should output '134'

console.log("Test case 3 :" +lab1.questionOne([2, 1, 2]));   //Test case 3 should output '9'

console.log("Test case 4 :" +lab1.questionOne([5, 10, 9]));  //Test case 4 should output '206'

console.log("Test case 5 :" +lab1.questionOne([2, 4, 6]));   //Test case 5 should output '56'

//**************************                End of test cases for questionOne       *******************************/

//**************************                 Test cases for questionTwo            *******************************/
console.log("Outputs for questionTwo : ");

console.log("Test case 1 :" +lab1.questionTwo(-7));         //Test case 1 (negative number) should output '0'

console.log("Test case 2 :" +lab1.questionTwo(0));          //Test case 2 (zero) should output '0'

console.log("Test case 3 :" +lab1.questionTwo(1));          //Test case 3 (one) should output '1'

console.log("Test case 4 :" +lab1.questionTwo(3));          //Test case 4 (positive number) should output '2'

console.log("Test case 5 :" +lab1.questionTwo(7));         //Test case  5 (from assignment) should output '13'
//**************************                End of test cases for questionTwo       *******************************/

//**************************                 Test case for questionThree            *******************************/

console.log("Output for questionThree : ");

console.log("Test case output :"+lab1.questionThree("Mr. and Mrs. Dursley, of number four, Privet Drive, were  proud  to  say  that  they  were  perfectly  normal,  thank you  very  much. They  were  the  last  people  youd  expect  to  be  involved in anything strange or mysterious, because they just didn't hold with such nonsense. \n Mr. Dursley was the director of a firm called Grunnings, which  made  drills.  He  was  a  big,  beefy  man  with  hardly  any  neck,  although he did have a very large mustache. Mrs. Dursley was thin and blonde and had nearly twice the usual amount of neck, which came in very useful as she spent so much of her time craning over garden fences, spying on the neighbors. The Dursleys had a small son  called  Dudley  and  in  their  opinion  there  was no finer boy anywhere.")); 
// Test case should output 196

//**************************                End of test case for questionThree     *******************************/

//**************************                 Test cases for questionFour            *******************************/

console.log("Outputs for questionFour : ");
console.log("Test case 1 :"+lab1.questionFour(-3)); // Test case 1 (negative number)should output 'NaN' 

console.log("Test case 2 :"+lab1.questionFour(0)); // Test case 2 (zero) should output '1' 

console.log("Test case 3 :"+lab1.questionFour(1)); // Test case 3 (one) should output '1' 

console.log("Test case 4 :"+lab1.questionFour(5)); // Test case 4 (from assignment) should output '120' 

console.log("Test case 5 :"+lab1.questionFour(10)); // Test case 5 (from assignment) should output '3628800' 

//**************************                End of test cases for questionFour     *******************************/

