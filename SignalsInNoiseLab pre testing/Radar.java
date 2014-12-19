import java.util.Scanner;
import java.util.Random;
/**
 * The model for radar scan and accumulator
 * 
 * @author @njrafacz
 * @version 17 December 2014
 */
public class Radar
{
    
    // stores whether each cell triggered detection for the current scan of the radar
    private boolean[][] currentScan;
    
    private int [][] candidatePosition = new int[100][100];
    private int [][] accumulator;
    public static int [] coordinateHolderX;
    public static int [] coordinateHolderY;
    private int xVelocity;
    private int yVelocity;
    public static int [] xVelocityHolder;
    public static int [] yVelocityHolder;
    
    
    //stores the x and y distances that the monster moves
    private int monsterDx;
    private int monsterDy;
    private double monsterSlope;
    
    // stores the value of the point slope to this 2d array
    private double pointSlope;
    private int [][]slopes;
    
    // location of the monster
    private int monsterLocationRow;
    private int monsterLocationCol;

    // probability that a cell will trigger a false detection (must be >= 0 and < 1)
    private double noiseFraction;
    
    // number of scans of the radar since construction
    private int numScans;

    /**
     * Constructor for objects of class Radar
     * 
     * @param   rows    the number of rows in the radar grid
     * @param   cols    the number of columns in the radar grid
     */
    public Radar(int rows, int cols)
    {
        // initialize instance variables
        currentScan = new boolean[rows][cols]; // elements will be set to false
        accumulator = new int[rows][cols]; // elements will be set to 0
        coordinateHolderX = new int[100];
        coordinateHolderY = new int[100];// elements will be set to 0
        xVelocityHolder = new int[100];
        yVelocityHolder = new int[100];
        // randomly set the location of the monster (can be explicity set through the
        //  setMonsterLocation method
        Scanner scan = new Scanner(System.in);
        System.out.println("Set the monster's starting row: ");
        monsterLocationRow = scan.nextInt();
        System.out.println("Set the monster's starting column: ");
        monsterLocationCol = scan.nextInt();
        
        System.out.println("To set the Dx of the monster please enter a number between 1 and 5 ");
        monsterDx = scan.nextInt();
        System.out.println("To set the Dy of the monster please enter a number between 1 and 5 ");
        monsterDy = scan.nextInt();
        
        
        monsterSlope = 1.00 * monsterDy / monsterDx;
            
        noiseFraction = 0.05;
        numScans= 0;
    }
    
    /**
     * Performs a scan of the radar. Noise is injected into the grid and the accumulator is updated.
     * 
     */
    public void scan()
    {
        int minX = monsterLocationRow -5;
        if(minX <0)
        {
            minX = 0;
        }
        int maxX = monsterLocationRow +5;
        if(maxX >99)
        {
            maxX = 99;
        }
        int minY = monsterLocationCol - 5;
        if(minY <0)
        {
            minY = 0;
        }
        int maxY = monsterLocationCol + 5;
        if(maxY >99) 
        {
            maxY = 99;
        }
        
        for(int monsterRow =minX; monsterRow<=maxX; monsterRow++)
        {
            for (int monsterCol = minY; monsterCol<=maxY; monsterCol++)
            {
                if(currentScan[monsterRow][monsterCol] == true)
                {
                    candidatePosition[monsterRow][monsterCol]= -1;
                }
            }
        }
        
        
     
        
    }
    public void calculateSlopes()
    {
        int counter =0;
        for(int row = 0; row < candidatePosition.length; row ++)
        {
            for( int col = 0; col < candidatePosition[0].length; col++)
            {
                if(candidatePosition[row][col] == -1)
                {
                    int xVelocity = (row - monsterLocationRow); 
                    if(xVelocity==0)
                    {
                        xVelocity = monsterDx;
                    }
                     int yVelocity = (col - monsterLocationCol);
                    if(yVelocity==0)
                    {
                        yVelocity = monsterDy;
                    }
                    xVelocityHolder[counter] = xVelocity;
                    yVelocityHolder[counter]= yVelocity;
                    coordinateHolderX[counter] = row;
                    coordinateHolderY[counter] = col;
                    counter++;
                }
            }
        }
    }
    public void detectFuturePoint(int scanNum)
    {
        int spots = 0;
        for(int i =0; i < xVelocityHolder.length; i++)
        {
            if(xVelocityHolder[i] != 0)
            {
                spots+=1;
            }
        }
        for(int counter = 0; counter <=spots ; counter ++)
        {
            if(xVelocityHolder[counter] >0 || yVelocityHolder[counter] >0)
            {
                int futurePointX = coordinateHolderX[counter]+( xVelocityHolder[counter]* scanNum);
                int futurePointY = coordinateHolderY[counter] + (yVelocityHolder[counter]* scanNum);                
                if(futurePointX <0 || futurePointY <0 )
                {
                    coordinateHolderX[counter] = 0;
                    coordinateHolderY[counter] = 0;
                    xVelocityHolder[counter] = -20;
                    yVelocityHolder[counter] = -20;
                }
                if(futurePointX >0 && futurePointY >0)
                {
                    if( currentScan[futurePointX][futurePointY] != true)
                    {
                        coordinateHolderX[counter] = 0;
                        coordinateHolderY[counter] = 0;
                        xVelocityHolder[counter] = -20;
                        yVelocityHolder[counter] = -20;
                    }  
                }
            }
        }
    }
    public void monsterMove()
    {
        monsterLocationRow = monsterLocationRow + monsterDx;
        monsterLocationCol = monsterLocationCol + monsterDy;
        if(monsterLocationRow >100)
        {
            monsterLocationRow = 99;
        }
        if( monsterLocationCol >100)
        {
            monsterLocationCol = 99;
        }
        currentScan[monsterLocationRow][monsterLocationCol] = true;
        System.out.println("THE MONSTER HAS MOVED");

    }
    public int getAccumulatedDetection(int row, int col)
    {
        return accumulator[row][col];
    }
     /**
     * Sets the probability that a given cell will generate a false detection
     * 
     * @param   fraction    the probability that a given cell will generate a flase detection expressed
     *                      as a fraction (must be >= 0 and < 1)
     */
    public void setNoiseFraction(double fraction)
    {
        noiseFraction = fraction;
    }
    
    /**
     * Returns true if the specified location in the radar grid triggered a detection.
     * 
     * @param   row     the row of the location to query for detection
     * @param   col     the column of the location to query for detection
     * @return true if the specified location in the radar grid triggered a detection
     */
    public boolean isDetected(int row, int col)
    {
        return currentScan[row][col];
    }
    
    /**
     * Returns the number of rows in the radar grid
     * 
     * @return the number of rows in the radar grid
     */
    public int getNumRows()
    {
        return currentScan.length;
    }
    
    /**
     * Returns the number of columns in the radar grid
     * 
     * @return the number of columns in the radar grid
     */
    public int getNumCols()
    {
        return currentScan[0].length;
    }
    
    /**
     * Returns the number of scans that have been performed since the radar object was constructed
     * 
     * @return the number of scans that have been performed since the radar object was constructed
     */
    public int getNumScans()
    {
        return numScans;
    }

    /**
     * Sets cells as falsely triggering detection based on the specified probability
     * 
     */
    public void injectNoise()
    {
        for(int row = 0; row < currentScan.length; row++)
        {
            for(int col = 0; col < currentScan[0].length; col++)
            {
                // each cell has the specified probablily of being a false positive
                if(Math.random() < noiseFraction)
                {
                    currentScan[row][col] = true;
                }
            }
        }
    }
    
    }

