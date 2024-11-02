package org.example;

import figures.ChessPiece;

import java.util.ArrayList;
import java.util.List;

public class ChessSquare {
    private int x;
    private int y;

    private List<ChessPiece> threatenedBy = new ArrayList<ChessPiece>();

    public ChessSquare(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ChessSquare(int[] coordinates) {
        this.x = coordinates[0];
        this.y = coordinates[1];
    }

    public boolean isThreatened() {
        return !threatenedBy.isEmpty();
    }

    public void addThreat(ChessPiece piece) {
        threatenedBy.add(piece);
    }

    public void removeThreat(ChessPiece piece) {
        threatenedBy.remove(piece);
    }

    public boolean isThreatenedBy(ChessPiece piece) {
        return threatenedBy.contains(piece);
    }

    public int[] distance(ChessSquare chessSquare) {
        int[] distance = new int[2];
        distance[0] = chessSquare.x - this.x;
        distance[1] = chessSquare.y - this.y;

        return distance;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean equals(Object obj) {
        if (obj instanceof ChessSquare chessSquare) {
            return this.x == chessSquare.x && this.y == chessSquare.y;
        }
        return false;
    }

    public int hashCode() {
        return 31 * this.x + this.y;
    }
}
