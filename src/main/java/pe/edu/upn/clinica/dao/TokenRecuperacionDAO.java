package pe.edu.upn.clinica.dao;

import pe.edu.upn.clinica.entity.TokenRecuperacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class TokenRecuperacionDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void guardar(TokenRecuperacion token) {
        String sql = "INSERT INTO tokens_recuperacion (token, id_usuario, fecha_expiracion) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, 
            token.getToken(), 
            token.getIdUsuario(), 
            Timestamp.valueOf(token.getFechaExpiracion())
        );
    }

    public TokenRecuperacion buscarPorToken(String token) {
        String sql = "SELECT id_token, token, id_usuario, fecha_expiracion FROM tokens_recuperacion WHERE token = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                TokenRecuperacion t = new TokenRecuperacion();
                t.setIdToken(rs.getInt("id_token"));
                t.setToken(rs.getString("token"));
                t.setIdUsuario(rs.getInt("id_usuario"));
                t.setFechaExpiracion(rs.getTimestamp("fecha_expiracion").toLocalDateTime());
                return t;
            }, token);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void eliminarPorUsuarioId(int idUsuario) {
        String sql = "DELETE FROM tokens_recuperacion WHERE id_usuario = ?";
        jdbcTemplate.update(sql, idUsuario);
    }
}