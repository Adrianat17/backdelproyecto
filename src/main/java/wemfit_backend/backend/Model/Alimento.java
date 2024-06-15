package wemfit_backend.backend.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="alimento")
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String alimento;
    private String grupoAlimento;
    private Double calorias;
    private String categoria;

    @ManyToOne
    @JoinColumn(name = "id_registro")
    private Registro registro;
}
