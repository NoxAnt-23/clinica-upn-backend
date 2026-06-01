package pe.edu.upn.clinica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "*") // Permite que React se conecte sin problemas de CORS
public class AuthController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            System.out.println("=== INTENTO DE LOGIN ===");
            System.out.println("Identificador recibido: [" + request.getIdentificador() + "]");
            
            // 1. LA CONSULTA MAESTRA (Une usuario, paciente y personalsalud)
            // 1. LA CONSULTA MAESTRA CORREGIDA
            String sql = "SELECT u.password, u.rol, u.correo, " +
                         "p.id_paciente, ps.id_personal_salud, " +
                         "COALESCE(p.nombre, ps.nombre) AS nombre, " +
                         "COALESCE(p.apellido, ps.apellido) AS apellido, " +
                         "p.dni, " + // Los médicos no tienen DNI en esta BD
                         "COALESCE(p.celular, ps.celular) AS celular, " +
                         "ps.tipo_personal AS especialidad " + // En tu BD se llama tipo_personal
                         "FROM usuario u " +
                         "LEFT JOIN paciente p ON u.id_usuario = p.id_usuario " +
                         "LEFT JOIN personal_salud ps ON u.id_usuario = ps.id_usuario " + // Tabla correcta
                         "WHERE u.correo = ? OR p.dni = ?"; // Solo buscamos por correo o DNI paciente

            // Pasamos el identificador SOLO 2 VECES
            List<Map<String, Object>> resultados = jdbcTemplate.queryForList(
                    sql, 
                    request.getIdentificador(), 
                    request.getIdentificador()
            );

            if (resultados.isEmpty()) {
                System.out.println("❌ ERROR: No se encontró ningún usuario con ese correo o DNI en la BD.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
            }

            Map<String, Object> usuarioEncontrado = resultados.get(0);
            String hashEnBaseDeDatos = (String) usuarioEncontrado.get("password");
            String rol = (String) usuarioEncontrado.get("rol");

            System.out.println("Usuario encontrado. Rol: " + rol);

            // 2. Comparamos la contraseña encriptada
            boolean coincide = passwordEncoder.matches(request.getPassword(), hashEnBaseDeDatos);
            System.out.println("¿La contraseña coincide con BCrypt?: " + coincide);

            if (coincide) {
                System.out.println("✅ LOGIN EXITOSO");
                
                // Borramos la contraseña del mapa para que no viaje a React por seguridad
                usuarioEncontrado.remove("password");
                
                // ¡Enviamos TODO el mapa completo a React!
                return ResponseEntity.ok(usuarioEncontrado);
                
            } else {
                System.out.println("❌ ERROR: La contraseña no coincide con el Hash.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
            }

        } catch (Exception e) {
            System.out.println("❌ ERROR CRÍTICO DEL SERVIDOR: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error del servidor");
        }
    }

    // Clase DTO interna para recibir los datos de React
    public static class LoginRequest {
        private String identificador;
        private String password;

        public String getIdentificador() { return identificador; }
        public void setIdentificador(String identificador) { this.identificador = identificador; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}