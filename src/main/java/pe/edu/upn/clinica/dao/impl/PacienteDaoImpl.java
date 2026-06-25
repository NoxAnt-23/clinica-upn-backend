package pe.edu.upn.clinica.dao.impl;

import pe.edu.upn.clinica.dao.PacienteDAO;
import pe.edu.upn.clinica.entity.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PacienteDAOImpl implements PacienteDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insertar(Paciente paciente) {
        // ✅ CORREGIDO: Sincronizado estrictamente con el orden de entrada del SP
        // CALL sp_insertar_paciente(p_dni, p_nombre, p_apellido, p_celular, p_direccion, p_sexo, p_fecha_nac, p_correo, p_password)
        String sql = "CALL sp_insertar_paciente(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        jdbcTemplate.update(sql, 
            paciente.getDni(),             // 1. 🆕 ¡El DNI pasa a la primera posición obligatoria!
            paciente.getNombre(),          // 2. Nombre
            paciente.getApellido(),        // 3. Apellido
            paciente.getCelular(),         // 4. Celular
            paciente.getDireccion(),       // 5. Direccion
            paciente.getSexo(),            // 6. Sexo
            paciente.getFechaNacimiento(), // 7. Fecha de Nacimiento
            paciente.getCorreo(),          // 8. Correo
            paciente.getPassword()         // 9. Contraseña ya hasheada desde el Service
        );
    }
}