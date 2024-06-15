package wemfit_backend.backend.Controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wemfit_backend.backend.Service.AlimentoService;
import wemfit_backend.backend.dto.AlimentoResponse;

import java.util.List;

@RestController
@RequestMapping(value = "/alimento")
@RequiredArgsConstructor
public class AlimentoController {

    private final AlimentoService alimentoService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlimento(@PathVariable(value = "id") Long id) {
        try {
            alimentoService.deleteAlimento(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //todos los alimentos
    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllAlimento() {
        try {
            // Devuelve una respuesta con todos los posts y estado OK si no hay errores.
            return ResponseEntity.status(HttpStatus.OK).body(alimentoService.getAllAlimentos());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //alimento x id
    @GetMapping("/{id}")
    public AlimentoResponse getAlimentoById(@PathVariable(name = "id") Long id) {
        return alimentoService.getAlimentoById(id);
    }

    //lista de categorias
    @GetMapping(value = "/categorias")
    public ResponseEntity<List<String>> getAllCategorias() {
        List<String> categorias = alimentoService.getAllCategorias();
        return ResponseEntity.ok(categorias);
    }

    //lista de grupos de alimentos
    @GetMapping(value = "/grupos")
    public ResponseEntity<List<String>> getAllGruposAlimento() {
        List<String> grupos = alimentoService.getAllGruposAlimento();
        return ResponseEntity.ok(grupos);
    }

    // categorias filtrando por grupos de alimentos
    @GetMapping(value = "/categorias/{grupoAlimento}")
    public ResponseEntity<List<String>> getCategoriasByGrupoAlimento(@PathVariable String grupoAlimento) {
        List<String> categorias = alimentoService.getCategoriasByGrupoAlimento(grupoAlimento);
        return ResponseEntity.ok(categorias);
    }

    // alimentos filtrando por grupos de alimentos y categorias
    @GetMapping(value = "/alimentos")
    public ResponseEntity<List<AlimentoResponse>> getAlimentosByGrupoAndCategoria(
            @RequestParam String grupoAlimento,
            @RequestParam String categoria) {
        List<AlimentoResponse> alimentos = alimentoService.getAlimentosByGrupoAndCategoria(grupoAlimento, categoria);
        return ResponseEntity.ok(alimentos);
    }

}
