package errors;

public class NoPieceException extends Exception {
    public NoPieceException() {
        super("В этом квадрате отсутствует фигура. Попробуйте еще раз.");
    }
}
