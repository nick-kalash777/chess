package org.example;

import errors.NoPieceException;
import errors.PathBlockedException;
import errors.WrongColorException;
import errors.WrongDirectionException;
import figures.ChessPiece;

import java.util.Scanner;

public class ConsoleInterface {
    public void run() {
        System.out.println("*** КОНСОЛЬНЫЙ ИНТЕРФЕЙС ***");
        System.out.println("ДОСТУПНЫЕ ДЕЙСТВИЯ:");
        System.out.println("1. Передвинуть фигуру.");
        System.out.println("2. Атаковать вражескую фигуру.");
        System.out.println("3. Отобразить все фигуры.");
        System.out.println("4. Совершить рокировку.");

        Scanner scanner = new Scanner(System.in).useDelimiter("\\n");;
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.println("Введите координаты вашей фигуры в формате X, Y");
                int[] coordinates = readCoordinates(scanner);
                try {
                    ChessPiece piece = ChessBoard.getPiece(coordinates);
                    System.out.println("Ваша фигура: " + piece);
                    System.out.println("Она может двигаться в следующих направлениях:"
                            + " "
                            + piece.getDirectionsString());
                    System.out.println("Введите координаты, куда вы хотите передвинуть фигуру.");
                    coordinates = readCoordinates(scanner);
                    int x = coordinates[0];
                    int y = coordinates[1];
                    piece.checkDirection(x, y);
                    piece.moveTo(x, y);
                    System.out.println(piece);

                } catch (NoPieceException
                         | WrongColorException
                         | PathBlockedException
                         | WrongDirectionException e) {
                    System.err.println(e.getMessage());
                    //рестарт
                }

        }

    }

    public int[] readCoordinates(Scanner scanner) {
        String coordinateString = scanner.next();
        String[] coordinates = coordinateString.split(",");
        int x = Integer.parseInt(coordinates[0].trim());
        int y = Integer.parseInt(coordinates[1].trim());
        return new int[]{x, y};
    }
}
