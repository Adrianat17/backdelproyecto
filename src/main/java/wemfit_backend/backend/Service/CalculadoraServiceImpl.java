package wemfit_backend.backend.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wemfit_backend.backend.Model.Usuario;
import wemfit_backend.backend.Repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class CalculadoraServiceImpl implements CalculadoraService {


    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    @Override
    public Double calcularCalorias(Usuario usuario) {
        if (usuario.getGenero().equals('M')) {
            return (655 + (9.6 * usuario.getPeso()) + (5 * usuario.getAltura()) - (6.8 * usuario.getEdad())) * usuario.getActividad();

        }
        return (655 + (9.6 * usuario.getPeso()) + (1.8 * usuario.getAltura()) - (4.7 * usuario.getEdad())) * usuario.getActividad();
    }
}
