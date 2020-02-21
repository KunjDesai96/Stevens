package Maze;
//package Assignment4;

/**
 * PairInt class implementation
 * @author kunj
 *
 */
public class PairInt {

	private int x;
	private int y;
	
	/**
	 * Constructor
	 * @param x
	 * @param y
	 */
	public PairInt(int x, int y)
	{
		this.x =x;
		this.y =y;
	}

	/**
	 * Getter for x
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Setter for x
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Getter for y
	 * @return y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Setter for y
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Override of equals method
	 */
	public boolean equals(Object obj)
	{
		PairInt pair = (PairInt)obj;
		if(pair.getX() == this.x && pair.getY() == this.y)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Method to print coordinates.
	 */
	public String toString()
	{
		String str = "(" + x + ", " + y + ")";
		return str;
	}
	
	/**
	 * Method to copy the PairInt object.
	 * @return copy 
	 */
	public PairInt copy()
	{
		PairInt copy = new PairInt(this.x, this.y);
		return copy;
				
	}
	
}
