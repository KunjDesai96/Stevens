package final_project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author Kunj Desai
 *
 */

public class ParseVariables {
	   
	//Initialize variables to phi operators
	private static List<String> select = new ArrayList<String>();
	private static int number;
	private static List<String> groupby = new ArrayList<String>();;
	private static List<FormAggregate> fvect_variable = new ArrayList<FormAggregate>();;
	private static List<Pair> suchthat = new ArrayList<Pair>();;
	private static List<String> having = new ArrayList<String>();
	private static List<String> where_condition = new ArrayList<String>();
	
	/**
	 * This method will scan the arguments/operators from the file and 
	 * add them to the respective variables.
	 * 
	 * @param input_file Takes the file with the operators.
	 */
	public static void addArguments(File input_file) {
	
		try {
			Scanner sc = new Scanner(input_file);
			
			String currentLine;
			String [] select_attributes = null, grouping_atributes = null, fvect = null, select_condition = null, where = null, having_condition = null;
			int numberofGV = 0;
			while (sc.hasNextLine()) {
				currentLine = sc.nextLine();
				if(currentLine.contains("select_variables"))
				{
					currentLine = currentLine.replaceAll(".+:","");
					select_attributes = currentLine.split(", ");
				}
				else if(currentLine.contains("number_of_gv"))
				{
					currentLine = currentLine.replaceAll(".+:","");
					numberofGV = Integer.parseInt(currentLine);
				}
				else if(currentLine.contains("grouping_attributes"))
				{
					currentLine = currentLine.replaceAll(".+:","");
					grouping_atributes = currentLine.split(", ");
				}
				else if(currentLine.contains("where"))
				{
					//If there is no where condition set it to null.
					currentLine = currentLine.replaceAll(".+:", "");
					if(currentLine.equals(""))
					{
						where = null;
					}
					else
					{
						where = currentLine.split(", ");
					}
					
				}
				else if(currentLine.contains("fvect"))
				{
					currentLine = currentLine.replaceAll(".+:","");
					fvect = currentLine.split(", ");
				}
				else if(currentLine.contains("select_condition"))
				{
					currentLine = currentLine.replaceAll(".+:","");
					select_condition= currentLine.split(", ");
				}
				else if(currentLine.contains("having_condition"))
				{
					//If there is no having condition set it to null.
					currentLine = currentLine.replaceAll(".+:","");
					if(currentLine.equals(""))
					{
						having_condition = null;
					}
					else
					{
						having_condition= currentLine.split(", ");
					}
					
				}
				else 
				{
					continue;
				}
			}
			
			
		getArguments(select_attributes, grouping_atributes, fvect, select_condition, numberofGV, where, having_condition);
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * This method will take all the operators and parse them in an appropriate manner 
	 * and add them to a list (where ever required), so that it becomes easy to use them in generator classes.
	 * 
	 * @param select_attributes
	 * @param grouping_atributes
	 * @param fvect
	 * @param select_condition
	 * @param numberofGV
	 * @param where
	 * @param having_condition
	 */
	private static void getArguments(String[] select_attributes, String[] grouping_atributes, String[] fvect,
			String[] select_condition, int numberofGV, String[] where, String[] having_condition) {
		// TODO Auto-generated method stub
		
		
		//Setting select variable
		for(String str: select_attributes)
		{
			if(str.contains("_"))
			{
				String [] temp = str.split("_");
				FormAggregate fa = new FormAggregate(temp[0], temp[1], temp[2]);
				select.add(fa.getString());
			}
			else
			{
				select.add(str);
			}
		}
		
		//Setting number  of grouping variables.
		number = numberofGV;
		for(String str: grouping_atributes)
		{
			groupby.add(str); 
		}
		
		//Setting where varibale
		if(where != null )
		{
			for(String str: where)
			{
				where_condition.add(str);
			}
		}
		
		//Setting FVect variable
		for(String str: fvect)
		{
			String [] temp = str.split("_");
			FormAggregate fa = new FormAggregate(temp[0], temp[1], temp[2]);
			fvect_variable.add(fa);
		}
		
		//Setting such that clause in select_condition variable
		for(String str: select_condition)
		{		
			String [] temp = str.split("_");		
			Pair pair = new Pair( Integer.parseInt(temp[0]), temp[1]);
			suchthat.add(pair);
		}
		
		//Setting having_condition
		if (having_condition != null ) {
			for(String str: having_condition)
			{
				having.add(str);			
			}
		}
		
	}
	
	//Getter for variables and getter for sizes of where and having condition.
	public List<String> getSelect() {
		return select;
	}

	
	public int getNumber() {
		return number;
	}

	public List<String> getGroupby() {
		return groupby;
	}

	public List<FormAggregate> getFvect() {
		return fvect_variable;
	}

	public List<Pair> getSuchthat() {
		return suchthat;
	}

	public List<String> getHaving() {
		return having;
	}
	public List<String> getWhere()
	{
		return where_condition;
	}
	
	public int getSizeWhere()
	{
		return where_condition.size();
	}

	public int getSizeHaving()
	{
		return having.size();
	}
}