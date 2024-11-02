package errors;

public class PathBlockedException extends Exception {
    public PathBlockedException() {
        super("Путь прегражден.");
    }
}
