package pe.edu.upn.clinica.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pe.edu.upn.clinica.dao.AdminDAO;

import java.util.List;
import java.util.Map;

@Repository
public class AdminDAOImpl implements AdminDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> listarPacientes() {
        String sql = "CALL sp_listar_pacientes()";
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public List<Map<String, Object>> listarCitasTotales() {
        String sql = "CALL sp_listar_citas_totales()";
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public List<Map<String, Object>> listarPersonal() {
        return jdbcTemplate.queryForList("CALL sp_listar_personal()");
    }

    @Override
    public void registrarPersonal(Map<String, String> datosMedico) {
        // ✅ CORREGIDO: Ahora enviamos los 5 parámetros exactos que pide tu base de datos
        String sql = "CALL sp_registrar_personal(?, ?, ?, ?, ?)";
        
        jdbcTemplate.update(sql, 
            datosMedico.get("nombre"),       // 1. p_nombre
            datosMedico.get("apellido"),     // 2. p_apellido
            datosMedico.get("correo"),       // 3. p_correo
            datosMedico.get("password"),     // 4. p_password (hasheada)
            datosMedico.get("especialidad")  // 5. p_especialidad
        );
    }

    @Override
    public void registrarPaciente(Map<String, Object> paciente) {
        String sql = "CALL sp_insertar_paciente(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        jdbcTemplate.update(sql, 
            paciente.get("nombres"),          
            paciente.get("apellidos"),        
            paciente.get("telefono"),         
            paciente.get("direccion"),        
            paciente.get("sexo"),             
            paciente.get("fecha_nacimiento"), 
            paciente.get("dni"),              
            paciente.get("correo"),           
            paciente.get("password")          
        );
    }

    @Override
    public void eliminarPersonal(int idPersonal) {
        String sqlCitas = "DELETE FROM cita WHERE id_personal_salud = ?";
        jdbcTemplate.update(sqlCitas, idPersonal);

        String sqlPersonal = "DELETE FROM personal_salud WHERE id_personal_salud = ?";
        jdbcTemplate.update(sqlPersonal, idPersonal);
    }

    @Override
    public void actualizarPersonal(int id, Map<String, String> payload) {
        String sql = "UPDATE personal_salud SET nombre = ?, tipo_personal = ? WHERE id_personal_salud = ?";
        jdbcTemplate.update(sql, 
            payload.get("medico"),       
            payload.get("especialidad"), 
            id
        );
    }

    @Override
    public void eliminarPaciente(int idPaciente) {
        String sqlCitas = "DELETE FROM cita WHERE id_paciente = ?";
        jdbcTemplate.update(sqlCitas, idPaciente);

        String sqlPaciente = "DELETE FROM paciente WHERE id_paciente = ?";
        jdbcTemplate.update(sqlPaciente, idPaciente);
    }

    @Override
    public void actualizarPaciente(int id, Map<String, Object> paciente) {
        String sql = "UPDATE paciente SET dni = ?, nombre = ?, apellido = ?, celular = ?, correo = ? WHERE id_paciente = ?";
        jdbcTemplate.update(sql, 
            paciente.get("dni"),
            paciente.get("nombres"),
            paciente.get("apellidos"),
            paciente.get("telefono"),
            paciente.get("correo"),
            id
        );
    }
}