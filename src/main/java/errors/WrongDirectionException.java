package errors;

public class WrongDirectionException extends Exception {
    public WrongDirectionException() {
        super("Данная фигура не может двигаться в этом направлении.");
    }
}
