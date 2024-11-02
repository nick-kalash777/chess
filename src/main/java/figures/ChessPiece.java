package figures;

import errors.PathBlockedException;
import errors.WrongDirectionException;
import org.example.ChessBoard;
import org.example.ChessSquare;

import java.util.EnumSet;

public abstract class ChessPiece {
    private ChessSquare square;
    private boolean white;
    protected boolean firstMove = true;

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

    public void checkDirection(int[] coordinates) throws WrongDirectionException {
        int x = coordinates[0];
        int y = coordinates[1];
        int absDistanceX = Math.abs(getX() - x);
        int absDistanceY = Math.abs(getY() - y);
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

    public ChessPiece (ChessSquare s, boolean white) {
        this.square = s;
        this.white = white;
        ChessBoard.board.put(square, this);
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

    public ChessSquare getSquare() {
        return square;
    }

    public int getX() {
        return square.getX();
    }

    public int getY() {
        return square.getY();
    }

    public void move(ChessSquare target) throws PathBlockedException {
        int[] distance = square.distance(target);
        if (isBlocked(distance[0], distance[1])) throw new PathBlockedException();
        firstMove = false;
        ChessBoard.board.remove(square);
        square = target;
        ChessBoard.board.put(target, this);
    }

    protected boolean isBlocked(int moveX, int moveY) {
        int xStep = Integer.compare(moveX, 0);
        int yStep = Integer.compare(moveY, 0);
        for (int x = xStep, y = yStep;
             Math.abs(x) <= Math.abs(moveX) && Math.abs(y) <= Math.abs(moveY);
             x += xStep, y += yStep) {
            ChessSquare traversingSquare = new ChessSquare(square.getX() + x, square.getY() + y);
            if (ChessBoard.board.get(traversingSquare) != null) {
                return true;
            }
        }
        return false;
    }

    protected void threatenSquares() {
        for (ChessSquare square : ChessBoard.board.keySet()) {
            if (square.isThreatenedBy(this)) square.removeThreat(this);
        }
        if (directions.contains(Directions.STRAIGHT)) {

        }
    }

    public abstract char getSymbol();

    public abstract String toString();
}
