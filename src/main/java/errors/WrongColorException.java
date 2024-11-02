package errors;

public class WrongColorException extends Exception {
    public WrongColorException() {
        super("Вы выбрали чужую фигуру. Попробуйте еще раз.");
    }
}
