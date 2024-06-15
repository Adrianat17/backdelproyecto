package wemfit_backend.backend.Service;

import wemfit_backend.backend.dto.AlimentoResponse;

import java.util.List;

public interface AlimentoService {

    List<AlimentoResponse> getAllAlimentos();

    void deleteAlimento(Long id);
    AlimentoResponse getAlimentoById(Long id);
    List<String> getAllCategorias();
    List<String> getAllGruposAlimento();
    List<String> getCategoriasByGrupoAlimento(String grupoAlimento);
    List<AlimentoResponse> getAlimentosByGrupoAndCategoria(String grupoAlimento, String categoria);
}
