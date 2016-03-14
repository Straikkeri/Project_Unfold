package Evolution;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;




public class DrawGraphics extends JFrame implements ActionListener{

    private Image forest, meadow, mountain, carni, herbi, omni, water, red, orange, yellow, lightGreen, green;
    private MediaTracker mt;
    Square[][] squares;
    ArrayList<Creature> creatures = new ArrayList<Creature>();
    boolean foodOverlay = true;
    double foodLevel = 0;
    
    public DrawGraphics(){
        
        //System.out.println("Pandoran boxissa, eli drawGraphicsissa!");
 
        
        }
    
    class DrawPanel extends JPanel{
        
        public void paintComponent(Graphics g){
         
            
            for(int x=0; x < squares.length; x++){
                for(int y=0; y < squares[x].length; y++){
                    //System.out.println("Terrain is: " + squares[x][y].getTerrain());
                    //System.out.println("Info is: " + squares[x][y].getInfo());
                    
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
                        //System.out.println("Sisällä perseessä. ");
                        if(foodLevel == 0){
                          //  System.out.println("Sisemmällä perseessä.");
                            g.drawImage(red, y*16, x*16, this);
                            //System.out.println("Drew red");
                        } else if(foodLevel > 0 && foodLevel < 10){
                         //   System.out.println("Oh no there's trains");
                            g.drawImage(orange, y*16, x*16, this);
                            //System.out.println("Drew orange");
                        } else if(foodLevel > 10 && foodLevel < 20){
                            g.drawImage(yellow, y*16, x*16, this);
                            //System.out.println("Drew yellow");
                        } else if(foodLevel > 25 && foodLevel < 35){
                            g.drawImage(lightGreen, y*16, x*16, this);
                            //System.out.println("Drew light green");
                        } else if(foodLevel > 35 && foodLevel < 50){
                            g.drawImage(green, y*16, x*16, this);
                          //  System.out.println("Drew green");
                        }
                    }
                }
            }
            //System.out.println("creatures.size() in DrawGraphics = "+creatures.size());
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
    
    public void drawGrid(Square[][] squares, ArrayList<Creature> creatures, int columns, int rows){
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(columns*16+16+150,rows*16+38); 
        
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

        getContentPane().add(new DrawPanel());
        
    }
    
    
    /*public void drawMenu(){
    
        JFrame menuFrame = new JFrame();
        JPanel mainPanel = new JPanel();
        
        menuFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        menuFrame.setSize(800, 600);
        menuFrame.setResizable(true);
        
        mainPanel.setBounds(1, 1, 798, 598);
                
        JButton startBTN = new JButton("Go!"); 
        JTextField rows = new JTextField();
        JTextField columns = new JTextField();
        JTextField mountainPercent = new JTextField("testi");
        JTextField meadowPercent = new JTextField();
        JTextField forestPercent = new JTextField();
        JTextField foodFactor = new JTextField();
        
        rows.setBounds(50, 50, 100, 150);
        rows.setText("Jo nyt on perkele");
        
        Component[] components = {rows, columns, mountainPercent, meadowPercent, forestPercent, foodFactor, startBTN};
                
        int height = 50;
        int width = 100;
        
        int x = 0;
        int y = 0;
        
        //for(int i = 0; i < 7; i++){
        //    components[i].setBounds(x, y + 55, width, height);
        //}
       
        //components[0].get
        
        startBTN.addActionListener(this);
        
        
        mainPanel.add(components[0]);
        mainPanel.add(components[1]);
        mainPanel.add(components[2]);
        mainPanel.add(components[3]);
        mainPanel.add(components[4]);
        mainPanel.add(components[5]);
        mainPanel.add(components[6]);
        
        menuFrame.getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);
        menuFrame.setVisible(true);
    }*/
    
    @Override
    public void actionPerformed(ActionEvent e){
        JButton buttonPressed = (JButton) e.getSource();
        buttonAction(buttonPressed);
    }
    
     public void buttonAction(JButton buttonPressed){

            System.out.println("Go! Pressed.");
    }
}

 