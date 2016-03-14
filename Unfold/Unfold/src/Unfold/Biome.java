
package Unfold;

import Unfold.Square;

/**
 * Biome handles the deployment of terrain types across the squares table. 
 * 
 * @author Aleksi Tommila
 * @author Janne Möttölä
 */
public class Biome 
{    
 
    private int grid, mountains , forests;
    private int[][]terrainLoc;

    /**
     * Initializes variables, calls for private methods to generate forests, mountains, valleys, to swell them after intial seed and finally to place a river.
     * 
     * @param Squares Two dimensional object table of Square type.
     * @param rows  Amount of rows the simulation area has.
     * @param columns Amount of columns the simulation area has.
     */        
    public void Biome(Square[][]Squares, int rows, int columns)
    {
        
        grid = rows*columns;
        terrainLoc = new int[rows][columns];
        mountains =(grid/10000)+2;
        forests = (grid/1200)+2;
        
        createSeedForest(Squares, rows, columns, forests, terrainLoc);
        swellForests(Squares, rows, columns, terrainLoc);
        createSeedMountains(Squares, rows, columns, mountains, terrainLoc);
        swellMountains(Squares, rows, columns, terrainLoc);
        createValley(Squares, rows, columns, grid);
        createRiver(Squares, rows, columns, terrainLoc);
        
    } 
    
    // a method to create a valley
    private void createValley(Square[][]Squares, int rows, int columns, int grid)
    {
        // a center point for the valley is randomized in relation to the number of rows or columns, whichever is smaller,
        // so the whole valley will fit inside the grid

        int seedRowRand = (int)(Math.random() * rows);
        int seedColRand = (int)(Math.random() * columns);
        int swellRandMax = 0;
        int swellRand = 0;
        int swellRand2 = 0;
        
        // define max swelling for the valley
        if (columns > rows)
        {
            swellRandMax = rows/10+1;
        }
        else
        {
            swellRandMax = columns/10+1;
        }
        
        // if the first randomed vertical valley seedlocation is too close to the borders, it is randomed again
        if (seedRowRand <= swellRandMax || seedRowRand >= rows-swellRandMax)
        {
            for (;seedRowRand <= swellRandMax || seedRowRand >= rows-swellRandMax;)
            {
                seedRowRand = (int)(Math.random()*rows);
            }   
        }
        
        // if the first randomed horizontal valley seedlocation is too close to the borders, it is randomed again
        if (seedColRand <= swellRandMax || seedColRand >= columns-swellRandMax)
        {
            for(;seedColRand <= swellRandMax || seedColRand >= columns-swellRandMax;)
            {
                seedColRand = (int)(Math.random()*rows);
            }
        }

        // the seed is placed
        Squares[seedRowRand][seedColRand].setTerrain(0);

        int scRand;

        // make the valley around the seed

        // the first for-loop is for making three differently shaped rectangles 
        // on top of the seed out of plains tiles to create a valley
        for(int a = 0;a<3;a++)
        {
            // the second for-loop is to place 500 plains randomly inside each rectangle, creating a valley
            for(int b = 0;b<500;b++)
            {
                // create a vertical rectangle
                if(a==0)
                {
                    swellRand = (int) ((Math.random() * Math.sqrt(grid))/6)+1;
                    swellRand2 = (int) ((Math.random() * Math.sqrt(grid))/12)+1;
                }

                //create a vertical rectangle
                if (a==1)
                {
                    swellRand = (int) ((Math.random() * Math.sqrt(grid))/12)+1;
                    swellRand2 = (int) ((Math.random() * Math.sqrt(grid))/6)+1;
                }

                // create a square
                if (a==2)
                {
                    swellRand = (int) ((Math.random() * Math.sqrt(grid))/8)+1;
                    swellRand2 = (int) ((Math.random() * Math.sqrt(grid))/8)+1;
                }
                                
                // random the placement for each plains tile
                scRand = (int) (Math.random() * 8) + 1;

                switch (scRand)
                {
                    case 1:
                        if(seedRowRand+swellRand < rows && seedColRand+swellRand2 <columns)
                        Squares[seedRowRand+swellRand][seedColRand+swellRand2].setTerrain(0);

                    case 2:
                        if(seedRowRand-swellRand >=0 && seedColRand-swellRand2 >=0)
                        Squares[seedRowRand-swellRand][seedColRand-swellRand2].setTerrain(0);

                    case 3: 
                        if(seedRowRand+swellRand<rows && seedColRand-swellRand2 >=0)
                        Squares[seedRowRand+swellRand][seedColRand-swellRand2].setTerrain(0);

                    case 4:
                        if(seedRowRand-swellRand >= 0 && seedColRand+swellRand2 <columns)
                        Squares[seedRowRand-swellRand][seedColRand+swellRand2].setTerrain(0);

                    case 5:
                        if(seedRowRand < rows && seedRowRand >= 0 && seedColRand+swellRand<columns)
                        Squares[seedRowRand][seedColRand+swellRand].setTerrain(0);

                    case 6:
                        if(seedRowRand < rows && seedRowRand >= 0 && seedColRand-swellRand >= 0)
                        Squares[seedRowRand][seedColRand-swellRand].setTerrain(0);

                    case 7:
                        if(seedRowRand+swellRand < rows && seedColRand < columns && seedColRand >= 0)
                        Squares[seedRowRand+swellRand][seedColRand].setTerrain(0);

                    case 8:
                        if(seedRowRand-swellRand >= 0 && seedColRand < columns && seedColRand >= 0)
                        Squares[seedRowRand-swellRand][seedColRand].setTerrain(0);
                }
            }
        }      
    }
    
