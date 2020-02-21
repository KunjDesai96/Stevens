package final_project;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 * This is the generator class which will
 * generate a program for MF queries.
 * 
 * @author Kunj Desai
 *
 */
public class MFGenerator {

	/**
	 * This is the first method from where writing of 
	 * code will initialize.
	 * 
	 * @param key_dataType  Schema of the DB
	 */
	public static void generateMF(HashMap<String, String> key_dataType) {
		// TODO Auto-generated method stub
		try {
			File output_file = new File("/Users/kunj/eclipse-workspace/CS562/src/final_output/MFOutput.java");
			PrintWriter writer = new PrintWriter(output_file);

			// Generate import files
			outputImportPart(writer);

			writer.print("\n");

			// Generate main class
			outputMain(writer, key_dataType);

			// Close instance of PrintWriter.
			writer.close();
			
		} catch (Exception e) {
			System.out.println("Code broke becuase: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * This method will generate the required variables variables and main method.
	 * 
	 * @param writer Print Writer instance.
	 * @param key_dataType Schema of DB
	 */
	private static void outputMain(PrintWriter writer, HashMap<String, String> key_dataType) {
		// TODO Auto-generated method stub

		writer.print("public class MFOutput {\n");
		writer.print("\t//Variables to connect to DB\n");
		writer.print("\tprivate static final String usr = \"postgres\";\n"
				+ "	private static final String pwd = \"dharmesh\";\n"
				+ "	private static final String url = \"jdbc:postgresql://localhost:5432/CS562\";\n");
		
		writer.print("\t//Variables to generate the output\n");
		writer.print("\tList<Result_Attributes> output_attributes = new ArrayList<Result_Attributes>();\n");
		writer.print("\tList<MF_Structure> mfStruct = new ArrayList<MF_Structure>();\n");
		writer.print("\n");

		// Generate DB structure
		writer.print("\t /** \n\t * This class contains the DB schema \n\t */ \n");
		outputDBStrct(writer, key_dataType);

		// Generate Output Structure using select attributes
		writer.print("\n\t /** \n\t * This class contains the selection attributes \n\t */ \n");
		outputResultAttributes(writer, key_dataType);

		// Generate class with all f-vect
		writer.print("\n\t /** \n\t * This class contains all f-vect attributes \n\t * and group by attribues \n\t */ \n");
		outputFvect(writer, key_dataType);

		//Writing the main method in the output file.
		writer.print("\n\t /** \n\t * Main - method \n\t * The program will start here. \n\t */ \n");
		writer.print("\tpublic static void main(String [] args){\n");

		writer.print("\n\t\tMFOutput mf = new MFOutput();\n");
		writer.print("\t\tmf.connect();\n");
		writer.print("\t\tmf.retrive();\n");
		writer.print("\t\tmf.addToOutput();\n");
		writer.print("\t\tmf.outputTable();\n");

		writer.print("\t}\n");

		// Create functions for connection
		writer.print("\n\t /** \n\t * This method will load the drivers for the DB. \n\t */ \n");
		InformationSchema.outputConnection(writer);

		// Main logic
		writer.print("\n\t /** \n\t * This method will create the data set required. \n\t */ \n");
		outputLogicCode(writer, key_dataType);

		//Create comapre Methods
		writer.print("\n\t /** \n\t * These are comapare methods to comapre two string values orinteger values. \n\t * @return boolean true if same or else false. \n\t */ \n");
		outputCompare(writer);
		
		//Create addToOutput to build result
		writer.print("\n\t /** \n\t * This method will filter output data if having conditions exsist. \n\t */ \n");
		outputAddToPut(writer);
		
		// Generate method to print output
		writer.print("\n\t /** \n\t * This method will create format for outputting the data table. \n\t */ \n");
		outputTableMethod(writer);
				
		writer.print("}\n");

	}

	/**
	 * This method will create a method called addToOutput()
	 * which will transfer the required output from MF structure 
	 * to the selection variables.
	 * 
	 * @param writer PrintWriter instance
	 */
	private static void outputAddToPut(PrintWriter writer) {
		// TODO Auto-generated method stub
		ParseVariables pv = new ParseVariables(); 
		writer.print("\tpublic void addToOutput(){\n");
		writer.print("\t\tfor(MF_Structure ms: mfStruct){\n");
		writer.print("\t\t\tResult_Attributes ra = new Result_Attributes();\n");
		
		for(String str: pv.getGroupby())
		{
			writer.print("\t\t\t\tra."+str+" = ms."+str+";\n");
		}
		
		
		writer.print("\t\t\tif(");
		
		boolean isSecondHaving = false;
		
		//Putting the having condition in the output file for filtering the output.
		
		if(pv.getSizeHaving()!= 0)
		{
			for(String str: pv.getHaving())
			{
				if(str.contains("sum") || str.contains("avg") || str.contains("max") || str.contains("min") || str.contains("count"))
				{
					str = str.replace("sum", "ms.sum");
					str = str.replace("avg", "ms.avg");
					str = str.replace("max", "ms.max");
					str = str.replace("min", "ms.min");
					str = str.replace("count","ms.count");
				}
				if(isSecondHaving == false)
				{
					writer.print("("+str+")");
					isSecondHaving = true;
				}
				else if(isSecondHaving == true)
				{
					writer.print(" && (" + str +")");
				}
			}
		}
		//If there is no having condition put "true".
		else
		{
			writer.print("true");
		}
		writer.print("){\n");
		for(String str: pv.getSelect())
		{
			for (FormAggregate fa: pv.getFvect())
			{
				if(str.equals(fa.getString()))
				{
					writer.print("\t\t\t\tra."+fa.getString()+" = ms."+fa.getString()+";\n");
				}
			}
			
		}
		
		writer.print("\t\t\t}\n");
		writer.print("\t\t\toutput_attributes.add(ra);\n");
		writer.print("\t\t}\n");
		writer.print("\t}\n");
		
	}

	/**
	 * This creates a method called compare in the output file
	 * which will compare the sting or integer values.
	 * 
	 * @param writer PrintWriter Instance
	 */
	private static void outputCompare(PrintWriter writer) {
		// TODO Auto-generated method stub
		  //generate compare method
		writer.print("\tboolean compare(String str1, String str2){\n");
		writer.print("\t\treturn str1.equals(str2);\n\t}\n");
		writer.print("\tboolean compare(int num1, int num2){\n");
		writer.print("\t\treturn (num1 == num2);\n\t}\n"); 
		
	}

	/**
	 * This will create a class called MF_Structure which contains
	 * all the variables needed to create output.
	 * @param writer PrintWriter Instance
	 * @param key_dataType 
	 */
	private static void outputFvect(PrintWriter writer, HashMap<String, String> key_dataType) {
		// TODO Auto-generated method stub
		ParseVariables pv = new ParseVariables();
		List<String> added_elements = new ArrayList<String>();
		
		//Class MF_Structure
		writer.print("\tpublic class MF_Structure{\n");
		for (String str : pv.getGroupby()) {
			for (Map.Entry<String, String> entry : key_dataType.entrySet())
				if(str.equals(entry.getKey()))
				{
					writer.print("\t\t" + entry.getValue() + " " + entry.getKey() + ";\n");
				}
				
		}
		
		for (FormAggregate fa : pv.getFvect()) {
			
			/**
			 * Checking if the aggregate function is average or not.
			 * 
			 * If it is average divide it into sum and count to calculate average.
			 */
			
			if (fa.aggregate.equals("avg")) {
				String sum = "sum_" + fa.attribute + "_" + fa.index;
				String count = "count_" + fa.attribute + "_" + fa.index;
				if (!added_elements.contains(sum))
				{
					added_elements.add(sum);
					writer.print("\t\tint" + " sum_" + fa.attribute + "_" + fa.index + ";\n");
				}
				if(!added_elements.contains(count))
				{
					added_elements.add(count);
					writer.print("\t\tint" + " count_" + fa.attribute + "_" + fa.index + ";\n");
				}
				if(!added_elements.contains(fa.getString()))
				{
					writer.print("\t\tint " + fa.getString() + ";\n");
                    added_elements.add(fa.getString());
				}
					
				} else {
					if(!added_elements.contains(fa.getString()))
					{
						writer.print("\t\tint " + fa.getString() + ";\n");
						added_elements.add(fa.getString());
					}
				
			}

		}
		writer.print("\t}\n");

	}

	/**
	 * 
	 * This will generate structure of the selection variables in the output file.
	 * 
	 * @param writer PrintWriter Instance
	 * @param key_dataType Schema of DB
	 */
	private static void outputResultAttributes(PrintWriter writer, HashMap<String, String> key_dataType) {
		// TODO Auto-generated method stub
		ParseVariables pv = new ParseVariables();
		
		//Class Result_Attributes
		writer.print("\tpublic class Result_Attributes{\n");
		for (String str : pv.getSelect()) {
			if (key_dataType.get(str) != null) {
				writer.print("\t\t" + key_dataType.get(str) + " " + str + ";\n");
			} else {
				writer.print("\t\tint " + str + ";\n");
			}
		}
		writer.print("\t}\n");
	}

	/**
	 * This will generate the retrieve method in the output file.
	 * 
	 * @param writer PrintWriter Instance.
	 * @param key_dataType Schema of the DB
	 */
	private static void outputLogicCode(PrintWriter writer, HashMap<String, String> key_dataType) {
		// TODO Auto-generated method stub
		ParseVariables pv = new ParseVariables();
		writer.print("\tpublic void retrive(){\n");
		// Trying Connection
		writer.print("\t\ttry {\n");
		writer.print("\t\t\tConnection con = DriverManager.getConnection(url, usr, pwd);\n");
		writer.print("\t\t\tSystem.out.println(\"Success connecting server!\");\n");

		// Declaring variables
		writer.print("\t\t\tResultSet result_set;\n");
		writer.print("\t\t\tboolean more;\n");
		writer.print("\t\t\tStatement st = con.createStatement();\n");
		writer.print("\t\t\tString query = \"select * from sales\";\n");
		writer.print("\n");

		outputWhileLoops(writer, pv, key_dataType);

		// Catch Block
		writer.print("\t\t}catch(Exception e) {\n");
		writer.print("\t\t\tSystem.out.println(\"Error occured due to:\" + e);\n");
		writer.print("\t\t\te.printStackTrace();\n");
		writer.print("\t\t}\n");
		writer.print("\t}\n");

	}

	/**
	 * 
	 * @param writer Instance of PrintWriter.
	 * @param pv Instance of ParseVariables class.
	 * @param key_dataType Schema of the DB
	 */
	private static void outputWhileLoops(PrintWriter writer, ParseVariables pv, HashMap<String, String> key_dataType) {
		// TODO Auto-generated method stub
		List<String> added_elements = new ArrayList<String>();
		List<String> updated_elements = new ArrayList<String>();
		
		//Generating number of while loops equal to number of Grouping variables.
		writer.print("\n\t\t\t /** \n\t\t\t * Generating while loops for each grouping variable. \n\t\t\t */ \n");
		for (int i = 0; i < pv.getNumber(); i++) {
			writer.print("\n\t\t\t//While loop for grouping variable "+ (i+1) +".\n");
			writer.print("\t\t\tresult_set = st.executeQuery(query);\n");
			writer.print("\t\t\tmore = result_set.next();\n");
			writer.print("\t\t\twhile(more){\n");
			writer.print("\t\t\t\tDBStruct currentRow = new DBStruct();\n");
			for (Map.Entry<String, String> entry : key_dataType.entrySet()) {
				if (entry.getValue().equals("String")) {
					writer.print("\t\t\t\tcurrentRow." + entry.getKey() + " = result_set.getString(\"" + entry.getKey()
							+ "\");\n");
				} else if (entry.getValue().equals("int")) {
					writer.print("\t\t\t\tcurrentRow." + entry.getKey() + " = result_set.getInt(\"" + entry.getKey()
							+ "\");\n");
				}
			}

			boolean isSecondWhere = false;
			boolean isSecondSuchThat = false;
			boolean negative = false;
			// Filtering data if it has where conditions
			writer.print("\t\t\t\tif(");
			if (pv.getSizeWhere() != 0) {
				for (String str : pv.getWhere()) {
					str = str.replace(" ", "");
					if(str.contains("<=") || str.contains(">=") || str.contains(">") || str.contains("<") || str.contains("!="))
					{
						str = str;
					}
					else if(str.contains("="))
					{
						str = str.replace("=", "==");
					}
					
					if(str.contains("prod==") || str.contains("state==") || str.contains("cust=="))
					{						
						String [] nameVal = str.split("=");
						str = str.replace(str, nameVal[0]+".equals(\""+nameVal[2]+"\")");
					}
					if(str.contains("prod!=") || str.contains("state!=") || str.contains("cust!="))
					{	
						negative = true;					
						String [] nameVal = str.split("!=");
						str = str.replace(str, nameVal[0]+".equals(\""+nameVal[1]+"\")");
					}
					if (isSecondWhere == false) {
						if(negative == true)
						{
							writer.print("!currentRow." + str);
							isSecondWhere = true;
							negative = false;
						}
						else
						{
							writer.print("currentRow." + str);
							isSecondWhere = true;
						}
						
					} else if (isSecondWhere == true) {
						if(negative == true)
						{
							writer.print(" && !currentRow." + str);
							negative = false;
						}
						else
						{
							writer.print(" && currentRow." + str);
						}
						
					}

				}
			} else {
				writer.print(true);
			}
			writer.print("){\n");

			//Putting the such that conditions if any.
			negative = false;
			writer.print("\t\t\t\t\tif (");
			for (Pair pair : pv.getSuchthat()) 
			{
				negative = false;
				String str = pair.getAttribute();
				str = str.replace(" ", "");
				if(str.contains("<=") || str.contains(">=") || str.contains(">") || str.contains("<"))
				{
					str = str;
				}
				else if(str.contains("="))
				{
					str = str.replace("=", "==");
				}
				if(str.contains("prod==") || str.contains("state==") || str.contains("cust=="))
				{						
					String [] nameVal = str.split("=");
					str = str.replace(str, nameVal[0]+".equals("+nameVal[2]+")");
				}
				if(str.contains("prod!=") || str.contains("state!=") || str.contains("cust!="))
				{	
					negative = true;					
					String [] nameVal = str.split("!==");
					str = str.replace(str, nameVal[0]+".equals(\""+nameVal[1]+"\")");
				}
				if (pair.getIndex() == i + 1 && isSecondSuchThat == false) {
					if(negative == true)
					{
						isSecondSuchThat = true;
						writer.print("!currentRow." + str);
					}
					else
					{
						isSecondSuchThat = true;
						writer.print("currentRow." + str);
					}
					
				} else if (pair.getIndex() == i + 1 && isSecondSuchThat == true) {
					if(negative == true)
					{
						writer.print(" && !currentRow." + str);
					}
					else
					{
						writer.print(" && currentRow." + str);
					}
					
				}
			}
			if (isSecondSuchThat == false) {
				writer.print("true");
			}
			writer.print("){\n");

			writer.print("\t\t\t\t\t\tboolean found = false;\n");
			writer.print("\t\t\t\t\t\tfor(MF_Structure row: mfStruct){\n");

			boolean isSecondGroupByVariable = false;
			writer.print("\t\t\t\t\t\t\tif(compare(row.");
			for(String str: pv.getGroupby())
			{
				if(isSecondGroupByVariable == false)
				{
					writer.print(str+",currentRow."+str+")");
					isSecondGroupByVariable = true;
				}
				else
				{
					writer.print(" && compare(row."+str+",currentRow."+str+")");
				}
			}
			writer.print("){\n");
			
			writer.print("\t\t\t\t\t\t\t\tfound = true;\n");
			
			
			//Outputting the aggregate functions if record is added already.
			for(FormAggregate fa: pv.getFvect())
			{
				 if (Integer.parseInt(fa.index) == i+1){
	                    if (fa.aggregate.equals("avg")){
	                    	String sum = "sum_" + fa.attribute + "_" + fa.index;
							String count = "count_" + fa.attribute + "_" + fa.index;
							if (!updated_elements.contains(sum))
							{
								updated_elements.add(sum);
							    writer.print("\t\t\t\t\t\t\t\trow."+sum+" += currentRow."+fa.attribute+";\n");			                       
							}
							if(!updated_elements.contains(count))
							{
								updated_elements.add(count);
								writer.print("\t\t\t\t\t\t\t\trow."+count+" ++;\n");
							}
							if(!updated_elements.contains(fa.getString()))
							{
								updated_elements.add(fa.getString());
								writer.print("\t\t\t\t\t\t\t\tif(row."+count+" !=0){\n");
								writer.print("\t\t\t\t\t\t\t\t\trow."+fa.getString()+" = row."+sum+"/row."+count+";\n");
								writer.print("\t\t\t\t\t\t\t\t}\n");
							}
						
							
	                    }
	                    if (!updated_elements.contains(fa.getString()) && fa.aggregate.equals("sum")){
	                        writer.print("\t\t\t\t\t\t\t\trow."+fa.getString()+" += currentRow."+fa.attribute+";\n");
	                        updated_elements.add(fa.getString());
	                    }
	                    if (!updated_elements.contains(fa.getString()) && fa.aggregate.equals("max")){
	                        writer.print("\t\t\t\t\t\t\t\trow."+fa.getString()+" = (row."+fa.getString()+"< currentRow."+fa.attribute+") ? currentRow."+fa.attribute +" :row."+fa.getString()+";\n");
	                        updated_elements.add(fa.getString());
	                    }
	                    if (!updated_elements.contains(fa.getString()) && fa.aggregate.equals("min")){
	                        writer.print("\t\t\t\t\t\t\t\trow."+fa.getString()+" = (row."+fa.getString()+"> currentRow."+fa.attribute+") ? currentRow."+fa.attribute +" :row."+fa.getString()+";\n");
	                        updated_elements.add(fa.getString());
	                    }
	                    if (!updated_elements.contains(fa.getString()) && fa.aggregate.equals("count")){
	                        writer.print("\t\t\t\t\t\t\t\trow."+fa.getString()+"++;\n");
	                        updated_elements.add(fa.getString());
	                    }
	                }
			}
			
			writer.print("\t\t\t\t\t\t\t}\n");
			writer.print("\t\t\t\t\t\t}\n");
			
			//If record is found for the first time the following lines will called.
			writer.print("\t\t\t\t\t\tif(found == false){\n");
			writer.print("\t\t\t\t\t\t\tMF_Structure addCurrentRow = new MF_Structure();\n");
			for(String str: pv.getGroupby())
			{
				writer.print("\t\t\t\t\t\t\taddCurrentRow."+str+" = currentRow."+str+";\n");
			}
			for(FormAggregate fa: pv.getFvect())
			{	
				if(Integer.parseInt(fa.index) == i+1)
				{
					if (fa.aggregate.equals("avg")) {
						String sum = "sum_" + fa.attribute + "_" + fa.index;
						String count = "count_" + fa.attribute + "_" + fa.index;
						if (!added_elements.contains(sum))
						{
							added_elements.add(sum);
							writer.print("\t\t\t\t\t\t\taddCurrentRow." + "sum_" + fa.attribute + "_" + fa.index + " = currentRow."+fa.attribute+";\n");
						}
						if(!added_elements.contains(count))
						{
							added_elements.add(count);
							writer.print("\t\t\t\t\t\t\taddCurrentRow." + "count_" + fa.attribute + "_" + fa.index + "++;\n");
						}
						if(!added_elements.contains(fa.getString()))
						{
							added_elements.add(fa.getString());
							writer.print("\t\t\t\t\t\t\tif(addCurrentRow."+count+" !=0){\n");
							writer.print("\t\t\t\t\t\t\t\taddCurrentRow."+fa.getString()+" = addCurrentRow."+sum+"/addCurrentRow."+count+";\n");
							writer.print("\t\t\t\t\t\t\t}\n");
						}
							
						} else {
							if(!added_elements.contains(fa.getString()))
							{
								if(fa.aggregate.equals("count"))
								{
									writer.print("\t\t\t\t\t\t\taddCurrentRow." + "count_" + fa.attribute + "_" + fa.index + "++;\n");
								}
								else
								{
									writer.print("\t\t\t\t\t\t\taddCurrentRow." + fa.getString() + " = currentRow."+fa.attribute+";\n");
								}
								added_elements.add(fa.getString());
							}
						
					}
				}
				
			}
			writer.print("\t\t\t\t\t\t\tmfStruct.add(addCurrentRow);\n");		
			writer.print("\t\t\t\t\t\t}\n");
			writer.print("\t\t\t\t\t}\n");
			writer.print("\t\t\t\t}\n");
			writer.print("\t\t\t\tmore = result_set.next();\n");
			writer.print("\t\t\t}\n");

		}
	}

	/**
	 * 
	 * This will output the code for generating the table.
	 * 
	 * @param writer PrintWriter Instance
	 *
	 */
	private static void outputTableMethod(PrintWriter writer) {
		// TODO Auto-generated method stub
		int length;
		ParseVariables pv = new ParseVariables();
		writer.print("\tpublic void outputTable(){\n");
		
		for(String str: pv.getSelect())
		{
			length = str.length();
			writer.print("\t\tSystem.out.printf(\"%-"+length+"s\",\"" +str+"\\t\");\n");
		}
		writer.print("\t\tSystem.out.printf(\"\\n\");\n");
		writer.print("\t\tSystem.out.printf(\"");
		for(String str: pv.getSelect())
		{
			length = str.length();
			for(int i =0; i< length; i++)
			{
				writer.print("=");
			}
			writer.print("\\t");
		}
		writer.print(" \");\n");
		writer.print("\t\tfor(Result_Attributes ra: output_attributes){\n");
		writer.print("\t\t\tSystem.out.printf(\"\\n\");\n");
		for(String str: pv.getSelect())
		{
			for(String str1: pv.getGroupby())
			{
				if(str.equals(str1))
				{
					length = str.length();
					if(str.equals("month") || str.equals("year") || str.equals("days") || str.contentEquals("quant"))
					{
						writer.print("\t\t\tSystem.out.printf(\"%"+length+"s\\t\", ra."+str+");\n");
					}
					else
					{
						writer.print("\t\t\tSystem.out.printf(\"%-"+length+"s\\t\", ra."+str+");\n");
					}
					
				}
			}
			for(FormAggregate fv : pv.getFvect())
			{
				if(str.equals(fv.getString()))
				{
					length = str.length();
					writer.print("\t\t\tSystem.out.printf(\"%"+length+"s\\t\", ra."+str+");\n");	
				}
			}
			
		}
		writer.print("\t\t}\n");
		writer.print("\t}\n");
	}

	/**
	 * This will generate class called DBStruct in the output file.
	 * 
	 * @param writer PrintWriter Instance
	 * @param key_dataType Scehma of DB
	 */
	private static void outputDBStrct(PrintWriter writer, HashMap<String, String> key_dataType) {
		// TODO Auto-generated method stub

		//Class DBStruct
		writer.print("\tpublic class DBStruct{\n");
		for (Map.Entry<String, String> entry : key_dataType.entrySet())
			writer.print("\t\t" + entry.getValue() + " " + entry.getKey() + ";\n");

		writer.print("\t}\n");
	}

	/**
	 * This will generate the Import files and time the file is generated.
	 * 
	 * @param writer PrintWriter Instance.
	 */
	private static void outputImportPart(PrintWriter writer) {
		// TODO Auto-generated method stub
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		writer.print("package final_output;\n");
		writer.print("import java.sql.*;\n");
		writer.print("import java.util.*;\n");
		writer.print("import java.io.*;\n");
		
		writer.print("\n\n/** \n* CS 562 Project\n");
		writer.print("* This is auto generated file on " + now + "\n");
		writer.print("* Author: Kunj Desai \n*/ \n");

	}
}