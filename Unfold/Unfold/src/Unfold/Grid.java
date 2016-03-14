package Unfold;

/**
 * Handles the creation and filling of a two dimensional array with Square objects.
 * 
 * @author Aleksi Tommila
 * @author Janne Möttölä 
 * @version 1.0 Build 1 april, 20.
 */

public class Grid{
/**
 * 
 * @param columns user defined amount of columns in the simulation area.
 * @param rows user defined amount of rows in the simulation area.
 * @return returns a two dimensional array of Square objects.
 */
public Square[][] createSquares(int columns, int rows){
    
        Square[][] squares = new Square[rows][columns];

        for(int x = 0; x < rows; x++){
            for(int y = 0; y < columns; y++){
            //http://stackoverflow.com/questions/363681/generating-random-integers-in-a-range-with-java
            squares[x][y] = new Square(x, y,/*Terrain*/0, /*Food*/40,/*Shelter*/ Math.random() * 100,/*Temperature*/ Math.random() * 25); 
            }
        }
        
        return squares;
    }
}