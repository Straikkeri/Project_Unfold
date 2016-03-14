/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Evolution;

import Evolution.Square;

/**
 *
 * @author H8244
 */
public class Biome 
{    
 
    private int grid, mountains , forests;
    private int[][]terrainLoc;
    private int swellRand = 0;
    private int swellRand2 = 0;
            
    public void Biome(Square[][]Squares, int rows, int columns)
    {
        
        grid = rows*columns;
        terrainLoc = new int[rows][columns];
        mountains =(grid/10000)+2;
        forests = (grid/1200)+2;
        
        createSeedForest(Squares, rows, columns, forests, terrainLoc);
        swellForests(Squares, rows, columns, terrainLoc);
        createSeedMountains(Squares, rows, columns, mountains, terrainLoc);
        swellMountains(Squares, rows, columns, terrainLoc);
        createValley(Squares, rows, columns, grid);
        createRiver(Squares, rows, columns, terrainLoc);
        
    } 
    
    // metodi jolla luodaan laakso
    private void createValley(Square[][]Squares, int rows, int columns, int grid)
    {
        // randomoidaan laaksolle keskipiste satunnaiseen paikkaan ruudukolle ja suhteutetaan
        // sen paikka joko rivien tai sarakkeiden lukumäärään, riippuen siitä kumpi on pienempi,
        // jotta koko laakso mahtuu ruudukkoon
        int seedRowRand = (int)(Math.random() * rows);
        int seedColRand = (int)(Math.random() * columns);
        int swellRandMax = 0;
        
        if (columns > rows)
        {
            swellRandMax = rows/10+1;
        }
        else
        {
            swellRandMax = columns/10+1;
        }

        
      //  System.out.println("Alkuperäinen seedRowRand = "+seedRowRand + " ja seedColRand = "+seedColRand);
      //  System.out.println("swellRandMax = " + swellRandMax);
        
        if (seedRowRand <= swellRandMax || seedRowRand >= rows-swellRandMax)
        {
            for (;seedRowRand <= swellRandMax || seedRowRand >= rows-swellRandMax;)
            {
                seedRowRand = (int)(Math.random()*rows);
                System.out.println("Korjattu seedRowRand = "+seedRowRand);
            }
                
        }
        
        if (seedColRand <= swellRandMax || seedColRand >= columns-swellRandMax)
        {
            for(;seedColRand <= swellRandMax || seedColRand >= columns-swellRandMax;)
            {
                
                seedColRand = (int)(Math.random()*rows);
                System.out.println("Korjattu seedColRand = "+seedColRand);
            }
            
        }
        System.out.println("Lopullinen seedRowRand = "+seedRowRand + " ja seedColRand = "+seedColRand);
        Squares[seedRowRand][seedColRand].setTerrain(0);
        
              

            int scRand;
            
            //turvotetaan seediä
            for(int a = 0;a<3;a++)
            {
                for(int b = 0;b<500;b++)
                {
                    scRand = (int) (Math.random() * 8) + 1;
                    
                    
                    if(a==0)
                    {
                        swellRand = (int) ((Math.random() * Math.sqrt(grid))/6)+1;
                        swellRand2 = (int) ((Math.random() * Math.sqrt(grid))/12)+1;
                    }
                    
                    if (a == 1)
                    {
                        swellRand = (int) ((Math.random() * Math.sqrt(grid))/12)+1;
                        swellRand2 = (int) ((Math.random() * Math.sqrt(grid))/6)+1;
                    }
                    
                    if (a==2)
                    {
                        swellRand = (int) ((Math.random() * Math.sqrt(grid))/8)+1;
                        swellRand2 = (int) ((Math.random() * Math.sqrt(grid))/8)+1;
                    }
                    
                    switch (scRand)
                    {
                        case 1:
                            if(seedRowRand+swellRand < rows && seedColRand+swellRand2 <columns)
                            Squares[seedRowRand+swellRand][seedColRand+swellRand2].setTerrain(0);
                            else
                                    {
                                        System.out.println("OHI MENI!");
                                    }
                        case 2:
                            if(seedRowRand-swellRand >=0 && seedColRand-swellRand2 >=0)
                            Squares[seedRowRand-swellRand][seedColRand-swellRand2].setTerrain(0);
                            else
                                    {
                                        System.out.println("OHI MENI!");
                                    }
                        case 3: 
                            if(seedRowRand+swellRand<rows && seedColRand-swellRand2 >=0)
                            Squares[seedRowRand+swellRand][seedColRand-swellRand2].setTerrain(0);
                            else
                                    {
                                        System.out.println("OHI MENI!");
                                    }
                        case 4:
                            if(seedRowRand-swellRand >= 0 && seedColRand+swellRand2 <columns)
                            Squares[seedRowRand-swellRand][seedColRand+swellRand2].setTerrain(0);
                            else
                                    {
                                        System.out.println("OHI MENI!");
                                    }
                        case 5:
                            if(seedRowRand < rows && seedRowRand >= 0 && seedColRand+swellRand<columns)
                            Squares[seedRowRand][seedColRand+swellRand].setTerrain(0);
                            else
                                    {
                                        System.out.println("OHI MENI!");
                                    }
                        case 6:
                            if(seedRowRand < rows && seedRowRand >= 0 && seedColRand-swellRand >= 0)
                            Squares[seedRowRand][seedColRand-swellRand].setTerrain(0);
                            else
                                    {
                                        System.out.println("OHI MENI!");
                                    }
                        case 7:
                            if(seedRowRand+swellRand < rows && seedColRand < columns && seedColRand >= 0)
                            Squares[seedRowRand+swellRand][seedColRand].setTerrain(0);
                            else
                                    {
                                        System.out.println("OHI MENI!");
                                    }
                        case 8:
                            if(seedRowRand-swellRand >= 0 && seedColRand < columns && seedColRand >= 0)
                            Squares[seedRowRand-swellRand][seedColRand].setTerrain(0);
                            else
                                    {
                                        System.out.println("OHI MENI!");
                                    }
                    }
                }
            }      
    }
    
