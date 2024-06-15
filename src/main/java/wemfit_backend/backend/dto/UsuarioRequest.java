package wemfit_backend.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UsuarioRequest {



    private String     nombre;
    private LocalDate fechaNacimiento;
    private Double peso;
    private Integer    altura;
    private String    email;
    private char      genero;
    private String contrasena;
}