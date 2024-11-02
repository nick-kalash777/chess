package org.example;

import figures.Horse;
import figures.Pawn;

public class Main {
    public static void main(String[] args) {
        ChessBoard.createBoard();
        Horse horse = new Horse(5, 4, true);
        new Pawn(4, 3, true);
        new Pawn(4, 4, true);
        new Pawn(3, 4, true);
        new Pawn(5, 5, true);
        new Pawn(5, 3, true);
        new Pawn(6, 5, true);
        new Pawn(6, 4, true);
        new Pawn(4, 3, true);
        try {
            horse.checkDirection(3, 5);
            horse.moveTo(3, 5);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        System.out.println(horse.getSquare());

    }
}