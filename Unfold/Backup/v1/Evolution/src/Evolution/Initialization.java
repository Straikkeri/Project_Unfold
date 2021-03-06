/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Evolution;

import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author H8951
 */
public class Initialization {
 
    private int x;
    private int y;
    private int creaturesCount = 100;
    private int columns = 5;
    private int rows = 5;
    private int speciesCount = 1;
    
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
        
        Square[][] squares = grid.createSquares(columns, rows);
        Biome landScape = new Biome();
        landScape.Biome(squares, rows, columns);
        mainProgramLoop(zoo.createCreatures(squares, creaturesCount, columns, rows), squares);
    }
    
    private void mainProgramLoop(Creature[] creatures, Square[][] squares){
        
        DrawGraphics drawgrap = new DrawGraphics();
        
        while(true){
            
            for (int i = 0; i < creatures.length; i++) {
                creatures[i].runCreature(squares, columns, rows);
            }
            
        drawgrap.drawGrid(squares, creatures, columns, rows);
        drawgrap.setVisible(true);
        
        for(int a=0;a<rows;a++)
        {
            for(int b = 0;b<columns;b++)
            {
                squares[a][b].updateSquare(creatures,squares, x, y,a,b);
            }
            
        }

        try {sleep(300);} catch (InterruptedException e){}
        }
    }
    

}