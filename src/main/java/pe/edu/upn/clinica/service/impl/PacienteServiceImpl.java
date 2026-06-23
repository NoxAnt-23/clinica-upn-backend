package pe.edu.upn.clinica.service.impl;

import pe.edu.upn.clinica.dao.PacienteDAO;
import pe.edu.upn.clinica.entity.Paciente;
import pe.edu.upn.clinica.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Importación necesaria
import org.springframework.stereotype.Service;

@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteDAO pacienteDao;

    // Inyectamos la herramienta que configuramos en SecurityConfig
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void registrarPaciente(Paciente paciente) {
        
        // 1. Extraemos la contraseña en texto plano que viene de React
        String passwordPlano = paciente.getPassword();
        
        // 2. Si la contraseña no es nula ni vacía, la encriptamos
        if (passwordPlano != null && !passwordPlano.isEmpty()) {
            String passwordHasheado = passwordEncoder.encode(passwordPlano);
            paciente.setPassword(passwordHasheado);
        }

        // Aquí podrías agregar validaciones en el futuro (ej. verificar si el DNI es válido)
        
        // 3. Mandamos el paciente con la contraseña ya cifrada a la base de datos
        pacienteDao.insertar(paciente);
    }
}