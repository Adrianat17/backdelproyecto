package wemfit_backend.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wemfit_backend.backend.Model.Alimento;
import wemfit_backend.backend.Model.Usuario;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistroRequest {
    private Integer cantidadIngerida;
    private LocalDate fechaRegistro;
    private Usuario usuario;
    private Integer cantidad;
    private List<Alimento> alimentos;
}