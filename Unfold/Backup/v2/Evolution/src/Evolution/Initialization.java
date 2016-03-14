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
        
        while(true){
            System.out.println(creatures.size()+" = creatures.size()");
            
            for (int i = 0; i < creatures.size(); i++) {
                
                
                returnCreature = this.creatures.get(i).runCreature(this.squares,this.creatures, columns, rows);
                this.creatures = returnCreature.getCreaturesArrayList();
                this.squares = returnCreature.getSquaresArray();
                
            }
            
        drawgrap.drawGrid(this.squares, this.creatures, columns, rows);
        drawgrap.setVisible(true);
        
        for(int a=0;a<rows;a++)
        {
            for(int b = 0;b<columns;b++)
            {
                this.squares[a][b].updateSquare(this.creatures,this.squares, x, y,a,b);
            }
        }

        try {sleep(300);} catch (InterruptedException e){}
        }
    }
    

}