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

    private int x = 0;
    private int y = 0;
    
    private int inHeat = 0;
    private int isMale = 0;
    private int energyLevel = 0;
    private int thirstLevel = 0;
    private int temperaturePreference = (int)(Math.random()*20+5);
    private int reproductivity = 0;
    private int fearLevel = 0;
    private int size = (int) (Math.random() * 5+0.5);
    private int diet;
    private int isFlying ;
    private int hasHerd = (int) (Math.random() * 2+0.5);
    private int isBurrower = (int) (Math.random() * 2+0.5);
    private int hasFangs = (int) (Math.random() * 2+0.5);
    private int hasClaws = (int) (Math.random() * 2+0.5);
    private int canProwl = (int) (Math.random() * 2+0.5);
    private int hasTusks = (int) (Math.random() * 2+0.5);
    
    private int terrainWhereIStand;
    private double foodWhereIStand;
    private double shelterWhereIStand;
    private double temperatureWhereIStand;
    
    private double[] anxiousness = {0,0,0,0,0};
    
    private int id = 0;
    private int age= 0;
    private int energyExpenditure  = 5;
    private int energyConsumption = energyExpenditure+1;
    private int energyReserve = 15;
    private int energyReserveMax = size*10+15;
    private int carcassEnergyValue = energyExpenditure*4+energyReserve;
    
    private ArrayList<Creature> inhabitants = new ArrayList<Creature>();
    private ArrayList<Creature> creatures = new ArrayList<Creature>();
    private Square[][]squares;

    private boolean isDead = false;
    
    private boolean[][] memoryFearedSquares;
    private boolean[][] memoryFoodRichSquares;
    private boolean[][] memoryTerritory;
    
    private boolean doneOnce = false;
    
    public Creature(int x, int y, int id){    
        this.x = x;
        this.y = y;
        this.id = id;
    }
    
    
    public Creature runCreature(Square[][] squares,ArrayList<Creature> creatures, int columns, int rows){
        
        this.creatures = creatures;
        this.squares = new Square[rows][columns];
        this.squares = squares;
        
        //System.out.println("In runCreature. My coords are X: " + x + " Y: " + y);
        lookAtLocation(this.squares);
        double anxIndex = determineAnxiousness(foodWhereIStand, shelterWhereIStand, temperatureWhereIStand, terrainWhereIStand);
        
        if (!doneOnce)
        {
            this.diet  = (int) (Math.random() * 2+0.5);
            this.isFlying = (int) (Math.random() * 2+0.5);
            this.hasHerd = (int) (Math.random() * 2+0.5);
            this.isBurrower = (int) (Math.random() * 2+0.5);
            this.hasFangs = (int) (Math.random() * 2+0.5);
            this.hasClaws = (int) (Math.random() * 2+0.5);
            this.canProwl = (int) (Math.random() * 2+0.5);
            this.hasTusks = (int) (Math.random() * 2+0.5);
            
            doneOnce = true;
        }
        //System.out.println(this.diet+"= diet runCreaturessa");
        //System.out.println(anxIndex + " = anxIndex, food tässä ruudussa = " +squares[x][y].getFood());
        if (anxIndex >= 80.0)
        {
            //System.out.println(energyReserve + "= energyReserve, "+energyExpenditure+" = energyExpenditure");
            if(energyReserve < 6 && this.squares[x][y].getFood() >= energyExpenditure)
            {
               // System.out.println("I am: " + creatures.get(0).id + " .Lähetetään fiidille, diet: " + this.creatures.get(0).diet);
                this.creatures = feed(this.squares,this.creatures);
            }
            else
            {
               // System.out.println("Haista hfke ");
                move(this.squares, columns, rows);
            }
            //System.out.println("In runCreature, calling move...");
        }
        else
        {
            this.creatures = feed(this.squares,this.creatures);
        }
        return this;
    }
    
    private void lookAtLocation(Square[][] squares){

        this.foodWhereIStand = squares[x][y].getFood();
        this.temperatureWhereIStand = squares[x][y].getTemperature();
        this.shelterWhereIStand = squares[x][y].getShelter();
        this.terrainWhereIStand = squares[x][y].getTerrain();
    }
    
    private double determineAnxiousness(double food, double shelter, double temperature, int terrain){

        double anxIndex = 0.0;
       
        anxIndex = determineHungerAnx(food, anxIndex);
        anxIndex = determineShelterAnx(shelter, anxIndex);
        anxIndex = determineTempAnx(temperature, anxIndex);
        
        return anxIndex;
    }
    
    // katsotaan pääseekö elikko karkuun - päätös saalistamisesta tehty jo
    private boolean hunt(int[]preyFeatures, int[]hunterFeatures)
    {        
        System.out.println("METSÄSTETÄÄN RUUDUSSA x:"+x+" y:"+y);
        // aliohjelma palauttaa kykyjen eroista lasketun kertoimen joka laitetaan math.randomiin
        double isCaught = Math.random() * (compareSurvivalFeatures(preyFeatures, hunterFeatures))+1;
        
        if (isCaught > 0.5)
        {
            System.out.println("Kiinni jäi");
            return true;
        }
        else
        {
            System.out.println("Karkuun pääsi");
            return false;
        }
        
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
    
    private ArrayList feed(Square[][]squares,ArrayList<Creature> creatures)
    {
        
        //KYSY PASILTA MITÄ IN THE NAME OF THE MACHINE SPIRIT
        
        
        
        this.inhabitants = squares[x][y].inhabitants;
        //System.out.println("I am: " + creatures.get(0).id + ".Feed diet: " + creatures.get(0).diet);
        
        // jos kasvissyöjä tai kaikkiruokainen
        if(this.diet == 0 || this.diet == 2)
        {
            //System.out.println("Kasvissyöjä/kaikkiruokainen ruudussa x: "+x+" y:"+y);
            // jos ruokaa on vähemmän kuin olisi tarve, niin syödään loput ja käytetään varastoja
            if(foodWhereIStand<energyExpenditure)
            {
                //System.out.println("Ei tarpeeksi ruokaa, energia -2");
                energyReserve = energyReserve-2;
                squares[x][y].setFood(0);
            }
        // jos ruokaa on juuri ja juuri riittävästi, syödään loput
        else if (foodWhereIStand>energyExpenditure && foodWhereIStand < energyConsumption)
        {
            //System.out.println("Juuri ja juuri tarpeeksi ruokaa");
            squares[x][y].setFood(0);
        }
        // jos on reilusti ruokaa, niin syödään ja kasvatetaan varastoja
        else
        {
            squares[x][y].setFood(foodWhereIStand - energyConsumption);
            //foodWhereIStand = foodWhereIStand - energyConsumption;
            if(energyReserve >= energyReserveMax)
            {   
                //System.out.println("Ruokaa tarpeeksi ja mahtuu energiavarastoon, lisätään energiaa+1");
                energyReserve++;
            }
        }
            
        }
        // jos lihansyöjä tai kaikkiruokainen
        if (this.diet == 1 || this.diet == 2)
        {
            System.out.println("Lihansyöjä/kaikkiruokainen ruudussa x:"+x+" y:"+y);
            // jos on pedolla on nälkä
            if (energyReserve<20)
            {
                System.out.println("Minulla on nälkä");
                //etsitään ruudusta sopivia syötäviä eläimiä looppaamalla ruudun oliotaulukkoa läpi
                for(int i = 0;i<this.inhabitants.size();i++)
                {
                    if (this.inhabitants.get(i).size <= size && this.inhabitants.get(i).id != id || this.inhabitants.get(i).hasHerd == 0 && hasHerd == 1 && this.inhabitants.get(i).id != id)
                    {
                        System.out.println("Saalis löytynyt, koitetaan ottaa kiinni");
                        // sopiva kohde löytynyt, verrataan ominaisuuksia ja katsotaan kumpi vetää pidemmän korren
                        // kiinni jäi
                        if(hunt(this.inhabitants.get(i).getSurvivalFeatures(), getSurvivalFeatures()))
                        {
                            System.out.println("Saatiin kiinni, lisätään energiaa varastoon "+energyReserve+"(energyReserve) +"+this.inhabitants.get(i).carcassEnergyValue+"carcassEnergyValue");
                            energyReserve = energyReserve + this.inhabitants.get(i).carcassEnergyValue;
                            if(energyReserve > energyReserveMax)
                            {
                                energyReserve = energyReserveMax;
                            }
                            System.out.println("Poistetaan saalis taulukosta");
                            creatures.remove(this.inhabitants.get(i));
                        }
                        //pääsi karkuun
                        else
                        {
                            System.out.println("Saalis pääsi karkuun, energia -2");
                        }
                    }
                }
                energyReserve = energyReserve-2;
            }
            if(energyReserve > 20)
            {
                System.out.println("Ei nälkä, käytetään varastoja");
                energyReserve = energyReserve-2;
            }
        }
        if(energyReserve <= 0)
        {
            System.out.println("Energiareservi 0, kuolema");
            creatures.remove(this);
        }
        return creatures;
       
    }

    
    private double determineHungerAnx(double food,double anxIndex)
    {
       // System.out.println("food in square " + y + "." + x + ": " + food);


        // jos kasvissyöjä tai kaikkiruokainen
        if(diet == 0 || diet == 2)
        {
            //jos ruudussa vähemmän ruokaa kuin on tarve syödä
            if(food < energyExpenditure)
            {
                if (diet == 0)
                {
                    anxIndex = anxIndex + 100;
                }
            
            }
            //jos ruudussa juuri ja juuri riittävästi ruokaa
            if (food >= energyExpenditure && food < energyConsumption)
            {
                anxIndex = anxIndex + 10;
            }
        }
        // jos lihansyöjä tai kaikkiruokainen
        if(diet == 1 || diet == 2)
        {
            
            // jos on nälkä
            if (energyReserve < 25)
            {
                //System.out.println("LIHANSYÖJÄÄ VITUTTAA");
                anxIndex = anxIndex+100;
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
    private double determineShelterAnx(double shelter, double anxIndex)
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
                       // System.out.println("moved north");
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
                      //  System.out.println("moved south");
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
                      //  System.out.println("moved west");
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
                       // System.out.println("moved east");
                        break;
                    }
            }
        }  
    } 
   
    private void mate(ArrayList<Creature> creatures,Square[][]squares, int x, int y)
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
    
    public int getId()
    {
        return id;
    }
    
    public ArrayList getCreaturesArrayList()
    {
        return this.creatures;
    }
    
    public Square[][] getSquaresArray()
    {
        return this.squares;
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