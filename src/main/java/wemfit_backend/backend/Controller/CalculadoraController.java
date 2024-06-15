package wemfit_backend.backend.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wemfit_backend.backend.Model.Usuario;
import wemfit_backend.backend.Service.CalculadoraService;
import wemfit_backend.backend.Service.UsuarioService;

@RestController
@RequestMapping(value = "/calculadora")
@RequiredArgsConstructor

public class CalculadoraController {

    private final CalculadoraService calculadoraService;
    private final UsuarioService usuarioService;

    @PutMapping("/calcular/{id}")
    public Double calcularCalorias (
            @PathVariable(name = "id") Long usuarioId, @RequestParam Integer altura, @RequestParam Double  peso,
            @RequestParam Double  actividad){
        Usuario usuario = usuarioService.updateAlturaPesoActividad(usuarioId,altura,peso,actividad);
        return calculadoraService.calcularCalorias(usuario);

    }
}