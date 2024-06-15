package wemfit_backend.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import wemfit_backend.backend.Enums.Rol;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponse {
    private Long id;
    private String nombre;
    private Integer edad;
    private Double peso;
    private Integer altura;
    private String email;
    private char genero;
    private Rol rol;

}