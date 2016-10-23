package com.gmail.tabertz.conway;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.gmail.tabertz.conway.Cell;
import com.gmail.tabertz.conway.DeadCell;
import com.gmail.tabertz.conway.LiveCell;

/*
 * 1. UNDERPOPULATION
 * Any live cell with fewer than two live neighbours dies.
 * 
 * 2. OVERCROWDING
 * Any live cell with more than three live neighbours dies.
 * 
 * 3. SURVIVAL
 * Any live cell with two or three live neighbours lives on to the next generation.
 * 
 * 4. REPRODUCTION
 * Any dead cell with exactly three live neighbours becomes a live cell.
 */

public class CellTest {

    private Cell aLiveCell;
    private Cell aDeadCell;

    @Before
    public void setUp() {
	aLiveCell = new LiveCell();
	aDeadCell = new DeadCell();
    }

    @Test
    public void testUnderpopulationZeroLiveNeighbours() throws Exception {
	aLiveCell.setNeighbours(createNeighbours(8, 0));
	assertEquals(false, aLiveCell.willBeAliveInNextGeneration());
    }

    @Test
    public void testUnderpopulationOneLiveNeighbour() throws Exception {
	aLiveCell.setNeighbours(createNeighbours(7, 1));
	assertEquals(false, aLiveCell.willBeAliveInNextGeneration());
    }

    @Test
    public void testOvercrowdingFourLiveNeighbours() throws Exception {
	aLiveCell.setNeighbours(createNeighbours(4, 4));
	assertEquals(false, aLiveCell.willBeAliveInNextGeneration());
    }

    @Test
    public void testOvercrowdingFiveLiveNeighbours() throws Exception {
	aLiveCell.setNeighbours(createNeighbours(3, 5));
	assertEquals(false, aLiveCell.willBeAliveInNextGeneration());
    }

    @Test
    public void testOvercrowdingSixLiveNeighbours() throws Exception {
	aLiveCell.setNeighbours(createNeighbours(2, 6));
	assertEquals(false, aLiveCell.willBeAliveInNextGeneration());
    }

    @Test
    public void testOvercrowdingSevenLiveNeighbours() throws Exception {
	aLiveCell.setNeighbours(createNeighbours(1, 7));
	assertEquals(false, aLiveCell.willBeAliveInNextGeneration());
    }

    @Test
    public void testOvercrowdingEightLiveNeighbours() throws Exception {
	aLiveCell.setNeighbours(createNeighbours(0, 8));
	assertEquals(false, aLiveCell.willBeAliveInNextGeneration());
    }

    @Test
    public void testSurvivalTwoLiveNeighbours() throws Exception {
	aLiveCell.setNeighbours(createNeighbours(6, 2));
	assertEquals(true, aLiveCell.willBeAliveInNextGeneration());
    }

    @Test
    public void testSurvivalThreeLiveNeighbours() throws Exception {
	aLiveCell.setNeighbours(createNeighbours(5, 3));
	assertEquals(true, aLiveCell.willBeAliveInNextGeneration());
    }

    @Test
    public void testReproductionThreeLiveNeighbours() throws Exception {
	aDeadCell.setNeighbours(createNeighbours(5, 3));
	assertEquals(true, aDeadCell.willBeAliveInNextGeneration());
    }

    private List<Cell> createNeighbours(int dead, int alive) throws Exception {
	if (dead < 0 || alive < 0) {
	    throw new Exception("CellTest: Parameter out of bounds");
	}
	List<Cell> neighbours = new ArrayList<>();
	for (int i = 0; i < dead; i++) {
	    neighbours.add(new DeadCell());
	}
	for (int j = 0; j < alive; j++) {
	    neighbours.add(new LiveCell());
	}
	return neighbours;
    }
}
