package figures;

import org.example.ChessBoard;
import org.example.ChessSquare;

public class Horse extends ChessPiece {

    public Horse(ChessSquare s, boolean isWhite) {
        super(s, isWhite);
        addDirection(Directions.HORSE);
    }


    @Override
    public boolean isBlocked(int moveX, int moveY) {
        int encounteredPieces = 0;
        int xStep = Integer.compare(moveX, 0);
        int yStep = Integer.compare(moveY, 0);
        int distanceX = Math.abs(moveX);
        int distanceY = Math.abs(moveY);

        //начинаем идти по X, потом по Y
        encounteredPieces += traverseSquares(getX(), getY(), xStep, 0, distanceX);
        encounteredPieces += traverseSquares(getX()+moveX, getY(), 0, yStep, distanceY);

        //если так пройти не получилось, то сначала идем по Y, потом X
        if (encounteredPieces > 1) {
            encounteredPieces = 0;
            encounteredPieces += traverseSquares(getX(), getY(), 0, yStep, distanceY);
            encounteredPieces += traverseSquares(getX(), getY()+moveY, xStep, 0, distanceX);
        }

        if (encounteredPieces > 1) return true;

        return false;
    }

    private int traverseSquares(int x, int y, int xStep, int yStep, int steps) {
        int encounteredPieces = 0;
        for (int i = 0; i < steps; i++) {
            x += xStep;
            y += yStep;

            ChessSquare square = new ChessSquare(x, y);
            System.out.println(x + " " + y);

            if (ChessBoard.board.get(square) != null) encounteredPieces++;
        }
        return encounteredPieces;
    }

    @Override
    public String toString() {
        return "Конь [" + getX() + "," + getY() + "]";
    }


    public char getSymbol() {
        return 'H';
    }
}
