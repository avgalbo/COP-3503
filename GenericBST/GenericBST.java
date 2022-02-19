// Sean Szumlanski
// COP 3503, Spring 2021

// Modified by Anthony Galbo
// an577845

// ====================
// GenericBST: GenericBST.java
// ====================
// Basic binary search tree (BST) implementation that supports insert() and
// delete() operations. With the modification to accept any data type.


import java.io.*;
import java.util.*;

// Class Node that stores any data type.
class Node<AnyNodeType>
{
	AnyNodeType data;
	Node<AnyNodeType> left, right;

	// Constructor method that takes in any data type.
	Node(AnyNodeType data)
	{
		this.data = data;
	}
}

// A GenericBST class that holds nodes with any type of data.
public class GenericBST<AnyType extends Comparable<AnyType>>
{
	private Node<AnyType> root;

	// Method that calls private Node insert() method.
	public void insert(AnyType data)
	{
		root = insert(root, data);
	}

	// Method that inserts a new node in a BST with any type of data.
	private Node<AnyType> insert(Node<AnyType> root, AnyType data)
	{
		// Case of an empty BST.
		if (root == null)
			return new Node<AnyType>(data);

		// Case where the node inserted is less than the value in the root node.
		else if (data.compareTo(root.data) < 0)
			root.left = insert(root.left, data);

		// Case where the node inserted is greater than the value in the root node.
		else if (data.compareTo(root.data) > 0)
			root.right = insert(root.right, data);

		return root;
	}

	// Method that calls private Node delete() method.
	public void delete(AnyType data)
	{
		root = delete(root, data);
	}

	// Method that deletes a node in a BST with any type of data.
	private Node<AnyType> delete(Node<AnyType> root, AnyType data)
	{
		// Case where the tree is empty.
		if (root == null)
			return null;

		// Case where the node deleted is less than the value in the root node.
		else if (data.compareTo(root.data) < 0)
			root.left = delete(root.left, data);

		// Case where the node deleted is greater than the value in the root node.
		else if (data.compareTo(root.data) > 0)
			root.right = delete(root.right, data);

		else
		{
			// Case where we encounter a leaf node.
			if (root.left == null && root.right == null)
				return null;

			// Case where node only has a right child.
			else if (root.left == null)
				return root.right;

			// Case where node only has a left child.
			else if (root.right == null)
				return root.left;

			// Special case where node has 2 children, find the max value in the left
			// subtree then call the delete() method.
			else
			{
				root.data = findMax(root.left);
				root.left = delete(root.left, root.data);
			}
		}

		return root;
	}

	// This method assumes root is non-null, since this is only called by
	// delete() on the left subtree, and only when that subtree is not empty.
	private AnyType findMax(Node<AnyType> root)
	{
		// Traverse until we do not encounter a right child, then return its data.
		while (root.right != null)
			root = root.right;

		return root.data;
	}

	// Method that calls the private boolean contains() method.
	public boolean contains(AnyType data)
	{
		return contains(root, data);
	}

	// Method that searches for a node when passed in any type of data.
	private boolean contains(Node<AnyType> root, AnyType data)
	{
		// Case where there is no node that exists in the BST.
		if (root == null)
			return false;

		// Case where the value passed is less than the roots value.
		else if (data.compareTo(root.data) < 0)
			return contains(root.left, data);

		// Case where the value passsed is greater than the roots value.
		else if (data.compareTo(root.data) > 0)
			return contains(root.right, data);

		// Found it!!!
		else
			return true;

	}

	// Method that calls the private void inorder() method.
	public void inorder()
	{
		System.out.print("In-order Traversal:");
		inorder(root);
		System.out.println();
	}

	// Method that prints the inorder traversal of a BST.
	private void inorder(Node<AnyType> root)
	{
		if (root == null)
			return;

		inorder(root.left);
		System.out.print(" " + root.data);
		inorder(root.right);
	}

	// Method that calls the private void preorder() method.
	public void preorder()
	{
		System.out.print("Pre-order Traversal:");
		preorder(root);
		System.out.println();
	}

	// Method that prints the preorder traversal of a BST.
	private void preorder(Node<AnyType> root)
	{
		if (root == null)
			return;

		System.out.print(" " + root.data);
		preorder(root.left);
		preorder(root.right);
	}

	// Method that calls the private void postorder() method.
	public void postorder()
	{
		System.out.print("Post-order Traversal:");
		postorder(root);
		System.out.println();
	}

	// Method that prints the postorder traversal of a BST.
	private void postorder(Node<AnyType> root)
	{
		if (root == null)
			return;

		postorder(root.left);
		postorder(root.right);
		System.out.print(" " + root.data);
	}

	public static double difficultyRating()
	{
		return 2.0;
	}

	public static double hoursSpent()
	{
		return 5.0;
	}

}
