package wemfit_backend.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlimentoResponse {

    private Long id;
    private String alimento;
    private String grupoAlimento;
    private Double calorias;
    private String categoria;
}
