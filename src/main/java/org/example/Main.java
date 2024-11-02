package org.example;

import figures.ChessPiece;
import figures.Horse;
import figures.Pawn;

public class Main {
    public static void main(String[] args) {
        ChessBoard.createBoard();
        Horse horse = new Horse(new ChessSquare(5, 4), true);
        //ChessPiece pawn2 = new Pawn(new ChessSquare(4, 4), true);
        ChessPiece pawn3 = new Pawn(new ChessSquare(6, 3), true);
        ChessPiece pawn4 = new Pawn(new ChessSquare(6, 5), true);
        ChessPiece pawn5 = new Pawn(new ChessSquare(4, 5), true);
        ChessPiece pawn6 = new Pawn(new ChessSquare(5, 5), true);
        ChessPiece pawn7 = new Pawn(new ChessSquare(4, 4), true);
        System.out.println(horse.isBlocked(-1, 2));

    }
}