package historialclinico.historialclinico.controlador;

import historialclinico.historialclinico.controlador.dto.EspecialidadDTO;
import historialclinico.historialclinico.modelo.Especialidad;
import historialclinico.historialclinico.repositorio.EspecialidadRepositorio;
import historialclinico.historialclinico.servicio.EspecialidadServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/especialidades")
public class EspecialidadControlador {

    @Autowired
    private EspecialidadRepositorio especialidadRepositorio;

    // Método para obtener todas las especialidades o buscar por nombre
    @GetMapping({"", "/"})
    public String obtenerEspecialidades(@RequestParam(value = "nombre", required = false) String nombre, Model model) {
        List<Especialidad> especialidades;

        // Si se proporciona un nombre, busca especialidades por nombre
        if (nombre != null && !nombre.isEmpty()) {
            especialidades = especialidadRepositorio.findByNombreContainingIgnoreCase(nombre, Sort.by(Sort.Direction.ASC, "id"));
        } else {
            // Si no se proporciona un nombre, lista todas las especialidades
            especialidades = especialidadRepositorio.findAll(Sort.by(Sort.Direction.ASC, "id"));
        }

        model.addAttribute("especialidades", especialidades);
        model.addAttribute("nombre", nombre);  // Para mostrar el valor de búsqueda en la barra de búsqueda

        return "especialidades/index";  // El nombre de la vista para mostrar la lista de especialidades
    }

    // Vista para crear una nueva especialidad
    @GetMapping("/crear")
    public String crearEspecialidad(Model model) {
        EspecialidadDTO especialidadDTO = new EspecialidadDTO();
        model.addAttribute("especialidadDTO", especialidadDTO);
        return "especialidades/crear";
    }

    // Crear una nueva especialidad
    @PostMapping("/crear")
    public String crearEspecialidad(@Valid @ModelAttribute EspecialidadDTO especialidadDTO, BindingResult result) {
        if (especialidadRepositorio.findByNombre(especialidadDTO.getNombre()) != null) {
            result.addError(new FieldError("especialidadDTO", "nombre", especialidadDTO.getNombre(), false, null, null, "Nombre de especialidad ya usado"));
        }
        if (result.hasErrors()) {
            return "especialidades/crear";
        }

        Especialidad especialidad = new Especialidad();
        especialidad.setNombre(especialidadDTO.getNombre());
        especialidad.setDescripcion(especialidadDTO.getDescripcion());
        especialidadRepositorio.save(especialidad);

        return "redirect:/especialidades";
    }

    // Vista para editar una especialidad
    @GetMapping("/editar/{id}")
    public String editarEspecialidad(Model model, @PathVariable Long id) {
        Especialidad especialidad = especialidadRepositorio.findById(id).orElse(null);
        if (especialidad == null) {
            return "redirect:/especialidades";
        }

        EspecialidadDTO especialidadDTO = new EspecialidadDTO();
        especialidadDTO.setNombre(especialidad.getNombre());
        especialidadDTO.setDescripcion(especialidad.getDescripcion());

        model.addAttribute("especialidadDTO", especialidadDTO);
        model.addAttribute("especialidad", especialidad);
        return "especialidades/editar";
    }

    // Editar una especialidad
    @PostMapping("/editar/{id}")
    public String editarEspecialidad(@PathVariable Long id, @Valid @ModelAttribute EspecialidadDTO especialidadDTO, BindingResult result, Model model) {
        Especialidad especialidad = especialidadRepositorio.findById(id).orElse(null);
        if (especialidad == null) {
            return "redirect:/especialidades";
        }

        if (result.hasErrors()) {
            model.addAttribute("especialidad", especialidad); // Para mantener el ID en la plantilla
            return "especialidades/editar";
        }

        especialidad.setNombre(especialidadDTO.getNombre());
        especialidad.setDescripcion(especialidadDTO.getDescripcion());

        try {
            especialidadRepositorio.save(especialidad);
        } catch (Exception e) {
            result.addError(new FieldError("especialidadDTO", "nombre", especialidadDTO.getNombre(), false, null, null, "Nombre ya en uso"));
            model.addAttribute("especialidad", especialidad);
            return "especialidades/editar";
        }

        return "redirect:/especialidades";
    }

    // Eliminar una especialidad
    @GetMapping("/eliminar")
    public String eliminarEspecialidad(@RequestParam Long id) {
        Especialidad especialidad = especialidadRepositorio.findById(id).orElse(null);
        if (especialidad != null) {
            especialidadRepositorio.delete(especialidad);
        }
        return "redirect:/especialidades";
    }
}
