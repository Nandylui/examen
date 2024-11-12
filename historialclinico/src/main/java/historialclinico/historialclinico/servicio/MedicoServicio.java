package historialclinico.historialclinico.servicio;

import historialclinico.historialclinico.modelo.Medico;

import java.util.List;
import java.util.Optional;

public interface MedicoServicio {
    List<Medico>obtenerTodosMedicos();
    Optional<Medico> obtenerMedicoPorId(Long id);
    Medico guardarMedico(Medico medico);
    void eliminarMedico(Long id);
    public List<Medico> listarMedicos();
    boolean existsByEmail(String email);
}
