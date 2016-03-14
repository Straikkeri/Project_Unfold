
package Unfold;

import java.util.ArrayList;

/**
 * Handles the creation of new creatures and storing them within an ArrayList.
 * 
 * @author Aleksi Tommila
 * @author Janne Möttölä
 */

public class Zoo{
    
    ArrayList<Creature> creatures;
    int initialCreatureCount;
    int rows;
    int columns;
    
    /**
     * Handles the initial creation of creatures at program start
     * 
     * @param squares two dimensional array of Square objects, aka simulation area
     * @param creaturesCount amount of creatures wanted within the simulation area
     * @param columns user defined amount of columns in the simulation area.
     * @param rows user defined amount of rows in the simulation area.
     * @return returns an ArrayList of Creature objects.
     */
    
    public ArrayList createCreatures(Square[][]squares, int creaturesCount, int columns, int rows){
        
        creatures = new ArrayList<Creature>();
        initialCreatureCount = creaturesCount;
        
        this.rows = rows;
        this.columns = columns;
        
        int x = 0;
        int y = 0;
        
        for(int i = 0; i < creaturesCount; i++){
            
            x = (int) (Math.random() * rows);
            y = (int) (Math.random() * columns);
            
            if (squares[x][y].getTerrain() == 3){
                
                while (squares[x][y].getTerrain() == 3){
                    x = (int) (Math.random() * rows);
                    y = (int) (Math.random() * columns);
                }
                creatures.add(new Creature(x,y, i, rows, columns));
            } else {
                
                creatures.add(new Creature(x,y,i, rows, columns));
            }
            
        } 
        return creatures;   
        
    }
    
    /**
     * Handles the creation of a new creature through mating within during simulation.
     * 
     * @param creatures ArrayList containing currently living creatures.
     * @param motherRacialFeatures featuers of the female creature involved in mating.
     * @param fatherRacialFeatures featuers of the male creature involved in mating.
     * @param creaturesCount amount of creatures within the simulation.
     * @param x x-coordinate of where the mating is happening.
     * @param y y-coordinate of where the mating is happening.
     * @return  returns an updated ArrayList of creatures, with a new creature added.
     */
    
    public ArrayList createCreatures(ArrayList<Creature>creatures, int[]motherRacialFeatures, int[]fatherRacialFeatures, int creaturesCount, int x, int y)
    {
        this.creatures = creatures;
        
        for(int i = initialCreatureCount;i<initialCreatureCount+creaturesCount;i++)
        {
            this.creatures.add(new Creature(x, y, i, rows, columns));
        }

        return this.creatures;
    }
    
    
}