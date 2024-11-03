package chess;

import figures.ChessPiece;

public class ChessSquare {
    private int x;
    private int y;

    private ChessPiece chessPiece = null;

    public ChessSquare(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ChessSquare(int[] coordinates) {
        this.x = coordinates[0];
        this.y = coordinates[1];
    }

    public ChessPiece getChessPiece() {
        return chessPiece;
    }

    public void setChessPiece(ChessPiece chessPiece) {
        this.chessPiece = chessPiece;
    }

    public int[] distanceTo(int x, int y) {
        int[] distance = new int[2];
        distance[0] = x - this.x;
        distance[1] = y - this.y;

        return distance;
    }

    public int[] distanceFrom(int x, int y) {
        int[] distance = new int[2];
        distance[0] = this.x - x;
        distance[1] = this.y - y;

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

    public String toString() {
        return "Квадрат " + this.x + ", " + this.y;
    }


}