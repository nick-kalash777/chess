package figures;

import errors.ChessMovementException;
import chess.ChessBoard;

public class King extends ChessPiece {

    public King(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
    }

    @Override
    public void tryToReach(int targetX, int targetY) throws ChessMovementException {
        int x = getCurrentSquare().getX();
        int y = getCurrentSquare().getY();

        int distanceX = targetX - x;
        int distanceY = targetY - y;

        if (Math.abs(distanceX) != Math.abs(distanceY) && (distanceX != 0 && distanceY != 0)) {
            throw new ChessMovementException("Король может двигаться только прямо/по диагонали.");
        }
        if (Math.abs(distanceX) > 1 || Math.abs(distanceY) > 1)
            throw new ChessMovementException("Король не может двигаться больше, чем на 1 клетку.");

        if (ChessBoard.isUnderThreat(targetX, targetY))
            throw new ChessMovementException("Король не может двигаться в клетку, которая находится под угрозой.");

        if (isPathBlocked(x, y, distanceX, distanceY))
            throw new ChessMovementException("Путь прегражден.");

    }

    public char getSymbol() {
        return 'K';
    }

    public String toString() {
        return "Король [" + getCurrentSquare().getX() + "," + getCurrentSquare().getY() + "]";
    }
}
