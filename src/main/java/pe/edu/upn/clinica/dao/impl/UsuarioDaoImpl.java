package pe.edu.upn.clinica.dao.impl;

import pe.edu.upn.clinica.dao.UsuarioDao;
import pe.edu.upn.clinica.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioDaoImpl implements UsuarioDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insertar(Usuario usuario) {
        String sql = "CALL sp_insertar_usuario(?, ?, ?)";
        jdbcTemplate.update(sql, usuario.getCorreo(), usuario.getPassword(), usuario.getRol());
    }

    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(); 
    }

    @Override
    public Usuario buscarPorId(Integer idUsuario) {
        return null;
    }

    @Override
    public void actualizar(Usuario usuario) {
        // Lógica pendiente
    }

    @Override
    public void eliminar(Integer idUsuario) {
        // Lógica pendiente
    }
}