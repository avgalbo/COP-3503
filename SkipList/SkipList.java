// Anthony Galbo
// COP 3503, Spring 2021
// an577845

import java.util.*;

class Node<AnyType extends Comparable<AnyType>>
{
  AnyType data;
  ArrayList<Node<AnyType>> next;

  // Constructer that creates a new node with a specified height.
  Node(int height)
  {
    next = new ArrayList<>(height);

    for (int i = 0; i < height; i++)
      next.add(null);
  }

  // Constructer that creates a new node with a specified height and initializes
  // the nodes value to data.
  Node(AnyType data, int height)
  {
    next = new ArrayList<>(height);
    this.data = data;

    for(int i = 0; i < height; i++)
      next.add(null);
  }

  // Method that returns the data of that node.
  public AnyType value()
  {
    return data;
  }

  // Method that returns the height of this node.
  public int height()
  {
    return next.size();
  }

  // Method that returns a reference to the next node at this particular level.
  public Node<AnyType> next(int level)
  {
    if (level < 0 || level > height() - 1)
      return null;

    return next.get(level);
  }

  // Method that sets the next refrence at the given level.
  public void setNext(int level, Node<AnyType> node)
  {
    if (level < 0 || level > height() - 1)
      return;

    next.set(level, node);
  }

  // Method that grows the node by one level.
  public void grow()
  {
    next.add(null);
  }

  // Method that returns true if there was a 50/50 change that grew. otherwise
  // it returns false.
  public boolean maybeGrow()
  {
    if ((int)(Math.random() * 2) == 1)
    {
      next.add(null);
      return true;
    }
    return false;
  }

  // Method that removes the top of this nodes tower.
  public void trim(int height)
  {
    next.remove(height);
  }
}

public class SkipList<AnyType extends Comparable<AnyType>>
{

  Node<AnyType> head;
  int numElements = 0;

  // Constructerthat that creates a new skip list with an initalized height to 0.
  SkipList()
  {
    head = new Node<>(0);
  }

  // Constructerthat that creates a new skip list and initalizes the head node to
  // the specified height paramater.
  SkipList(int height)
  {
    if (height < 1)
      height = 0;

    head = new Node<>(height);
  }

  // Method that returns the number of nodes in the list.
  public int size()
  {
    return numElements;
  }

  // Method that returns the height of the list.
  public int height()
  {
    return head.height();
  }

  // Method that returns the head of the list.
  public Node<AnyType> head()
  {
    return head;
  }

  // Method that inserts a node with a raondomly generated height.
  public void insert(AnyType data)
  {
    insert(data, generateRandomHeight(getMaxHeight(numElements + 1)));
  }

  // Method that inserts a node with a specified height paramater.
  public void insert(AnyType data, int height)
  {
    // Insertion increments the number of nodes in the list to one more.
    numElements++;

    // Grow the skip list as long as the max height is greater than the origonal
    // height of the list.
    if (getMaxHeight(numElements) > this.height())
      growSkipList();

    Node<AnyType> newNode = new Node<>(data, height);
    Node<AnyType> iter = head();
    int skipListHeight = height() - 1;
    HashMap <Integer, Node<AnyType>> tracker = new HashMap<>();

    // Loop though the list as long as iter does not equal to null and as long
    // as we dont fall off of the skiplist.
    while (iter != null && skipListHeight >= 0)
    {
        int cmp = 0;

        // Set compare variable as long as the next node is not equal to null.
        if (iter.next(skipListHeight) != null)
          cmp = data.compareTo(iter.next(skipListHeight).value());

        // Case where next node is pointing to null or the data is bigger than
        // the inserted data, if so, drop a "bread crumb" and decrement the height.
        if (iter.next(skipListHeight) == null || cmp <= 0)
        {
          tracker.put(skipListHeight, iter);
          skipListHeight--;
        }

        // Case where the data is smaller than the inserted data. If so, skip
        // forward to the next node at that particular height of the list.
        else if (cmp > 0)
          iter = iter.next(skipListHeight);
    }

    // Loop through where we dropped a "bread crumb" starting from the top of
    // of the node and patch in the new node.
    for (int i = height - 1; i >= 0; i--)
    {
      newNode.setNext(i, tracker.get(i).next(i));
      tracker.get(i).setNext(i, newNode);
    }
  }

  // Method that deletes a single occurence of data from the list.
  public void delete(AnyType data)
  {
    Node<AnyType> iter = head();
    int skipListHeight = height() - 1;
    HashMap <Integer, Node<AnyType>> tracker = new HashMap<>();


    // Loop though the list as long as iter does not equal to null and as long
    // as we dont fall off of the skiplist.
    while (iter != null && skipListHeight >= 0)
    {
      int cmp = 0;

      // Set compare variable as long as the next node is not equal to null.
      if (iter.next(skipListHeight) != null)
        cmp = data.compareTo(iter.next(skipListHeight).value());

      // Case where next node is pointing to null or the data is bigger than
      // the compared data, if so, decrement the height.
      if (iter.next(skipListHeight) == null || cmp < 0)
          skipListHeight--;

      // Case where the data is smaller than the data passed. If so, skip forward
      // to the next node at that particular height of the list.
      else if (cmp > 0)
        iter = iter.next(skipListHeight);

      // Case where the data passed is equal to the data found, if so, drop a
      // "bread crumb" then decrement the height of the list.
      else
      {
        tracker.put(skipListHeight, iter);
        skipListHeight--;
      }
    }

      // We have fallen of the list so point iter to the next node so we can
      // delete it.
      iter = iter.next(skipListHeight);

      // Trim the skip list as long as the max height is greater than the origonal
      // height of the list.
      if (getMaxHeight(numElements) > this.height())
        trimSkipList();

      // Set the references of the dropped "bread crumbs" starting from the top of
      // the node to the next node then decrement the number of nodes in the list.
      if (iter != null)
      {
          for (int i = skipListHeight - 1; i >= 0; i--)
            tracker.get(i).setNext(i, iter.next(i));

          numElements--;
      }
  }

