package figures;

import errors.WrongDirectionException;
import org.example.ChessSquare;

public class Pawn extends ChessPiece {

    public Pawn(ChessSquare s, boolean isWhite) {
        super(s, isWhite);
        addDirection(Directions.STRAIGHT);
    }

    public char getSymbol() {
        return 'P';
    }

    @Override
    public void checkDirection(int[] coordinates) throws WrongDirectionException {
        int absDistanceX = Math.abs(getX() - coordinates[0]);
        int absDistanceY = Math.abs(getY() - coordinates[1]);
        if (absDistanceY != 0) throw new WrongDirectionException();
        switch(absDistanceX) {
            case 2:
                if (!firstMove) throw new WrongDirectionException();
            case 1:
                break;
            default: throw new WrongDirectionException();
        }
    }

    @Override
    public String toString() {
        return "Пешка [" + getX() + "," + getY() + "]";
    }
}
