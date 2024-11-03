package chess;

public class Main {
    public static void main(String[] args) {
        ChessBoard.createBoard();
        ConsoleInterface console = new ConsoleInterface();
        console.run();

    }
}