package pl.edu.wat.wcy.isi.autoapproximationappbackend.configuration.exception;

public class LoginException extends Exception {

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginException(Throwable cause) {
        super(cause);
    }
}
