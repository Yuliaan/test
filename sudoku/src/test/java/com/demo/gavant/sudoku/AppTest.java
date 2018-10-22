package com.demo.gavant.sudoku;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

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
        assertTrue( true );
    }
    
    
    /**
     * Test running from file
     */
    public void testSudokuFile()
    {
    	Board sudoku = new Board("files/puzzle1.txt");
    	boolean result = sudoku.solve();
    	assertEquals(true, result );
    }
    
    /**
     * Past tests
    public void testNoSolutionFound()
    {
        Board sudoku = new Board("0003", "0204", "1000", "4000");

        boolean result = sudoku.solve();
        assertEquals(false, result );
    }

    public void testWithSolution()
    {
    	Board sudoku = new Board(
    		"...84...9",
            "..1.....5",
            "8...2146.",
            "7.8....9.",
            ".........",
            ".5....3.1",
            ".2491...7",
            "9.....5..",
            "3...84..."
        );

        String[] answer = new String[]
        {
            "632845179",
            "471369285",
            "895721463",
            "748153692",
            "163492758",
            "259678341",
            "524916837",
            "986237514",
            "317584926",
        };

        String[] solution = sudoku.solve();
        assertEquals(answer, solution);
    }
    */
}
