package wemfit_backend.backend.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import wemfit_backend.backend.Model.Alimento;

import java.util.List;

public interface AlimentoRepository extends JpaRepository<Alimento, Long> {
    List<Alimento> findByGrupoAlimento(String grupoAlimento);
    List<Alimento> findByCategoriaAndGrupoAlimento(String categoria, String grupoAlimento);
}
