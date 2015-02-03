package com.meterware.kata.reversi;

class CoordinatePair {
    private int row;
    private int column;

    static CoordinatePair fromSquareName(String squareName) {
        return new CoordinatePair(getRow(squareName), getColumn(squareName));
    }

    static private int getRow(String coordinates) {
        return coordinates.charAt(0) - '1';
    }

    static private int getColumn(String coordinates) {
        return coordinates.charAt(1) - 'a';
    }

    CoordinatePair(int row, int column) {
        this.row = row;
        this.column = column;
    }

    String toSquareName() {
        return String.valueOf(row + 1) + (char) ((int) 'a' + column);
    }

    int getRow() {
        return row;
    }

    int getColumn() {
        return column;
    }

    boolean isValid() {
        return 0 <= row && row < Board.NUM_ROWS && 0 <= column && column < Board.NUM_COLUMNS;
    }

    CoordinatePair getNeighbor(Direction direction) {
        return new CoordinatePair(row + direction.getRowOffset(), column + direction.getColumnOffset());
    }

    CoordinatePair getOppositeNeighbor(Direction direction) {
        return new CoordinatePair(row - direction.getRowOffset(), column - direction.getColumnOffset());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoordinatePair that = (CoordinatePair) o;
        return column == that.column && row == that.row;

    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        return result;
    }

    @Override
    public String toString() {
        return "CoordinatePair{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }
}
