package figures;

import errors.ChessMovementException;
import chess.ChessBoard;
import chess.ChessSquare;

import java.util.EnumSet;

public abstract class ChessPiece {
    private boolean white;
    private boolean firstMove = true;
    private ChessSquare currentSquare = null;

    public enum Directions {
        STRAIGHT,
        DIAGONAL,
        HORSE;
    }

    protected EnumSet<Directions> directions = EnumSet.noneOf(Directions.class);

    public boolean hasDirection(Directions direction) {
        return directions.contains(direction);
    }

    public void addDirection(Directions direction) {
        directions.add(direction);
    }

    public String getDirectionsString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (directions.contains(Directions.STRAIGHT)) {
            stringBuilder.append("ПРЯМО ");
        }
        if (directions.contains(Directions.DIAGONAL)) {
            stringBuilder.append("ПО ДИАГОНАЛИ ");
        }
        if (directions.contains(Directions.HORSE)) {
            stringBuilder.append("БУКВОЙ Г ");
        }
        return stringBuilder.toString();
    }

    public ChessPiece (int x, int y, boolean white) {
        this.currentSquare = ChessBoard.getSquareByXY(x, y);
        this.white = white;
        this.currentSquare.setChessPiece(this);
        ChessBoard.addPiece(this);
    }

    public String getColor() {
        if (white) return "Белая фигура";
        return "Черная фигура";
    }

    public boolean isWhite() {
        return white;
    }

    public void destroy() {
        ChessBoard.removePiece(this);
        currentSquare.setChessPiece(null);

    }

    public ChessSquare getCurrentSquare() {
        return currentSquare;
    }

    public void setCurrentSquare(ChessSquare square) {
        this.currentSquare = square;
    }

    public void tryMoveTo(int targetX, int targetY) throws ChessMovementException {
        if (getCurrentSquare().getX() == targetX && getCurrentSquare().getY() == targetY) {
            throw new ChessMovementException("Фигура уже находится в этой клетке.");
        }
        tryToReach(targetX, targetY);

        ChessSquare target = ChessBoard.getSquareByXY(targetX, targetY);
        ChessPiece targetPiece = target.getChessPiece();
        if (targetPiece != null) {
            if (targetPiece.isWhite() == isWhite())
                throw new ChessMovementException("Вы не можете атаковать собственную фигуру.");
            targetPiece.destroy();
        }

        moveTo(target);
    }

    //ignores all restriction on movement. Don't call unless sure that no pieces in the targetSquare
    public void moveTo(ChessSquare target) {
        currentSquare.setChessPiece(null);
        setCurrentSquare(target);
        target.setChessPiece(this);
        firstMove();
    }

    public void moveTo(int targetX, int targetY) {
        ChessSquare target = ChessBoard.getSquareByXY(targetX, targetY);
        moveTo(target);
    }

    protected boolean isPathBlocked(int x, int y, int distanceX, int distanceY) {
        int stepX = Integer.compare(distanceX, 0);
        int stepY = Integer.compare(distanceY, 0);

        int steps = Math.max(Math.abs(distanceX), Math.abs(distanceY));

        for (int i = 1; i < steps; i++, x += stepX, y += stepY) {
            int newX = x + stepX;
            int newY = y + stepY;
            ChessSquare newSquare = ChessBoard.getSquareByXY(newX, newY);
            if (newSquare.getChessPiece() != null) {
                return true;
            }
        }
        return false;
    }

    public void firstMove() {
        firstMove = false;
    }

    public boolean hasMoved() {
        return !firstMove;
    }

    public abstract void tryToReach(int targetX, int targetY) throws ChessMovementException;

    //used in ChessBoard.castling() to determine piece type
    public abstract char getSymbol();

    public abstract String toString();
}
