package figures;

import errors.PathBlockedException;
import errors.WrongDirectionException;
import org.example.ChessBoard;
import org.example.ChessSquare;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public abstract class ChessPiece {
    private boolean white;
    protected boolean firstMove = true;
    protected ChessSquare square = null;
    public List<ChessSquare> threatenedSquares = new ArrayList<>();

    public void addThreatenedSquare(ChessSquare square) {
        threatenedSquares.add(square);
    }

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
        this.square = ChessBoard.getSquareByXY(x, y);
        this.white = white;
        this.square.setChessPiece(this);
        if (white) ChessBoard.addWhitePiece(this);
        else ChessBoard.addBlackPiece(this);
    }

    public String getColor() {
        if (white) return "Белая фигура";
        return "Черная фигура";
    }

    public boolean isWhite() {
        return white;
    }

    public void checkDirection(int x, int y) throws WrongDirectionException {
        int absDistanceX = Math.abs(square.getX() - x);
        int absDistanceY = Math.abs(square.getY() - y);
        if ((absDistanceX != 0 && absDistanceY == 0) || (absDistanceX == 0 && absDistanceY != 0)) {
            if (!directions.contains(Directions.STRAIGHT)) throw new WrongDirectionException();
        } else if (absDistanceX == absDistanceY) {
            if (!directions.contains(Directions.DIAGONAL)) throw new WrongDirectionException();
        } else if ((absDistanceX == 2 && absDistanceY == 1)
                || (absDistanceX == 1 && absDistanceY == 2)) {
            if (!directions.contains(Directions.HORSE)) throw new WrongDirectionException();
        } else {
            throw new WrongDirectionException();
        }
    }

    public void moveTo(int targetX, int targetY) throws PathBlockedException {
        ChessSquare target = ChessBoard.getSquareByXY(targetX, targetY);
        int[] distance = square.distance(targetX, targetY);
        if (isBlocked(distance[0], distance[1])) throw new PathBlockedException();
        firstMove = false;
        square.setChessPiece(null);
        target.setChessPiece(this);
        square = target;
        threatenSquares();
    }

    protected void threatenSquares() {
        if (directions.contains(Directions.STRAIGHT)) {
            ChessBoard.threatenSquares(this, Directions.STRAIGHT);
        }
        if (directions.contains(Directions.DIAGONAL)) {
            ChessBoard.threatenSquares(this, Directions.DIAGONAL);
        }
        if (directions.contains(Directions.HORSE)) {
            ChessBoard.threatenSquares(this, Directions.HORSE);
        }
    }

    protected boolean isBlocked(int moveX, int moveY) {
        int xStep = Integer.compare(moveX, 0);
        int yStep = Integer.compare(moveY, 0);
        for (int x = xStep, y = yStep;
             Math.abs(x) <= Math.abs(moveX) && Math.abs(y) <= Math.abs(moveY);
             x += xStep, y += yStep) {
            ChessSquare traversingSquare = ChessBoard.getSquareByXY(square.getX() + x, square.getY() + y);
            if (traversingSquare.getChessPiece() != null) {
                return true;
            }
        }
        return false;
    }

    public ChessSquare getSquare() {
        return square;
    }

    public abstract char getSymbol();

    public abstract String toString();
}
