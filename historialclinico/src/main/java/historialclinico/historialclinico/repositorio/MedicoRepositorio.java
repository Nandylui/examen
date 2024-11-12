package historialclinico.historialclinico.repositorio;

import historialclinico.historialclinico.modelo.Medico;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicoRepositorio extends JpaRepository<Medico, Long> {
    List<Medico>findByNombre(String palabraclave);
    public List<Medico>findAll();
    public Medico findByEmail(String mail);

//    hemos agregado esto
    List<Medico> findByNombreContainingIgnoreCase(String nombre, Sort sort);

}
