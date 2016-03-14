/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * Puuttuu ikääntyminen, kuolema, juominen, parittelu, evoluutio, muistaminen, saalistaminen
 */

package Evolution;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author H8951
 */
public class Creature {
    
    private Random rand = new Random();
    
    private int id = 0;
    private int age= 0;
    private int energyExpenditure  = 5;
    private int energyConsumption = energyExpenditure+1;
    private int energyReserve = 15;
    private int carcassEnergyValue = energyExpenditure*4+energyReserve;
  
    private int x = 0;
    private int y = 0;
    
    private int inHeat = 0;
    private int isMale = 0;
    private int energyLevel = 0;
    private int thirstLevel = 0;
    private int temperaturePreference = 0;
    private int reproductivity = 0;
    private int fearLevel = 0;
    private int size = 0;
    private int diet = (int) (Math.random() * 4);
    private int isFlying = 0;
    private int hasHerd = 0;
    private int isBurrower = 0;
    private int hasFangs = 0;
    private int hasClaws = 0;
    private int canProwl = 0;
    private int hasTusks = 0;
    
    private ArrayList<Creature> inhabitants = new ArrayList<Creature>();
    private double[] anxiousness = {0,0,0,0,0};
    
    private boolean isDead = false;
    private boolean[][] memoryFearedSquares;
    private boolean[][] memoryFoodRichSquares;
    private boolean[][] memoryTerritory;
    
    public Creature(int x, int y, int id){    
        this.x = x;
        this.y = y;
        this.id = id;
    }
    
    
    public void runCreature(Square[][] squares, int columns, int rows){
            System.out.println("In runCreature. My coords are X: " + x + " Y: " + y);
            double anxIndex = lookAtLocation(squares);
        
            if (anxIndex >= 60.0){
                System.out.println("In runCreature, calling move...");
                move(squares, columns, rows);
            }   
    }
    
    private double lookAtLocation(Square[][] squares){
        System.out.println("In lookAtLocation");
        
        return determineAnxiousness(squares[x][y].getFood(), squares[x][y].getShelter(), squares[x][y].getTemperature(), squares[x][y].getTerrain(), squares);
    }
    
    private double determineAnxiousness(double food, double shelter, double temperature, int terrain, Square[][]squares){
        System.out.println("In determineAnxiousness");
        double anxIndex = 0.0;
       
        anxIndex = determineHunger(food, anxIndex, squares);
        anxIndex = determineShelter(shelter, anxIndex);
        anxIndex = determineTempAnx(temperature, anxIndex);
        
        if(determineHeat(anxIndex, getAnxiousness()))
        {
            inHeat = 1;
        }
        else
        {
            inHeat = 0;
        }
        
        System.out.println("AnxIndex: " + anxIndex);
        return anxIndex;
    }
    // SIIRRETTY KOKONAISUUDESSAAN determineHUNGER()-METODIN SISÄLLE
    // katsotaan olisiko sopivaa metsästettävää ruudussa
   /* private boolean determinePrey(Square[][]squares, int x, int y)
    {
        this.inhabitants = squares[x][y].getInhabitants();
        for(int i = 0;i<=squares[x][y].inhabitants.size();i++)
        {
            // metsästys toteutuu jos metsästettävä on samankokoinen tai pienempi kuin metsästäjä
            // tai jos metsästäjä on laumaeläin ja metsästettävä ei ole
            if(this.inhabitants.get(i).getSize() <= size || this.inhabitants.get(i).hasHerd == 0 && hasHerd == 1)
            {         
                //sopiva metsästettävä löytynyt, kokeillaan saadaanko syötyä
                if(hunt(this.inhabitants.get(i).getSurvivalFeatures(), getSurvivalFeatures()))
                {
                    return true;
                }
            }
        }
        return false;
    }*/
    
    // katsotaan pääseekö elikko karkuun - päätös saalistamisesta tehty jo
    private boolean hunt(int[]preyFeatures, int[]hunterFeatures)
    {        
        // aliohjelma palauttaa kykyjen eroista lasketun kertoimen joka laitetaan math.randomiin
        double feed = Math.random() * (compareSurvivalFeatures(preyFeatures, hunterFeatures));
        
        if (feed > 2)
        {
            return true;
        }
        else return false;
        
    }
    
