import java.util.Scanner;
/**
 * The model for radar
 * Holds all instance variables used throughout the lab.
 * 
 * @author @njrafacz
 * @version 18 December 2014
 */
public class Radar
{
    
    // stores whether each cell triggered detection for the current scan of the radar
    private boolean[][] currentScan;
    //stores values found to be true within the original range of the monster.
    private int [][] candidatePosition = new int[100][100];
    //holds the coordinates for points to be found true in candidatePosition
    private  int [] coordinateHolderX;
    private  int [] coordinateHolderY;
    //stores  x and y velocities
    private int xVelocity;
    private int yVelocity;
    //stores the x and y velocities of points in candidatePosition
    private  int [] xVelocityHolder;
    private  int [] yVelocityHolder;
    
    
    //stores the x and y distances that the monster moves
    private int monsterDx;
    private int monsterDy;
    
    
    // location of the monster
    private int monsterLocationRow;
    private int monsterLocationCol;
    //stores the value of the monster's x and y velocities found via the algorithm.
    private int discoveredMonsterDx;
    private int discoveredMonsterDy;

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
        coordinateHolderX = new int[100];
        coordinateHolderY = new int[100];// elements will be set to 0
        xVelocityHolder = new int[100];
        yVelocityHolder = new int[100];
        //randomly set the location of the monster (can be explicity set through the
        //setMonsterLocation method
            
        noiseFraction = 0.05;
        numScans= 0;
    }
    
    /**
     * Performs a scan of the radar. Creates an array of possible monster locations which are then stored into a list if they are detected to be true.
     * 
     */
    public void scan()
    {
        //This series of "if" statements finds the range around the monster that could possibly be the monster.
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
        //This loops through that range and any points found to be true are stored in a list for further manipulation.
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
    /**
     * Calculates the slopes of the point stored in candidatePosition from scan method.
     * If the velocity is equal to zero, that means it is calculating the monster point, and automatically sets the velocity of that point to the inputted monster velocity.
     * Values are stored into 4 different arrays that hold x and y coordinates, and x and y velocities. The indexes will match for each array will match for a specific point.
     */
    public void calculateSlopes()
    {
        int counter =0;
        //this nested for loop calculates the x and y velocities for the points found in candidatePosition and stores them in two different arrays.
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
    /**
     * Detects future points in the grid to compare against points found to be true in the first iteration via scan. 
     * If a point is invalid in any way, the x and y coordinate are set to 0, and the x and y velocities set to -20.
     */
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
    /**
     * Updates the position of the monster. If the monster attempts to go past the size of Radar, it will set the monster coordinates to (99,99)
     * After updating the position of the monster, it will set that point in currentScan to true. 
     */
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
    /**
     * Sets the monster Location Row
     * @param row  sets the value of monsterLocationRow equal to this.
     * 
     */
    public void setMonsterLocationRow(int row)
    {
        monsterLocationRow = row;
    }
    /**
     * Sets the monster Location Column
     * @param col  sets the value of monsterLocationCol equal to this.
     */
    public void setMonsterLocationCol(int col)
    {
        monsterLocationCol = col;
    }
    /**
     * Sets the monster's x velocity
     * @param monsterVelocityX  sets the value of monsterDx equal to this.
     */
    public void setMonsterDx( int monsterVelocityX)
    {
        monsterDx = monsterVelocityX;
    }
    /**
     * Sets the monster's y velocity.
     * @param monsterVelocityY  sets the value of monsterDy equal to this.
     */
    public void setMonsterDy(int monsterVelocityY)
    {
        monsterDy = monsterVelocityY;
    }
    /**
     * Returns the monster's x velocity found via the algorithm.
     * 
     */
    public int getDiscoveredDx()
    {
        return discoveredMonsterDx;
    }
    /**
     * Returns the monster's y velocity found via the algorithm.
     * 
     */
    public int getDiscoveredDy()
    {
        return discoveredMonsterDy;
    }
    /**
     * Stores the monster's x velocity found via the algorithm.
     * @param x  sets the value of discoveredMonsterDx equal to this.
     */
    public void setDiscoveredDx(int x)
    {
        discoveredMonsterDx = x;
    }
    /**
     * Stores the monster's y velocity found via the algorithm.
     * @param y sets the value of discoveredMonsterDy equal to this.
     */
    public void setDiscoveredDy(int y)
    {
        discoveredMonsterDy = y;
    }
    /**
     * Finds the x and y velocity of the monster.
     * This is the main algorithm.
     * 
     */
    public int findMonsterVelocity()
    {
        boolean firstTime = true;
        for(int i = 1; i < 100; i++)
        {
            monsterMove();
            injectNoise();
            if(firstTime == true)
            {
            scan();
            calculateSlopes();
            firstTime = false;
        }
        else{
            int scanNum = i-1;
            detectFuturePoint(scanNum);
            int counter = 0;
            int saveJ = 0;
            for(int j =0; j < xVelocityHolder.length; j++)
            {
                if(xVelocityHolder[j] > 0 && yVelocityHolder[j] >0)
                {
                    
                    counter+=1;
                    saveJ=j;
                }
            }
            if(counter == 1)
                    {
                        setDiscoveredDx(xVelocityHolder[saveJ]);
                        setDiscoveredDy(yVelocityHolder[saveJ]);
                        i = 100;
                    }
        }
            
        }
        return 0;
        }
     
       public static void main(String[] args) 
    {
        int monsterLocationRow1;
        int monsterLocationCol1;
        int monsterDx1;
        int monsterDy1;
        Radar radar = new Radar(100,100);
        Scanner scan = new Scanner(System.in);
        System.out.println("Set the monster's starting row: ");
        monsterLocationRow1 = scan.nextInt();
        radar.setMonsterLocationRow(monsterLocationRow1);
        System.out.println("Set the monster's starting column: ");
        monsterLocationCol1 = scan.nextInt();
        radar.setMonsterLocationCol(monsterLocationCol1);
        System.out.println("To set the Dx of the monster please enter a number between 1 and 5 ");
        monsterDx1 = scan.nextInt();
        radar.setMonsterDx(monsterDx1);
        System.out.println("To set the Dy of the monster please enter a number between 1 and 5 ");
        monsterDy1 = scan.nextInt();
        radar.setMonsterDy(monsterDy1);
        radar.findMonsterVelocity();
        radar.getDiscoveredDx();
        System.out.println("This is the monster's x velocity: " + radar.getDiscoveredDx());
        radar.getDiscoveredDy();
        System.out.println("This is the monster's y velocity: " + radar.getDiscoveredDy());
}
    }
    

