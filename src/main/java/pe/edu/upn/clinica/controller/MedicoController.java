package pe.edu.upn.clinica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder; // Asegura esta importación
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/medico")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class MedicoController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inyectamos el componente de Spring Security para hashear

    // Endpoint corregido para registrar personal médico con password encriptado
    @PostMapping("/registrar")
    public Map<String, Object> registrarMedico(@RequestBody Map<String, String> request) {
        // 1. Extraemos los campos del formulario mapeados con la estructura de tu React
        String nombreFull = request.get("medico"); // Corregido a "medico" para que coincida con tu front
        String correo = request.get("correo");
        String passwordPlano = request.get("password");
        String especialidad = request.get("especialidad");

        // Separamos el nombre del apellido por si tu procedimiento almacenado los pide divididos
        String nombre = nombreFull;
        String apellido = "";
        if (nombreFull != null && nombreFull.contains(" ")) {
            int primerEspacio = nombreFull.indexOf(" ");
            nombre = nombreFull.substring(0, primerEspacio);
            apellido = nombreFull.substring(primerEspacio + 1);
        }

        try {
            // 🔐 2. Hasheamos la contraseña usando BCrypt antes de enviarla a la base de datos
            String passwordHasheado = passwordEncoder.encode(passwordPlano);

            // 3. Ejecutamos el CALL al procedimiento almacenado con los 5 argumentos correspondientes
            String sqlCall = "CALL sp_registrar_personal(?, ?, ?, ?, ?)";
            jdbcTemplate.update(sqlCall, nombre, apellido, correo, passwordHasheado, especialidad);
            
            return Map.of("status", "success", "message", "Personal registrado correctamente con clave encriptada");
        } catch (Exception e) {
            e.printStackTrace(); // Imprime el log real en tu consola de Spring Boot
            return Map.of("status", "error", "message", "Error interno: " + e.getMessage());
        }
    }

    // 🩺 Endpoint profesional llamando al Procedimiento Almacenado
    @GetMapping("/citas/{id}")
    public List<Map<String, Object>> obtenerCitasPorMedico(@PathVariable int id) {
        String sql = "CALL sp_listar_citas_por_medico(?)";
        
        System.out.println("=== EJECUTANDO SP PARA AGENDA DEL MÉDICO ID: " + id + " ===");
        
        return jdbcTemplate.queryForList(sql, id);
    }

    // 🩺 NUEVO: Endpoint optimizado usando el Stored Procedure nativo de la base de datos
    @GetMapping("/listar")
    public List<Map<String, Object>> listarMedicos() {
        // Ejecutamos el procedimiento almacenado que ya maneja las especialidades de forma segura
        String sql = "CALL sp_listar_personal()";
        List<Map<String, Object>> lista = jdbcTemplate.queryForList(sql);
        
        // Mensaje de control en la terminal de VS Code / Spring Boot para verificar los datos cargados
        System.out.println("=== MÉDICOS TRAÍDOS DE FORMA SEGURA POR SP (" + lista.size() + ") ===");
        
        return lista;
    }
}