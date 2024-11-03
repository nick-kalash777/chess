package figures;

import errors.ChessMovementException;

public class Bishop extends ChessPiece {

    public Bishop(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
        addDirection(Directions.DIAGONAL);
    }

    @Override
    public void tryToReach(int targetX, int targetY) throws ChessMovementException {
        int x = getCurrentSquare().getX();
        int y = getCurrentSquare().getY();

        int distanceX = targetX - x;
        int distanceY = targetY - y;

        //checking absolute values in case we move in a negative axis
        if (Math.abs(distanceX) != Math.abs(distanceY))
            throw new ChessMovementException("Слон может ходить только по диагонали.");

        if (isPathBlocked(x, y, distanceX, distanceY))
            throw new ChessMovementException("Путь прегражден.");
    }

    public char getSymbol() {
        return 'B';
    }

    public String toString() {
        return "Слон [" + getCurrentSquare().getX() + "," + getCurrentSquare().getY() + "]";
    }
}
