
package Evolution;

import java.util.ArrayList;
import java.util.Random;


public class Zoo{
    
    ArrayList<Creature> creatures;
    
    public ArrayList createCreatures(Square[][]squares, int creaturesCount, int columns, int rows){
        
        creatures = new ArrayList<Creature>();
        
        Random rand = new Random();
        int x = 0;
        int y = 0;
        
        System.out.println("Number of created creatures in Zoo: "+creaturesCount);
        
        for(int i = 0; i < creaturesCount; i++){
            
            x = (int) (Math.random() * rows);
            y = (int) (Math.random() * columns);
            
            if (squares[x][y].getTerrain() == 3)
            {
                while (squares[x][y].getTerrain() == 3)
                {
                    x = (int) (Math.random() * rows);
                    y = (int) (Math.random() * columns);
                }
                creatures.add(new Creature(x,y,i));
            }
            else
            {
                creatures.add(new Creature(x,y,i));
            }
            
        } 
        //System.out.println(creatures[creaturesCount-1].toString());
        return creatures;   
        
    }
    
    
}