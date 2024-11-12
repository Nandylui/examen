package historialclinico.historialclinico.controlador;

import historialclinico.historialclinico.controlador.dto.MedicoDTO;
import historialclinico.historialclinico.modelo.Especialidad;
import historialclinico.historialclinico.modelo.Medico;
import historialclinico.historialclinico.repositorio.EspecialidadRepositorio;
import historialclinico.historialclinico.repositorio.MedicoRepositorio;
import historialclinico.historialclinico.servicio.EspecialidadServicio;
import historialclinico.historialclinico.servicio.MedicoServicioImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/medicos")
public class MedicoControlador {
    @Autowired
    private MedicoRepositorio servicio;

    @Autowired
    private EspecialidadServicio especialidadServicio;

    @Autowired
    private MedicoServicioImpl medicoServicio;

//    @GetMapping({"","/"})
//    public String obtenerMedicos(Model model){
//        var medicos = servicio.findAll(Sort.by(Sort.Direction.ASC, "id"));
//        model.addAttribute("medicos", medicos);
//
//        return "medicos/index";
//    }

//    esto me permite buscar y listar nombres
    @GetMapping({"", "/"})
    public String obtenerMedicos(@RequestParam(value = "nombre", required = false) String nombre, Model model){
        List<Medico> medicos;

        // Si se proporciona un nombre, busca médicos por nombre
        if (nombre != null && !nombre.isEmpty()) {
            medicos = servicio.findByNombreContainingIgnoreCase(nombre, Sort.by(Sort.Direction.ASC, "id"));
        } else {
            // Si no se proporciona un nombre, lista todos los médicos
            medicos = servicio.findAll(Sort.by(Sort.Direction.ASC, "id"));
        }

        model.addAttribute("medicos", medicos);
        model.addAttribute("nombre", nombre); // Para mostrar el valor de búsqueda en la barra de búsqueda

        return "medicos/index"; // El nombre de la vista para mostrar la lista de médicos
    }

    @GetMapping("/crear")
    public String crearMedico(Model model){
        MedicoDTO medicoDTO = new MedicoDTO();
        List<Especialidad> especialidades = especialidadServicio.obtenerTodasLasEspecialidades(); // Obtener todas las especialidades
        model.addAttribute("medicoDTO", medicoDTO);
        model.addAttribute("especialidades", especialidades);
        return "medicos/crear";
    }


//    @PostMapping("/crear")
//    public String crearMedico(@Valid @ModelAttribute MedicoDTO medicoDTO, BindingResult result){
//        // Validar si el email ya está en uso
//        if(servicio.findByEmail(medicoDTO.getEmail()) != null){
//            result.addError(new FieldError("medicoDTO", "email", medicoDTO.getEmail(), false, null, null,"Email ya usado"));
//        }
//        if(result.hasErrors()){
//            return "medicos/crear"; // Si hay errores, vuelve a mostrar el formulario
//        }
//        // Crear objeto Medico y asignar los valores del DTO
//        Medico medico = new Medico();
//        medico.setNombre(medicoDTO.getNombre());
//        medico.setApellido(medicoDTO.getApellido());
//        medico.setSexo(medicoDTO.getSexo());
//        medico.setEmail(medicoDTO.getEmail());
//        medico.setTelefono(medicoDTO.getTelefono());
//        medico.setNromatricula(medicoDTO.getNromatricula());
//        // Asignar la especialidad usando el ID de especialidad
//
////        Especialidad especialidad = especialidadServicio.obtenerEspecialidadPorId(medicoDTO.getEspecialidadId().getId());
//        Especialidad especialidad = especialidadServicio.obtenerEspecialidadPorId(medicoDTO.getEspecialidadId());
//
//        medico.setEspecialidad(especialidad);
//
//        // Guardar el médico en la base de datos
//        servicio.save(medico);
//
//        return "redirect:/medicos"; // Redirige al listado de médicos
//    }


//    @PostMapping("/crear")
//    public ResponseEntity<Medico> crearMedico(@RequestBody MedicoDTO medicoDTO) {
//        // Aquí obtienes el ID de la especialidad desde el DTO
//        Optional<Especialidad> especialidadOpt = especialidadServicio.obtenerEspecialidadPorId(medicoDTO.getEspecialidadId());
//
//        if (especialidadOpt.isPresent()) {
//            Especialidad especialidad = especialidadOpt.get();
//
//            // Crear el médico con los datos del DTO
//            Medico medico = new Medico();
//            medico.setNombre(medicoDTO.getNombre());
//            medico.setApellido(medicoDTO.getApellido());
//            medico.setSexo(medicoDTO.getSexo());
//            medico.setEmail(medicoDTO.getEmail());
//            medico.setTelefono(medicoDTO.getTelefono());
//            medico.setNromatricula(medicoDTO.getNromatricula());
//            medico.setEspecialidad(especialidad);
//
//            // Guardar el médico
//            Medico medicoGuardado = medicoServicio.guardarMedico(medico);
//
//            return ResponseEntity.status(HttpStatus.CREATED).body(medicoGuardado);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }
//
//    @PostMapping("/medicos/crear")
//    public String guardarMedico(@ModelAttribute Medico medico) {
//        medicoServicio.guardarMedico(medico);
//        return "redirect:/medicos";  // redirige después de guardar
//    }


