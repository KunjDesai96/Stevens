package final_project;

import java.io.PrintWriter;
import java.sql.*;
import java.util.HashMap;

public class InformationSchema {

	private static final String usr = "postgres";
	private static final String pwd = "dharmesh";
	private static final String url = "jdbc:postgresql://localhost:5432/CS562";
	public static HashMap<String, String> key_dataType = new HashMap<String, String>();;

	/**
	 * This will help getting information of the schema of DB.
	 * 
	 * @return HashMap<String, String> contains dataType, variable name from DB.
	 */
	public static HashMap<String, String> getInformationSchema() {
		try {

			Connection con = DriverManager.getConnection(url, usr, pwd); // connect to the database using the password																	// and username
			System.out.println("Success connecting server!");

			ResultSet resultSet;
			boolean more;

			Statement st = con.createStatement();
			
			//Query to get information of schema
			String info_query = "select data_type, column_name from information_schema.columns where table_name= 'sales'";
			resultSet = st.executeQuery(info_query);
			more = resultSet.next();
			while (more) {
				if (resultSet.getString("data_type").contains("character")) {
					key_dataType.put(resultSet.getString("column_name"), "String");
				} else {
					key_dataType.put(resultSet.getString("column_name"), "int");
				}

				more = resultSet.next();
			}
			
		} catch (SQLException e) {
			System.out.println("Connection URL or username or password errors!");
			e.printStackTrace();
		}
		return key_dataType;
	}

	/**
	 * This will generate connect() method in the output file.
	 * @param writer PrintWriter instance
	 */
	public static void outputConnection(PrintWriter writer) {
		// TODO Auto-generated method stub
		 	writer.print("\tpublic void connect(){\n");
	        writer.print("\t\ttry {\n");
	        writer.print("\t\tClass.forName(\"org.postgresql.Driver\");\n");
	        writer.print("\t\tSystem.out.println(\"Success loading Driver!\");\n");
	        writer.print("\t\t} catch(Exception exception) {\n");
	        writer.print("\t\texception.printStackTrace();\n");
	        writer.print("\t\t}\n\t}\n");

		
	}
}
