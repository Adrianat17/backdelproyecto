package wemfit_backend.backend.Service;

import wemfit_backend.backend.dto.RegistroRequest;
import wemfit_backend.backend.dto.RegistroResponse;

import java.util.List;

public interface RegistroService {
    List<RegistroResponse> getAllRegistro();

    RegistroResponse getRegistroById(Long id);

    RegistroResponse createRegistro(RegistroRequest registro);
    void deleteRegistro(Long id);

    RegistroResponse updateRegistro(Long id, RegistroRequest registroRequest);
}
