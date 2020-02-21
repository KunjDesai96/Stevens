package final_project;

/**
 * This will parse the variables for such that condition.
 * 
 * @author Kunj Desai
 *
 */
public class Pair {
	
	public int index;
	public String attribute;
	
	/**
	 * Constructor.
	 * @param index Number of the grouping variable.
	 * @param attribute The attribute related to the grouping variable.
	 */
	public Pair(int index, String attribute) {
		this.index = index;
		this.attribute = attribute;
	}

	//Setter and getter methods.
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}



}
