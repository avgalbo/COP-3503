// Anthony Galbo
// COP 3503, Spring 2021
// an577845

public class RunLikeHell
{
  // Wrapper method that returns the overloaded maxGainRecursive method
  public static int maxGainRecursive(int [] blocks)
  {
    return maxGainRecursive(blocks, 0, blocks.length);
  }

  // Recursive method that returns the max number of gain in a given array.
  // Note: solving the recursive method assisted me with my memoization version.
  private static int maxGainRecursive(int [] blocks, int start, int end)
  {
    // Base cases...
    // Case where end is equal to the start, just return 0.
    if (end - start == 0)
      return 0;

    // Case where start is 1 less than the end, return the start of the
    // blocks index.
    if (end - start == 1)
      return blocks[start];

    // Case where start is 2 less than the end, return max of the start or
    // the start of the next block.
    if (end - start == 2)
      return Math.max(blocks[start], blocks[start + 1]);

    // Return the max of number of blocks either the one where we skip 1 block
    // or the one where we skip 2 blocks.
    return Math.max(
        maxGainRecursive(blocks, start + 2, end) + blocks[start],
        maxGainRecursive(blocks, start + 3, end) + blocks[start + 1]
    );

  }

  // Wrapper method that returns the overloaded maxGainMemo() method.
  public static int maxGainMemo(int [] blocks)
  {
    int [] memo = new int[blocks.length + 1];
    return maxGainMemo(blocks, 0, blocks.length, memo);
  }

  // Memoization method that returns the max number of gain in a given array.
  // Note: Then solving the memoization version assisted me with my DP version.
  private static int maxGainMemo(int [] blocks, int start, int end, int [] memo)
  {
    // Base cases...
    // Case where end is equal to the start, just return 0.
    if (end - start == 0)
      return 0;

    // Case where start is 1 less than the end, return the start of the
    // blocks index.
    if (end - start == 1)
      return blocks[start];

    // Case where start is 2 less than the end, return max of the start or
    // the start of the next block.
    if (end - start == 2)
      return Math.max(blocks[start], blocks[start + 1]);

    /// Return the max of number of blocks either the one where we skip 1 block
    // or the one where we skip 2 blocks. Store the result in the memo array.
    memo[blocks.length] = Math.max(
        maxGainMemo(blocks, start + 2, end, memo) + blocks[start],
        maxGainMemo(blocks, start + 3, end, memo) + blocks[start + 1]

    );

    // Return our result at the end of the array.
    return memo[blocks.length];
  }

  // Dynamic Programming version that returns the max number of gain in a
  // given array.
  public static int maxGain(int [] blocks)
  {
    int [] array = new int[blocks.length + 1];
    int max = 0;

    // Initalize base cases...
    // The 1st element of the array stores the 1st block while the 2nd element
    // holds the 2nd block and the 3rd element holds the sum of the
    // 1st and 3rd block.
    array[0] = blocks[0];
    array[1] = blocks[1];
    array[2] = blocks[0] + blocks[2];

    // Use a for-loop to approach problem using a "bottom-up" style to avoid
    // repeated computation. Start at i=3 since we already dealt with index 0-2.
    for (int i = 3; i < blocks.length; i++)
    {
      // Take the max between skiping 1 block or skipping 2 blocks.
      array[i] = Math.max(array[i-2], array[i-3]);

      // Whichever block we take, lets add that to our array.
      array[i] += blocks[i];

      // Ask is the max less than our current index? if so, set max to our
      // current index.
      if (max < array[i])
        max = array[i];
    }

    // Return our max.
    return max;
  }

  // Dynamic Programming version that returns the max number of gain in a
  // given array. With O(1) optimized space complexity.
  public static int maxGainFancy(int [] blocks)
  {
    // We will only need to make our array of length 3 in this case.
    int [] array = new int[3];
    int max = 0;

    // Initalize base cases...
    // The 1st element of the array stores the 1st block while the 2nd element
    // holds the 2nd block and the 3rd element holds the sum of the
    // 1st and 3rd block.
    array[0] = blocks[0];
    array[1] = blocks[1];
    array[2] = blocks[0] + blocks[2];

    // Use a for-loop to approach problem using a "bottom-up" style to avoid
    // repeated computation. Start at i=3 since we already dealt with index 0-2.
    // The for loop will use a toggle to alternate between overwriting index 0,
    // index 1 and index 2 in the array.
    for (int i = 3; i < blocks.length; i++)
    {
      // Take the max between skiping 1 block or skipping 2 blocks.
      array[i%3] = Math.max(array[(i-2)%3], array[(i-3)%3]);

      // Whichever block we take, lets add that to our array.
      array[i%3] += blocks[i];

      // Ask is the max less than our current index? if so, set max to our
      // current index.
      if (max < array[i%3])
        max = array[i%3];
    }

    // Return our max.
    return max;
  }

  public static double difficultyRating()
  {
    return 3.5;
  }

  public static double hoursSpent()
  {
    return 7.0;
  }

  // private static void failwhale()
  // {
  //   System.out.println("fail whale :(");
  //   System.exit(0);
  // }
  //
  // public static void main(String[] args)
  // {
  //   int [] blocks = new int[] {15, 3, 6, 17, 2, 1, 20};
  //   int ans = 52;
  //
  //   if (RunLikeHell.maxGainRecursive(blocks) != ans)
  //     failwhale();
  //   else
  //     System.out.println("Hooray!");
  //
  //   if (RunLikeHell.maxGainMemo(blocks) != ans)
  //     failwhale();
  //   else
  //     System.out.println("Hooray!");
  //
  //   if (RunLikeHell.maxGain(blocks) != ans)
  //     failwhale();
  //   else
  //     System.out.println("Hooray!");
  //
  //   if (RunLikeHell.maxGainFancy(blocks) != ans)
  //     failwhale();
  //   else
  //     System.out.println("Hooray!");
  // }

}
