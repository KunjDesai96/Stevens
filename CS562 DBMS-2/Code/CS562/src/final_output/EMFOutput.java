package final_output;
import java.sql.*;
import java.util.*;
import java.io.*;


/** 
* CS 562 Project
* This is auto generated file.
* Author:Deep Chokshi
*/ 
class dbTuple{
	String	prod;
	int	month;
	int	year;
	String	state;
	int	quant;
	String	cust;
	int	day;
}

class MF_structure{
	String	prod;
	int	month;
	int	count_quant_1;
	int	count_quant_2;
	void output(){
		System.out.printf(prod+"\t"+month);
		System.out.printf("\t"+count_quant_1);
		System.out.printf("\t"+count_quant_2);
		System.out.printf("\n");
	}
}

public class EMFOutput {
	String usr ="postgres";
	String pwd ="dharmesh";
	String url ="jdbc:postgresql://localhost:5432/CS562";
	ArrayList<MF_structure> result_list = new ArrayList<MF_structure>();
	int	count_quant_1 = 0;
	int	count_quant_2 = 0;

	public static void main(String[] args) {
		EMFOutput emf = new EMFOutput();
		emf.connect();
		emf.retrieve();
		emf.output();
	}
	public void connect(){
		try {
		Class.forName("org.postgresql.Driver");
		System.out.println("Success loading Driver!");
		} catch(Exception exception) {
		exception.printStackTrace();
		}
	}
	void retrieve(){
		try {
		Connection con = DriverManager.getConnection(url, usr, pwd);
		System.out.println("Success connecting server!");
		ResultSet rs;
		boolean more;
		Statement st = con.createStatement();
		String ret = "select * from sales";
		rs = st.executeQuery(ret);
		more=rs.next();
		while(more){
			dbTuple newtuple = new dbTuple();
			newtuple.prod = rs.getString("prod");
			newtuple.month = rs.getInt("month");
			newtuple.year = rs.getInt("year");
			newtuple.state = rs.getString("state");
			newtuple.quant = rs.getInt("quant");
			newtuple.cust = rs.getString("cust");
			newtuple.day = rs.getInt("day");
			count_quant_1 ++;
			count_quant_2 ++;
			if(newtuple.year == 1997){
				boolean found = false;
				for (MF_structure temp : result_list){
					if(compare(temp.prod,newtuple.prod) && compare(temp.month,newtuple.month)){
						found=true;
						break;
					}
				}
				if (found == false){
					MF_structure newrow = new MF_structure();
					newrow.prod = newtuple.prod;
					newrow.month = newtuple.month;
					newrow.count_quant_1 = 0;
					newrow.count_quant_2 = 0;
					result_list.add(newrow);
				}
			}
			more=rs.next();
		}

		rs = st.executeQuery(ret);
		more=rs.next();
		while(more){
			dbTuple newtuple = new dbTuple();
			newtuple.prod = rs.getString("prod");
			newtuple.month = rs.getInt("month");
			newtuple.year = rs.getInt("year");
			newtuple.state = rs.getString("state");
			newtuple.quant = rs.getInt("quant");
			newtuple.cust = rs.getString("cust");
			newtuple.day = rs.getInt("day");
			if(newtuple.year == 1997){
				for (MF_structure temp : result_list){
					if (newtuple.prod.equals(temp.prod)&&newtuple.month+1 == temp.month){
						temp.count_quant_1 ++;
					}
				}
			}
			more=rs.next();
		}

		rs = st.executeQuery(ret);
		more=rs.next();
		while(more){
			dbTuple newtuple = new dbTuple();
			newtuple.prod = rs.getString("prod");
			newtuple.month = rs.getInt("month");
			newtuple.year = rs.getInt("year");
			newtuple.state = rs.getString("state");
			newtuple.quant = rs.getInt("quant");
			newtuple.cust = rs.getString("cust");
			newtuple.day = rs.getInt("day");
			if(newtuple.year == 1997){
				for (MF_structure temp : result_list){
					if (newtuple.prod.equals(temp.prod)&&newtuple.month-1 == temp.month){
						temp.count_quant_2 ++;
					}
				}
			}
			more=rs.next();
		}

		}catch(Exception e) {
			System.out.println("errors!");
			e.printStackTrace();
		}
	}
	void output(){
		for (MF_structure temp : result_list)
			temp.output();
	}
	boolean compare(String s1, String s2){
		return s1.equals(s2);
	}
	boolean compare(int i1, int i2){
		return (i1 == i2);
	}
}
		
		
		
		
		
