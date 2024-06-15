package wemfit_backend.backend.Controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wemfit_backend.backend.Exceptions.RegistroNotFoundException;
import wemfit_backend.backend.Service.RegistroService;
import wemfit_backend.backend.dto.RegistroRequest;
import wemfit_backend.backend.dto.RegistroResponse;

@RestController
@RequestMapping(value = "/registro")
@RequiredArgsConstructor


public class RegistroController {

    private final RegistroService registroService;

    @PostMapping(value = "/crear_registro")
    public ResponseEntity<String> createRegistro(@RequestBody RegistroRequest registro) {

        try {
            registroService.createRegistro(registro);
            return ResponseEntity.status(HttpStatus.OK).body("Registro creado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public void deleteRegistro(@PathVariable(value = "id") Long id) {
        registroService.deleteRegistro(id);
    }
    @GetMapping("/{id}")
    public RegistroResponse getRegistroById(@PathVariable(name = "id") Long id) {

        return registroService.getRegistroById(id);
    }
    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllRegistros() {

        try {
            // Devuelve una respuesta con todos los registros y estado OK si no hay errores.
            return ResponseEntity.status(HttpStatus.OK).body(registroService.getAllRegistro());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRegistro(@PathVariable(value = "id") Long id, @RequestBody RegistroRequest registroRequest) {
        try {
            RegistroResponse updatedRegistro = registroService.updateRegistro(id, registroRequest);
            return ResponseEntity.status(HttpStatus.OK).body(updatedRegistro);
        } catch (RegistroNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registro no encontrado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
