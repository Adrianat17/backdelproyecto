package wemfit_backend.backend.Exceptions;

public class PostNotFoundException extends RuntimeException {


    public PostNotFoundException(Long id) {
        super("Post con id " + id + " no encontrado");
    }
    public PostNotFoundException() {
        super();
    }
}

