
package Evolution;

import java.util.Random;


public class Zoo{
    
    Creature[] creatures;
    
    public Creature[] createCreatures(Square[][]squares, int creaturesCount, int columns, int rows){
        
        creatures = new Creature[creaturesCount];
        
        Random rand = new Random();
        int x = 0;
        int y = 0;
        
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
                creatures[i] = new Creature(x,y,i);
            }
            else
            {
                creatures[i] = new Creature(x,y,i);
            }
            
        } 
        //System.out.println(creatures[creaturesCount-1].toString());
        return creatures;   
        
    }
    
    
}