    private void createRiver(Square[][]Squares, int rows, int columns, int[][] terrainLoc)
        {
        // luodaan joki
        int riverRand = (int )(Math.random() * columns);
            
        if (riverRand < columns/6 || riverRand > columns/1.2)
            {
                for(;riverRand > columns/6 && riverRand < columns/1.2 && riverRand < columns - 2;)
                {
                    riverRand = (int )(Math.random() * columns + 1);
                }
            }
        Squares[0][riverRand].setTerrain(3);
        Squares[1][riverRand].setTerrain(3);
        terrainLoc[0][riverRand] = 3;
        terrainLoc[1][riverRand] = 3;
                
                
        for(int a = 1;a<rows;a=a+2)
        {
            for (int b = 0; b<columns-1 ; b++)
            {  
                if (terrainLoc[a][b] == 3)
                {   
                    //System.out.println("Ekan if lauseen sisällä:");     
                    int rand = (int)(Math.random() * 2 +1);
                    
                    switch(rand)
                    {
                        
                        case 1: 
                        {   
                            //System.out.println("Ekan switchin case 1 sisällä:");
                            if (b == columns -1)
                            {
                                Squares[a][b-1].setTerrain(3);
                                
                                terrainLoc[a][b-1] = 3;
                                if(a!=rows-1)
                                {    
                                    Squares[a+1][b-1].setTerrain(3);
                                    terrainLoc[a+1][b-1] = 3;
                                }
                                if (a == rows -2)
                                {
                                    terrainLoc[a+2][b-1] = 3;
                                    Squares[a+2][b-1].setTerrain(3);
                                }
                            }
                            else
                            {
                                //System.out.println("Asetetaan joki paikkaan a = "+a+" , b+1 = "+(b+1));
                                Squares[a][b+1].setTerrain(3);
                                
                                
                                terrainLoc[a][b+1] = 3;
                                if(a!=rows-1)
                                {    
                                   // System.out.println("Asetetaan joki paikkaan a+1 = "+(a+1)+" , b+1 = "+(b+1));
                                    terrainLoc[a+1][b+1] = 3;
                                    Squares[a+1][b+1].setTerrain(3);
                                }
                                if(a<rows-2)
                                {
                                    terrainLoc[a+2][b+1] = 3;
                                    Squares[a+2][b+1].setTerrain(3);
                                }
                            }
                            if (b<=columns-3)
                            {
                                b=b+2;
                            }
                            if (b==columns-2)
                            {
                                b++;
                            }
                            break;
                        }
                            
                        case 2: 
                        {    
                           // System.out.println("Ekan switchin case 2 sisällä. b = "+b);
                            if (b == 0)
                            {
                                Squares[a][b+1].setTerrain(3);
                                
                                terrainLoc[a][b+1] = 3;
                                if(a!=rows-1)
                                {
                                    terrainLoc[a+1][b+1] = 3;
                                    Squares[a+1][b+1].setTerrain(3);
                                }
                                
                                if(a<rows-2)
                                {
                                    terrainLoc[a+2][b+1] = 3;
                                    Squares[a+2][b+1].setTerrain(3);
                                }
                            }
                            
                            else
                            {
                             //   System.out.println("Asetetaan joki paikkaan a = "+a+" , b-1 = "+ (b-1));
                                Squares[a][b-1].setTerrain(3);
                                
                                terrainLoc[a][b-1] = 3;
                                if(a!=rows-1)
                                {
                                  //  System.out.println("Asetetaan joki paikkaan a+1 = "+(a+1)+" , b-1 = "+(b-1));
                                    terrainLoc[a+1][b-1] = 3;
                                    Squares[a+1][b-1].setTerrain(3);
                                }
                                if(a<rows-2)
                                {
                                    terrainLoc[a+2][b-1] = 3;
                                    Squares[a+2][b-1].setTerrain(3);
                                }
                            }
                            if (b<=columns-3)
                            {
                                b=b+2;
                            }
                            if (b==columns-2)
                            {
                                b++;
                            }
                            break;
                        }    
                    }
                }
            } 
        } 
    }
    
