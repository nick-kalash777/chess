package chess;

import errors.*;
import figures.ChessPiece;
import figures.King;
import figures.Rook;

import java.util.ArrayList;
import java.util.Scanner;

public class ChessBoard {
    private static boolean currentlyWhite = true;
    public static ArrayList<ChessSquare> board;
    private static ArrayList<ChessPiece> allPieces;

    public static void createBoard() {
        board = new ArrayList<>();
        for (int x = 1; x <= 8; x++) {
            for (int y = 1; y <= 8; y++) {
                ChessSquare square = new ChessSquare(x, y);
                board.add(square);
            }
        }

        createPieces();
    }

    public static void createPieces() {
        allPieces = new ArrayList<>();

        //Белые
        for (int x = 1; x < 9; x++) {
            new figures.Pawn(x, 2, true);
        }
        new figures.Rook(1, 1, true);
        new figures.Horse(2, 1, true);
        new figures.Bishop(3, 1, true);
        new figures.King(4, 1, true);
        new figures.Queen(5, 1, true);
        new figures.Bishop(6, 1, true);
        new figures.Horse(7, 1, true);
        new figures.Rook(8, 1, true);

        //черные
        for (int x = 1; x < 9; x++) {
            new figures.Pawn(x, 7, false);
        }
        new figures.Rook(1, 8, false);
        new figures.Horse(2, 8, false);
        new figures.Bishop(3, 8, false);
        new figures.King(4, 8, false);
        new figures.Queen(5, 8, false);
        new figures.Bishop(6, 8, false);
        new figures.Horse(7, 8, false);
        new figures.Rook(8, 8, false);


    }

    public static void addPiece (ChessPiece piece) {
        allPieces.add(piece);
    }

    public static void removePiece (ChessPiece piece) {
        allPieces.remove(piece);
    }

    public static ChessPiece getPiece (int[] coordinates) throws NoPieceException, WrongColorException {

        ChessSquare square = getSquareByXY(coordinates[0], coordinates[1]);
        ChessPiece piece = square.getChessPiece();

        if (piece == null) throw new NoPieceException();
        if (isCurrentlyWhite())
            if (!piece.isWhite()) throw new WrongColorException();
        if (!isCurrentlyWhite())
            if (piece.isWhite()) throw new WrongColorException();

        return piece;
    }

    public static void moveToPosition (int pieceX, int pieceY, int targetX, int targetY)
            throws NoPieceException, WrongColorException, ChessMovementException {
        ChessPiece piece = getPiece(new int[] {pieceX, pieceY});
        piece.tryMoveTo(targetX, targetY);
        turn();
    }

    public static String nowPlayerColor() {
        if (currentlyWhite) return "Белый";
        return "Черный";
    }

    public static void printBoard() {
        //на настоящей шахматной доске будут буквы, но тут для удобства
        System.out.println("  1 2 3 4 5 6 7 8 ");
        for (int i = 1; i < 9; i++) {
            System.out.print(i + " ");
            for (int j = 1; j < 9; j++) {
                ChessSquare square = ChessBoard.getSquareByXY(j, i);
                ChessPiece piece = square.getChessPiece();
                if (piece != null) {
                    if (piece.isWhite()) System.out.print(piece.getSymbol() + "|");
                    else System.out.print(Character.toLowerCase(piece.getSymbol()) + "|");
                }
                else System.out.print(" |");
            }
            System.out.println();
        }
    }


    public static boolean isCurrentlyWhite() {
        return currentlyWhite;
    }

    public static ChessSquare getSquareByXY(int x, int y) {
        int squareIndex = (x - 1) * 8 + y -1;
        return board.get(squareIndex);
    }

    public static ArrayList<ChessPiece> getAllPieces() {
        return allPieces;
    }

    public static boolean isUnderAttack(int targetX, int targetY) {
        for (ChessPiece piece : getAllPieces()) {
            try {
                if (piece.getCurrentSquare() == getSquareByXY(targetX, targetY)) continue;
                if (isCurrentlyWhite() == piece.isWhite())
                    continue;
                piece.tryToReach(targetX, targetY);
                return true;
            } catch (ChessMovementException | IndexOutOfBoundsException _) {}
            //CME checks if piece can reach the checked currentSquare,
            //IOOBE ensures it doesn't go out of the board trying to do so
        }
        return false;
    }

    public static void castling(Scanner scanner) throws ChessMovementException {
        King king = null;
        Rook[] rooks = {null, null};
        Rook finalRook = null;

        int kingSteps = 2;

        for (ChessPiece piece : getAllPieces()) {
            if (!piece.isWhite() == currentlyWhite) continue;
            try {
                if (king == null && piece.getSymbol() == 'K') {
                    king = (King) piece;
                }else if (rooks[0] == null && piece.getSymbol() == 'R') {
                    rooks[0] = (Rook) piece;
                }else if (rooks[1] == null && piece.getSymbol() == 'R') {
                    rooks[1] = (Rook) piece;
                    break;
                }
            } catch (ClassCastException e) {
                System.err.println("Каким-то образом вы сломали рокировку. Поздравляю!");
            }
        }
        if (king == null) throw new ChessMovementException("Что-то пошло серьезно не так. Почему у вас нет короля?");
        if (king.hasMoved())
            throw new ChessMovementException("Нельзя совершать рокировку, когда король уже сделал ход.");

        int kingX = king.getCurrentSquare().getX();
        int currentY = king.getCurrentSquare().getY();

        if (isUnderAttack(kingX, currentY))
            throw new ChessMovementException("Нельзя совершать рокировку, пока объявлен шах.");

        if (rooks[0] != null && rooks[1] != null) {
            System.out.println("С какой ладьей вы хотите сделать рокировку?");
            System.out.println("1. " + rooks[0]);
            System.out.println("2. " + rooks[1]);

            while (finalRook == null) {
                try {
                    int rookIndex = scanner.nextInt() - 1;
                    finalRook = rooks[rookIndex];
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Неправильный номер. Используйте 1 или 2.");
                }
            }
        } else if (rooks[0] != null) finalRook = rooks[0];
        else if (rooks[1] != null) finalRook = rooks[1];
        else throw new ChessMovementException("У вас нет ладьи, чтобы сделать рокировку.");

        if (finalRook.hasMoved())
            throw new ChessMovementException("Нельзя совершать рокировку, когда ладья уже сделала ход.");

        int rookX = finalRook.getCurrentSquare().getX();
        finalRook.tryToReach(kingX, currentY);

        //если ладья правее
        if (rookX > kingX) {
            king.moveTo(kingX + kingSteps, currentY);
            finalRook.moveTo(king.getCurrentSquare().getX()-1, currentY);
        } else {
            king.moveTo(kingX - kingSteps, currentY);
            finalRook.moveTo(king.getCurrentSquare().getX()+1, currentY);
        }
    }


    public static void turn() {
        currentlyWhite = !currentlyWhite;
    }

}
