/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Evolution;

import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author H8951
 */
public class Initialization {
 
    private int x;
    private int y;
    private int creaturesCount = 60;
    private int columns = 5;
    private int rows = 5;
    private int speciesCount = 1;
    
    Square[][]squares;
    
    ArrayList<Creature> creatures = new ArrayList<Creature>();
    
    Random rand = new Random();
    
    public void Initialization(){
        
        DrawGraphics drawG = new DrawGraphics();
        Zoo zoo = new Zoo();
        Grid grid = new Grid();
        Scanner scan = new Scanner(System.in);
        
        System.out.println("How many columns?");
        columns = scan.nextInt();
        System.out.println("How many rows?");
        rows = scan.nextInt();
        squares = new Square[rows][columns];
        squares = grid.createSquares(columns, rows);
        Biome landScape = new Biome();
        landScape.Biome(squares, rows, columns);
        mainProgramLoop(zoo.createCreatures(squares, creaturesCount, columns, rows), squares);
    }
    
    private void mainProgramLoop(ArrayList<Creature> creatures, Square[][] squares){
        
        DrawGraphics drawgrap = new DrawGraphics();
        this.creatures = creatures;
        this.squares = squares;
        Creature returnCreature;
        
        while(true)
        {
            for(int a=0;a<rows;a++)
            {
                for(int b = 0;b<columns;b++)
                {
                    // a,b ja x,y käyttäminen ehkä tarpeetonta. Sillä tosin pysyy selkeys mukana jos ei avaruusajattelu seuraa
                    this.squares = this.squares[a][b].updateSquare(this.creatures,this.squares, /*this.squares[a][b].getX(), this.squares[a][b].getY(),*/ rows, columns, a, b);
                }
            }
            
            
            
            for (int i = 0; i < creatures.size(); i++) {
                
                returnCreature = this.creatures.get(i).runCreature(this.squares,this.creatures, columns, rows, i);
                this.creatures = returnCreature.getCreaturesArrayList();
                this.squares = returnCreature.getSquaresArray();
            }
            
        drawgrap.drawGrid(this.squares, this.creatures, columns, rows);
        drawgrap.setVisible(true);

          //  System.out.println("creaturesin koko initializationissa: "+this.creatures.size());
       /* for(int i = 0;i<this.creatures.size();i++)
        {
            System.out.println(this.creatures.get(i).getDiet()+ " on diet initializationin lopussa ennen sleeppiä");
        }*/
        
        try {sleep(300);} catch (InterruptedException e){}
        }
    }
    

}
