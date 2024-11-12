package historialclinico.historialclinico.servicio;

import historialclinico.historialclinico.modelo.Consulta;

import java.time.LocalDate;
import java.util.List;

public interface ConsultaServicio {

    Consulta crearConsulta(Consulta consulta);

    Consulta obtenerConsultaPorId(Long id);

    List<Consulta> obtenerConsultasPorMedico(Long medicoId);

    List<Consulta> obtenerConsultasPorHistoriaClinica(Long historiaClinicaId);

//    List<Consulta> obtenerConsultasPorFecha(LocalDate FechaIni, LocalDate FechaFin);

    Consulta actualizarConsulta(Long id, Consulta consulta);

    void eliminarConsulta(Long id);
}
