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

public class RegistroResponse {
    private Long id;
    private Integer cantidadIngerida;
    private LocalDate fechaRegistro;
    private Integer cantidad;
    private Usuario usuario;
    private List<Alimento> alimentos;

}