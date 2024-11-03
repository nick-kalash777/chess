package figures;

import errors.ChessMovementException;
import chess.ChessBoard;
import chess.ChessSquare;

public class Horse extends ChessPiece {

    public Horse(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
        addDirection(Directions.HORSE);
    }

    @Override
    public void tryToReach(int targetX, int targetY) throws ChessMovementException {
        int x = getCurrentSquare().getX();
        int y = getCurrentSquare().getY();

        int distanceX = targetX - x;
        int distanceY = targetY - y;

        if (Math.abs(distanceX) > 2 || Math.abs(distanceY) > 2 || Math.abs(distanceX) < 1 || Math.abs(distanceY) < 1) {
            throw new ChessMovementException("Конь может ходить только буквой Г.");
        }

        if (isPathBlocked(x, y, distanceX, distanceY))
            throw new ChessMovementException("Путь прегражден.");
    }



    @Override
    protected boolean isPathBlocked(int x, int y, int distanceX, int distanceY) {
        int encountered = 0;
        int xStep = Integer.compare(distanceX, 0);
        int yStep = Integer.compare(distanceY, 0);
        int absDistanceX = Math.abs(distanceX);
        int absDistanceY = Math.abs(distanceY);

        //начинаем идти по X, потом по Y
        encountered += traverseSquares(x, y, xStep, 0, absDistanceX);
        encountered += traverseSquares(x+ distanceX, y, 0, yStep, absDistanceY);

        //если так пройти не получилось, то сначала идем по Y, потом X
        if (encountered > 1) {
            encountered = 0;
            encountered += traverseSquares(x, y, 0, yStep, absDistanceY);
            encountered += traverseSquares(x, y+distanceY, xStep, 0, absDistanceX);
        }

        return encountered >= 2;
    }

    //проходит клетки и возвращает количество найденных на пути пешек
    private int traverseSquares(int x, int y, int xStep, int yStep, int steps) {
        int encounteredPieces = 0;
        for (int i = 0; i < steps; i++) {
            x += xStep;
            y += yStep;

            ChessSquare square = ChessBoard.getSquareByXY(x, y);

            if (square.getChessPiece() != null) {
                encounteredPieces++;
            }
        }
        return encounteredPieces;
    }

    @Override
    public String toString() {
        return "Конь [" + getCurrentSquare().getX() + "," + getCurrentSquare().getY() + "]";
    }


    public char getSymbol() {
        return 'H';
    }
}
