// Anthony Galbo
// COP 3503, Spring 2021
// an577845

import java.util.*;

public class SneakyQueens
{

  public static boolean allTheQueensAreSafe(ArrayList<String> coordinateStrings,
  int boardSize)
  {

    boolean [] rows = new boolean[boardSize + 1];
    boolean [] cols = new boolean[boardSize + 1];
    boolean [] upDiagonal = new boolean[(boardSize * 2) + 1];
    boolean [] downDiagonal = new boolean[(boardSize * 2) + 1];
    int x, y, upDiagonalIndex, downDiagonalIndex;

    // Iterate through the ArrayList.
    for (String str : coordinateStrings)
    {

      // Convert strings to 2 ints via helper methods.
      x = convertx(str);
      y = converty(str);

      // 1st check columns...
      // if there is no queen on the column, mark it to true
      // if there is a queen on the column already, there is a collision,
      // so return false
      if (cols[x] == false)
        cols[x] = true;
      else if (cols[x] == true)
        return false;

      // 2nd check rows...
      // if there is no queen on the row, mark it to true
      // if there is a queen on the row already, there is a collision,
      // so return false
      if (rows[y] == false)
        rows[y] = true;
      else if (rows[y] == true)
        return false;

      // Find upDiagonalIndex by subtracting the colums and the rows.
      // Add the boardSize to avoid a negative index.
      upDiagonalIndex =  y - x + boardSize;

      // Find downDiagonalIndex by adding the colums and the rows.
      downDiagonalIndex = y + x;

      // 3rd check upDiagonals...
      // if there is no queen on the upDiagonal, mark it by setting it to true
      // if there is a queen on the upDiagonal already, there is a collision
      // so return false
      if (upDiagonal[upDiagonalIndex] == false)
        upDiagonal[upDiagonalIndex] = true;
      else if (upDiagonal[upDiagonalIndex] == true)
        return false;

      // 4th check downDiagonal...
      // if there is no queen on the downDiagonal, mark it by setting it to true
      // if there is a queen on the downDiagonal already, there is a collision
      // so return false
      if (downDiagonal[downDiagonalIndex] == false)
        downDiagonal[downDiagonalIndex] = true;
      else if (downDiagonal[downDiagonalIndex] == true)
        return false;

    }// End of for each loop

    // No collisions so return true
    return true;

  }// End of allTheQueensAreSafe() method

  // Method to convert the x coordinate (column coordinate) of the string.
  public static int convertx(String str)
  {
    int res = 0;

    // Loop through the string and break as soon as you see a digit.
    for (int i = 0; i < str.length(); i++)
    {
      if (Character.isDigit(str.charAt(i)))
        break;

      // Apply horners rule...
      res = (res * 26) + (str.charAt(i) - ('a' - 1));
    }

    return res;
  }

  // Method to convert the y coordinate (row coordinate) of the string.
  public static int converty(String str)
  {
    int res = 0;

    // Loop through the string and continue the loop if you see a letter
    for (int i = 0; i < str.length(); i++)
    {
      if (Character.isLetter(str.charAt(i)))
        continue;

      // Convert only at the specified index (or when string turns into a number)
      res = Integer.parseInt(str.substring(i));
      break;
    }

    return res;
  }

  public static double difficultyRating()
  {
    return 4.0;
  }

  public static double hoursSpent()
  {
    return 13.0;
  }

}// End of SneakyQueens class
