package com.meterware.kata.reversi;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class BoardSquareMatcher extends TypeSafeMatcher<Board> {

    static BoardSquareMatcher hasBlackDisksAt(String... squares) {
        return new BoardSquareMatcher(DiskColor.black, squares);
    }

    static BoardSquareMatcher hasWhiteDisksAt(String... squares) {
        return new BoardSquareMatcher(DiskColor.white, squares);
    }

    public BoardSquareMatcher(DiskColor color, String[] squares) {
        this.color = color;
        this.squares = squares;
    }

    private DiskColor color;
    private String[] squares;

    @Override
    protected boolean matchesSafely(Board board) {
        for (String square : squares)
            if (board.getDiskAt(square) != color)
                return false;
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("board with ").appendText(color.toString()).appendValueList(" squares at [", ",", "]", squares);
    }

    @Override
    protected void describeMismatchSafely(Board board, Description mismatchDescription) {
        String firstMismatchedSquare = getFirstMismatchedSquare(board);
        mismatchDescription.appendText("square ").appendText(firstMismatchedSquare).appendText(" has value ").appendValue(board.getDiskAt(firstMismatchedSquare));
    }

    private String getFirstMismatchedSquare(Board board) {
        for (String square : squares)
            if (board.getDiskAt(square) != color)
                return square;
        throw new IllegalStateException("Should not reach this line");
    }

}
