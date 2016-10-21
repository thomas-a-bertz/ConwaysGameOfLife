import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/* -- THE RULEZ --
 * 
 * 1. UNDERPOPULATION
 * Any live cell with fewer than two live neighbours dies.
 * 
 * 2. OVERCROWDING
 * Any live cell with more than three live neighbours dies.
 * 
 * 3. LIVE-ON
 * Any live cell with two or three live neighbours lives on to the next generation.
 * 
 * 4. REPRODUCTION
 * Any dead cell with exactly three live neighbours becomes a live cell.
 */

public class BoardTest {

    private String expected;
    private String actual;
    private int[][] neighborsExpected;
    private Board board;

    @Before
    public void setUp() {
	expected = "";
	actual = "";
	neighborsExpected = null;
	board = null;
    }

    @Test
    public void testCreateInitialBoard() throws Exception {
	board = new Board("_____\n" + "_____\n" + "_____\n" + "_____\n"
		+ "_____\n");
	expected = "_____\n" + "_____\n" + "_____\n" + "_____\n" + "_____\n";
	actual = board.toString();
	assertEquals(expected, actual);
    }

    @Test
    public void testNextPopulation() throws Exception {
	board = new Board("_____\n" + "_____\n" + "_____\n" + "_____\n"
		+ "_____\n");
	expected = "_____\n" + "_____\n" + "_____\n" + "_____\n" + "_____\n";
	board.calculateNext();
	actual = board.toString();
	assertEquals(expected, actual);
    }

    @Test
    public void testRule1Underpopulation() throws Exception {
	board = new Board("_____\n" + "_____\n" + "___X_\n" + "_____\n"
		+ "_____\n");
	expected = "_____\n" + "_____\n" + "_____\n" + "_____\n" + "_____\n";
	board.calculateNext();
	actual = board.toString();
	assertEquals(expected, actual);
    }

    @Test
    public void testRule2Overcrowding() throws Exception {
	board = new Board("_____\n" + "___X_\n" + "__XXX\n" + "___X_\n"
		+ "_____\n");
	expected = "_____\n" + "__XXX\n" + "__X_X\n" + "__XXX\n" + "_____\n";
	board.calculateNext();
	actual = board.toString();
	assertEquals(expected, actual);
    }

    @Test
    public void testRule3EveryDayLife() throws Exception {
	board = new Board("_____\n" + "_____\n" + "__XXX\n" + "_____\n"
		+ "_____\n");
	expected = "_____\n" + "___X_\n" + "___X_\n" + "___X_\n" + "_____\n";
	board.calculateNext();
	actual = board.toString();
	assertEquals(expected, actual);
    }

    @Test
    public void testRule4ReproductionWithoutUI() {
	board = new Board(new boolean[][] { { false, false, false },
		{ false, true, false }, { true, false, true },
		{ false, false, false } });
	Board expectedBoard = new Board(new boolean[][] {
		{ false, false, false }, { false, true, false },
		{ false, true, false }, { false, false, false } });
	board.calculateNext();
	assertEquals(expectedBoard, board);
    }

    @Test
    public void testCalculateNeighborBoard() {
	board = new Board(new boolean[][] {
		{ false, false, false, false, false },
		{ false, false, true, false, false },
		{ false, true, false, true, false },
		{ false, false, false, false, false },
		{ false, false, false, false, false } });
	neighborsExpected = new int[][] { { 0, 1, 1, 1, 0 }, { 1, 2, 2, 2, 1 },
		{ 1, 1, 3, 1, 1 }, { 1, 1, 2, 1, 1 }, { 0, 0, 0, 0, 0 } };
	assertArrayEquals(neighborsExpected, board.calculateNeighborBoard());
    }

    @Test
    public void testSame() {
	board = new Board(new boolean[][] { { false, false, false },
		{ false, true, false }, { true, false, true },
		{ false, false, false } });
	Board expectedBoard = new Board(new boolean[][] {
		{ false, false, false }, { false, true, false },
		{ true, false, true }, { false, false, false } });
	assertEquals(expectedBoard, board);
    }
}
