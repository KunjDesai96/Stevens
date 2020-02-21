package final_output;
import java.sql.*;
import java.util.*;
import java.io.*;


/** 
* CS 562 Project
* This is auto generated file on 2019-12-06T13:45:11.008942
* Author: Kunj Desai 
*/ 

public class MFOutput {
	//Variables to connect to DB
	private static final String usr = "postgres";
	private static final String pwd = "dharmesh";
	private static final String url = "jdbc:postgresql://localhost:5432/CS562";
	//Variables to generate the output
	List<Result_Attributes> output_attributes = new ArrayList<Result_Attributes>();
	List<MF_Structure> mfStruct = new ArrayList<MF_Structure>();

	 /** 
	 * This class contains the DB schema 
	 */ 
	public class DBStruct{
		String prod;
		int month;
		int year;
		String state;
		int quant;
		String cust;
		int day;
	}

	 /** 
	 * This class contains the selection attributes 
	 */ 
	public class Result_Attributes{
		String cust;
		String prod;
		int sum_quant_1;
		int sum_quant_2;
		int sum_quant_3;
		int count_month_1;
		int count_month_2;
		int count_month_3;
	}

	 /** 
	 * This class contains all f-vect attributes 
	 * and group by attribues 
	 */ 
	public class MF_Structure{
		String cust;
		String prod;
		int sum_quant_1;
		int sum_quant_2;
		int sum_quant_3;
		int count_month_1;
		int count_month_2;
		int count_month_3;
	}

	 /** 
	 * Main - method 
	 * The program will start here. 
	 */ 
	public static void main(String [] args){

		MFOutput mf = new MFOutput();
		mf.connect();
		mf.retrive();
		mf.addToOutput();
		mf.outputTable();
	}

	 /** 
	 * This method will load the drivers for the DB. 
	 */ 
	public void connect(){
		try {
		Class.forName("org.postgresql.Driver");
		System.out.println("Success loading Driver!");
		} catch(Exception exception) {
		exception.printStackTrace();
		}
	}

	 /** 
	 * This method will create the data set required. 
	 */ 
	public void retrive(){
		try {
			Connection con = DriverManager.getConnection(url, usr, pwd);
			System.out.println("Success connecting server!");
			ResultSet result_set;
			boolean more;
			Statement st = con.createStatement();
			String query = "select * from sales";


			 /** 
			 * Generating while loops for each grouping variable. 
			 */ 

			//While loop for grouping variable 1.
			result_set = st.executeQuery(query);
			more = result_set.next();
			while(more){
				DBStruct currentRow = new DBStruct();
				currentRow.prod = result_set.getString("prod");
				currentRow.month = result_set.getInt("month");
				currentRow.year = result_set.getInt("year");
				currentRow.state = result_set.getString("state");
				currentRow.quant = result_set.getInt("quant");
				currentRow.cust = result_set.getString("cust");
				currentRow.day = result_set.getInt("day");
				if(true){
					if (currentRow.state.equals("NY")){
						boolean found = false;
						for(MF_Structure row: mfStruct){
							if(compare(row.cust,currentRow.cust) && compare(row.prod,currentRow.prod)){
								found = true;
								row.sum_quant_1 += currentRow.quant;
								row.count_month_1++;
							}
						}
						if(found == false){
							MF_Structure addCurrentRow = new MF_Structure();
							addCurrentRow.cust = currentRow.cust;
							addCurrentRow.prod = currentRow.prod;
							addCurrentRow.sum_quant_1 = currentRow.quant;
							addCurrentRow.count_month_1++;
							mfStruct.add(addCurrentRow);
						}
					}
				}
				more = result_set.next();
			}

			//While loop for grouping variable 2.
			result_set = st.executeQuery(query);
			more = result_set.next();
			while(more){
				DBStruct currentRow = new DBStruct();
				currentRow.prod = result_set.getString("prod");
				currentRow.month = result_set.getInt("month");
				currentRow.year = result_set.getInt("year");
				currentRow.state = result_set.getString("state");
				currentRow.quant = result_set.getInt("quant");
				currentRow.cust = result_set.getString("cust");
				currentRow.day = result_set.getInt("day");
				if(true){
					if (currentRow.state.equals("NJ")){
						boolean found = false;
						for(MF_Structure row: mfStruct){
							if(compare(row.cust,currentRow.cust) && compare(row.prod,currentRow.prod)){
								found = true;
								row.sum_quant_2 += currentRow.quant;
								row.count_month_2++;
							}
						}
						if(found == false){
							MF_Structure addCurrentRow = new MF_Structure();
							addCurrentRow.cust = currentRow.cust;
							addCurrentRow.prod = currentRow.prod;
							addCurrentRow.sum_quant_2 = currentRow.quant;
							addCurrentRow.count_month_2++;
							mfStruct.add(addCurrentRow);
						}
					}
				}
				more = result_set.next();
			}

			//While loop for grouping variable 3.
			result_set = st.executeQuery(query);
			more = result_set.next();
			while(more){
				DBStruct currentRow = new DBStruct();
				currentRow.prod = result_set.getString("prod");
				currentRow.month = result_set.getInt("month");
				currentRow.year = result_set.getInt("year");
				currentRow.state = result_set.getString("state");
				currentRow.quant = result_set.getInt("quant");
				currentRow.cust = result_set.getString("cust");
				currentRow.day = result_set.getInt("day");
				if(true){
					if (currentRow.state.equals("CT")){
						boolean found = false;
						for(MF_Structure row: mfStruct){
							if(compare(row.cust,currentRow.cust) && compare(row.prod,currentRow.prod)){
								found = true;
								row.sum_quant_3 += currentRow.quant;
								row.count_month_3++;
							}
						}
						if(found == false){
							MF_Structure addCurrentRow = new MF_Structure();
							addCurrentRow.cust = currentRow.cust;
							addCurrentRow.prod = currentRow.prod;
							addCurrentRow.sum_quant_3 = currentRow.quant;
							addCurrentRow.count_month_3++;
							mfStruct.add(addCurrentRow);
						}
					}
				}
				more = result_set.next();
			}
		}catch(Exception e) {
			System.out.println("Error occured due to:" + e);
			e.printStackTrace();
		}
	}

