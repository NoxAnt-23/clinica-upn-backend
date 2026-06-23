package pe.edu.upn.clinica.dao;

import pe.edu.upn.clinica.entity.Usuario;
import java.util.List;

public interface UsuarioDAO {
    void insertar(Usuario usuario);
    List<Usuario> listarTodos();
    Usuario buscarPorId(Integer idUsuario);
    void actualizar(Usuario usuario);
    void eliminar(Integer idUsuario);

    Usuario buscarPorCorreo(String correo);
    void actualizarPassword(int idUsuario, String nuevoPasswordEncriptado);
}