import javax.swing.JFrame;
import java.util.Scanner;
/**
 * Class that contains the main method for the program and creates the frame containing the component.
 * 
 * @author @gcschmit
 * @version 19 July 2014
 */
public class RadarViewer
{
    /**
     * main method for the program which creates and configures the frame for the program
     *
     */
    public static void main(String[] args) throws InterruptedException
    {
        final int ROWS = 100;
        final int COLS = 100;
        Radar radar = new Radar(100, 100);
        radar.setNoiseFraction(0.10);
        boolean firstTime = true;
        

        for(int i = 1; i < 100; i++)
        {
            radar.monsterMove();
            radar.injectNoise();
            if(firstTime == true)
            {
            radar.scan();
            radar.calculateSlopes();
            firstTime = false;
        }
        else{
            Scanner scan = new Scanner(System.in);
            System.out.println("Please input how many times detectFuturePoint has been run (min 1): ");
            int scanNum = scan.nextInt();
            radar.detectFuturePoint(scanNum);
            int counter = 0;
            int saveJ = 0;
            for(int j =0; j < radar.xVelocityHolder.length; j++)
            {
                if(radar.xVelocityHolder[j] > 0 && radar.yVelocityHolder[j] >0)
                {
                    
                    counter+=1;
                    saveJ=j;
                }
            }
            if(counter == 1)
                    {
                        System.out.println("This is the monster's x velocity: " + radar.xVelocityHolder[saveJ]);
                        System.out.println("This is the monster's y velocity: " + radar.yVelocityHolder[saveJ]);
                        i = 100;
                    }
        }
            
        }
            
        }
    

}

