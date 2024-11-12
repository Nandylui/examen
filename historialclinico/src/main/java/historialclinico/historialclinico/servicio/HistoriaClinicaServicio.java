package historialclinico.historialclinico.servicio;

import historialclinico.historialclinico.modelo.HistoriaClinica;

import java.util.List;
import java.util.Optional;

public interface HistoriaClinicaServicio {

    HistoriaClinica guardarHistoriaClinica(HistoriaClinica historiaClinica);
    Optional<HistoriaClinica> obtenerHistoriaClinicaPorId(Long id);
    Optional<HistoriaClinica> obtenerHistoriaClinicaPorPacienteId(Long pacienteId);
    List<HistoriaClinica> obtenerTodasLasHistoriasClinicas();
}
