package org.example;

import errors.*;
import figures.ChessPiece;

import java.util.ArrayList;
import java.util.HashMap;

public class ChessBoard {
    private static ArrayList<ChessPiece> whitePieces;
    private static ArrayList<ChessPiece> blackPieces;
    public static boolean currentlyWhite = true;
    public static HashMap<ChessSquare, ChessPiece> board;

    public static void createBoard() {
        board = new HashMap<>();
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessSquare square = new ChessSquare(row, col);
                board.put(square, null);
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

    public static ChessPiece getPiece (ChessSquare square) throws NoPieceException, WrongColorException {
        ChessPiece piece = board.get(square);

        if (piece == null) throw new NoPieceException();
        if (currentlyWhite)
            if (!piece.isWhite()) throw new WrongColorException();
        if (!currentlyWhite)
            if (piece.isWhite()) throw new WrongColorException();

        return piece;
    }

    public static void turn() {
        ConsoleInterface console = new ConsoleInterface();
        console.run();
    }
}
