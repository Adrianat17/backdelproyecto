package wemfit_backend.backend.Controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wemfit_backend.backend.Enums.Rol;
import wemfit_backend.backend.Exceptions.LoginNotFoundException;
import wemfit_backend.backend.Exceptions.UsuarioNotFoundException;
import wemfit_backend.backend.Model.Usuario;
import wemfit_backend.backend.Service.UsuarioService;
import wemfit_backend.backend.dto.UsuarioRequest;
import wemfit_backend.backend.dto.UsuarioRequestLogin;
import wemfit_backend.backend.dto.UsuarioResponse;
import wemfit_backend.backend.dto.UsuarioResponseLogin;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping(value = "/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<UsuarioResponse>> getAllUsuarios() {
        try {
            List<UsuarioResponse> usuarios = usuarioService.getAllUsuarios();
            return ResponseEntity.status(HttpStatus.OK).body(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> getUsuarioById(@PathVariable(name = "id") Long id) {
        try {
            UsuarioResponse usuario = usuarioService.getUsuarioById(id);
            return ResponseEntity.status(HttpStatus.OK).body(usuario);
        } catch (UsuarioNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping(value = "/registro")
    public ResponseEntity<String> createUsuario(@RequestBody UsuarioRequest usuario) {
        try {
            usuarioService.createUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<UsuarioResponse> updateUsuario(@PathVariable(value = "id") Long id, @RequestBody UsuarioRequest usuarioRequest) {
        try {
            UsuarioResponse usuario = usuarioService.updateUsuario(id, usuarioRequest);
            return ResponseEntity.status(HttpStatus.OK).body(usuario);
        } catch (UsuarioNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable(value = "id") Long id) {
        try {
            usuarioService.deleteUsuario(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (UsuarioNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<UsuarioResponseLogin> loginUsuario(@RequestBody UsuarioRequestLogin loginRequest) {
        try {
            UsuarioResponseLogin response = usuarioService.authenticateUsuario(loginRequest);
            return ResponseEntity.ok(response);
        } catch (LoginNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/update_calcular/{id}")
    public ResponseEntity<Usuario> updateAlturaPesoActividad(@PathVariable(value = "id") Long id, @RequestParam(name = "altura", required = false) Integer altura, @RequestParam(name = "peso", required = false) Double peso, @RequestParam(name = "actividad", required = false) Double actividad) {
        try {
            Usuario usuario = usuarioService.updateAlturaPesoActividad(id, altura, peso, actividad);
            return ResponseEntity.ok(usuario);
        } catch (UsuarioNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    //////////////////

    @PutMapping("/asignar/{id}")
    public ResponseEntity<String> asignarRol(@PathVariable Long id, @RequestParam Rol rol, @RequestParam Long adminId) {
        try {
            usuarioService.asignarRol(id, rol, adminId);
            return ResponseEntity.status(HttpStatus.OK).body("Rol asignado correctamente");
        } catch (UsuarioNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error interno");
        }
    }


    @GetMapping("/rol")
    public ResponseEntity<List<UsuarioResponse>> getUsuariosByRol(@RequestParam Rol rol) {
        try {
            List<UsuarioResponse> usuarios = usuarioService.getAllUsuariosByRol(rol);
            return ResponseEntity.status(HttpStatus.OK).body(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