    // method creates a river from top to bottom
    private void createRiver(Square[][]Squares, int rows, int columns, int[][] terrainLoc)
    {
        // create a seed location for the river to start
        int riverRand = (int )(Math.random() * columns);
            
        // making sure the river will not start too close to the borders
        if (riverRand < columns/6 || riverRand > columns/1.2)
        {
            for(;riverRand > columns/6 && riverRand < columns/1.2 && riverRand < columns - 2;)
            {
                riverRand = (int )(Math.random() * columns + 1);
            }
        }
        // place the river seeds
        Squares[0][riverRand].setTerrain(3);
        Squares[1][riverRand].setTerrain(3);
        terrainLoc[0][riverRand] = 3;
        terrainLoc[1][riverRand] = 3;
                
        // start building the river downward     
        
        // the grid will be looped horizontally for every second row to find a river tile, from top to bottom,
        // and place an extension to it either to its left or to its right side, which is three blocks long
        for(int a = 1;a<rows;a=a+2)
        {
            for (int b = 0; b<columns-1 ; b++)
            {  
                if (terrainLoc[a][b] == 3)
                {       
                    int rand = (int)(Math.random() * 2 +1);
                    
                    switch(rand)
                    {
                        case 1: 
                        {   
                            if (b == columns -1)
                            {
                                Squares[a][b-1].setTerrain(3);
                                terrainLoc[a][b-1] = 3;
                                
                                if(a!=rows-1)
                                {    
                                    Squares[a+1][b-1].setTerrain(3);
                                    terrainLoc[a+1][b-1] = 3;
                                }
                                if (a == rows -2)
                                {
                                    terrainLoc[a+2][b-1] = 3;
                                    Squares[a+2][b-1].setTerrain(3);
                                }
                            }
                            else
                            {
                                Squares[a][b+1].setTerrain(3);
                                terrainLoc[a][b+1] = 3;
                                
                                if(a!=rows-1)
                                {    
                                    terrainLoc[a+1][b+1] = 3;
                                    Squares[a+1][b+1].setTerrain(3);
                                }
                                if(a<rows-2)
                                {
                                    terrainLoc[a+2][b+1] = 3;
                                    Squares[a+2][b+1].setTerrain(3);
                                }
                            }
                            
                            // ifs to hop the iterator over previously created rivers to avoid adding multiple
                            // river extensions on the same row 
                            if (b<=columns-3)
                            {
                                b=b+2;
                            }
                            if (b==columns-2)
                            {
                                b++;
                            }
                            break;
                        }
                            
                        case 2: 
                        {    
                            if (b == 0)
                            {
                                Squares[a][b+1].setTerrain(3);
                                terrainLoc[a][b+1] = 3;
                                
                                if(a!=rows-1)
                                {
                                    terrainLoc[a+1][b+1] = 3;
                                    Squares[a+1][b+1].setTerrain(3);
                                }
                                
                                if(a<rows-2)
                                {
                                    terrainLoc[a+2][b+1] = 3;
                                    Squares[a+2][b+1].setTerrain(3);
                                }
                            }
                            
                            else
                            {
                                Squares[a][b-1].setTerrain(3);
                                terrainLoc[a][b-1] = 3;
                                
                                if(a!=rows-1)
                                {
                                    terrainLoc[a+1][b-1] = 3;
                                    Squares[a+1][b-1].setTerrain(3);
                                }
                                if(a<rows-2)
                                {
                                    terrainLoc[a+2][b-1] = 3;
                                    Squares[a+2][b-1].setTerrain(3);
                                }
                            }
                            
                            // ifs to hop the iterator over previously created rivers to avoid adding multiple
                            // river extensions on the same row 
                            if (b<=columns-3)
                            {
                                b=b+2;
                            }
                            if (b==columns-2)
                            {
                                b++;
                            }
                            break;
                        }    
                    }
                }
            } 
        } 
    }
    
