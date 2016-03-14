/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Unfold;

import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Handles the first time initialization and running of the main program loop. Initializes the startup variables for the program and runs the main program loop.
 * @author Aleksi Tommila
 * @author Janne Möttölä 
 * @version 1.0 Build 1 april, 20.
 */
public class Initialization{
 
    private int x;
    private int y;
    private int creaturesCount = 200;
    private int columns = 5;
    private int rows = 5;
    private int speciesCount = 1;
    
    Square[][]squares;
    
    ArrayList<Creature> creatures = new ArrayList<Creature>();
    
    private Random rand = new Random();
    
    /**
     * 
     * Constructor for the class. Initializes the startup variables, asks for user input regarding the simulation size and inhabitants counts.
     * Creates objects of Zoo, Grid and Biome classes. 
     * Calls grid, zoo, biome and mainProgramLoop in order.
     */
    
    public void Initialization(){
        
        Zoo zoo = new Zoo();
        Grid grid = new Grid();
        Scanner scan = new Scanner(System.in);
        
        System.out.println("How many columns?");
        columns = scan.nextInt();
        System.out.println("How many rows?");
        rows = scan.nextInt();
        System.out.println("How many creatures?");
        creaturesCount = scan.nextInt();
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
            
            for(int a=0;a<rows;a++){
                for(int b = 0;b<columns;b++){
                    
                    this.squares = this.squares[a][b].updateSquare(this.creatures,this.squares, rows, columns, a, b);
                }
            }        
            
            for (int i = 0; i < creatures.size(); i++) {
                
                returnCreature = this.creatures.get(i).runCreature(this.squares,this.creatures, columns, rows, this.creatures.get(i).getX(), this.creatures.get(i).getY());
                this.creatures = returnCreature.getCopiedCreatures();
                this.squares = returnCreature.getCopiedSquares();
            }
            
        drawgrap = drawgrap.drawGrid(this.squares, this.creatures, columns, rows);
        drawgrap.setVisible(true);   
        
        
            try {
                sleep(1000);
             } catch (InterruptedException ex) {
                Logger.getLogger(Initialization.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        
        }
    }
}
