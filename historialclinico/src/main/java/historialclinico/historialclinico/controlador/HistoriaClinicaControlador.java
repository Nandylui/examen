package historialclinico.historialclinico.controlador;

import historialclinico.historialclinico.modelo.HistoriaClinica;
import historialclinico.historialclinico.servicio.HistoriaClinicaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/historias-clinicas")
public class HistoriaClinicaControlador {

    private final HistoriaClinicaServicio historiaClinicaServicio;

    @Autowired
    public HistoriaClinicaControlador(HistoriaClinicaServicio historiaClinicaServicio) {
        this.historiaClinicaServicio = historiaClinicaServicio;
    }

    // Endpoint para crear una nueva historia clínica
    @PostMapping("/crear")
    public ResponseEntity<HistoriaClinica> crearHistoriaClinica(@RequestBody HistoriaClinica historiaClinica) {
        try {
            HistoriaClinica nuevaHistoriaClinica = historiaClinicaServicio.guardarHistoriaClinica(historiaClinica);
            return new ResponseEntity<>(nuevaHistoriaClinica, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint para obtener una historia clínica por su ID
    @GetMapping("/detalle/{id}")
    public ResponseEntity<HistoriaClinica> obtenerHistoriaClinicaPorId(@PathVariable Long id) {
        Optional<HistoriaClinica> historiaClinica = historiaClinicaServicio.obtenerHistoriaClinicaPorId(id);
        return historiaClinica.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint para obtener una historia clínica por el ID del paciente
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<HistoriaClinica> obtenerHistoriaClinicaPorPacienteId(@PathVariable Long pacienteId) {
        Optional<HistoriaClinica> historiaClinica = historiaClinicaServicio.obtenerHistoriaClinicaPorPacienteId(pacienteId);
        return historiaClinica.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint para obtener todas las historias clínicas
    @GetMapping("/listar")
    public ResponseEntity<List<HistoriaClinica>> obtenerTodasLasHistoriasClinicas() {
        List<HistoriaClinica> historiasClinicas = historiaClinicaServicio.obtenerTodasLasHistoriasClinicas();
        return new ResponseEntity<>(historiasClinicas, HttpStatus.OK);
    }

}
