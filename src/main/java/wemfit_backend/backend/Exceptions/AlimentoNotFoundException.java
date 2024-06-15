package wemfit_backend.backend.Exceptions;

public class AlimentoNotFoundException extends RuntimeException{
    public AlimentoNotFoundException(Long id) {
        super("Alimento con id " + id + " no encontrado");
    }
    public AlimentoNotFoundException() {
        super();
    }
}

