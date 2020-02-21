package final_project;

/**
 * This class will parse variables which 
 * contains grouping variables in the form of following structure.
 * 
 * @author Kunj Desai
 *
 */
public class FormAggregate {

	public String aggregate, attribute, index;
	
	/**
	 * Constructor
	 * 
	 * @param index Number of grouping variable.
	 * @param aggregate Aggregate with that grouping variable.
	 * @param attribute Attribute with that grouping variable.
	 */
	FormAggregate(String index, String aggregate, String attribute)
	{
		this.index = index;
		this.attribute = attribute;
		this.aggregate = aggregate;
	}
	
	/**
	 * 
	 * @return String with in the given manner.
	 */
	public String getString()
	{
		return aggregate+"_"+attribute+"_"+index;
	}
}
