package final_project;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class EMFGenerator {
	static void generateEMF(HashMap<String, String> db_struct) {
		try {
			ParseVariables pv = new ParseVariables();
			File output_file = new File(
					"/Users/kunj/eclipse-workspace/CS562/src/final_output/EMFOutput.java");
			PrintWriter output = new PrintWriter(output_file);
			// generate code part
			outputImportPart(output);

			// output required classes
			outputRequiredClass(output, db_struct);

			// output main class.
			output.print("public class EMFOutput {\n");
			output.print("\tString usr =\"postgres\";\n");
			output.print("\tString pwd =\"dharmesh\";\n");
			output.print("\tString url =\"jdbc:postgresql://localhost:5432/CS562\";\n");
			output.print("\tArrayList<MF_structure> result_list = new ArrayList<MF_structure>();\n");

			// define the aggregate function with index 0
			if (pv.getFvect().size() > 0) {
				for (FormAggregate info : pv.getFvect()) {
					if (info.aggregate.equals("avg")) {
						output.print("\t" + db_struct.get(info.attribute) + "\tsum_" + info.attribute + "_" + info.index
								+ " = 0;\n");
						output.print("\t" + db_struct.get(info.attribute) + "\tcount_" + info.attribute + "_"
								+ info.index + " = 0;\n");

					} else
						output.print("\t" + db_struct.get(info.attribute) + "\t" + info.aggregate + "_" + info.attribute
								+ "_" + info.index + " = 0;\n");
				}
			}

			outputMain(output);

			//output the connection
			InformationSchema.outputConnection(output);
			
			//output retrieve method
			outputRetriveMethod(output, db_struct);

			// generate compare method
			output.print("\tboolean compare(String s1, String s2){\n");
			output.print("\t\treturn s1.equals(s2);\n\t}\n");
			output.print("\tboolean compare(int i1, int i2){\n");
			output.print("\t\treturn (i1 == i2);\n\t}\n");

			output.print("}\n");

			output.print("\t\t\n");
			output.print("\t\t\n");
			output.print("\t\t\n");
			output.print("\t\t\n");
			output.print("\t\t\n");
			output.close();
		} catch (Exception e) {
			System.out.println("Error:" + e);
			e.printStackTrace();
		}
	}
	/**
	 * This will help to output retrieve method
	 * @param output
	 * @param db_struct
	 */
	private static void outputRetriveMethod(PrintWriter output, HashMap<String, String> db_struct) {
		ParseVariables pv = new ParseVariables();
		// TODO Auto-generated method stub
		output.print("\tvoid retrieve(){\n");
		output.print("\t\ttry {\n");
		output.print("\t\tConnection con = DriverManager.getConnection(url, usr, pwd);\n");
		output.print("\t\tSystem.out.println(\"Success connecting server!\");\n");
		output.print("\t\tResultSet rs;\n");
		output.print("\t\tboolean more;\n");
		output.print("\t\tStatement st = con.createStatement();\n");
		output.print("\t\tString ret = \"select * from sales\";\n");

		// first while loop the add value to aggregate with index 0 and add grouping
		// variables to the list.
		output.print("\t\trs = st.executeQuery(ret);\n");
		output.print("\t\tmore=rs.next();\n");
		output.print("\t\twhile(more){\n");
		output.print("\t\t\tdbTuple newtuple = new dbTuple();\n");
		for (Map.Entry<String, String> entry : db_struct.entrySet()) {
			if (entry.getValue().equals("String")) {
				output.print("\t\t\tnewtuple." + entry.getKey() + " = rs.getString(\"" + entry.getKey() + "\");\n");
			}
			if (entry.getValue().equals("int")) {
				output.print("\t\t\tnewtuple." + entry.getKey() + " = rs.getInt(\"" + entry.getKey() + "\");\n");
			}
		}
		if (pv.getFvect().size() > 0) {
			for (FormAggregate info : pv.getFvect()) {
				if (info.aggregate.equals("sum")) {
					output.print("\t\t\t" + info.getString() + " += newtuple." + info.attribute + ";\n");
				} else if (info.aggregate.equals("max")) {
					output.print("\t\t\t" + info.getString() + " = (" + info.getString() + "< newtuple."
							+ info.attribute + ") ? newtuple." + info.attribute + " : " + info.getString() + ";\n");
				} else if (info.aggregate.equals("min")) {
					output.print("\t\t\t" + info.getString() + " = (" + info.getString() + "> newtuple."
							+ info.attribute + ") ? newtuple." + info.attribute + " : " + info.getString() + ";\n");
				} else if (info.aggregate.equals("count")) {
					output.print("\t\t\t" + info.getString() + " ++;\n");
				} else if (info.aggregate.equals("avg")) {
					output.print("\t\t\tsum_" + info.attribute + "_" + info.index + " += newtuple." + info.attribute
							+ ";\n");
					output.print("\t\t\tcount_" + info.attribute + "_" + info.index + " ++;\n");

				}
			}
		}
		boolean flag = false;
		output.print("\t\t\tif(");
		if (pv.getSizeWhere() == 0) {
			output.print("true");
		} else {
			for (String temp : pv.getWhere()) {
				if (flag == false) {
					output.print(temp);
					flag = true;
				} else if (flag == true)
					output.print(" && " + temp);
			}
		}
		output.print("){\n");
		output.print("\t\t\t\tboolean found = false;\n");
		output.print("\t\t\t\tfor (MF_structure temp : result_list){\n");

		// find if the grouping attributes are already in the list
		output.print("\t\t\t\t\tif(compare(temp.");
		flag = false;
		for (String temp : pv.getGroupby()) {
			if (flag == false) {
				output.print(temp + ",newtuple." + temp + ")");
				flag = true;
			} else if (flag == true) {
				output.print(" && compare(temp." + temp + ",newtuple." + temp + ")");
			}
		}
		flag = false;
		output.print("){\n");
		output.print("\t\t\t\t\t\tfound=true;\n");
		output.print("\t\t\t\t\t\tbreak;\n");
		output.print("\t\t\t\t\t}\n");
		output.print("\t\t\t\t}\n");
		output.print("\t\t\t\tif (found == false){\n");
		output.print("\t\t\t\t\tMF_structure newrow = new MF_structure();\n");
		for (String temp : pv.getGroupby()) {
			output.print("\t\t\t\t\tnewrow." + temp + " = newtuple." + temp + ";\n");
		}
		for (FormAggregate temp : pv.getFvect()) {
			if (temp.aggregate.equals("avg")) {
				output.print("\t\t\t\t\tnewrow.sum_" + temp.attribute + "_" + temp.index + " = 0;\n");
				output.print("\t\t\t\t\tnewrow.count_" + temp.attribute + "_" + temp.index + " = 0;\n");
			}
			if (temp.aggregate.equals("sum") || temp.aggregate.equals("max"))
				output.print("\t\t\t\t\tnewrow." + temp.getString() + " = 0;\n");
			if (temp.aggregate.equals("min"))
				output.print("\t\t\t\t\tnewrow." + temp.getString() + " = Integer.MAX_VALUE;\n");
			if (temp.aggregate.equals("count"))
				output.print("\t\t\t\t\tnewrow." + temp.getString() + " = 0;\n");
		}
		output.print("\t\t\t\t\tresult_list.add(newrow);\n");
		output.print("\t\t\t\t}\n");
		output.print("\t\t\t}\n");
		output.print("\t\t\tmore=rs.next();\n");
		output.print("\t\t}\n\n");

		// generate core code!
		for (int i = 1; i <= pv.getNumber(); i++) {
			output.print("\t\trs = st.executeQuery(ret);\n");
			output.print("\t\tmore=rs.next();\n");
			output.print("\t\twhile(more){\n");
			// get each tuple from database
			output.print("\t\t\tdbTuple newtuple = new dbTuple();\n");
			for (Map.Entry<String, String> entry : db_struct.entrySet()) {
				if (entry.getValue().equals("String")) {
					output.print(
							"\t\t\tnewtuple." + entry.getKey() + " = rs.getString(\"" + entry.getKey() + "\");\n");
				}
				if (entry.getValue().equals("int")) {
					output.print(
							"\t\t\tnewtuple." + entry.getKey() + " = rs.getInt(\"" + entry.getKey() + "\");\n");
				}
			}

			flag = false;
			output.print("\t\t\tif(");
			if (pv.getSizeWhere() == 0) {
				output.print("true");
			} else {
				for (String temp : pv.getWhere()) {
					if (flag == false) {
						output.print(temp);
						flag = true;
					} else if (flag == true)
						output.print(" && " + temp);
				}
			}
			flag = false;
			output.print("){\n");
			output.print("\t\t\t\tfor (MF_structure temp : result_list){\n");

			output.print("\t\t\t\t\tif (");
			for (Pair temp : pv.getSuchthat()) {
				if (temp.index == i && flag == false) {
					flag = true;
					output.print(temp.attribute);
				} else if (temp.index == i && flag == true) {
					output.print("&&" + temp.attribute);
				}
			}
			if (flag == false)
				output.print("true");
			flag = false;
			output.print("){\n");
			for (FormAggregate info : pv.getFvect()) {
				if (Integer.parseInt(info.index) == i) {
					if (info.aggregate.equals("avg")) {
						output.print("\t\t\t\t\t\ttemp.sum_" + info.attribute + "_" + info.index + " += newtuple."
								+ info.attribute + ";\n");
						output.print("\t\t\t\t\t\ttemp.count_" + info.attribute + "_" + info.index + " ++;\n");
					}
					if (info.aggregate.equals("sum")) {
						output.print(
								"\t\t\t\t\t\ttemp." + info.getString() + " += newtuple." + info.attribute + ";\n");
					}
					if (info.aggregate.equals("max")) {
						output.print("\t\t\t\t\t\ttemp." + info.getString() + " = (temp." + info.getString()
								+ "< newtuple." + info.attribute + ") ? newtuple." + info.attribute + " :temp."
								+ info.getString() + ";\n");
					}
					if (info.aggregate.equals("min")) {
						output.print("\t\t\t\t\ttemp." + info.getString() + " = (temp." + info.getString()
								+ "> newtuple." + info.attribute + ") ? newtuple." + info.attribute + " :temp."
								+ info.getString() + ";\n");
					}
					if (info.aggregate.equals("count")) {
						output.print("\t\t\t\t\t\ttemp." + info.getString() + " ++;\n");
					}
				}
			}
			output.print("\t\t\t\t\t}\n");
			output.print("\t\t\t\t}\n");
			output.print("\t\t\t}\n");
			output.print("\t\t\tmore=rs.next();\n");
			output.print("\t\t}\n\n");
		}
		output.print("\t\t}catch(Exception e) {\n");
		output.print("\t\t\tSystem.out.println(\"errors!\");\n");
		output.print("\t\t\te.printStackTrace();\n");
		output.print("\t\t}\n");
		output.print("\t}\n");

		output.print("\tvoid output(){\n");
		output.print("\t\tfor (MF_structure temp : result_list)\n");
		output.print("\t\t\ttemp.output();\n");
		output.print("\t}\n");
	}

	/**
	 * This will output the main method
	 * @param output
	 */
	private static void outputMain(PrintWriter output) {
		// TODO Auto-generated method stub

		output.print("\n\tpublic static void main(String[] args) {\n");
		output.print("\t\tEMFOutput emf = new EMFOutput();\n");
		output.print("\t\temf.connect();\n");
		output.print("\t\temf.retrieve();\n");
		output.print("\t\temf.output();\n\t}\n");

	}

	/**
	 * This will output required structures in the form of classes
	 * @param output
	 * @param db_struct
	 */
	private static void outputRequiredClass(PrintWriter output, HashMap<String, String> db_struct) {
		// TODO Auto-generated method stub
		ParseVariables pv = new ParseVariables();
		// generate class dbTuple
		output.print("class dbTuple{\n");
		for (Map.Entry<String, String> entry : db_struct.entrySet())
			output.print("\t" + entry.getValue() + "\t" + entry.getKey() + ";\n");
		output.print("}\n\n");

		/* generate mf structure class */
		output.print("class MF_structure{\n");
		// output grouping attributes
		for (String temp : pv.getGroupby())
			output.print("\t" + db_struct.get(temp) + "\t" + temp + ";\n");
		// output select attributes
		for (FormAggregate temp : pv.getFvect()) {
			if (temp.aggregate.equals("avg")) {
				output.print(
						"\t" + db_struct.get(temp.attribute) + "\tsum_" + temp.attribute + "_" + temp.index + ";\n");
				output.print(
						"\t" + db_struct.get(temp.attribute) + "\tcount_" + temp.attribute + "_" + temp.index + ";\n");
			} else
				output.print("\t" + db_struct.get(temp.attribute) + "\t" + temp.getString() + ";\n");
		}
		output.print("\tvoid output(){\n");
		output.print("\t\tSystem.out.printf(");
		boolean found = false;
		for (String temp : pv.getGroupby()) {
			if (found == false) {
				output.print(temp);
				found = true;
			} else if (found == true) {
				output.print("+\"\\t\"+" + temp);
			}
		}
		output.print(");\n");
		for (FormAggregate temp : pv.getFvect()) {
			if (temp.aggregate.equals("avg")) {
				output.print("\t\tif (count_" + temp.attribute + "_" + temp.index + " == 0)\n");
				output.print("\t\t\tSystem.out.printf(\"\\t0\");\n");
				output.print("\t\telse\n");
				output.print("\t\t\tSystem.out.printf(\"\\t\"+sum_" + temp.attribute + "_" + temp.index + "/count_"
						+ temp.attribute + "_" + temp.index + ");\n");
			} else if (temp.aggregate.equals("max")) {
				output.print("\t\tif (" + temp.getString() + " == 0)\n");
				output.print("\t\t\tSystem.out.printf(\"\\t0\");\n");
				output.print("\t\telse\n");
				output.print("\t\t\tSystem.out.printf(\"\\t\"+" + temp.getString() + ");\n");
			} else if (temp.aggregate.equals("min")) {
				output.print("\t\tif (" + temp.getString() + " == Integer.MAX_VALUE)\n");
				output.print("\t\t\tSystem.out.printf(\"\\t0\");\n");
				output.print("\t\telse\n");
				output.print("\t\t\tSystem.out.printf(\"\\t\"+" + temp.getString() + ");\n");
			} else {
				output.print("\t\tSystem.out.printf(\"\\t\"+" + temp.getString() + ");\n");
			}
		}
		output.print("\t\tSystem.out.printf(\"\\n\");\n");
		output.print("\t}\n");

		output.print("}\n\n");

	}

	/**
	 * This will generate the Import files and time the file is generated.
	 * 
	 * @param writer PrintWriter Instance.
	 */
	private static void outputImportPart(PrintWriter writer) {
		// TODO Auto-generated method stub
		
		writer.print("package final_output;\n");
		writer.print("import java.sql.*;\n");
		writer.print("import java.util.*;\n");
		writer.print("import java.io.*;\n");

		writer.print("\n\n/** \n* CS 562 Project\n");
		writer.print("* This is auto generated file.\n");
		writer.print("* Author:Deep Chokshi\n*/ \n");

	}
}
