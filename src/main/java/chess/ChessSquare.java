package chess;

import figures.ChessPiece;

public class ChessSquare {
    private final int x;
    private final int y;

    private ChessPiece chessPiece = null;

    public ChessSquare(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ChessPiece getChessPiece() {
        return chessPiece;
    }

    public void setChessPiece(ChessPiece chessPiece) {
        this.chessPiece = chessPiece;
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
