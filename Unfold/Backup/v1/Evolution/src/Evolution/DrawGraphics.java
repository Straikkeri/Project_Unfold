package Evolution;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;




public class DrawGraphics extends JFrame implements ActionListener{

    private Image forest, meadow, mountain, carni, herbi, omni, water;
    private MediaTracker mt;
    Square[][] squares;
    Creature[] creatures;
    
    public DrawGraphics(){
        
        System.out.println("Graphicsin konstruktorissa!");
 
        
        }
    
    class DrawPanel extends JPanel{
        
        public void paintComponent(Graphics g){
            
            for(int x=0; x < squares.length; x++){
                for(int y=0; y < squares[x].length; y++){
                    //System.out.println("Terrain is: " + squares[x][y].getTerrain());
                    //System.out.println("Info is: " + squares[x][y].getInfo());
                    
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
                }
            }
            
            for(int i = 0; i < creatures.length; i++){
                
                if(creatures[i].getDiet()==1){
                    g.drawImage(carni, creatures[i].getY()*16, creatures[i].getX()*16, this);
                }
                else if(creatures[i].getDiet()==2){
                    g.drawImage(omni, creatures[i].getY()*16, creatures[i].getX()*16, this);
                }
                else {
                    g.drawImage(herbi, creatures[i].getY()*16, creatures[i].getX()*16, this);
                }
            }
            
            
        }
    }
    
    public void drawGrid(Square[][] squares, Creature[] creatures, int columns, int rows){
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(columns*16+16,rows*16+38); 
    
        this.squares = squares;
        this.creatures = creatures;

        this.forest = getToolkit().createImage("forest.png");
        this.meadow = getToolkit().createImage("meadow.png");
        this.mountain = getToolkit().createImage("mountain.png");
        this.water = getToolkit().createImage("wuter.png");
        
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
    
    
    public void drawMenu(){
    
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
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        JButton buttonPressed = (JButton) e.getSource();
        buttonAction(buttonPressed);
    }
    
     public void buttonAction(JButton buttonPressed){

            System.out.println("Go! Pressed.");
    }
}

 