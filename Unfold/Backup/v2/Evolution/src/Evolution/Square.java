/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Evolution;

import java.util.ArrayList;


/**
 *
 * @author H8951
 */
public class Square {
    
    private int terrain;
    private double food;
    private double shelter;
    private double temperature;
    private int X;
    private int Y;
    private int barrenCycles = 0;
    private boolean doneOnce = false;
    ArrayList<Creature> inhabitants = new ArrayList<Creature>();
    ArrayList<Creature> creatures = new ArrayList<Creature>();
    
    public Square(int x, int y, int terrain, double food, double shelter, double temperature){
        
        this.X = x;
        this.Y = y;
        this.terrain = terrain;
        
        
        
        
        switch(terrain)
        {
            case 0:
                //plains
                this.food = food * 0.3;
                this.shelter = shelter * 0.5;
                this.temperature = temperature;
                break;
            case 1:
                //mountains
                this.food = food * 1.2;
                this.shelter = shelter * 1.5;
                this.temperature = temperature*0.3;
                break;
            case 2:
                //forest
                this.food = food;
                this.shelter = shelter;
                this.temperature = temperature*1.5;
                break;
            case 3:
                //water
                break;
        }
    }
    
            public ArrayList getInhabitants()
            {
                return inhabitants;
            }
    
            public void updateSquare(ArrayList<Creature> creatures,Square[][]squares, int x, int y, int a, int b)
            {
            
               
                updateSquareCreatures(creatures, x, y);
                
                updateSquareValues(squares, a, b);
                
            }
        
            // taulukoidaan möröt ruutu-olioiden omiin taulukoihin
            public void updateSquareCreatures(ArrayList<Creature> creatures, int x, int y)
            {
                this.creatures = creatures;
                inhabitants.removeAll(inhabitants);
                for(int i = 0;i<creatures.size();i++)
                {
                    if(this.creatures.get(i).getX() == x && this.creatures.get(i).getY() == y)
                        inhabitants.add(this.creatures.get(i));
                }
            }
            
            public void updateSquareValues(Square[][]squares, int a, int b)
            {
                if(doneOnce == false){
                    if(squares[a][b].getFood() == 0){

                        barrenCycles = 100;
                        doneOnce = true;
                    } 
                }
                if(barrenCycles == 0)
                {
                   
                    if      (squares[a][b].terrain == 0)
                    {
                        if(squares[a][b].getFood() < 35)
                        {    
                            squares[a][b].setFood(food+0.4);
                        }    
                    }
                    else if (squares[a][b].terrain == 1)
                    {
                        if(squares[a][b].getFood() < 20)
                        {    
                            squares[a][b].setFood(food+0.2);
                        }    
                    }
                    else if (squares[a][b].terrain == 2)
                    {
                        if(squares[a][b].getFood() < 50)
                        {    
                            squares[a][b].setFood(food+0.6);
                        }    
                    }
                    
                    doneOnce = false;
                    
                }
                else if (barrenCycles != 0)
                {
                    barrenCycles--;
                }
            }
            
            public double getFood(){
                return food;
            }
            public double getShelter(){
                return shelter;
            }
            public double getTemperature(){
                return temperature;
            }
            public int getTerrain(){
                return terrain;
            }
            public void setFood( double food)
            {
                this.food = food;
            }
                                                
            public String getInfo(){
                return this.food + " for food, " + this.shelter + " for shelter, " +  this.temperature + " for temp and " + this.terrain + " for terrain.\nI sit comfly at " + X + "." + Y;
            }
            
            public void setTerrain(int newTerrain){
                this.terrain = newTerrain;
            }
                    
            @Override
            public String toString() 
            { 
                String result = "I am a square object and I have: " + getInfo(); 
                return result;
            } 
}

