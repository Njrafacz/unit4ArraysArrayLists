

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class RadarTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class RadarTest
{
    /**
     * Default constructor for test class RadarTest
     */
    public RadarTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }
    /**
     * Tests the algorithm with specific values for monsterLocation and the monster's velocity.
     * 
     */
    @Test 
    public void checkMonsterVelocity()
    {
        Radar radar = new Radar(100,100);
        radar.setMonsterLocationRow(50);
        radar.setMonsterLocationCol(50);
        radar.setMonsterDx(5);
        radar.setMonsterDy(2);
        radar.findMonsterVelocity();
        assertEquals("The value from setMonsterDx should be equal to the value found in getDiscoveredDx", 5, radar.getDiscoveredDx());
        assertEquals("The value from setMonsterDy should be equal to the value found in getDiscoveredDy", 2, radar.getDiscoveredDy());
    }
    /**
     * Tests the algorithm with specific values for monsterLocation and the monster's velocity.
     * 
     */
    @Test 
    public void checkMonsterVelocity1()
    {
        Radar radar = new Radar(100,100);
        radar.setMonsterLocationRow(70);
        radar.setMonsterLocationCol(85);
        radar.setMonsterDx(3);
        radar.setMonsterDy(4);
        radar.findMonsterVelocity();
        assertEquals("The value from setMonsterDx should be equal to the value found in getDiscoveredDx", 3, radar.getDiscoveredDx());
        assertEquals("The value from setMonsterDy should be equal to the value found in getDiscoveredDy", 4, radar.getDiscoveredDy());
    }
    /**
     * Tests the algorithm with specific values for monsterLocation and the monster's velocity.
     * 
     */
    @Test 
    public void checkMonsterVelocity2()
    {
        Radar radar = new Radar(100,100);
        radar.setMonsterLocationRow(30);
        radar.setMonsterLocationCol(45);
        radar.setMonsterDx(1);
        radar.setMonsterDy(4);
        radar.findMonsterVelocity();
        assertEquals("The value from setMonsterDx should be equal to the value found in getDiscoveredDx", 1, radar.getDiscoveredDx());
        assertEquals("The value from setMonsterDy should be equal to the value found in getDiscoveredDy", 4, radar.getDiscoveredDy());
    }
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
}
