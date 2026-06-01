package pe.edu.upn.clinica.dao.impl;

import pe.edu.upn.clinica.dao.PacienteDao;
import pe.edu.upn.clinica.entity.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PacienteDaoImpl implements PacienteDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insertar(Paciente paciente) {
        // Quitamos el signo de interrogación del ID, y nos aseguramos de que haya 9
        // (Nombre, Apellido, Celular, Direccion, Sexo, Fecha, DNI, Correo, Password)
        String sql = "CALL sp_insertar_paciente(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        jdbcTemplate.update(sql, 
            // ELIMINAMOS paciente.getIdUsuario(),
            paciente.getNombre(),
            paciente.getApellido(),
            paciente.getCelular(),
            paciente.getDireccion(),
            paciente.getSexo(),
            paciente.getFechaNacimiento(),
            paciente.getDni(),
            paciente.getCorreo(),
            paciente.getPassword() // ¡Agregamos la contraseña que viene de React!
        );
    }
}