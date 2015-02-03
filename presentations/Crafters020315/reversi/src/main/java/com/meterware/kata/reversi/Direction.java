package com.meterware.kata.reversi;

enum Direction {
    upLeft, up, upRight, left, right, downLeft, down, downRight;

    int getColumnOffset() {
        switch (this) {
            case left: case upLeft: case downLeft:
                return -1;
            case right: case upRight: case downRight:
                return +1;
            default:
                return 0;
        }
    }

    int getRowOffset() {
        switch (this) {
            case upLeft: case upRight: case up:
                return -1;
            case downLeft: case down: case downRight:
                return +1;
            default:
                return 0;
        }
    }
}
