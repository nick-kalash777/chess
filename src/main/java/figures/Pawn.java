package figures;

import errors.WrongDirectionException;
import org.example.ChessBoard;
import org.example.ChessSquare;

public class Pawn extends ChessPiece {

    public Pawn(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
        addDirection(Directions.STRAIGHT);
    }

    public char getSymbol() {
        return 'P';
    }


    @Override
    public void checkDirection(int x, int y) throws WrongDirectionException {
        int absDistanceX = Math.abs(square.getX() - x);
        int absDistanceY = Math.abs(square.getY() - y);
        System.out.println(absDistanceX + " " + absDistanceY);
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
        return "Пешка [" + square.getX() + "," + square.getY() + "]";
    }
}
