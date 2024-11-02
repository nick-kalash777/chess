package org.example;

import errors.*;
import figures.ChessPiece;
import figures.Pawn;

import java.util.ArrayList;

public class ChessBoard {
    private static ArrayList<ChessPiece> whitePieces;
    private static ArrayList<ChessPiece> blackPieces;
    public static boolean currentlyWhite = true;
    public static ArrayList<ChessSquare> board;

    public static void createBoard() {
        board = new ArrayList<>();
        for (int col = 1; col <= 8; col++) {
            for (int row = 1; row <= 8; row++) {
                ChessSquare square = new ChessSquare(col, row);
                board.add(square);
            }
        }

        whitePieces = new ArrayList<>();
        blackPieces = new ArrayList<>();
    }

    public static void addWhitePiece (ChessPiece piece) {
        whitePieces.add(piece);
    }

    public static void addBlackPiece (ChessPiece piece) {
        blackPieces.add(piece);
    }

    public static ChessPiece getPiece (int[] coordinates) throws NoPieceException, WrongColorException {
        ChessSquare square = getSquareByXY(coordinates[0], coordinates[1]);
        ChessPiece piece = square.getChessPiece();

        if (piece == null) throw new NoPieceException();
        if (currentlyWhite)
            if (!piece.isWhite()) throw new WrongColorException();
        if (!currentlyWhite)
            if (piece.isWhite()) throw new WrongColorException();

        return piece;
    }

    public static ChessSquare getSquareByXY(int column, int row) {
        int squareIndex = (column-1)*8 + row -1;
        return board.get(squareIndex);
    }

    public static void turn() {
        ConsoleInterface console = new ConsoleInterface();
        console.run();
    }

}