    @PostMapping("/crear")
    public String guardarMedico(@Valid @ModelAttribute("medicoDTO") MedicoDTO medicoDTO, BindingResult result, Model model) {
        // Validar si el email ya está en uso
//        if (medicoServicio.existsByEmail(medicoDTO.getEmail())) {
//            result.addError(new FieldError("medicoDTO", "email", "El email ya está en uso"));
//        }

        // Validar errores
        if (result.hasErrors()) {
            List<Especialidad> especialidades = especialidadServicio.obtenerTodasLasEspecialidades();
            model.addAttribute("especialidades", especialidades);
            return "medicos/crear"; // Volver al formulario si hay errores
        }

        // Asignar los datos del DTO al objeto Medico
        Medico medico = new Medico();
        medico.setNombre(medicoDTO.getNombre());
        medico.setApellido(medicoDTO.getApellido());
        medico.setSexo(medicoDTO.getSexo());
        medico.setEmail(medicoDTO.getEmail());
        medico.setTelefono(medicoDTO.getTelefono());
        medico.setNromatricula(medicoDTO.getNromatricula());

        // Obtener y asignar la especialidad
        Especialidad especialidad = especialidadServicio.obtenerEspecialidadPorId(medicoDTO.getEspecialidadId()).orElse(null);
        if (especialidad == null) {
            result.addError(new FieldError("medicoDTO", "especialidadId", "Especialidad no encontrada"));
            List<Especialidad> especialidades = especialidadServicio.obtenerTodasLasEspecialidades();
            model.addAttribute("especialidades", especialidades);
            return "medicos/crear";
        }
        medico.setEspecialidad(especialidad);

        // Guardar el médico
        medicoServicio.guardarMedico(medico);

        return "redirect:/medicos"; // Redirigir al listado de médicos
    }




//    @GetMapping("/editar/{id}")
//    public String editarMedico(Model model, @PathVariable Long id){
//        Medico medico=servicio.findById(id).orElse(null);
//        if(medico == null){
//            return "redirect:/medicos";
//        }
//
//        MedicoDTO medicoDTO = new MedicoDTO();
//        medicoDTO.setNombre(medico.getNombre());
//        medicoDTO.setApellido(medico.getApellido());
//        medicoDTO.setSexo(medico.getSexo());
//        medicoDTO.setEmail(medico.getEmail());
//        medicoDTO.setTelefono(medico.getTelefono());
//        medicoDTO.setNromatricula( medico.getNromatricula());
//        model.addAttribute("medicoDTO", medicoDTO);
//        model.addAttribute("medico", medico);
//        return "medicos/editar";
//    }

