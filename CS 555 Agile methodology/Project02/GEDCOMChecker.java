package project02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;


public class GEDCOMChecker {

	public static void main(String [] args) throws FileNotFoundException
	{
		
		File input_file = new File("/Users/kunj/Downloads/proj02test.ged");
		Scanner sc = new Scanner(input_file); 
		String [] split_by_space;
		String currentLine;
		Set<String> valuesForZeroArg = Set.of("FAM", "INDI");
		Set<String> valuesForZero = Set.of("HEAD", "TRLR");
		Set<String> valuesForOneArg = Set.of("NAME", "SEX",  "FAMS", "FAMC",  "HUSB", "WIFE", "CHIL");
		Set<String> valuesForOne = Set.of("BIRT", "DEAT", "MARR", "DIV");
		Set<String> months = Set.of("JAN", "FEB", "MAR", "APR",	"MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC");
		int firstIndex, secondIndex, thirdIndex, currentIndex; 
		boolean flag;
		 while (sc.hasNextLine()) 
		 {
			 firstIndex = 0;
			 currentLine = sc.nextLine();
			 System.out.println("--> "+currentLine);
			 split_by_space = currentLine.split(" ");
			 if(split_by_space[firstIndex].equals("0"))
			 {
				 secondIndex = firstIndex + 1;
				 thirdIndex =  secondIndex + 1;
				 if(valuesForZero.contains(split_by_space[secondIndex]))
				 {
					 System.out.println("<-- "+split_by_space[firstIndex]+"|"+split_by_space[secondIndex]+"|Y|");
				 }
				 else if (valuesForZeroArg.contains(split_by_space[thirdIndex]))
				 {
					 System.out.println("<-- "+split_by_space[firstIndex]+"|"+split_by_space[thirdIndex]+"|Y|"+split_by_space[secondIndex]);
				 }
				 else if ((split_by_space[secondIndex]).equals("NOTE"))
				 {
					 System.out.print("<-- "+split_by_space[firstIndex]+"|"+split_by_space[secondIndex]+"|Y|"+split_by_space[thirdIndex]);
					 currentIndex = thirdIndex + 1;
					 while(currentIndex < split_by_space.length)
					 {
						 System.out.print(" "+split_by_space[currentIndex]);
						 currentIndex++;
					 }
					 System.out.println("");
				 }
				 else
				 {
					 secondIndex = firstIndex + 1;
					 currentIndex = secondIndex + 1;
					 System.out.print("<-- "+split_by_space[firstIndex]+"|"+split_by_space[secondIndex]+"|N|");					
					 while(currentIndex < split_by_space.length)
					 {
						 System.out.print(" "+split_by_space[currentIndex]);
						 currentIndex++;
					 }
					 System.out.println("");
				 }
				 
			 }
			 else if(split_by_space[firstIndex].equals("1"))
			 {
				 secondIndex = firstIndex + 1;
				 thirdIndex =  secondIndex + 1;
				 
				 if(valuesForOneArg.contains(split_by_space[secondIndex]))
				 {
					 if((split_by_space[secondIndex]).equals("NAME"))
					 {
						 System.out.print("<-- "+split_by_space[firstIndex]+"|"+split_by_space[secondIndex]+"|Y|"+split_by_space[thirdIndex]);
						 currentIndex = thirdIndex + 1;
						 while(currentIndex < split_by_space.length)
						 {
							 System.out.print(" "+split_by_space[currentIndex]);
							 currentIndex++;
						 }
						 System.out.println("");
					 }
					 else
					 {
						 System.out.println("<-- "+split_by_space[firstIndex]+"|"+split_by_space[secondIndex]+"|Y|"+split_by_space[thirdIndex]);
					 }
				 }
				 else if(valuesForOne.contains(split_by_space[secondIndex]))
				{
					 System.out.println("<-- "+split_by_space[firstIndex]+"|"+split_by_space[secondIndex]+"|Y|");
				}
				 else
				 {
					 secondIndex = firstIndex + 1;
					 currentIndex = secondIndex + 1;
					 System.out.print("<-- "+split_by_space[firstIndex]+"|"+split_by_space[secondIndex]+"|N|");					
					 while(currentIndex < split_by_space.length)
					 {
						 System.out.print(" "+split_by_space[currentIndex]);
						 currentIndex++;
					 }
					 System.out.println("");
				 }
			 }
			 else if (split_by_space[firstIndex].equals("2"))
			 {
				secondIndex = firstIndex + 1;
				thirdIndex =  secondIndex + 1;
				if(split_by_space[secondIndex].equals("DATE"))
				{
					try {
						if(Integer.parseInt(split_by_space[thirdIndex]) <= 31)
							flag = true;
						else
							flag = false;
					}catch(Exception e)
					{
						flag = false;
					}
					
					if(flag && months.contains(split_by_space[thirdIndex + 1]) && (split_by_space[thirdIndex + 2]).length() == 4)
					{
						 System.out.print("<-- "+split_by_space[firstIndex]+"|"+split_by_space[secondIndex]+"|Y|"+split_by_space[thirdIndex]);
						 currentIndex = thirdIndex + 1;
						 while(currentIndex < split_by_space.length)
						 {
							 System.out.print(" "+split_by_space[currentIndex]);
							 currentIndex++;
						 }
						 System.out.println("");
					}
					else
					{
						if((split_by_space.length - 1) == thirdIndex)
						 {
							 System.out.println("<-- "+split_by_space[firstIndex]+"|"+split_by_space[secondIndex]+"|N|"+split_by_space[thirdIndex]);
						 }
						 else
						 {
							 System.out.println("<-- "+split_by_space[firstIndex]+"|"+split_by_space[secondIndex]+"|N|");
						 }
					}
				}
				else
				{
					 secondIndex = firstIndex + 1;
					 currentIndex = secondIndex + 1;
					 System.out.print("<-- "+split_by_space[firstIndex]+"|"+split_by_space[secondIndex]+"|N|");					
					 while(currentIndex < split_by_space.length)
					 {
						 System.out.print(" "+split_by_space[currentIndex]);
						 currentIndex++;
					 }
					 System.out.println("");
				}
			 }
			 else
			 {
				 secondIndex = firstIndex + 1;
				 currentIndex = secondIndex + 1;
				 System.out.print("<-- "+split_by_space[firstIndex]+"|"+split_by_space[secondIndex]+"|N|");					
				 while(currentIndex < split_by_space.length)
				 {
					 System.out.print(" "+split_by_space[currentIndex]);
					 currentIndex++;
				 }
				 System.out.println("");
			 }
			 
		 }

	}

}
