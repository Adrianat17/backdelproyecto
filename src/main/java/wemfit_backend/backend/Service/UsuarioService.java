package wemfit_backend.backend.Service;

import wemfit_backend.backend.Enums.Rol;
import wemfit_backend.backend.Model.Usuario;
import wemfit_backend.backend.dto.UsuarioRequest;
import wemfit_backend.backend.dto.UsuarioRequestLogin;
import wemfit_backend.backend.dto.UsuarioResponse;
import wemfit_backend.backend.dto.UsuarioResponseLogin;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface UsuarioService {

    List<UsuarioResponse> getAllUsuarios();

    UsuarioResponse getUsuarioById(Long id);

    UsuarioResponse createUsuario(UsuarioRequest usuario);

    UsuarioResponse updateUsuario(Long id, UsuarioRequest usuarioRequest);

    void deleteUsuario(Long id);

    UsuarioResponseLogin authenticateUsuario(UsuarioRequestLogin loginRequest);

    Usuario updateAlturaPesoActividad(Long id,Integer altura, Double peso, Double actividad);

    void asignarRol(Long idUsuario, Rol rol, Long adminId) throws AccessDeniedException;

    List<UsuarioResponse> getAllUsuariosByRol(Rol rol);


}