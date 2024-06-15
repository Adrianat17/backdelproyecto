package wemfit_backend.backend.Exceptions;

public class UsuarioNotFoundException extends RuntimeException {


    public UsuarioNotFoundException(Long id) {
        super("Usuario con id " + id + " no encontrado");
    }
    public UsuarioNotFoundException() {
        super();
    }


}
