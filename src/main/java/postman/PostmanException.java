package postman;

public class PostmanException extends java.lang.Exception {
    public PostmanException(Throwable ex) {
        super("An error occurred while trying to make a request.", ex);
    }

    public PostmanException(String message, Throwable ex) {
        super(message, ex);
    }
}
