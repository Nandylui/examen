package historialclinico.historialclinico.repositorio;

import historialclinico.historialclinico.modelo.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioRepositorio extends JpaRepository<Horario, Long> {
}
