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

    @Override
    public void registrarPaciente(Map<String, Object> paciente) {
        // Ahora enviamos 9 signos de interrogación
        String sql = "CALL sp_insertar_paciente(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        jdbcTemplate.update(sql, 
            paciente.get("nombres"),          // 1. p_nombre
            paciente.get("apellidos"),        // 2. p_apellido
            paciente.get("telefono"),         // 3. p_celular (Asegúrate que en React se llame 'telefono')
            paciente.get("direccion"),        // 4. p_direccion
            paciente.get("sexo"),             // 5. p_sexo
            paciente.get("fecha_nacimiento"), // 6. p_fecha_nacimiento
            paciente.get("dni"),              // 7. p_dni
            paciente.get("correo"),           // 8. p_correo
            paciente.get("password")          // 9. p_password
        );
    }
    @Override
    public void eliminarPersonal(int idPersonal) {
        // 1. Primero borramos las citas asociadas a este médico para liberar el candado
        String sqlCitas = "DELETE FROM cita WHERE id_personal_salud = ?";
        jdbcTemplate.update(sqlCitas, idPersonal);

        // 2. Ahora sí, eliminamos al personal médico sin problemas
        String sqlPersonal = "DELETE FROM personal_salud WHERE id_personal_salud = ?";
        jdbcTemplate.update(sqlPersonal, idPersonal);
    }
    @Override
    public void actualizarPersonal(int id, Map<String, String> payload) {
        // Actualizamos el nombre y la especialidad según el ID
        String sql = "UPDATE personal_salud SET nombre = ?, tipo_personal = ? WHERE id_personal_salud = ?";
        
        jdbcTemplate.update(sql, 
            payload.get("medico"),       // Asumiendo que React envía 'medico' como nombre
            payload.get("especialidad"), // Asumiendo que React envía 'especialidad'
            id
        );
    }
    @Override
    public void eliminarPaciente(int idPaciente) {
        // Primero borramos las citas del paciente para que no haya error de Llave Foránea
        String sqlCitas = "DELETE FROM cita WHERE id_paciente = ?";
        jdbcTemplate.update(sqlCitas, idPaciente);

        // Luego borramos al paciente
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