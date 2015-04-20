package tree_factory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import tree.Action_Node;
import tree.Comparison;
import tree.Decision_Node;
import tree.Node;
import ai.DecisionTreeAI;
import all_together.All_Together_Control;

/**
 * DecisionTreeLearning contains an algorithm that recursively reduces data by
 * determining the variable with the most information gain then splits the data
 * on that variable. That variable becomes an internal node in the decision
 * tree. If either of the two halves of the data have only one action that
 * action becomes a leaf node. This is done until the entire data set has been
 * reduced and all internal nodes lead to leaf nodes.
 * 
 * @author James Woods
 * 
 */
public class DecisionTreeLearning extends DecisionTreeFactory {

	private static final int NUMBER_OF_ACTIONS = 7;
	private static final String REGEX = ":";
	private static final int BASE_TWO = 2;
	private static final int NUMBER_OF_VARIABLES = 7;
	private static final int NUMBER_OF_VALUES = 8;
	private static final int ACTION_NAME = 7;
	private File file;
	private FileReader fileReader;
	private BufferedReader reader;

	int countForTesting = 0;

	/**
	 * DecisionTreeLearning takes an input file name to be used for the data
	 * mining.
	 * 
	 * @param inputFile
	 *            the file containing raw data.
	 */
	public DecisionTreeLearning(String inputFile,
			DecisionTreeAI decisionTreeAI, All_Together_Control control) {
		super(decisionTreeAI, control);

		file = new File(inputFile);
		try {
			fileReader = new FileReader(file.getAbsolutePath());
			reader = new BufferedReader(fileReader);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		

	}

	/**
	 * LearnTree applies the concept of Information Gain to find a variable that
	 * will reduce the amount of randomness in the data the most thus preserving
	 * the information therein. Then nodes are created for the tree and the tree
	 * is built recursively. Finally, the tree is returned in a Decision_Node.
	 * 
	 * @return a Decision_Node containing the learned tree.
	 * @throws IOException
	 */
	public Decision_Node learnTree() throws IOException {

		// Read the file into an ArrayList of type String
		// To be used in the rest of the algorithm.
		ArrayList<String> falsePath = new ArrayList<String>();
		String toAdd = reader.readLine();
		while (toAdd != null) {
			falsePath.add(toAdd);
			toAdd = reader.readLine();
		}
		reader.close();
		
		boolean[] actions = new boolean[NUMBER_OF_ACTIONS];
		for(int i = 0; i < NUMBER_OF_ACTIONS; i++)
			actions[i] = true;

		// Create an array of boolean values that will track which
		// indices have already been checked.
		boolean[] indices = getBooleanArray();

		// Perform the Information Gain on the data.
		int variable = informationGain(falsePath, indices, actions);

		// Track the variables that have been eliminated.
		indices[variable] = false;

		// Get the Comparison for this data.
		Comparison compare = getComparison(variable);

		// Split the data.
		ArrayList<String> truePath = split(falsePath, variable);

		Node forLeft = null;
		Node forRight = null;
		boolean right = false;
		boolean left = false;
		// If one of the remaining lists contains only one action
		// create that action.
		if (truePath.size() > 0) {
			left = allTheSame(truePath);
		} else {
			// This will happen when we have a branch that is never or rarely
			// used.
			// In this case we are going to supply a default for random
			// movement.
			forLeft = new Action_Node(ai, control, new MoveRandomAction(ai));
		}
		if (falsePath.size() > 0) {
			right = allTheSame(falsePath);
		} else {
			// This will happen when we have a branch that is never or rarely
			// used.
			// In this case we are going to supply a default for random
			// movement.
			forRight = new Action_Node(ai, control, new MoveRandomAction(ai));
		}
		if (left) {
			forLeft = getNodeFor(truePath, actions);
		}
		if (right) {
			forRight = getNodeFor(falsePath, actions);
		}

		if (forLeft != null && forRight != null) {
			return new Decision_Node(ai, control, compare, forLeft, forRight);
		} else if (forLeft != null) {
			return new Decision_Node(ai, control, compare, forLeft, reduce(
					falsePath, indices.clone(), actions.clone(), 0));
		} else if (forRight != null) {
			return new Decision_Node(ai, control, compare, reduce(falsePath,
					indices.clone(), actions.clone(), 1), forRight);
		}

		// Return a new Decision Node with the recursively
		// created tree.
		return new Decision_Node(ai, control, compare, reduce(truePath,
				indices.clone(), actions.clone(), 1), reduce(falsePath, indices.clone(), actions.clone(), 0));
	}

	private boolean[] getBooleanArray() {
		boolean[] indices = new boolean[NUMBER_OF_VARIABLES];
		for (int i = 0; i < NUMBER_OF_VARIABLES; i++)
			indices[i] = true;
		return indices;
	}

	/**
	 * creates a Node for the action represented by the given data.
	 * 
	 * @param pathInfo
	 *            the data to create an action for.
	 * @return the node containing an action appropriate for the information.
	 */
	private Node getNodeFor(ArrayList<String> pathInfo, boolean[] actions) {
		// Get the name of the action.
		String action = pathInfo.get(0).split(REGEX)[ACTION_NAME];

		// Create the appropriate action.
		if ("SpinAction".equals(action)) {
			actions[0] = false;
			return new Action_Node(ai, control, new SpinAction(ai));
		} else if ("MoveCenterAction".equals(action)) {
			actions[1] = false;
			return new Action_Node(ai, control, new MoveCenterAction(ai));
		} else if ("MoveRandomAction".equals(action)) {
			actions[2] = false;
			return new Action_Node(ai, control, new MoveRandomAction(ai));
		} else if ("NextRoomAction".equals(action)) {
			actions[3] = false;
			return new Action_Node(ai, control, new NextRoomAction(ai));
		} else if ("NoAction".equals(action)) {
			actions[4] = false;
			return new Action_Node(ai, control, new NoAction());
		} else if ("PersueAction".equals(action)) {
			actions[5] = false;
			return new Action_Node(ai, control, new PersueAction(ai));
		} else if ("BloodAndGuts".equals(action)) {
			actions[6] = false;
			return new Action_Node(ai, control, new BloodAndGuts(ai));
		}


		return null;
	}

	/**
	 * getComparison creates and returns the comparison of the type
	 * corresponding to the given index in the Strings of data.
	 * 
	 * @param variable
	 *            the index in the String.
	 * @return the comparison type.
	 */
	private Comparison getComparison(int variable) {

		switch (variable) {
		case 0: {

			return new InSameRoom(ai);
		}
		case 1: {
			return new Arrived(ai);
		}
		case 2: {
			return new Collision(ai);
		}
		case 3: {
			return new CanRun(ai);
		}
		case 4: {
			return new EnemyFar(ai);
		}
		case 5: {
			return new EnemyInSight(ai);
		}
		case 6: {
			return new PauseTimer(ai);
		}
		}

		return null;
	}

	/**
	 * Performs the Information Gain function on the data to determine which
	 * variable should be used as the next node.
	 * 
	 * @param data
	 *            the array of remaining data.
	 * @return the index of the variable type to be used.
	 */
	public int informationGain(ArrayList<String> data, boolean[] indices, boolean[] actions) {

		double[] P_action = new double[NUMBER_OF_ACTIONS];
		int[] totalAction = new int[NUMBER_OF_ACTIONS];
		double[][] P_action_variable = new double[NUMBER_OF_ACTIONS][BASE_TWO
				* NUMBER_OF_VARIABLES];
		double H_Y = 0;
		double[] H_Y_X = new double[NUMBER_OF_VARIABLES];
		double[] IG_X = new double[NUMBER_OF_VARIABLES];
		int totalCount = 0;

		int toReturn = 9;

		if (data.size() == 0)
			return toReturn;

		String[] splitStore = new String[NUMBER_OF_VALUES];
		double currentMax = Float.MIN_VALUE;

		for (int x = 0; x < NUMBER_OF_ACTIONS; x++) {
			P_action[x] = 0;
			totalAction[x] = 0;
			for (int y = 0; y < NUMBER_OF_VARIABLES; y++) {
				P_action_variable[x][y] = 0;
				P_action_variable[x][y + NUMBER_OF_VARIABLES] = 0;
			}
		}
		for (int x = 0; x < NUMBER_OF_VARIABLES; x++) {
			H_Y_X[x] = 0;
			IG_X[x] = 0;
		}

		// Move through the data and sum the times each action is called.

		for (int y = 0; y < data.size(); y++) {

			String toCalc = data.get(y);
			splitStore = toCalc.split(REGEX);
			toCalc = splitStore[ACTION_NAME];

			// Record each time the action was used.
			if ("SpinAction".equals(toCalc) && actions[0]) {
				P_action[0]++;
				totalCount++;
			} else if ("MoveCenterAction".equals(toCalc) && actions[1]) {
				P_action[1]++;
				totalCount++;
			} else if ("MoveRandomAction".equals(toCalc) && actions[2]) {
				P_action[2]++;
				totalCount++;
			} else if ("NextRoomAction".equals(toCalc) && actions[3]) {
				P_action[3]++;
				totalCount++;
			} else if ("NoAction".equals(toCalc) && actions[4]) {
				P_action[4]++;
				totalCount++;
			} else if ("PersueAction".equals(toCalc) && actions[5]) {
				P_action[5]++;
				totalCount++;
			} else if ("BloodAndGuts".equals(toCalc) && actions[6]) {
				P_action[6]++;
				totalCount++;
			}
				

		}

		// Now we can calculate the entropy for the system.
		for (int y = 0; y < NUMBER_OF_ACTIONS; y++) {
			if (P_action[y] == 0 || !actions[y]) {
				H_Y += 0;
			} else
				H_Y += (-(P_action[y] / totalCount) * (Math.log(P_action[y]
						/ totalCount) / Math.log(BASE_TWO)));
		}

		// Now we must find the Entropy values for each variable given each
		// action.
		// We will move through the data and get the sums of each action when
		// each
		// when each variable is true and when each variable is false.
		for (int dat = 0; dat < data.size(); dat++) {

			// Get each string and split it up.
			String toCalc = data.get(dat);
			splitStore = toCalc.split(REGEX);

			// Determine the action for each string then move through the
			// variables and
			// Get the sum of true and false.
			toCalc = splitStore[ACTION_NAME];
			for (int var = 0; var < NUMBER_OF_VARIABLES; var++) {
				if (indices[var]) {
					if ("SpinAction".equals(toCalc) && actions[0]) {
						totalAction[0]++;
						if ("true".equals(splitStore[var])) {
							P_action_variable[0][var]++;
						} else if ("false".equals(splitStore[var])) {
							P_action_variable[0][var + NUMBER_OF_VARIABLES]++;
						}
					} else if ("MoveCenterAction".equals(toCalc) && actions[1]) {
						totalAction[1]++;
						if ("true".equals(splitStore[var])) {
							P_action_variable[1][var]++;
						} else if ("false".equals(splitStore[var])) {
							P_action_variable[1][var + NUMBER_OF_VARIABLES]++;
						}
					} else if ("MoveRandomAction".equals(toCalc) && actions[2]) {
						totalAction[2]++;
						if ("true".equals(splitStore[var])) {
							P_action_variable[2][var]++;
						} else if ("false".equals(splitStore[var])) {
							P_action_variable[2][var + NUMBER_OF_VARIABLES]++;
						}
					} else if ("NextRoomAction".equals(toCalc) && actions[3]) {
						totalAction[3]++;
						if ("true".equals(splitStore[var])) {
							P_action_variable[3][var]++;
						} else if ("false".equals(splitStore[var])) {
							P_action_variable[3][var + NUMBER_OF_VARIABLES]++;
						}
					} else if ("NoAction".equals(toCalc) && actions[4]) {
						totalAction[4]++;
						if ("true".equals(splitStore[var])) {
							P_action_variable[4][var]++;
						} else if ("false".equals(splitStore[var])) {
							P_action_variable[4][var + NUMBER_OF_VARIABLES]++;
						}
					} else if ("PersueAction".equals(toCalc) && actions[5]) {
						totalAction[5]++;
						if ("true".equals(splitStore[var])) {
							P_action_variable[5][var]++;
						} else if ("false".equals(splitStore[var])) {
							P_action_variable[5][var + NUMBER_OF_VARIABLES]++;
						}
					} else if ("BloodAndGuts".equals(toCalc) && actions[6]) {
						totalAction[6]++;
						if ("true".equals(splitStore[var])) {
							P_action_variable[6][var]++;
						} else if ("false".equals(splitStore[var])) {
							P_action_variable[6][var + NUMBER_OF_VARIABLES]++;
						}
					}
				}

			}

		}

		// Now we calculate H(Y|X).
		// We must go through each of the action variable combinations and sum
		// them.
		for (int act = 0; act < NUMBER_OF_ACTIONS; act++) {

			// First get the sum of the variable parts for each action.
			for (int var = 0; var < NUMBER_OF_VARIABLES; var++) {

				if (indices[var] && actions[act]) {
					if (P_action_variable[act][var] == 0)
						H_Y_X[var] += 0;
					else
						H_Y_X[var] += (totalAction[act] / totalCount)
								* (-(P_action_variable[act][var] / totalAction[act]) * (Math
										.log(P_action_variable[act][var]
												/ totalAction[act]) / Math
										.log(BASE_TWO)));
					if (P_action_variable[act][var + NUMBER_OF_VARIABLES] == 0)
						H_Y_X[var] += 0;
					else
						H_Y_X[var] += (totalAction[act] / totalCount)
								* (-(P_action_variable[act][var
										+ NUMBER_OF_VARIABLES] / totalAction[act]) * (Math
										.log(P_action_variable[act][var
												+ NUMBER_OF_VARIABLES]
												/ totalAction[act]) / Math
										.log(BASE_TWO)));
				}
			}

		}

		// Now we can calculate Information Gain to find the variable to split
		// on.
		// We will cycle through the data that we have already assembled and
		// subtract H(Y|X) from H(Y) to get the IG for each variable.
		// While we are moving through the data we will track the largest
		// value and its variable index.
		for (int var = 0; var < NUMBER_OF_VARIABLES; var++) {
			if (indices[var]) {
				IG_X[var] = H_Y - H_Y_X[var];

				// Check the value against the current maximum.
				if (currentMax < IG_X[var]) {
					toReturn = var;
					currentMax = IG_X[var];
				}
			}
		}

		return toReturn;
	}

	/**
	 * reduce determines which variable has the highest Information Gain and
	 * returns a node that contains the required Comparison.
	 * 
	 * @param remaining
	 *            the remaining data to check.
	 * @return
	 */
	public Node reduce(ArrayList<String> remaining, boolean[] indices, boolean[] actions, int branch) {

		// Perform the Information Gain on the data.
		int variable = informationGain(remaining, indices, actions);

		// Check the indices. If they are all false we can
		// return a node for the most common value left in the array.
		// Also, if we get an incoherent value for information gain
		// We can select the most common node.
		if (variable == 9 || noneLeft(indices)) {
			return getCommon(remaining, actions, variable);
		}

		// Track the variables that have been eliminated.
		indices[variable] = false;

		// Get the Comparison for this data.
		Comparison compare = getComparison(variable);

		// Split the data.
		ArrayList<String> truePath = split(remaining, variable);

		Node forLeft = null;
		Node forRight = null;
		boolean right = false;
		boolean left = false;
		// If one of the remaining lists contains only one action
		// create that action.
		if (truePath.size() > 0) {
			left = allTheSame(truePath);
		} else {
			// This will happen when we have a branch that is never or rarely
			// used.
			// In this case we are going to supply a default value of NoAction.
			forLeft = new Action_Node(ai, control, new MoveRandomAction(ai));
		}
		if (remaining.size() > 0) {
			right = allTheSame(remaining);
		} else {
			// This will happen when we have a branch that is never or rarely
			// used.
			// In this case we are going to supply a default value of NoAction.
			forRight = new Action_Node(ai, control, new MoveRandomAction(ai));
		}
		if (left) {
			forLeft = getNodeFor(truePath, actions);
		}
		if (right) {
			forRight = getNodeFor(remaining, actions);
		}

		if (forLeft != null && forRight != null) {
			return new Decision_Node(ai, control, compare, forLeft, forRight);
		} else if (forLeft != null) {
			return new Decision_Node(ai, control, compare, forLeft, reduce(
					remaining, indices.clone(), actions.clone(), 0));
		} else if (forRight != null) {
			return new Decision_Node(ai, control, compare, reduce(remaining,
					indices.clone(), actions.clone(), 1), forRight);
		}

		// Return a new Decision Node with the recursively
		// created tree.
		return new Decision_Node(ai, control, compare,
				reduce(truePath, indices.clone(), actions.clone(), 1), reduce(remaining, indices.clone(), actions.clone(), 0));
	}

	/**
	 * returns the most common node remaining in the data.
	 * 
	 * @param remaining
	 *            the data to look at
	 * @return the most common Action node.
	 */
	private Node getCommon(ArrayList<String> remaining, boolean[] actions, int attribute) {

		int[] averages = new int[NUMBER_OF_ACTIONS];

		for (int i = 0; i < remaining.size(); i++) {

			String action = remaining.get(i).split(REGEX)[ACTION_NAME];
			
			//If collision attribute was selected choose the blood and guts action.
			if (attribute == 2) {
				return new Action_Node(ai, control, new BloodAndGuts(ai));
			}

			// Create the appropriate action.
			if ("SpinAction".equals(action)) {
				averages[0]++;
			} else if ("MoveCenterAction".equals(action)) {
				averages[1]++;
			} else if ("MoveRandomAction".equals(action)) {
				averages[2]++;
			} else if ("NextRoomAction".equals(action)) {
				averages[3]++;
			} else if ("NoAction".equals(action)) {
				averages[4]++;
			} else if ("PersueAction".equals(action)) {
				averages[5]++;
			} else if ("BloodAndGuts".equals(action)) {
				averages[6]++;
			}


		}

		int index = 0;
		int currentMax = Integer.MIN_VALUE;
		for (int i = 0; i < NUMBER_OF_ACTIONS; i++) {
			if (averages[i] > currentMax) {
				index = i;
				currentMax = averages[i];
			}
		}

		// Create the appropriate action.
		if (index == 0 && actions[index]) {
			return new Action_Node(ai, control, new SpinAction(ai));
		} else if (index == 1 && actions[index]) {
			return new Action_Node(ai, control, new MoveCenterAction(ai));
		} else if (index == 2 && actions[index]) {
			return new Action_Node(ai, control, new MoveRandomAction(ai));
		} else if (index == 3 && actions[index]) {
			return new Action_Node(ai, control, new NextRoomAction(ai));
		} else if (index == 5 && actions[index]) {
			return new Action_Node(ai, control, new PersueAction(ai));
		} else if (index == 6 && actions[index]) {
			return new Action_Node(ai, control, new BloodAndGuts(ai));
		} else {
			return new Action_Node(ai, control, new MoveRandomAction(ai));
		}
	}

	private boolean noneLeft(boolean[] indices) {
		for (int x = 0; x < indices.length; x++) {
			if (indices[x]) {
				return false;
			}
		}
		return true;
	}

	private ArrayList<String> returnedStrings;

	/**
	 * split creates and returns an ArrayList with only the String data that
	 * contains the true value for the given variable. The ArrayList passed in
	 * as a parameter will contain the false value variable Strings.
	 * 
	 * @param toSplit
	 *            the array list to be split
	 * @param the
	 *            index of the variable to split on in the String.
	 * @return the ArrayList containing the true path.
	 */
	public ArrayList<String> split(ArrayList<String> toSplit, int variableIndex) {
		returnedStrings = new ArrayList<String>();

		// Move through the input ArrayList and remove items
		// that are true and add them to the toReturn list.
		for (int i = 0; i < toSplit.size(); i++) {
			String toCheck = toSplit.get(i);
			String[] broken = toCheck.split(REGEX);

			if ("true".equals(broken[variableIndex])) {
				returnedStrings.add(toSplit.remove(i--));

			}
		}

		return returnedStrings;

	}

	/**
	 * allTheSame checks the last index of the given ArrayList to see if they
	 * are all the same.
	 * 
	 * @param toCheck
	 *            the ArrayList of strings to be checked.
	 * @return true if and only if all the strings contain the same Action type.
	 */
	public boolean allTheSame(ArrayList<String> toCheck) {

		// If the list has one value return true.
		if (toCheck.size() == 1)
			return true;

		String previous = null;

		// Get the first String to check and break it apart.
		// Since having an array of length zero here would be a problem
		// for our tree, we will just throw an index out of bounds exception
		// with a short explanation.
		previous = toCheck.get(0);

		previous = previous.split(REGEX)[ACTION_NAME];

		for (int i = 1; i < toCheck.size(); i++) {
			String current = toCheck.get(i).split(REGEX)[ACTION_NAME];
			// If any of these are different return false immediately.
			if (!current.equals(previous))
				return false;

			// Set the current as previous.
			previous = current;
		}

		// If we make it here we have checked all of the String values.
		return true;
	}

}
