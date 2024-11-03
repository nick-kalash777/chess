package figures;

import errors.ChessMovementException;

public class Queen extends ChessPiece {

    public Queen(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
        addDirection(Directions.STRAIGHT);
        addDirection(Directions.DIAGONAL);
    }

    public char getSymbol() {
        return 'Q';
    }

    @Override
    public void tryToReach(int targetX, int targetY) throws ChessMovementException {
        int x = getCurrentSquare().getX();
        int y = getCurrentSquare().getY();

        int distanceX = targetX - x;
        int distanceY = targetY - y;

        if (Math.abs(distanceX) != Math.abs(distanceY) && (distanceX != 0 && distanceY != 0)) {
            throw new ChessMovementException("Ферзь может двигаться только прямо/по диагонали.");
        }

        if (isPathBlocked(x, y, distanceX, distanceY))
            throw new ChessMovementException("Путь прегражден.");

    }

    public String toString() {
        return "Ферзь [" + getCurrentSquare().getX() + "," + getCurrentSquare().getY() + "]";
    }
}
