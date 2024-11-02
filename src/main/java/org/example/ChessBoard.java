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

    public static void threatenSquares(ChessPiece piece, ChessPiece.Directions direction) {
        ChessSquare originSquare = piece.getSquare();
        switch(direction) {

            case STRAIGHT: {
                int[][] directionsXY = {
                        {1, 0},
                        {0, 1}
                };
                for (int[]directionXY : directionsXY) {
                    int deltaX = directionXY[0];
                    int deltaY = directionXY[1];
                    for (int x = deltaX, y = deltaY; x <= 8 && y <= 8; x += deltaX, y += deltaY) {
                        threatenSquare(piece, x, y);
                    }
                }
            }

            case DIAGONAL: {
                int posX = originSquare.getX();
                int posY = originSquare.getY();
                int[][] directionsXY = {
                        {1, 1},   // top right
                        {1, -1},  // bottom right
                        {-1, 1},  // top left
                        {-1, -1}  // bottom left
                };

                for (int[] directionXY : directionsXY) {
                    int deltaX = directionXY[0];
                    int deltaY = directionXY[1];

                    for (int x = posX + deltaX, y = posY + deltaY;
                         x >= 1 && x <= 8 && y >= 1 && y <= 8;
                         x += deltaX, y += deltaY) {
                        threatenSquare(piece, x, y);
                    }
                }
            }

            case HORSE: {
                int posX = originSquare.getX();
                int posY = originSquare.getY();

                int[][] directionsXY = {
                        {2, 1},
                        {1, 2},
                        {-1, 2},
                        {2, -1},
                        {-2, 1},
                        {1, -2},
                        {-1, -2},
                        {-2, -1},
                };

                for (int[] directionXY : directionsXY) {
                    threatenSquare(piece, posX+ directionXY[0], posY+ directionXY[1]);
                }

                System.out.println(piece.threatenedSquares);
            }
        }
    }

    public static void threatenSquares(Pawn pawn) {
        ChessSquare originSquare = pawn.getSquare();

        if (originSquare.getY() + 1 )
    }

    private static void threatenSquare (ChessPiece piece, int x, int y) {
        piece.addThreatenedSquare(getSquareByXY(x, y));
    }
}
