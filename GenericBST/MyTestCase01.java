// Sean Szumlanski
// COP 3503, Spring 2021

// ===========================
// GenericBST: TestCase01.java
// ===========================
// A brief test case to help ensure you've implemented the difficultyRating()
// and hoursSpent() methods correctly, and that you can create and use a
// GenericBST.
//
// For detailed compilation and testing instructions, see the assignment PDF.


import java.io.*;
import java.util.*;

public class MyTestCase01
{
	public static void main(String [] args)
	{

		// Create a GenericBST.
		GenericBST<String> myTree = new GenericBST<String>();

		String [] array = {"Anthony", "is", "the", "man"};

		for (int i = 0; i < array.length; i++)
			myTree.insert(array[i]);

		myTree.inorder();
		myTree.preorder();
		myTree.postorder();
	}
}
