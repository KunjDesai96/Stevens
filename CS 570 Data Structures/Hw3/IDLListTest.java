//package Assignment3;
/**
 * 
 * @author Kunj Desai
 * 
 * This is test file with 15 test cases.
 *
 */
public class IDLListTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			IDLList<String> idl = new IDLList<String>();
			IDLList<String> idl1 = new IDLList<String>();
			try
			{
				System.out.println("================ Test case 1 ==================");
				idl1.append("AppendInEmpty");
				System.out.println(idl1.toString());
				System.out.println("Removed head if only one element in list: "+idl1.remove());
				System.out.println(idl1.toString());
				
				System.out.println("================ Test case 2 ==================");
				idl1.append("Addtolast");
				System.out.println(idl1.toString());
				System.out.println("Removed tail/last if only one element in list: "+idl1.removeLast());
				System.out.println(idl1.toString());
		
				System.out.println("================ Test case 3 ==================");
				idl.add("FirstInEmpty");
				System.out.println(idl.toString());
				idl.add(0,"FirstwithIndex");
				System.out.println(idl.toString());
				idl.add("FirstInNonEmpty");
				System.out.println(idl.toString());
				idl.add(3,"AddwithIndex");
				System.out.println(idl.toString());
				idl.append("AppendInNonEmpty");
				System.out.println(idl.toString());
				System.out.println("The current size of the list is: "+idl.size());
				
				System.out.println("================ Test case 4 ==================");
				System.out.println("The head of the list is: "+idl.getHead());
				System.out.println("Removed head if only one element in list: "+idl.remove());
				System.out.println("After removing head,"+idl.toString());
				
				System.out.println("================ Test case 5 ==================");
				System.out.println("The tail of the list is: "+idl.getLast());
				System.out.println("Removed tail if only one element in list: "+idl.removeLast());
				System.out.println("After removing tail,"+idl.toString());
				
				System.out.println("================ Test case 6 ==================");
				System.out.println("The element at given index is: "+idl.get(2));
				
				System.out.println("================ Test case 7 ==================");
				System.out.println(idl.toString());
				System.out.println("The item removed at given index is: "+idl.removeAt(1));
				System.out.println(idl.toString());
				
				System.out.println("================ Test case 8 ==================");
				idl.add(0,"AddwithIndex");
				System.out.println(idl.toString());
				idl.remove("AddwithIndex");
				System.out.println("After removing first occurrance of \"AddwithIndex\" "+idl.toString());
				
			}
			catch(Exception e)
			{
				
				System.out.println("Failed happened because "+e.getMessage());
			}
			
			try
			{
				System.out.println("================ Test case 9 ==================");
				idl.add(-1,"ThrowError");
			}
			catch(Exception e)
			{
				System.out.println("add() failed successfully: "+e.getMessage());
			}
			

			try
			{
				System.out.println("================ Test case 10 ==================");
				idl.get(100);
			}
			catch(Exception e)
			{
				System.out.println("get() failed successfully: "+e.getMessage());
			}
			
			try
			{
				System.out.println("================ Test case 11 ==================");
				idl1.getHead();
			}
			catch(Exception e)
			{
				System.out.println("getHead() failed successfully: "+e.getMessage());
			}
			try
			{
				System.out.println("================ Test case 12 ==================");
				idl1.getLast();
			}
			catch(Exception e)
			{
				System.out.println("getLast() failed successfully: "+e.getMessage());
			}
			try
			{
				System.out.println("================ Test case 13 ==================");
				idl1.remove();
			}
			catch(Exception e)
			{
				System.out.println("remove() failed successfully: "+e.getMessage());
			}
			try
			{
				System.out.println("================ Test case 14 ==================");
				idl1.removeLast();
			}
			catch(Exception e)
			{
				System.out.println("removeLast() failed successfully: "+e.getMessage());
			}
			try
			{
				System.out.println("================ Test case 15 ==================");
				idl1.removeAt(0);
			}
			catch(Exception e)
			{
				System.out.println("removeAt() failed successfully: "+e.getMessage());
			}
	}
}
