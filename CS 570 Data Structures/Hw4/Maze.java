package Maze;
//package Assignment4;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Class that solves maze problems with backtracking.
 * 
 * @author Koffman and Wolfgang
 **/
public class Maze implements GridColors {

	/** The maze */
	private TwoDimGrid maze;

	public Maze(TwoDimGrid m) {
		maze = m;
	}

	/** Wrapper method. */
	public boolean findMazePath() {
		return findMazePath(0, 0); // (0, 0) is the start point.
	}

	/**
	 * Attempts to find a path through point (x, y).
	 * 
	 * @pre Possible path cells are in BACKGROUND color; barrier cells are in
	 *      ABNORMAL color.
	 * @post If a path is found, all cells on it are set to the PATH color; all
	 *       cells that were visited but are not on the path are in the TEMPORARY
	 *       color.
	 * @param x The x-coordinate of current point
	 * @param y The y-coordinate of current point
	 * @return If a path through (x, y) is found, true; otherwise, false
	 */
	public boolean findMazePath(int x, int y) {
		// COMPLETE HERE FOR PROBLEM 1
		boolean found;
		if ((x < 0 || x > (maze.getNCols() - 1)) || (y < 0 || y > (maze.getNRows() - 1))
				|| (!maze.getColor(x, y).equals(NON_BACKGROUND))) {
			found = false;
		} else if (x == maze.getNCols() - 1 && y == maze.getNRows() - 1) {
			maze.recolor(x, y, PATH);
			found = true;
		} else {
			maze.recolor(x, y, PATH);
			if (findMazePath(x - 1, y) || findMazePath(x + 1, y) || findMazePath(x, y - 1) || findMazePath(x, y + 1)) {
				found = true;
			} else {
				maze.recolor(x, y, TEMPORARY);
				found = false;
			}
		}
		return found;
	}

	// ADD METHOD FOR PROBLEM 2 HERE
	/**
	 * Problem 2 to find all possible paths in the maze
	 * 
	 * @param x The x-coordinate of current point
	 * @param y The y-coordinate of current point
	 * @return result ArratList of all paths found in the an ArrayList or an empty
	 *         ArrayList.
	 */
	public ArrayList<ArrayList<PairInt>> findAllMazePaths(int x, int y) {
		// Reference from the homework - pdf.
		ArrayList<ArrayList<PairInt>> result = new ArrayList<>();
		Stack<PairInt> trace = new Stack<>();
		findMazePathStackBased(0, 0, result, trace);
		return result;
	}

	/**
	 * This is a helper function which will be called to keep track of all visited
	 * paths. It will also do backtracking to find multiple possible paths.
	 * 
	 * @param x      The x-coordinate of current point
	 * @param y      The y-coordinate of current point
	 * @param result Takes an empty ArrayzList to store all possible paths in the
	 *               form of ArrayList.
	 * @param trace  An empty stack which will store all current coordinates visited
	 *               and help for backtracking.
	 */
	private void findMazePathStackBased(int x, int y, ArrayList<ArrayList<PairInt>> result, Stack<PairInt> trace) {
		// TODO Auto-generated method stub

		trace.push(new PairInt(x, y));
		if (x == maze.getNCols() - 1 && y == maze.getNRows() - 1) {
			ArrayList<PairInt> currentList = new ArrayList<>();
			currentList.addAll(trace);
			result.add(currentList);
			return;
		}
		if (maze.getColor(x, y).equals(NON_BACKGROUND) && (x >= 0 && x <= maze.getNCols() - 1)
				&& (y >= 0 && y <= maze.getNRows() - 1)) {
			maze.recolor(x, y, PATH);
			if (x + 1 <= maze.getNCols() - 1 && maze.getColor(x + 1, y).equals(NON_BACKGROUND)) {
				findMazePathStackBased(x + 1, y, result, trace);
			}
			if (x - 1 >= 0 && maze.getColor(x - 1, y).equals(NON_BACKGROUND)) {
				findMazePathStackBased(x - 1, y, result, trace);
			}
			if (y + 1 <= maze.getNRows() - 1 && maze.getColor(x, y + 1).equals(NON_BACKGROUND)) {
				findMazePathStackBased(x, y + 1, result, trace);
			}

			if (y - 1 >= 0 && maze.getColor(x, y - 1).equals(NON_BACKGROUND)) {
				findMazePathStackBased(x, y - 1, result, trace);
			}
		}

		// Backtracking
		if (!trace.empty()) {
			for (int i = 0; i < trace.size(); i++) {
				PairInt compareObj = new PairInt(x, y);
				if (trace.pop().equals(compareObj)) {
					break;
				}
			}
		}

		maze.recolor(x, y, NON_BACKGROUND);
	}

	// ADD METHOD FOR PROBLEM 3 HERE
	/**
	 * Problem 3: From the method of findAllMazePaths() it will find the minimum
	 * path to destination
	 * 
	 * @param x The x-coordinate of current point
	 * @param y The y-coordinate of current point
	 * @return resultMin This will contain the minimum path to the destination or an
	 *         empty ArrayList.
	 */
	public ArrayList<PairInt> findMazePathMin(int x, int y) {
		ArrayList<ArrayList<PairInt>> result = this.findAllMazePaths(0, 0);
		ArrayList<PairInt> resultMin = new ArrayList<>();
		int resultSize = result.size();
		if (!result.isEmpty()) {
			int[] sizeArr = new int[resultSize];
			int currentIndex = 0;
			int currentIndexElement;
			for (int i = 0; i < resultSize; i++) {
				sizeArr[i] = result.get(i).size();
			}

			currentIndexElement = sizeArr[0];
			for (int j = 0; j < resultSize; j++) {
				if (currentIndexElement > sizeArr[j]) {
					currentIndexElement = sizeArr[j];
					currentIndex = j;
				}
			}
			resultMin = result.get(currentIndex);

		}
		return resultMin;

	}

	/* <exercise chapter="5" section="6" type="programming" number="2"> */
	public void resetTemp() {
		maze.recolor(TEMPORARY, BACKGROUND);
	}
	/* </exercise> */

	/* <exercise chapter="5" section="6" type="programming" number="3"> */
	public void restore() {
		resetTemp();
		maze.recolor(PATH, BACKGROUND);
		maze.recolor(NON_BACKGROUND, BACKGROUND);
	}
	/* </exercise> */
}
/* </listing> */
