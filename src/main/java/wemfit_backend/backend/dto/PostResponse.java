package wemfit_backend.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import wemfit_backend.backend.Enums.Categoria;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PostResponse {
    private long id;
    private String titulo;
    private String descripcion;
    private LocalDate fecha;
    private Categoria categoria;
}