	 /** 
	 * These are comapare methods to comapre two string values orinteger values. 
	 * @return boolean true if same or else false. 
	 */ 
	boolean compare(String str1, String str2){
		return str1.equals(str2);
	}
	boolean compare(int num1, int num2){
		return (num1 == num2);
	}

	 /** 
	 * This method will filter output data if having conditions exsist. 
	 */ 
	public void addToOutput(){
		for(MF_Structure ms: mfStruct){
			Result_Attributes ra = new Result_Attributes();
				ra.cust = ms.cust;
				ra.prod = ms.prod;
			if(true){
				ra.sum_quant_1 = ms.sum_quant_1;
				ra.sum_quant_2 = ms.sum_quant_2;
				ra.sum_quant_3 = ms.sum_quant_3;
				ra.count_month_1 = ms.count_month_1;
				ra.count_month_2 = ms.count_month_2;
				ra.count_month_3 = ms.count_month_3;
			}
			output_attributes.add(ra);
		}
	}

	 /** 
	 * This method will create format for outputting the data table. 
	 */ 
	public void outputTable(){
		System.out.printf("%-4s","cust\t");
		System.out.printf("%-4s","prod\t");
		System.out.printf("%-11s","sum_quant_1\t");
		System.out.printf("%-11s","sum_quant_2\t");
		System.out.printf("%-11s","sum_quant_3\t");
		System.out.printf("%-13s","count_month_1\t");
		System.out.printf("%-13s","count_month_2\t");
		System.out.printf("%-13s","count_month_3\t");
		System.out.printf("\n");
		System.out.printf("====\t====\t===========\t===========\t===========\t=============\t=============\t=============\t ");
		for(Result_Attributes ra: output_attributes){
			System.out.printf("\n");
			System.out.printf("%-4s\t", ra.cust);
			System.out.printf("%-4s\t", ra.prod);
			System.out.printf("%11s\t", ra.sum_quant_1);
			System.out.printf("%11s\t", ra.sum_quant_2);
			System.out.printf("%11s\t", ra.sum_quant_3);
			System.out.printf("%13s\t", ra.count_month_1);
			System.out.printf("%13s\t", ra.count_month_2);
			System.out.printf("%13s\t", ra.count_month_3);
		}
	}
}
