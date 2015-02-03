package com.meterware.kata.reversi;

import java.util.*;

import static com.meterware.kata.reversi.CoordinatePair.fromSquareName;
import static com.meterware.kata.reversi.DiskColor.*;

/**
 * A reversi board. The rows are numbered 1-8, top to bottom, while the columns are lettered a-h, left to right.
 * A cell or a possible move is identified by row and column, so the upper-left cell is "1a".
 *
 * The first four moves must be in the center, for example, the classic Othello start is:
 *
 *     a b c d e f g h
 *   1 . . . . . . . .
 *   2 . . . . . . . .
 *   3 . . . . . . . .
 *   4 . . . B W . . .
 *   5 . . . W B . . .
 *   6 . . . . . . . .
 *   7 . . . . . . . .
 *   8 . . . . . . . .
 */
public class Board {
    private final static String[] INITIAL_MOVES = {"4d", "5d", "4e", "5e"};
    public static final int NUM_ROWS = 8;
    public static final int NUM_COLUMNS = 8;

    private Set<String> remainingInitialMoves = new HashSet<String>(Arrays.asList(INITIAL_MOVES));
    private DiskColor currentPlayer = black;
    private DiskColor[][] squares = new DiskColor[NUM_ROWS][NUM_COLUMNS];

    DiskColor getDiskAt(String square) {
        return getDiskAt(fromSquareName(square));
    }

    /**
     * Returns the identify of the player whose turn it is to move.
     */
    public DiskColor getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Returns an array of permitted moves. At the start of the game, each move must be to an unoccupied central
     * square. Once those are all occupied, each move must flip at least one of the opponent's disks.
     */
    public String[] getLegalMoves() {
        if (remainingInitialMoves.isEmpty())
            return computeLegalMoves();
        else
            return toArray(remainingInitialMoves);
    }

    private String[] toArray(Collection<String> strings) {
        return strings.toArray(new String[strings.size()]);
    }

    private String[] computeLegalMoves() {
        Set<String> legalMoves = new HashSet<String>();
        for (int i = 0; i < NUM_ROWS; i++)
            for (int j = 0; j < NUM_COLUMNS; j++)
                if (isOpponentDiskAt(i, j))
                    legalMoves.addAll(getMovesNextTo(i, j));
        return toArray(legalMoves);
    }

    private boolean isOpponentDiskAt(int i, int j) {
        return squares[i][j] == getOpponent();
    }

    private Collection<String> getMovesNextTo(int i, int j) {
        CoordinatePair coordinates = new CoordinatePair(i, j);
        Set<String> moves = new HashSet<String>();

        for (Direction direction : Direction.values()) {
            if (neighboringMoveFlipsCurrentDisk(coordinates, direction))
                moves.add(coordinates.getNeighbor(direction).toSquareName());
        }
        return moves;
    }

    private boolean neighboringMoveFlipsCurrentDisk(CoordinatePair coordinates, Direction direction) {
        return null == getDiskAt(coordinates.getNeighbor(direction)) && currentPlayer == getDiskAt(coordinates.getOppositeNeighbor(direction));
    }

    /**
     * Places a disk for the current player in the specified square.
     * @param squareName the name of the current square as a digit and a letter.
     * @throws java.lang.IllegalArgumentException if the specified square is not a legal move for the current player.
     */
    public void placeDisk(String squareName) {
        if (!isLegalMove(squareName))
            throw new IllegalArgumentException("'%' is not a legal move.".replace("%", squareName));

        setDisk(fromSquareName(squareName), currentPlayer);
        swapPlayer();
        remainingInitialMoves.remove(squareName);
    }

    private void setDisk(int row, int column, DiskColor color) {
        squares[row][column] = color;
    }

    private void setDisk(CoordinatePair coordinates, DiskColor color) {
        squares[coordinates.getRow()][coordinates.getColumn()] = color;
    }

    private boolean isLegalMove(String coordinates) {
        return Arrays.asList(getLegalMoves()).contains(coordinates);
    }

    private void swapPlayer() {
        currentPlayer = getOpponent();
    }

    private DiskColor getOpponent() {
        return currentPlayer == white ? black : white;
    }

    /**
     * Returns the color of the disk at the specified row and column. May be null if the square is unoccupied.
     * @param row the specified row
     * @param column the specified column
     */
    public DiskColor getDiskAt(int row, int column) {
        return squares[row][column];
    }

    DiskColor getDiskAt(CoordinatePair coordinates) {
        if (!coordinates.isValid()) return no_such_square;

        return squares[coordinates.getRow()][coordinates.getColumn()];
    }

    /**
     * Returns the square name for the specified row and column.
     * @param row the specified row
     * @param column the specified column
     */
    public static String toSquareName(int row, int column) {
        return String.valueOf(row + 1) + (char) ((int) 'a' + column);
    }

}
