package errors;

public class ChessMovementException extends Exception {
    public ChessMovementException(String errorMessage) {
        super(errorMessage);
    }
}