    // method to create seeds for forests
    private void createSeedForest(Square[][]Squares, int rows, int columns, int forests, int[][] terrainLoc)
    {
        // for loop to go through every column
        for (int i = 0;i < rows;i++)
        {
            // for loop to create a number of forest seeds specified (can be done with setForests() )
            for(int a = 0;a < forests;a++)
            {
                int rand = (int )(Math.random() * columns +1);
             
                // if the seed would be placed to an improper place, the placement is randomed again
                if (rand < 0 || rand >= columns)
                {
                    for (;rand < 0 || rand >= columns;)
                    {
                        rand = (int )(Math.random() * columns +1);
                    }                    
                }
                
                // forest seed placement for each column
                Squares[i][rand].setTerrain(2);
                terrainLoc[i][rand] = 2;
            }
        }
    }
    
    // method to enlarge seed forests
    private void swellForests(Square[][]Squares, int rows, int columns, int[][] terrainLoc)
    {
        // for loop to go through every second column
        for (int a = 1; a < rows-1; a=a+2)
        {
            // for loop to go through every row
            for (int b = 1 ; b < columns-1 ; b++)
            {
                // checking for seeds
                if (terrainLoc[a][b] == 2)
                {
                    // randoming the amount of forests to add around the found seed
                    int rand = (int ) (Math.random() * 8 +1);
                   
                    // for loop to random the locations and place the new forest tiles
                    for (int c = 0 ; c<=rand;c++)
                    {                     
                        int randvalue = (int ) (Math.random() * 8 +1);
                    
                        switch (randvalue)
                        {
                            case 1: Squares[a-1][b-1].setTerrain(2); // vasen ylänurkka
                            case 2: Squares[a][b-1].setTerrain(2); // yläpuoli
                            case 3: Squares[a+1][b-1].setTerrain(2); // oikea ylänurkka
                            case 4: Squares[a-1][b].setTerrain(2); // vasen
                            case 5: Squares[a+1][b].setTerrain(2); // oikea
                            case 6: Squares[a-1][b+1].setTerrain(2); // vasen alanurkka
                            case 7: Squares[a][b+1].setTerrain(2); // alapuoli
                            case 8: Squares[a+1][b+1].setTerrain(2); // oikea alanurkka
                        }
                    }
                }
            }
        }
    }
    
    private void createSeedMountains(Square[][]Squares, int rows, int columns, int mountains, int[][] terrainLoc)
    {
        // make sure the seeds are not too close to the borders for swelling
        int swellTolerance;
        
        if(columns>rows)
        {
            swellTolerance = rows/20+2;
        }
        else
        {
            swellTolerance = columns/20+2;
        }

        // go through every second column tops. iterator builds up in relation to horizontal size of the grid
        for (int i = 1;i < rows;i=i+rows/10+2)
        {
            // go through the row a number of times specified (can be done by setMountains() )
            for(int a = 0;a < mountains;a++)
            {
                // random a placement for the mountain seed
                int rand = (int )(Math.random() * columns + 1);
                
                // making sure the seed is far enough from the borders horizontally
                if (rand < 0 || rand >= columns)
                {
                    for (;rand < 0 || rand >= columns;)
                    {
                        rand = (int )(Math.random() * columns +1);
                    }
                }
                // making sure the seed is far enough from the borders vertically
                if (i<= columns-swellTolerance && i>=swellTolerance)
                {
                    if(rand <=rows-swellTolerance && rand>=swellTolerance)
                    {
                        // placing seeds
                        Squares[i][rand].setTerrain(1);
                        terrainLoc[i][rand] = 1;
                    }   
                    
                }
            }
        }
    }
    
