package wemfit_backend.backend.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wemfit_backend.backend.Exceptions.RegistroNotFoundException;
import wemfit_backend.backend.Model.Registro;
import wemfit_backend.backend.Repository.RegistroRepository;
import wemfit_backend.backend.dto.RegistroRequest;
import wemfit_backend.backend.dto.RegistroResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor

public class RegistroServiceImpl implements RegistroService {
    private final RegistroRepository registroRepository;

    public List<RegistroResponse> getAllRegistro() {
        List<Registro> registros = registroRepository.findAll();
        List<RegistroResponse> registroResponses = new ArrayList<>();

        for (Registro registro : registros) {
            registroResponses.add(toRegistroResponse(registro));
        }

        return registroResponses;
    }


    public RegistroResponse getRegistroById(Long id) {
        Registro registro = registroRepository.findById(id).orElseThrow(RegistroNotFoundException::new);
        return this.toRegistroResponse(registro);
    }

    public RegistroResponse createRegistro(RegistroRequest registroRequest) {
        return this.toRegistroResponse(registroRepository.save(this.toRegistro(registroRequest)));
    }


    public void deleteRegistro(Long id) {
        registroRepository.deleteById(id);
    }

    public RegistroResponse updateRegistro(Long id, RegistroRequest registroRequest) {
        Registro existingRegistro = registroRepository.findById(id).orElseThrow(RegistroNotFoundException::new);

        // Actualizar los campos del registro existente con los nuevos valores
        existingRegistro.setUsuario(registroRequest.getUsuario());
        existingRegistro.setCantidadIngerida(registroRequest.getCantidadIngerida());
        existingRegistro.setFechaRegistro(LocalDate.now());
        existingRegistro.setCantidad(registroRequest.getCantidad());
        existingRegistro.setAlimentos(registroRequest.getAlimentos());

        // Guardar el registro actualizado en la base de datos
        return this.toRegistroResponse(registroRepository.save(existingRegistro));
    }


    private Registro toRegistro(RegistroRequest registroRequest) {
        Registro registro = new Registro();
        registro.setUsuario(registroRequest.getUsuario());
        registro.setCantidadIngerida(registroRequest.getCantidadIngerida());
        registro.setFechaRegistro(registroRequest.getFechaRegistro());
        registro.setCantidad(registroRequest.getCantidad());
        registro.setAlimentos(registroRequest.getAlimentos());
        return registro;
    }

    private RegistroResponse toRegistroResponse(Registro registro) {
        RegistroResponse registroResponse = new RegistroResponse();

        registroResponse.setId(registro.getId());
        registroResponse.setCantidadIngerida(registro.getCantidadIngerida());
        registroResponse.setCantidad(registro.getCantidad());
        registroResponse.setUsuario(registro.getUsuario());
        registroResponse.setAlimentos(registro.getAlimentos());
        registroResponse.setFechaRegistro(registro.getFechaRegistro());

        return registroResponse;
    }

}