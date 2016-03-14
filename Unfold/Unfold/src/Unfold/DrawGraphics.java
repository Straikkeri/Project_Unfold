package Unfold;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Handles rendering for the program
 * 
 * @author Aleksi Tommila
 * @author Janne Möttölä
 */

public class DrawGraphics extends JFrame{

    private Image forest, meadow, mountain, carni, herbi, omni, water, red, orange, yellow, lightGreen, green;
    private MediaTracker mt;
    Square[][] squares;
    ArrayList<Creature> creatures = new ArrayList<Creature>();
    private boolean foodOverlay = false;
    private boolean stop = false;
    private double foodLevel = 0;
    
    public DrawGraphics(){}
    
    /**
     * Handles the creation of the panel the simulation is drawn on. Overrides the paintComponent.
     */
    
    class DrawPanel extends JPanel{
        
        @Override
        public void paintComponent(Graphics g){
            
            if(!stop){
                for(int x=0; x < squares.length; x++){
                    for(int y=0; y < squares[x].length; y++){

                        foodLevel = squares[x][y].getFood();


                        if(squares[x][y].getTerrain() == 0){
                            g.drawImage(meadow, y*16, x*16, this);
                        }
                        else if(squares[x][y].getTerrain() == 1){
                            g.drawImage(mountain, y*16, x*16, this);
                        }
                        else if(squares[x][y].getTerrain() == 2){
                            g.drawImage(forest, y*16, x*16, this);
                        }
                        else if (squares[x][y].getTerrain() == 3){
                            g.drawImage(water, y*16, x*16, this);
                        }

                        if(foodOverlay == true){

                                if(foodLevel == 0){

                                    g.drawImage(red, y*16, x*16, this);

                                } else if(foodLevel > 0 && foodLevel < 10){

                                    g.drawImage(orange, y*16, x*16, this);

                                } else if(foodLevel > 10 && foodLevel < 20){
                                    g.drawImage(yellow, y*16, x*16, this);

                                } else if(foodLevel > 25 && foodLevel < 35){
                                    g.drawImage(lightGreen, y*16, x*16, this);

                                } else if(foodLevel > 35 && foodLevel < 50){
                                    g.drawImage(green, y*16, x*16, this);

                                }
                        }
                    }
                }

                for(int i = 0; i < creatures.size(); i++){

                    if(creatures.get(i).getDiet()== 1){
                        g.drawImage(carni, creatures.get(i).getY()*16, creatures.get(i).getX()*16, this);
                    }
                    else if(creatures.get(i).getDiet()== 2){
                        g.drawImage(omni, creatures.get(i).getY()*16, creatures.get(i).getX()*16, this);
                    }
                    else {
                        g.drawImage(herbi, creatures.get(i).getY()*16, creatures.get(i).getX()*16, this);
                    }
                }
            }
        }
    }
    
    /**
     * Initializes images, creatures and squares for rendering.
     * 
     * @param squares squares to be rendered.
     * @param creatures creatures to be rendered.
     * @param columns columns in the simulation.
     * @param rows rows in the simulation.
     * @return returns itself.
     */
    
    public DrawGraphics drawGrid(Square[][] squares, ArrayList<Creature> creatures, int columns, int rows){
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(columns*16+16,rows*16+38); 
        
        this.squares = squares;
        this.creatures = creatures;

        this.forest = getToolkit().createImage("forest.png");
        this.meadow = getToolkit().createImage("meadow.png");
        this.mountain = getToolkit().createImage("mountain.png");
        this.water = getToolkit().createImage("wuter.png");
        
        this.red = getToolkit().createImage("red.png");
        this.orange = getToolkit().createImage("orange.png");
        this.yellow = getToolkit().createImage("yellow.png");
        this.lightGreen = getToolkit().createImage("lightGreen.png");
        this.green = getToolkit().createImage("green.png");
        
        this.herbi = getToolkit().createImage("monster_herbi_trans.png");
        this.carni = getToolkit().createImage("monster_carni_trans.png");

        this.mt = new MediaTracker(this);

        this.mt.addImage(forest, 0);
        this.mt.addImage(meadow, 0);
        this.mt.addImage(mountain, 0);
        this.mt.addImage(water,0);
        
        this.mt.addImage(herbi, 0);
        this.mt.addImage(carni, 0);

        try{this.mt.waitForAll();}catch(Exception e){}
        
        JPanel basePanel = new DrawPanel();
        
        basePanel.setLayout(null);
        
        getContentPane().add(basePanel);
        
        return this;
        
    }
        
}

 