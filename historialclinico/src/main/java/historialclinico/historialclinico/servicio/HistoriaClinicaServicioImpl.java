package historialclinico.historialclinico.servicio;

import historialclinico.historialclinico.modelo.HistoriaClinica;
import historialclinico.historialclinico.repositorio.HistoriaClinicaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistoriaClinicaServicioImpl implements HistoriaClinicaServicio{

    private final HistoriaClinicaRepositorio historiaClinicaRepositorio;

    @Autowired
    public HistoriaClinicaServicioImpl(HistoriaClinicaRepositorio historiaClinicaRepositorio) {
        this.historiaClinicaRepositorio = historiaClinicaRepositorio;
    }

    @Override
    public HistoriaClinica guardarHistoriaClinica(HistoriaClinica historiaClinica) {
        // Antes de guardar, podemos verificar si el paciente ya tiene una historia clínica
        if (historiaClinicaRepositorio.existsByPacienteId(historiaClinica.getPaciente().getId())) {
            throw new RuntimeException("El paciente ya tiene una historia clínica.");
        }
        return historiaClinicaRepositorio.save(historiaClinica);
    }

    @Override
    public Optional<HistoriaClinica> obtenerHistoriaClinicaPorId(Long id) {
        return historiaClinicaRepositorio.findById(id);
    }

    @Override
    public Optional<HistoriaClinica> obtenerHistoriaClinicaPorPacienteId(Long pacienteId) {
        return historiaClinicaRepositorio.findByPacienteId(pacienteId);
    }

    @Override
    public List<HistoriaClinica> obtenerTodasLasHistoriasClinicas() {
        return historiaClinicaRepositorio.findAll();
    }


}
