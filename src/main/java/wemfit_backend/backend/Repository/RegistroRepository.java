package wemfit_backend.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wemfit_backend.backend.Model.Registro;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Long> {

}
