package wemfit_backend.backend.Model;

import jakarta.persistence.*;
import lombok.Data;
import wemfit_backend.backend.Enums.Categoria;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descripcion;
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}

