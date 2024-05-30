package main.DrawPuzzle;

import static org.junit.Assert.*;
import java.awt.image.BufferedImage;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;

public class PuzzleTest {
    private Puzzle puzzle;

    @Before
    public void setUp() {
        puzzle = new Puzzle(3); // Initialize a 3x3 puzzle
    }

    @Test
    public void testSetDifficulty() {
        puzzle.setDifficulty(4); // Change to a 4x4 puzzle
        assertEquals(16, puzzle.getDifficulty());
    }

    @Test
    public void testResetImage() {
        puzzle.resetImage();
        assertNotNull(puzzle.orderPieces);
        assertEquals(puzzle.getDifficulty(), puzzle.orderPieces.size());
    }

    @Test
    public void testSolve() {
        puzzle.solve();
        for (int i = 0; i < puzzle.getDifficulty(); i++) {
            assertEquals(i, (int) puzzle.orderPieces.get(i));
        }
    }

    @Test
    public void testIsWin() {
        puzzle.solve();
        assertTrue(puzzle.isWin());
    }

    @Test
    public void testDrawPieces() {
        BufferedImage img = new BufferedImage(550, 620, BufferedImage.TYPE_INT_RGB);
        puzzle.drawPieces(img.getGraphics());
        assertNotNull(img);
    }
}
