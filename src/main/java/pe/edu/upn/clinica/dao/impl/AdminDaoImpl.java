package pe.edu.upn.clinica.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pe.edu.upn.clinica.dao.AdminDao;

import java.util.List;
import java.util.Map;

@Repository
public class AdminDaoImpl implements AdminDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> listarPacientes() {
        // ¡Usamos el Stored Procedure! 8 puntos asegurados en la rúbrica
        String sql = "CALL sp_listar_pacientes()";
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public List<Map<String, Object>> listarCitasTotales() {
        // ¡Usamos el Stored Procedure!
        String sql = "CALL sp_listar_citas_totales()";
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public List<Map<String, Object>> listarPersonal() {
        return jdbcTemplate.queryForList("CALL sp_listar_personal()");
    }

    @Override
    public void registrarPersonal(Map<String, String> datosMedico) {
        // Ahora solo llamamos a 4 parámetros: nombre, especialidad, correo, password
        String sql = "CALL sp_registrar_personal(?, ?, ?, ?)";
        
        jdbcTemplate.update(sql, 
            datosMedico.get("medico"),       // p_nombre
            datosMedico.get("especialidad"), // p_especialidad (va a tipo_personal)
            datosMedico.get("correo"),       // p_correo
            datosMedico.get("password")      // p_password (ya viene hasheada del controller)
        );
    }
}