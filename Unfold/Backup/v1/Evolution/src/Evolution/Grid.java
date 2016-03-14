package Evolution;

import java.util.Random;

public class Grid{

public Square[][] createSquares(int columns, int rows){
        
        Random rand = new Random();
    
        Square[][] squares = new Square[rows][columns];

        for(int x = 0; x < rows; x++){
            //this.x = x;
            for(int y = 0; y < columns; y++){
                //this.y = y;
            //http://stackoverflow.com/questions/363681/generating-random-integers-in-a-range-with-java
            //minimum + rn.nextInt(maxValue - minvalue + 1)
            squares[x][y] = new Square(x, y,/*rand.nextInt(3)*/0, /*50 + rand.nextInt(100 - 50 + 1)*/Math.random()*50, Math.random() * 100, Math.random() * 25);
            //squares[i] = new Square(1,1,1,1);
        
            }
        }
        
        return squares;
        //System.out.println(squares[x][y].toString());

    }
}