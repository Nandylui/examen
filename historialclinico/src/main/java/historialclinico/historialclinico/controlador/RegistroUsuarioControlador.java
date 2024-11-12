package historialclinico.historialclinico.controlador;


import historialclinico.historialclinico.controlador.dto.UsuarioRegistroDTO;
import historialclinico.historialclinico.modelo.Usuario;
import historialclinico.historialclinico.servicio.UsuarioServicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/registro")
public class RegistroUsuarioControlador {
    private UsuarioServicio usuarioServicio;

    public RegistroUsuarioControlador(UsuarioServicio usuarioServicio) {
        super();
        this.usuarioServicio = usuarioServicio;
    }

    @ModelAttribute("usuario")
    public UsuarioRegistroDTO retornarNuevoUsuarioRegistroDTO(){
        return new UsuarioRegistroDTO();
    }

    @GetMapping
    public String mostrarFormualarioDeRegistro() {
        return "registro";
    }

    @PostMapping
    public String registrarCuentaDeUsuario(@ModelAttribute("usuario") UsuarioRegistroDTO registroDTO) {
        usuarioServicio.guardar(registroDTO);
        return "redirect:/registro?exito";

    }

    // Nuevo método para mostrar la lista de usuarios
    @GetMapping("/usuarios") // Ruta para ver la lista de usuarios
    public String verUsuarios(Model model) {
        List<Usuario> usuarios = usuarioServicio.obtenerTodosLosUsuarios(); // Suponiendo que tienes un método para obtener todos los usuarios
        model.addAttribute("usuarios", usuarios); // Añade la lista de usuarios al modelo
        return "usuarios"; // Nombre del archivo HTML que muestra la lista de usuarios
    }




}