    private boolean determineHeat(double anxIndex, double[]anxiousness)
    {
        anxiousness[4] = anxiousness[3];
        anxiousness[3] = anxiousness[2];
        anxiousness[2] = anxiousness[1];
        anxiousness[1] = anxiousness[0];
        
        anxiousness[0] = anxIndex;
        
        double anxietyAverage = (anxiousness[0]+anxiousness[1]+anxiousness[2]+anxiousness[3]+anxiousness[4])/5; 
        
        if (anxietyAverage >= 50)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    private double determineHunger(double food,double anxIndex, Square[][]squares)
    {
        System.out.println("food in square " + y + "." + x + ": " + food);

        // jos kasvissyöjä tai kaikkiruokainen
        if(diet == 0 || diet == 2)
        {
            //jos ruudussa vähemmän ruokaa kuin on tarve syödä
            if(food < energyExpenditure)
            {
                if (diet == 0)
                {
                    System.out.println("No food, anxIndex +100");
                    squares[x][y].setFood(0);
                    energyReserve = energyReserve - 2;
                    anxIndex = anxIndex + 100;
                }
            
            }
            //jos ruudussa reilusti ruokaa
            else if (food >= energyConsumption)
            {
                System.out.println( food+" food, eating 6 food. New food amount "+(food-energyConsumption));
                squares[x][y].setFood(food-energyConsumption);
            if(energyReserve < 10)
            {   
                System.out.println("Adding +1 to energyReserves");
                energyReserve = energyReserve+1;
            }
            }
            //jos ruudussa juuri ja juuri riittävästi ruokaa
            else if (food >= energyExpenditure && food < energyConsumption)
            {
                System.out.println("Barely enough food. Food: "+food);
                squares[x][y].setFood(food-energyConsumption);
                anxIndex = anxIndex + 10;
            }
        }
        // jos lihansyöjä tai kaikkiruokainen
        if(diet == 1 || diet == 2)
        {
            // jos on nälkä
            if (energyReserve < 25)
            {
               /* if(determinePrey(squares, x, y))
                {
                    energyReserve = energyReserve+ CARCASS VALUE
                }*/
                
                // katsotaan onko sopivaa saalista ruudussa
                this.inhabitants = squares[x][y].getInhabitants();
                for(int i = 0;i<squares[x][y].inhabitants.size();i++)
                {
                    // saalistusyritys toteutuu jos metsästettävä on samankokoinen tai pienempi kuin metsästäjä
                    // tai jos metsästäjä on laumaeläin ja metsästettävä ei ole
                    if(this.inhabitants.get(i).id != id && this.inhabitants.get(i).getSize() <= size || this.inhabitants.get(i).id != id && this.inhabitants.get(i).hasHerd == 0 && hasHerd == 1)
                    {         
                        //sopiva metsästettävä löytynyt, kokeillaan saadaanko syötyä
                        if(hunt(this.inhabitants.get(i).getSurvivalFeatures(), getSurvivalFeatures()))
                        {
                            energyReserve = energyReserve + this.inhabitants.get(i).carcassEnergyValue;
                        }
                        else
                        {
                            anxIndex = anxIndex + 100;
                        }
                    }
                }
            }
        }
        return anxIndex;
    }
    
    // paljonko lämpötilaero ahdistaa
    private double determineTempAnx(double temperature, double anxIndex)
    {
        
        if(temperature >= temperaturePreference + 5 || temperature <= temperaturePreference - 5)
        {
            anxIndex = anxIndex + 10;
            if (temperature >= temperaturePreference + 10 || temperature <= temperaturePreference - 10)
            {
                anxIndex = anxIndex + 10;
                if (temperature >= temperaturePreference + 15 || temperature <= temperaturePreference - 15)
                {
                    anxIndex = anxIndex + 20;
                    if (temperature >= temperaturePreference + 20 || temperature <= temperaturePreference - 20)
                    {
                        anxIndex = anxIndex + 20;
                    }
                }
            }
        }
        return anxIndex;
    }
    
    // paljonko suojanpuute ahdistaa
    private double determineShelter(double shelter, double anxIndex)
    {
        if(shelter<90)
        {
            anxIndex = anxIndex + 10;
            if(shelter<70)
            {
                anxIndex = anxIndex + 10;
                if(shelter<50)
                {
                    anxIndex = anxIndex + 10;
                    if(shelter<30)
                    {
                        anxIndex = anxIndex + 10;
                        if(shelter <10)
                        {
                            anxIndex = anxIndex + 10;
                        }
                    }
                }
            }
        }  
         
        return anxIndex;
    }
    
    public void move(Square[][]squares, int columns, int rows){
        
        boolean moved = false;
       // System.out.println("In move.");
        
        
        while(moved == false){     
           // System.out.println("in while... moved: " + moved);
           // int fig = rand.nextInt(3);
            int fig = (int)(Math.random() * 4);
          //  System.out.println("Randomized direction: " + fig);
            switch(fig){
                case 0:
                    if (x==0)
                    {
                        break;
                    }
                    if(squares[x-1][y].getTerrain() != 3)
                    {
                        x = x - 1;
                        moved = true;
                        System.out.println("moved north");
                        break;
                    }    
                case 1:
                    if (x == rows-1)
                    {
                        break;
                    }
                    if(squares[x+1][y].getTerrain() != 3){
                        x = x + 1;
                        moved = true;
                        System.out.println("moved south");
                        break;
                    }
                case 2:
                    if (y == 0)
                    {
                        break;
                    }
                    if(squares[x][y-1].getTerrain() != 3){
                        y = y - 1;
                        moved = true;
                        System.out.println("moved west");
                        break;
                    }
                case 3:
                    if (y == columns-1)
                    {
                        break;
                    }
                    if(squares[x][y+1].getTerrain() != 3){
                        y = y + 1;
                        moved = true;
                        System.out.println("moved east");
                        break;
                    }
            }
        }  
    } 
   
    private void mate(Creature[]creature,Square[][]squares, int x, int y)
    {
        this.inhabitants = squares[x][y].getInhabitants();
        for(int i = 0;i<squares[x][y].inhabitants.size();i++)
        {
            
            if(inhabitants.get(i).inHeat == 1 && inHeat == 1 && inhabitants.get(i).isMale != isMale)
            {
               if (compareRacialFeatures(inhabitants.get(i).getRacialFeatures(), getRacialFeatures()))
               {
                   // NUSNUSNUS
               }
            }
        }
    }
    
    // verrataan saalistajan ja saaliin ominaisuuksia ja määritetään erojen perusteella kerroin joka määrittää metsästyksen onnistumista
    private int compareSurvivalFeatures(int[]preyFeatures, int[]hunterFeatures)
    {
        int compareIndex = 0;
        
        if(preyFeatures[0] < hunterFeatures[0])
        {
            compareIndex--;
        }
        if(preyFeatures[0] > hunterFeatures[0])
        {
            compareIndex++;
        }
        if(preyFeatures[1] < hunterFeatures[1])
        {
            compareIndex--;
        }
        if(preyFeatures[1] > hunterFeatures[1])
        {
            compareIndex++;
        }
        if(preyFeatures[2] < hunterFeatures[2])
        {
            compareIndex--;
        }
        if(preyFeatures[2] > hunterFeatures[2])
        {
            compareIndex++;
        }
        if(preyFeatures[3] < hunterFeatures[3])
        {
            compareIndex--;
        }
        if(preyFeatures[3] > hunterFeatures[3])
        {
            compareIndex++;
        }
        if(preyFeatures[4] < hunterFeatures[4])
        {
            compareIndex--;
        }
        if(preyFeatures[4] > hunterFeatures[4])
        {
            compareIndex++;
        }
        if(preyFeatures[5] < hunterFeatures[5])
        {
            compareIndex--;
        }
        if(preyFeatures[5] > hunterFeatures[5])
        {
            compareIndex++;
        }
        if(preyFeatures[6] < hunterFeatures[6])
        {
            compareIndex--;
        }
        if(preyFeatures[6] > hunterFeatures[6])
        {
            compareIndex++;
        }
        
        return compareIndex;
    }    
    
    private boolean compareRacialFeatures(int[] mateFeatures, int[] myFeatures)
    {
        int compareIndex = 0;
        
        if (mateFeatures[0] == myFeatures[0])
        {
            compareIndex++;
        }
        
        if (mateFeatures[1] == myFeatures[1] || mateFeatures[1]+1 == myFeatures[1] || mateFeatures[1]-1 == myFeatures[1])
        {
            compareIndex++;
        }
        
        if (mateFeatures[2] == myFeatures[2])
        {
            compareIndex++;
        }
        
        if (mateFeatures[3] == myFeatures[3])
        {
            compareIndex++;
        }
        
        if (compareIndex > 2)
        {
            return true;
        }
        
        else
        {
            return false;
        }
    }
    
    public int[] getSurvivalFeatures()
    {
        int[] features = new int[7];
        
        features[0] = isFlying;
        features[1] = isBurrower;
        features[2] = hasFangs;
        features[3] = hasClaws;
        features[4] = hasTusks;
        features[5] = canProwl;
        features[6] = hasHerd;
        
        return features;        
    }
    
    public int[] getRacialFeatures()
    {
        int[] features = new int[4];
        
        features[0] = isFlying;
        features[1] = size;
        features[2] = diet;
        features[3] = isBurrower;
        
        return features;
    }

    public int getSize()
    {
        return size;
    }
    
    public double[] getAnxiousness()
    {
        return anxiousness;
    }
    
    public int getInHeat()
    {
        return inHeat;
    }
    
    public int getDiet() {
        return diet;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    
    public String getInfo(){
        return this.id + " id, " + this.age + " age, " +  this.energyConsumption + " energyConsumption, \n" 
       + this.carcassEnergyValue + " carcassEnergyValue, " + this.x + " X, " +  this.y + " Y, \n"
       + this.size + " size, " +  this.isFlying + " isFlying, " +  this.hasHerd +  " hasHerd, \n"
       + this.diet + " diet, " +  this.energyLevel + " energyLevel, \n"
       + this.thirstLevel + " thirstLevel, " +  this.temperaturePreference + " temperatureLevel, \n"
       + this.reproductivity + " reproductivity, " +  this.inHeat + " heat, \n"
       + this.fearLevel + " fearLevel and I sit comfly at: " + x + "." + y;
    }
    
    @Override
    public String toString() { 
        String result = "I am a creature object and I have: \n" + getInfo(); 
        return result;
    }
    
}