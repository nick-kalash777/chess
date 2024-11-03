package figures;

import errors.ChessMovementException;

public class Rook extends ChessPiece {

    public Rook(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
    }

    @Override
    public void tryToReach(int targetX, int targetY) throws ChessMovementException {
        int x = getCurrentSquare().getX();
        int y = getCurrentSquare().getY();

        int distanceX = targetX - x;
        int distanceY = targetY - y;

        //checking absolute values in case we move in a negative axis
        if (Math.abs(distanceX) != 0 && Math.abs(distanceY) != 0)
            throw new ChessMovementException("Ладья может ходить только прямо.");

        if (isPathBlocked(x, y, distanceX, distanceY))
            throw new ChessMovementException("Путь прегражден.");
    }

    public char getSymbol() {
        return 'R';
    }

    public String toString() {
        return "Ладья [" + getCurrentSquare().getX() + "," + getCurrentSquare().getY() + "]";
    }
}
