package wemfit_backend.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import wemfit_backend.backend.Enums.Rol;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UsuarioResponseLogin {

    private String token;
    private Long id;
    private Rol rol;

}