package com.meterware.kata.reversi;

import org.junit.Ignore;
import org.junit.Test;

import static com.meterware.kata.reversi.BoardSquareMatcher.hasBlackDisksAt;
import static com.meterware.kata.reversi.BoardSquareMatcher.hasWhiteDisksAt;
import static com.meterware.kata.reversi.DiskColor.black;
import static com.meterware.kata.reversi.DiskColor.white;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

public class BoardTest {

    Board board = new Board();

    @Test
    public void whenBoardCreated_blackGoesFirst() throws Exception {
        assertThat(board.getCurrentPlayer(), is(black));
    }

    @Test
    public void whenBoardCreated_legalMovesAreCenterSquares() {
        assertThat(board.getLegalMoves(), arrayContainingInAnyOrder("4d", "4e", "5d", "5e"));
    }

    @Test
    public void convertCoordinatesToSquareName() {
        assertThat(Board.toSquareName(0, 0), equalTo("1a"));
        assertThat(Board.toSquareName(2, 1), equalTo("3b"));
        assertThat(Board.toSquareName(7, 7), equalTo("8h"));
    }

    @Test
    public void whenBoardCreated_squaresAreEmpty() {
        for (int i = 0; i < Board.NUM_ROWS; i++)
            for (int j = 0; j < Board.NUM_COLUMNS; j++)
                assertThat(board.getDiskAt(i, j), nullValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenIllegalMove_throwsException() throws Exception {
        board.placeDisk("1a");
    }

    @Test
    public void afterFirstMove_whiteGoesNext() throws Exception {
        board.placeDisk("4d");

        assertThat(board.getCurrentPlayer(), is(white));
    }

    @Test
    public void afterFirstMove_boardHasPlacedDisk() throws Exception {
        board.placeDisk("4d");

        assertThat(board.getDiskAt(3, 3), equalTo(black));
    }

    @Test
    public void afterFirstMove_legalMovesAreEmptyCenterSquares() throws Exception {
        board.placeDisk("4d");

        assertThat(board.getLegalMoves(), arrayContainingInAnyOrder("4e", "5d", "5e"));
    }

    @Test
    public void afterCenterFilledDiagonally_blackHasFourChoices() throws Exception {
        placeDisks("4d", "4e", "5e", "5d");

        assertThat(board.getLegalMoves(), arrayContainingInAnyOrder("3e", "4f", "5c", "6d"));
    }

    private void placeDisks(String... squares) {
        for (String square : squares)
            board.placeDisk(square);
    }

    @Test
    public void afterCenterFilledHorizontally_blackHasFourChoices() throws Exception {
        placeDisks("4d", "5d", "4e", "5e");

        assertThat(board.getLegalMoves(), arrayContainingInAnyOrder("6c", "6d", "6e", "6f"));
    }

    @Test @Ignore("Not yet implemented")
    public void afterMove_disksAreFlipped() {
        placeDisks("4d", "5d", "4e", "5e", "6f");

        assertThat(board, hasBlackDisksAt("4d", "4e", "5e", "6f"));
        assertThat(board, hasWhiteDisksAt("5d"));
    }

    // todo test flip multiple disks in a line
    // todo test player turns skipped due to no move
    // todo test end of game detection


}
