package application;

import application.core.Game;
import application.logger.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

/**
 * Test class for Main.java.
 * @author Arthur Breurkes.
 */
@SuppressWarnings({
        "checkstyle:magicnumber",
        "checkstyle:visibilitymodifier"
})
public class MainTest {
    private Main testMain;

    @Mock
    public final Logger testLogger = mock(Logger.class);

    /**
     * Setup variables for the test process.
     * @throws Exception possible Exception.
     */
    @Before
    public void setUp() throws Exception {
        testMain = new Main("Test");
    }

    /**
     * Test whether Main was initialized correctly.
     * @throws Exception possible Exception.
     */
    @Test
    public void testMainNotNull() throws Exception {
        assertNotNull(testMain);
    }

    /**
     * Test whether newGame() works correctly.
     * @throws Exception possible Exception.
     */
    @Test
    public void testNewGame() throws Exception {
        testMain.tGame = null;
        testMain.newGame(1);

        assertNotNull(testMain.getGame());
    }

    /**
     * Test whether getGame() returns the correct Game.
     * @throws Exception possible Exception.
     */
    @Test
    public void testGetGame() throws Exception {
        Game testGame = new Game(0, 0, testLogger, 1);
        testMain.tGame = testGame;

        assertEquals(testGame, testMain.getGame());
    }

    /**
     * Test whether changeDifficulty() works correctly.
     * @throws Exception possible Exception.
     */
    @Test
    public void testChangeDifficulty() throws Exception {
        testMain.DIFFICULTY = 3;
        testMain.changeDifficulty();

        assertEquals(1, testMain.DIFFICULTY);
    }
}