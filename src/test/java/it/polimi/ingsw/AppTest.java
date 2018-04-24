package it.polimi.ingsw;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.awt.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        Dice dice;
        for(int diceValue = 1; diceValue < 7; diceValue++) {
            try {
                dice = new Dice(diceValue, Color.RED);
                assertEquals(diceValue, dice.getValue());
            } catch (Exception exc) {
                assertTrue(false);
            }
        }
        try {
            dice = new Dice(7, Color.RED);
            assertTrue(false);
        }
        catch (Exception exc) {
            assertTrue(true);
        }
    }
}
