package com.gmail.tabertz.conway;

import java.util.ArrayList;
import java.util.List;

public abstract class Cell implements WillBeAliveInNextGeneration {

    protected List<Cell> neighbours = new ArrayList<>();

    @Override
    public abstract boolean willBeAliveInNextGeneration();

    public void setNeighbours(List<Cell> neighbours) {
	this.neighbours = neighbours;
    }

    protected int countLiveNeighbours() {
	int liveNeigbourCount = 0;
	for (Cell cell : neighbours) {
	    if (cell instanceof LiveCell) {
		liveNeigbourCount++;
	    }
	}
	return liveNeigbourCount;
    }
}
