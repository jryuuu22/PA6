/**
 * Manual BST exercises for PA6, insertion, deletion, and traversal
 */

import java.util.*;

public class BSTManual {

	/**
	 * Answers to PA6 BST Worksheet.
	 * @author Jay Wu
	 * @since  11/11/2026
	 */

// No style for this file.	

	public static ArrayList<String>  insertElements() {

		ArrayList<String> answer_pr1 = new ArrayList<>(11);

		answer_pr1.add("54");
		answer_pr1.add("18, 67");
		answer_pr1.add("12, 41, X, 72");
		answer_pr1.add("X, X, 29, 43, X, X, X, 95");
		answer_pr1.add("X, X, X, X, 24, X, X, X, X, X, X, X, X, X, X, X");

		return answer_pr1;

	}

	public static ArrayList<ArrayList<String>> deleteElements() {

		ArrayList<ArrayList<String>> answer_pr2 = new ArrayList<>();

		// after deleting 22
		answer_pr2.add(new ArrayList<>(Arrays.asList(
				"56", "47, 60", "20, X, 59, 72", "X, 21, X, X, 57, X, 63, 94")));
		// after deleting 59
		answer_pr2.add(new ArrayList<>(Arrays.asList(
				"56", "47, 60", "20, X, 57, 72", "X, 21, X, X, X, X, 63, 94")));
		// after deleting 56
		answer_pr2.add(new ArrayList<>(Arrays.asList(
				"57", "47, 60", "20, X, X, 72", "X, 21, X, X, X, X, 63, 94")));
		// after deleting 72
		answer_pr2.add(new ArrayList<>(Arrays.asList(
				"57", "47, 60", "20, X, X, 94", "X, 21, X, X, X, X, 63, X")));
		// after deleting 63
		answer_pr2.add(new ArrayList<>(Arrays.asList(
				"57", "47, 60", "20, X, X, 94", "X, 21, X, X, X, X, X, X")));

		return answer_pr2;

	}

	public static ArrayList<String>  traversals() {

		ArrayList<String> answer_pr3 = new ArrayList<>(11);

		answer_pr3.add("4, 19, 22, 28, 39, 56, 64, 68, 93, 95, 96, 97, 98");
		answer_pr3.add("39, 19, 4, 22, 28, 93, 64, 56, 68, 96, 95, 98, 97");
		answer_pr3.add("4, 28, 22, 19, 56, 68, 64, 95, 97, 98, 96, 93, 39");

		return answer_pr3;

	}


}