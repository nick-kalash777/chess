package figures;

import errors.ChessMovementException;
import chess.ChessBoard;

public class Pawn extends ChessPiece {

    //какое направление для пешки считается "вперед по Y" если белая
    private double forwardY = 1;

    public Pawn(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
        addDirection(Directions.STRAIGHT);
    }

    public char getSymbol() {
        return 'P';
    }

    @Override
    public void tryToReach(int targetX, int targetY) throws ChessMovementException {
        int x = getCurrentSquare().getX();
        int y = getCurrentSquare().getY();

        int distanceX = targetX - x;
        int distanceY = targetY - y;

        int movementLimit = (hasMoved()) ? 1 : 2;

        if (ChessBoard.getSquareByXY(targetX, targetY).getChessPiece() != null) {
            if (Math.abs(distanceX) != 1 || Math.abs(distanceY) != 1)
                throw new ChessMovementException("Пешка может бить только на 1 клетку по диагонали.");

        } else {
            if (distanceX != 0)
                throw new ChessMovementException("Пешка может ходить только прямо.");
            if (Math.abs(distanceY) > movementLimit) {
                throw new ChessMovementException("Пешка может ходить только на " + movementLimit + " клетку вперед.");
            }
        }
        if (isWhite()) {
            if (distanceY*forwardY < 0) throw new ChessMovementException("Пешка не может действовать назад.");
        } else {
            if (distanceY*forwardY > 0) throw new ChessMovementException("Пешка не может действовать назад.");
        }
        if (isPathBlocked(x, y, distanceX, distanceY))
            throw new ChessMovementException("Путь прегражден.");
    }

    @Override
    public String toString() {
        return "Пешка [" + getCurrentSquare().getX() + "," + getCurrentSquare().getY() + "]";
    }
}
