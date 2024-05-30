package main.Leaderboard;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class LeaderboardTest {
    private Leaderboard leaderboard;

    @Before
    public void setUp() {
        leaderboard = new Leaderboard();
    }

    @Test
    public void testAddEntry() {
        leaderboard.addEntry("Player1", 5000);
        String result = leaderboard.getLeaderboard();
        assertTrue(result.contains("Player1"));
        assertTrue(result.contains("5 seconds"));
    }

    @Test
    public void testGetLeaderboard() {
        leaderboard.addEntry("Player1", 5000);
        leaderboard.addEntry("Player2", 3000);
        String result = leaderboard.getLeaderboard();
        assertTrue(result.contains("Player1"));
        assertTrue(result.contains("Player2"));
        assertTrue(result.contains("3 seconds"));
        assertTrue(result.contains("5 seconds"));
    }
}