  // Method that returns true if the list contains data. Otherwise, return false.
  public boolean contains(AnyType data)
  {
    Node<AnyType> iter = head();
    int skipListHeight = height() - 1;

    // Loop though the list as long as iter does not equal to null and as long
    // as we dont fall off of the skiplist.
    while (iter != null && skipListHeight >= 0)
    {
      int cmp = 0;

      // Set compare variable as long as the next node is not equal to null.
      if (iter.next(skipListHeight) != null)
        cmp = data.compareTo(iter.next(skipListHeight).value());

      // Case where next node is pointing to null or the data is bigger than
      // the compared data, if so, decrement the height.
      if (iter.next(skipListHeight) == null || cmp < 0)
          skipListHeight--;

      // Case where the data is smaller than the data passed. If so, skip forward
      // to the next node at that particular height of the list.
      else if (cmp > 0)
        iter = iter.next(skipListHeight);

      // Found it!
      else
        return true;
    }

    // Node is not present in the list.
    return false;
  }

  // Method that returns a reference to a node if present in the list,
  // if node exists, the method returns null.
  public Node<AnyType> get(AnyType data)
  {
    Node<AnyType> iter = head();
    int skipListHeight = height() - 1;

    // Loop though the list as long as iter does not equal to null and as long
    // as we dont fall off of the skiplist.
    while (iter != null && skipListHeight >= 0)
    {
      int cmp = 0;

      // Set compare variable as long as the next node is not equal to null.
      if (iter.next(skipListHeight) != null)
        cmp = data.compareTo(iter.next(skipListHeight).value());

      // Case where next node is pointing to null or the data is bigger than
      // the compared data, if so, decrement the height.
      if (iter.next(skipListHeight) == null || cmp < 0)
          skipListHeight--;

      // Case where the data is smaller than the data passed. If so, skip forward
      // to the next node at that particular height of the list.
      else if (cmp > 0)
        iter = iter.next(skipListHeight);

      // Found it!
      else
        return iter.next(skipListHeight);
    }

    // Node is not present in the list.
    return null;
  }

  // Method that returns the max height of the list with n nodes. Which is
  // coverted via change of base formula of the ceiling log base 2 of n.
  private static int getMaxHeight(int n)
  {
    if (n < 2)
      return 1;

    return ((int) Math.ceil(Math.log(n) / Math.log(2)));
  }

  // Method that returns 1 with a 50% probability, 2 with 25% probability,
  // 3 with 12.5% probability and so on with out exceeding max height.
  private static int generateRandomHeight(int maxHeight)
  {
    int height = 1;

    while ((int)(Math.random() * 2) == 1 && height < maxHeight)
      height++;

    return height;
  }

  // Method to grow the skipList
  private void growSkipList()
  {
    Node<AnyType> iter = head();
    int maxHeight = getMaxHeight(numElements) - 1;
    HashMap <Integer, Node<AnyType>> tracker = new HashMap<>();

    iter.grow();

    // Loop through the list and find all the nodes that we may need to grow.
    for (int i = 0; i < maxHeight; i++)
    {
      while (iter.next(maxHeight) != null)
      {
        if (height() > maxHeight)
          if(iter.maybeGrow())
            tracker.put(i, iter);

        // Traverse to the next node.
        iter = iter.next(maxHeight);
      }
    }

    // Link up the remaining nodes only on the nodes that grew.
    for (int i = 0; i < tracker.size() - 1; i++)
      tracker.get(i).setNext(maxHeight, tracker.get(i + 1));
  }

  // Method to trim the skipList
  private void trimSkipList()
  {
    Node<AnyType> iter = head();
    int maxHeight = getMaxHeight(numElements) - 1;

    // Loop through find all the nodes that need to be trimmed.
    while (iter.next(maxHeight) != null)
      if (height() > maxHeight)
        iter.trim(maxHeight);

    iter.setNext(maxHeight, iter.next(maxHeight));
  }

  // Helper method to print the skiplist in the following format...
  // ==================
  // Node: 1
  // reference node: Node0x94827348
  // value: 10
  // height 1
  // arraylist(0): Node0x61899142
  // ==================
  // ==================
  // Node 2
  // reference Node0x61899142
  // value 20
  // height 1
  // arraylist(0): null
  // ==================
  public void print(int skipListHeight)
  {
      Node<AnyType> iter = head();
      Node<AnyType> nextNode = head().next(skipListHeight);

      int nodeCount = 1;

      while (iter != null)
      {
        System.out.println("===================");
        System.out.println("node: " + nodeCount);
        System.out.println("reference node: " + iter);
        System.out.println("value: " + iter.value());
        System.out.println("height: " + skipListHeight);
        System.out.println("arraylist(" + skipListHeight + ")" + nextNode);
        System.out.println("===================");
        nodeCount++;
        iter = iter.next(skipListHeight);
      }
  }

  public static double difficultyRating()
  {
    return 4.9;
  }

  public static double hoursSpent()
  {
    return 25.0;
  }
}
