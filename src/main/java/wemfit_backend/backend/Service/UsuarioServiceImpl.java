package wemfit_backend.backend.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wemfit_backend.backend.Enums.Rol;
import wemfit_backend.backend.Exceptions.LoginNotFoundException;
import wemfit_backend.backend.Exceptions.UsuarioNotFoundException;
import wemfit_backend.backend.Model.Usuario;
import wemfit_backend.backend.Repository.UsuarioRepository;
import wemfit_backend.backend.dto.UsuarioRequest;
import wemfit_backend.backend.dto.UsuarioRequestLogin;
import wemfit_backend.backend.dto.UsuarioResponse;
import wemfit_backend.backend.dto.UsuarioResponseLogin;

import java.nio.file.AccessDeniedException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public List<UsuarioResponse> getAllUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::toUsuarioResponse).toList();
    }

    @Override
    public UsuarioResponse getUsuarioById(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(UsuarioNotFoundException::new);
        return this.toUsuarioResponse(usuario);
    }

    @Override
    public UsuarioResponse createUsuario(UsuarioRequest usuarioRequest) {
        LocalDate fechaNacimiento = usuarioRequest.getFechaNacimiento();

        if (fechaNacimiento == null) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser nula");
        }

        LocalDate fechaActual = LocalDate.now();
        Period periodo = Period.between(fechaNacimiento, fechaActual);
        int edad = periodo.getYears();

        Usuario usuario = toUsuario(usuarioRequest);
        usuario.setEdad(edad);
        usuario.setContrasena(hashPassword(usuarioRequest.getContrasena()));

        // Verificar si hay algún administrador
        boolean hayAdmin = !usuarioRepository.findByRol(Rol.ADMINISTRADOR).isEmpty();

        // Asignar rol al usuario
        if (!hayAdmin) {
            usuario.setRol(Rol.ADMINISTRADOR);
        } else {
            usuario.setRol(Rol.USUARIO);
        }

        Usuario savedUsuario = usuarioRepository.save(usuario);
        return toUsuarioResponse(savedUsuario);
    }

    @Override
    public UsuarioResponse updateUsuario(Long id, UsuarioRequest usuarioRequest) throws UsuarioNotFoundException {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(UsuarioNotFoundException::new);

        usuario.setAltura(usuarioRequest.getAltura() != null ? usuarioRequest.getAltura() : usuario.getAltura());
        usuario.setPeso(usuarioRequest.getPeso() != null ? usuarioRequest.getPeso() : usuario.getPeso());

        if (usuarioRequest.getFechaNacimiento() != null) {
            LocalDate fechaNacimiento = usuarioRequest.getFechaNacimiento();
            LocalDate ahora = LocalDate.now();
            Period periodo = Period.between(fechaNacimiento, ahora);
            int edad = periodo.getYears();
            usuario.setEdad(edad);
        }
        usuario.setNombre(usuarioRequest.getNombre() != null ? usuarioRequest.getNombre() : usuario.getNombre());
        usuario.setEmail(usuarioRequest.getEmail() != null ? usuarioRequest.getEmail() : usuario.getEmail());
        if (usuarioRequest.getContrasena() != null) {
            usuario.setContrasena(hashPassword(usuarioRequest.getContrasena()));
        }

        return toUsuarioResponse(usuarioRepository.save(usuario));
    }

    @Override
    @Transactional
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    private Usuario toUsuario(UsuarioRequest usuarioRequest) {
        return Usuario.builder()
                .nombre(usuarioRequest.getNombre())
                .email(usuarioRequest.getEmail())
                .peso(usuarioRequest.getPeso())
                .altura(usuarioRequest.getAltura())
                .genero(usuarioRequest.getGenero())
                .contrasena(hashPassword(usuarioRequest.getContrasena()))
                .build();
    }

    private UsuarioResponse toUsuarioResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEdad(),
                usuario.getPeso(),
                usuario.getAltura(),
                usuario.getEmail(),
                usuario.getGenero(),
                usuario.getRol()
        );
    }

    @Override
    public UsuarioResponseLogin authenticateUsuario(UsuarioRequestLogin loginRequest) {
        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new LoginNotFoundException("Credenciales inválidas"));

        if (usuario.getContrasena().equals(hashPassword(loginRequest.getContrasena()))) {
            String token = generateToken(33);
            Long userId = usuario.getId();
            Rol userRol = usuario.getRol();

            return new UsuarioResponseLogin(token, userId, userRol);
        } else {
            throw new LoginNotFoundException("Credenciales inválidas");
        }
    }

    @Override
    public Usuario updateAlturaPesoActividad(Long id, Integer altura, Double peso, Double actividad) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(UsuarioNotFoundException::new);
        usuario.setAltura(altura);
        usuario.setPeso(peso);
        usuario.setActividad(actividad);
        return usuarioRepository.save(usuario);
    }

    private String generateToken(int tokenLength) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[tokenLength];
        secureRandom.nextBytes(randomBytes);
        StringBuilder tokenBuilder = new StringBuilder();
        for (byte b : randomBytes) {
            tokenBuilder.append(String.format("%02x", b));
        }
        return tokenBuilder.toString();
    }

    private String hashPassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("La contraseña no puede ser nula");
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void asignarRol(Long id, Rol rol, Long adminId) throws AccessDeniedException {
        Usuario adminUsuario = usuarioRepository.findById(adminId).orElseThrow(UsuarioNotFoundException::new);

        if (adminUsuario.getRol() != Rol.ADMINISTRADOR) {
            throw new AccessDeniedException("Solo un administrador puede modificar los roles");
        }

        Usuario usuario = usuarioRepository.findById(id).orElseThrow(UsuarioNotFoundException::new);
        usuario.setRol(rol);
        usuarioRepository.save(usuario);
    }

    @Override
    public List<UsuarioResponse> getAllUsuariosByRol(Rol rol) {
        return usuarioRepository.findByRol(rol).stream()
                .map(this::toUsuarioResponse).toList();
    }
}
