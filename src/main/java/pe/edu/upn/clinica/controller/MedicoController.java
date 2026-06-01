package pe.edu.upn.clinica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/medico")
@CrossOrigin(origins = "*")
public class MedicoController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Este es el endpoint que tu DashboardMedico está llamando
    @GetMapping("/citas/{id}")
    public List<Map<String, Object>> obtenerCitasPorMedico(@PathVariable int id) {
        String sql = "SELECT c.id_cita, c.fecha, c.hora, c.estado, " +
                     "p.nombre AS paciente " +
                     "FROM cita c " +
                     "JOIN paciente p ON c.id_paciente = p.id_paciente " +
                     "WHERE c.id_personal_salud = ? " +
                     "ORDER BY c.fecha, c.hora";
        
        return jdbcTemplate.queryForList(sql, id);
    }
}