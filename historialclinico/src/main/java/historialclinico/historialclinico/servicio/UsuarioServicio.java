package historialclinico.historialclinico.servicio;

import historialclinico.historialclinico.controlador.dto.UsuarioRegistroDTO;
import historialclinico.historialclinico.modelo.Usuario;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UsuarioServicio extends UserDetailsService {
    public Usuario guardar(UsuarioRegistroDTO registroDTO);
    public List<Usuario> listarUsuarios();
    public List<Usuario> obtenerTodosLosUsuarios();




}
