package com.gmail.tabertz.conway;

public class DeadCell extends Cell {

    @Override
    public boolean willBeAliveInNextGeneration() {
	int liveNeigbourCount = countLiveNeighbours();
	return liveNeigbourCount == 3;
    }

}