    // method to create bigger mountains on the seed locations
    private void swellMountains(Square[][]Squares, int rows, int columns, int[][] terrainLoc)
    {
        int swellRand = (int) Math.sqrt(grid);
        int swellRand2 = (int) Math.sqrt(grid);
        int swellTolerance;
        
        // make sure that the mountains are swelled in proportion to the grid shape
        if(columns>rows)
        {
            swellTolerance = rows/25+1;
        }
        else
        {
            swellTolerance = columns/25+1;
        }
        
        // creating new mountains around the seeds
        
        // going through every second row 
        for (int a = 1; a < rows-2 ; a=a+2)
        {
            // going through every column
            for (int b = 1 ; b < columns-2 ; b++)
            { 
                if (terrainLoc[a][b] == 1)
                {
                    // if a mountain seed is found, start making a bigger mountain on it by creating
                    // three differently shaped rectangles out of mountain tiles
                    int scRand;
                    
                    // loop for going through each rectangle
                    for(int c=0;c<3;c++)
                    {
                        // loop for randoming mountain tiles 20 times around the seed
                        for(int d = 0;d<20;d++)
                        {
                            // create a vertical rectangle
                            if(c==0)
                            {
                                swellRand = (int) ((Math.random() * Math.sqrt(grid))/20)+1;
                                swellRand2 = (int) ((Math.random() * Math.sqrt(grid))/40)+1;
                            }
                            // create a horizontal rectangle
                            if (c == 1)
                            {
                                swellRand = (int) ((Math.random() * Math.sqrt(grid))/40)+1;
                                swellRand2 = (int) ((Math.random() * Math.sqrt(grid))/20)+1;
                            }
                            // create a square
                            if (c==2)
                            {
                                swellRand = (int) ((Math.random() * Math.sqrt(grid))/30)+1;
                                swellRand2 = (int) ((Math.random() * Math.sqrt(grid))/30)+1;
                            }
                            
                            // randoming the actual place for a tile and making sure it does not go over the array
                            scRand = (int) (Math.random() * 8) + 1;
                            
                            switch (scRand)
                            {
                                case 1:
                                    if(a+swellRand < rows && b+swellRand2 < columns)
                                        Squares[a+swellRand][b+swellRand2].setTerrain(1);
                                
                                case 2:
                                    if(a-swellRand >= 0 && b-swellRand2 >=0)
                                    Squares[a-swellRand][b-swellRand2].setTerrain(1);
                                
                                case 3: 
                                    if(a+swellRand < rows && b-swellRand2 >=0)
                                        Squares[a+swellRand][b-swellRand2].setTerrain(1);
                                
                                case 4:
                                    if(a-swellRand > 0 && b+swellRand2 < columns)
                                        Squares[a-swellRand][b+swellRand2].setTerrain(1);
                                
                                case 5:
                                    if(a<rows && a>=0 && b+swellRand < columns)
                                    Squares[a][b+swellRand].setTerrain(1);
                                
                                case 6:
                                    if(a<rows && a>=0 && b-swellRand >= 0)
                                    Squares[a][b-swellRand].setTerrain(1);

                                case 7:
                                    if(a+swellRand < rows && b>=0 && b<columns)
                                    Squares[a+swellRand][b].setTerrain(1);

                                case 8:
                                    if(a-swellRand >= 0 && b>=0 && b<columns)
                                    Squares[a-swellRand][b].setTerrain(1);
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
     * 
     * Decides the relative amount of forests generated in the map.
     * @param value int between 1 to 3. Three being the most forests.
     */
    public void setForest(int value)
    {
        if (value == 1)
        {
            
            this.forests = (grid/3000)+1;
        }
        
        if (value == 2)
        {
            this.forests = (grid/1200)+2;
        }
        
        if (value == 3)
        {
            this.forests = (grid/800)+2;
        }
    }
    
    /**
     * Decides the relative amount of mountains generated in the map.
     * @param value int between 1 to 3. Three being the most mountains. 
     */
    public void setMountains (int value)
    {
        if (value == 1)
        {
            this.mountains = (grid/12000)+1;
        }
        
        if (value == 2)
        {
            this.mountains = (grid/10000)+2;
        }
        
        if (value == 3)
        {
            this.mountains = (grid/8000)+3;
        }
    }
}