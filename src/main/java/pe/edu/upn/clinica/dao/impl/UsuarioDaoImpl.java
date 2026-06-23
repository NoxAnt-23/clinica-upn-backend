package pe.edu.upn.clinica.dao.impl;

import pe.edu.upn.clinica.dao.UsuarioDAO;
import pe.edu.upn.clinica.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioDAOImpl implements UsuarioDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insertar(Usuario usuario) {
        String sql = "CALL sp_insertar_usuario(?, ?, ?)";
        jdbcTemplate.update(sql, usuario.getCorreo(), usuario.getPassword(), usuario.getRol());
    }

    // 🔍 NUEVO: Buscar por correo usando JDBC nativo
    @Override
    public Usuario buscarPorCorreo(String correo) {
        // 🆕 Agregamos cambio_pendiente al SELECT
        String sql = "SELECT id_usuario, correo, password, rol, cambio_pendiente FROM usuario WHERE correo = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setCorreo(rs.getString("correo"));
                u.setPassword(rs.getString("password"));
                u.setRol(rs.getString("rol"));
                u.setCambioPendiente(rs.getInt("cambio_pendiente")); // 🆕 Seteamos en la entidad
                return u;
            }, correo);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // 🆕 NUEVO MÉTODO: Para apagar la bandera cuando cambie la clave
    public void desactivarCambioPendiente(int idUsuario) {
        String sql = "UPDATE usuario SET cambio_pendiente = 0 WHERE id_usuario = ?";
        jdbcTemplate.update(sql, idUsuario);
    }

    // 🔐 NUEVO: Actualizar la contraseña encriptada por BCrypt
    @Override
    public void actualizarPassword(int idUsuario, String nuevoPasswordEncriptado) {
        String sql = "UPDATE usuario SET password = ? WHERE id_usuario = ?";
        jdbcTemplate.update(sql, nuevoPasswordEncriptado, idUsuario);
    }

    @Override
    public List<Usuario> listarTodos() {
        String sql = "SELECT id_usuario, correo, password, rol FROM usuario";
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setCorreo(rs.getString("correo"));
                u.setPassword(rs.getString("password"));
                u.setRol(rs.getString("rol"));
                return u;
            });
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Usuario buscarPorId(Integer idUsuario) {
        String sql = "SELECT id_usuario, correo, password, rol FROM usuario WHERE id_usuario = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setCorreo(rs.getString("correo"));
                u.setPassword(rs.getString("password"));
                u.setRol(rs.getString("rol"));
                return u;
            }, idUsuario);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void actualizar(Usuario usuario) {
        String sql = "UPDATE usuario SET correo = ?, password = ?, rol = ? WHERE id_usuario = ?";
        jdbcTemplate.update(sql, usuario.getCorreo(), usuario.getPassword(), usuario.getRol(), usuario.getIdUsuario());
    }

    @Override
    public void eliminar(Integer idUsuario) {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
        jdbcTemplate.update(sql, idUsuario);
    }
}