    private void createSeedForest(Square[][]Squares, int rows, int columns, int forests, int[][] terrainLoc)
    {
        // luodaan metsiä joka toiselle sarakkeelle random paikkaan ja tallennetaan niiden sijainnit taulukkoon
        for (int i = 0;i < rows;i++)
        {
            for(int a = 0;a < forests;a++)
            {
                int rand = (int )(Math.random() * columns +1);
             
                if (rand < 0 || rand >= columns)
                {
                    for (;rand < 0 || rand >= columns;)
                    {
                        rand = (int )(Math.random() * columns +1);
                    }                    
                }
                
                Squares[i][rand].setTerrain(2);
                terrainLoc[i][rand] = 2;
            }
        }
    }
    
    private void swellForests(Square[][]Squares, int rows, int columns, int[][] terrainLoc)
    {
    // luodaan uusia metsiä aikasempien luotujen metsien vierelle taulukon perusteella
        for (int a = 1; a < rows-1; a=a+2)
        {
            for (int b = 1 ; b < columns-1 ; b++)
            {
                if (terrainLoc[a][b] == 2) //jos on metsä niin randomoidaan vierelle lisättävien metsien määrä
                {
                    int rand = (int ) (Math.random() * 8 +1);
                   
                    for (int c = 0 ; c<=rand;c++) // randomoidaan uusien  paikat
                    {                     
                        int randvalue = (int ) (Math.random() * 8 +1);
                        
                        switch (randvalue)
                        {
                            case 1: Squares[a-1][b-1].setTerrain(2); // vasen ylänurkka
                            case 2: Squares[a][b-1].setTerrain(2); // yläpuoli
                            case 3: Squares[a+1][b-1].setTerrain(2); // oikea ylänurkka
                            case 4: Squares[a-1][b].setTerrain(2); // vasen
                            case 5: Squares[a+1][b].setTerrain(2); // oikea
                            case 6: Squares[a-1][b+1].setTerrain(2); // vasen alanurkka
                            case 7: Squares[a][b+1].setTerrain(2); // alapuoli
                            case 8: Squares[a+1][b+1].setTerrain(2); // oikea alanurkka
                        }
                        
                    }
                }
            }
        }
    }
    
