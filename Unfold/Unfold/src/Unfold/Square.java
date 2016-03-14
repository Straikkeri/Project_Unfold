/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Unfold;

import java.util.ArrayList;


/**
 *
 * Contains all the functionality and data for square objects that make the simulation area.
 * 
 * @author Aleksi Tommila
 * @author Janne Möttölä
 */
public class Square {
    
    private int terrain, X, Y;
    private double shelter, temperature, food;
    private int barrenCycles = 0;
    private boolean doneOnce = false;
    private ArrayList<Creature> inhabitants = new ArrayList<Creature>();
    private ArrayList<Creature> creatures = new ArrayList<Creature>();
    Square[][]squares;
    
    /**
     * Constructor for the class, sets coordinates for each instance and decides values based on its terrain type.
     * 
     * @param x x-coordinate of the square.
     * @param y y-coordinate of the square.
     * @param terrain type of terrain. Can be 0=plains, 1=mountains, 2=forest, 3= water.
     * @param food amount of food in the square.
     * @param shelter amount of shelter in the square.
     * @param temperature  amount of temperature in the square.
     */
    
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
                this.food = 0;
                this.shelter = 0;
                //water
                break;
        }
    }
    
    /**
     * Updates values changed during creature run time, 
     * 
     * @param creatures creatures ArrayList as it is before running the update.
     * @param squares   squares array as it is before running the update.
     * @param rows      amount of rows in the simulation area.
     * @param columns   amount of columns in the simulation area.
     * @param x         x-coordinate for the square.
     * @param y         y-coordinate for the square.
     * @return          returns an updated Squares table. 
     */
    
    public Square[][] updateSquare(ArrayList<Creature> creatures,Square[][]squares,/* int x, int y,*/ int rows, int columns, int x, int y){
        this.squares = new Square[rows][columns];
        this.squares = squares;
       // this.creatures = cleanUp(creatures);
        this.squares[x][y].inhabitants = updateSquareCreatures(creatures,this.squares[x][y].inhabitants, x, y);

        updateSquareValues(this.squares, x, y);

        return this.squares;

    }

    // taulukoidaan möröt ruutu-olioiden omiin taulukoihin
    private ArrayList updateSquareCreatures(ArrayList<Creature> creatures,ArrayList<Creature> inhabitants, int x, int y){
        this.creatures = creatures;
        this.inhabitants = inhabitants;

        inhabitants.removeAll(inhabitants);
        for(int i = 0;i<this.creatures.size();i++){
            if(this.creatures.get(i).getX() == x && this.creatures.get(i).getY() == y){
               // System.out.println("Ehto toteutui, lisätään mörri");
                this.inhabitants.add(this.creatures.get(i));
            }
        }
        //System.out.println("inhabitantsin koko updateSquareCreaturesissa: "+this.inhabitants.size());
        return this.inhabitants;
    }

    private void updateSquareValues(Square[][]squares, int a, int b){
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
    
    public ArrayList<Creature> getInhabitants() {
        return inhabitants;
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
    public int getX()
    {
        return X;
    }

    public int getY()
    {
        return Y;
    }

    @Override
    public String toString() 
    { 
        String result = "I am a square object and I have: " + getInfo(); 
        return result;
    } 
}

