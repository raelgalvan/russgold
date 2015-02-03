package com.meterware.kata.reversi;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CoordinatePairTest {

    @Test
    public void givenCoordinate_computeItsNeighbor() {
        assertThat(new CoordinatePair(0, 0).getNeighbor(Direction.right), equalTo(new CoordinatePair(0, 1)));
        assertThat(new CoordinatePair(7, 0).getNeighbor(Direction.upRight), equalTo(new CoordinatePair(6, 1)));
        assertThat(new CoordinatePair(3, 7).getNeighbor(Direction.downLeft), equalTo(new CoordinatePair(4, 6)));
    }

    @Test
    public void givenCoordinate_computeItsNeighborInTheOppositeDirection() {
        assertThat(new CoordinatePair(2, 3).getOppositeNeighbor(Direction.left), equalTo(new CoordinatePair(2, 4)));
        assertThat(new CoordinatePair(6, 1).getOppositeNeighbor(Direction.downRight), equalTo(new CoordinatePair(5, 0)));
        assertThat(new CoordinatePair(4, 4).getOppositeNeighbor(Direction.up), equalTo(new CoordinatePair(5, 4)));
    }
}
