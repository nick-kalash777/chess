package chess;

import errors.NoPieceException;
import errors.WrongColorException;
import errors.ChessMovementException;
import figures.ChessPiece;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleInterface {
    public void run() {
        while (true) {
            System.out.println("***************");
            if (ChessBoard.isCurrentlyWhite()) System.out.println("ХОД БЕЛЫХ");
            else System.out.println("ХОД ЧЕРНЫХ");
            System.out.println("ДОСТУПНЫЕ ДЕЙСТВИЯ:");
            System.out.println("1. Передвинуть фигуру.");
            System.out.println("2. Отобразить доску.");
            System.out.println("3. Совершить рокировку.");

            Scanner scanner = new Scanner(System.in).useDelimiter("\\n");
            ;

            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("Введите координаты вашей фигуры в формате X, Y");
                        int[] coordinates = readCoordinates(scanner);
                        ChessPiece piece = ChessBoard.getPiece(coordinates);
                        System.out.println("Ваша фигура: " + piece);
                        System.out.println("Она может двигаться в следующих направлениях:"
                                + " "
                                + piece.getDirectionsString());
                        System.out.println("Введите координаты, куда вы хотите передвинуть фигуру.");
                        coordinates = readCoordinates(scanner);
                        int x = coordinates[0];
                        int y = coordinates[1];
                        piece.tryMoveTo(x, y);
                        ChessBoard.isUnderThreat(x, y);
                        ChessBoard.turn();
                        showBoard();

                        break;

                    case 2:
                        showBoard();
                        break;
                    case 3:
                        ChessBoard.castling(scanner);
                        ChessBoard.turn();
                        break;
                }
            } catch (InputMismatchException _) {
                System.err.println("Используйте только отображенные выше цифры.");
            } catch (NoPieceException
                     | WrongColorException
                     | ChessMovementException e) {
                System.err.println(e.getMessage());
            } catch (IndexOutOfBoundsException _) {
                System.err.println("Используйте координаты только в диапазоне 1-8.");
            } catch (NumberFormatException _) {
                System.err.println("Перечисляйте координаты через запятую.");
            }
        }
    }

    private void showBoard() {
        System.out.println("  a|b|c|d|e|f|g|h|");
        for (int i = 1; i < 9; i++) {
            System.out.print(i + " ");
            for (int j = 1; j < 9; j++) {
                ChessSquare square = ChessBoard.getSquareByXY(j, i);
                if (square.getChessPiece() != null) System.out.print(square.getChessPiece().getSymbol() + "|");
                else System.out.print(" |");
            }
            System.out.println();
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
