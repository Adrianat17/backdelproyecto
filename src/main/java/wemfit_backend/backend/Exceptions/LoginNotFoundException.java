package wemfit_backend.backend.Exceptions;

public class LoginNotFoundException extends RuntimeException{
    public LoginNotFoundException(String message) {
        super(message);
    }
}