    private void createSeedMountains(Square[][]Squares, int rows, int columns, int mountains, int[][] terrainLoc)
    {
        
        // luodaan vuoria joka toiselle sarakkeelle random paikkaan ja tallennetaan niiden sijainnit taulukkoon sekä olioihin
        int swellTolerance;
        for (int i = 1;i < rows;i=i+rows/10+2)
        {
            for(int a = 0;a < mountains;a++)
            {
                int rand = (int )(Math.random() * columns + 1);
                
                if (rand < 0 || rand >= columns)
                {
                    for (;rand < 0 || rand >= columns;)
                    {
                        rand = (int )(Math.random() * columns +1);
                    }
                }
                
                if(columns>rows)
                {
                    swellTolerance = rows/20+2;
                }
                else
                {
                    swellTolerance = columns/20+2;
                }
                
                if (i<= columns-swellTolerance && i>=swellTolerance)
                {

                    if(rand <=rows-swellTolerance && rand>=swellTolerance)
                    {
                        Squares[i][rand].setTerrain(1);
                        terrainLoc[i][rand] = 1;
                    }   
                    
                }
            }
        }
    }
    
    private void swellMountains(Square[][]Squares, int rows, int columns, int[][] terrainLoc)
    {
        int swellRand = (int) Math.sqrt(grid);
        int swellRand2 = (int) Math.sqrt(grid);
        int swellTolerance;
        
        if(columns>rows)
        {
            swellTolerance = rows/25+1;
            System.out.println("swellTolerance = "+swellTolerance);
        }
        else
        {
            swellTolerance = columns/25+1;
            System.out.println("swellTolerance = "+swellTolerance);
        }
        
        // luodaan uusia vuoria aikasempien luotujen vuorien vierelle taulukon perusteella
        for (int a = 1; a < rows-2 ; a=a+2)
        {
            for (int b = 1 ; b < columns-2 ; b++)
            {
                if (terrainLoc[a][b] == 1) //jos on vuori niin randomoidaan tuotettavien vuorien määrä
                {
                    int scRand;
                    
                    for(int c=0;c<3;c++)
                    {
                        for(int d = 0;d<20;d++)
                        {
                            // luodaan suorakaide1
                            if(c==0)
                            {
                                swellRand = (int) ((Math.random() * Math.sqrt(grid))/20)+1;
                                //System.out.println("Eka vuorenturvotuksen swellRand = " + swellRand);
                                             
                               /* while (swellRand <= swellTolerance || swellRand >= columns-swellTolerance)
                                {
                                    swellRand = (int) ((Math.random() * Math.sqrt(grid))/20)+1;
                                    System.out.println("Korjattu vuorenturvotuksen swellRand = " + swellRand);
                                }*/
                                
                                swellRand2 = (int) ((Math.random() * Math.sqrt(grid))/40)+1;
                                //System.out.println("Eka vuorenturvotuksen swellRand2 = " + swellRand2);
                                
                               /* while (swellRand2 <= swellTolerance || swellRand >=rows-swellTolerance)
                                {
                                    swellRand2 = (int) ((Math.random() * Math.sqrt(grid))/40)+1;
                                    System.out.println("Korjattu vuorenturvotuksen swellRand2 = " + swellRand2);
                                }*/
                            }
                            //luodaan suorakaide2
                            if (c == 1)
                            {
                                swellRand = (int) ((Math.random() * Math.sqrt(grid))/40)+1;
                                             
                               /* while (swellRand <= swellTolerance && swellRand >= columns-swellTolerance)
                                {
                                    swellRand = (int) ((Math.random() * Math.sqrt(grid))/40)+1;
                                }*/
                                
                                swellRand2 = (int) ((Math.random() * Math.sqrt(grid))/20)+1;
                                
                               /* while (swellRand2 <= swellTolerance && swellRand >=rows-swellTolerance)
                                {
                                    swellRand2 = (int) ((Math.random() * Math.sqrt(grid))/20)+1;
                                }*/
                            }
                            //luodaan neliö
                            if (c==2)
                            {
                                swellRand = (int) ((Math.random() * Math.sqrt(grid))/30)+1;
                                             
                                /*while (swellRand <= swellTolerance && swellRand >= columns-swellTolerance)
                                {
                                    swellRand = (int) ((Math.random() * Math.sqrt(grid))/30)+1;
                                }*/
                                
                                swellRand2 = (int) ((Math.random() * Math.sqrt(grid))/30)+1;
                                
                              /*  while (swellRand2 <= swellTolerance && swellRand >=rows-swellTolerance)
                                {
                                    swellRand2 = (int) ((Math.random() * Math.sqrt(grid))/30)+1;
                                }*/
                            }
                            
                            scRand = (int) (Math.random() * 8) + 1;
                            
                            switch (scRand)
                            {
                                case 1:
                                    if(a+swellRand < rows && b+swellRand2 < columns)
                                    {
                                        Squares[a+swellRand][b+swellRand2].setTerrain(1);
                                    }
                                else
                                    {
                                        System.out.println("OHI MENI!");
                                    }
                                
                                case 2:
                                    if (a-swellRand >= 0 && b-swellRand2 >=0)
                                    {
                                    Squares[a-swellRand][b-swellRand2].setTerrain(1);
                                    }
                                    else
                                    {
                                        System.out.println("OHI MENI!");
                                    }
                                
                                case 3: 
                                    if(a+swellRand < rows && b-swellRand2 >=0)
                                    {
                                        Squares[a+swellRand][b-swellRand2].setTerrain(1);
                                    }
                                else
                                    {
                                        System.out.println("OHI MENI!");
                                    }
                                
                                case 4:
                                    if(a-swellRand > 0 && b+swellRand2 < columns)
                                    {
                                        Squares[a-swellRand][b+swellRand2].setTerrain(1);
                                    }
                                else
                                    {
                                        System.out.println("OHI MENI!");
                                    }
                                
                                case 5:
                                    if(a<rows && a>=0 && b+swellRand < columns)
                                    {
                                    Squares[a][b+swellRand].setTerrain(1);
                                    }
                                else
                                    {
                                        System.out.println("OHI MENI!");
                                    }
                                
                                case 6:
                                    if(a<rows && a>=0 && b-swellRand >= 0)
                                    Squares[a][b-swellRand].setTerrain(1);
                                    else
                                    {
                                        System.out.println("OHI MENI!");
                                    }
                                case 7:
                                    if(a+swellRand < rows && b>=0 && b<columns)
                                    Squares[a+swellRand][b].setTerrain(1);
                                    else
                                    {
                                        System.out.println("OHI MENI!");
                                    }
                                case 8:
                                    if(a-swellRand >= 0 && b>=0 && b<columns)
                                    Squares[a-swellRand][b].setTerrain(1);
                                    else
                                    {
                                        System.out.println("OHI MENI!");
                                    }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void setForest(int value)
    {
        if (value == 1)
        {
            
            this.forests = (grid/3000)+1;
        }
        
        if (value == 2)
        {
            this.forests = (grid/1200)+2;
        }
        
        if (value == 3)
        {
            this.forests = (grid/800)+2;
        }
    }
    
    public void setMountains (int value)
    {
        if (value == 1)
        {
            this.mountains = (grid/12000)+1;
        }
        
        if (value == 2)
        {
            this.mountains = (grid/10000)+2;
        }
        
        if (value == 2)
        {
            this.mountains = (grid/8000)+3;
        }
    }
}