    @GetMapping("/editar/{id}")
    public String editarMedico(Model model, @PathVariable Long id){
        Medico medico = servicio.findById(id).orElse(null);
        if(medico == null){
            return "redirect:/medicos";
        }

        MedicoDTO medicoDTO = new MedicoDTO();
        medicoDTO.setNombre(medico.getNombre());
        medicoDTO.setApellido(medico.getApellido());
        medicoDTO.setSexo(medico.getSexo());
        medicoDTO.setEmail(medico.getEmail());
        medicoDTO.setTelefono(medico.getTelefono());
        medicoDTO.setNromatricula(medico.getNromatricula());
        medicoDTO.setEspecialidadId(medico.getEspecialidad().getId());  // Asignar la especialidad seleccionada

        List<Especialidad> especialidades = especialidadServicio.obtenerTodasLasEspecialidades();  // Obtener todas las especialidades
        model.addAttribute("medicoDTO", medicoDTO);
        model.addAttribute("medico", medico);
        model.addAttribute("especialidades", especialidades);  // Pasar las especialidades al modelo
        return "medicos/editar";
    }



//    @PostMapping("/editar/{id}")
//    public String editarMedico(@PathVariable Long id, @Valid @ModelAttribute MedicoDTO medicoDTO, BindingResult result){
//        Medico medico=servicio.findById(id).orElse(null);
//        if(medico != null){
//            return "redirect:/medicos";
//        }
//        if(result.hasErrors()){
//            return "medicos/editar";
//        }
//        medico.setNombre(medicoDTO.getNombre());
//        medico.setApellido(medicoDTO.getApellido());
//        medico.setSexo(medicoDTO.getSexo());
//        medico.setEmail(medicoDTO.getEmail());
//        medico.setTelefono(medicoDTO.getTelefono());
//        medico.setNromatricula(medicoDTO.getNromatricula());
//
//        try{
//            servicio.save(medico);
//        } catch (Exception e) {
//            result.addError(new FieldError("medicoDTO", "email", medicoDTO.getEmail(), false, null, null, "email ya en uso"));
//            return "medicos/editar";
//        }
//
//        return "redirect:/medicos";
//    }

    @PostMapping("/editar/{id}")
    public String editarMedico(@PathVariable Long id, @Valid @ModelAttribute MedicoDTO medicoDTO, BindingResult result, Model model){
        Medico medico = servicio.findById(id).orElse(null);
        if(medico == null){
            return "redirect:/medicos";
        }

        // Validar errores
        if (result.hasErrors()) {
            List<Especialidad> especialidades = especialidadServicio.obtenerTodasLasEspecialidades();
            model.addAttribute("especialidades", especialidades);
            return "medicos/editar";
        }

        // Asignar datos
        medico.setNombre(medicoDTO.getNombre());
        medico.setApellido(medicoDTO.getApellido());
        medico.setSexo(medicoDTO.getSexo());
        medico.setEmail(medicoDTO.getEmail());
        medico.setTelefono(medicoDTO.getTelefono());
        medico.setNromatricula(medicoDTO.getNromatricula());

        // Asignar la especialidad seleccionada
        Optional<Especialidad> especialidadOpt = especialidadServicio.obtenerEspecialidadPorId(medicoDTO.getEspecialidadId());
        if (especialidadOpt.isPresent()) {
            medico.setEspecialidad(especialidadOpt.get());
        } else {
            result.addError(new FieldError("medicoDTO", "especialidadId", "Especialidad no encontrada"));
            List<Especialidad> especialidades = especialidadServicio.obtenerTodasLasEspecialidades();
            model.addAttribute("especialidades", especialidades);
            return "medicos/editar";
        }

        // Guardar el médico actualizado
        servicio.save(medico);
        return "redirect:/medicos";
    }



    @GetMapping("/eliminar")
    public String eliminarMedico(@RequestParam Long id){
        Medico medico=servicio.findById(id).orElse(null);
        if(medico!= null){
            servicio.delete(medico);
        }
        return "redirect:/medicos";
    }


}
