package wemfit_backend.backend.Exceptions;

public class RegistroNotFoundException extends RuntimeException{

    public RegistroNotFoundException(Long id) {
        super("Registro con id " + id + " no encontrado");
    }
    public RegistroNotFoundException() {
        super();
    }
}
