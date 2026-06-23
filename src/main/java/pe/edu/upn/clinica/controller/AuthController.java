package pe.edu.upn.clinica.controller;

import pe.edu.upn.clinica.dao.UsuarioDAO;
import pe.edu.upn.clinica.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    // 🔄 Inyectamos tu interfaz clásica que apunta a UsuarioDAOImpl
    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try {
            // Extraemos los datos directamente del mapa nativo de la petición HTTP
            String identificador = request.get("identificador");
            String password = request.get("password");

            System.out.println("=== INTENTO DE LOGIN (DAO IMPL) ===");
            System.out.println("Identificador recibido: [" + identificador + "]");
            
            // 🔍 Usamos tu método clásico del DAO que ya configuramos con JdbcTemplate
            Usuario usuario = usuarioDAO.buscarPorCorreo(identificador);

            if (usuario == null) {
                System.out.println("❌ ERROR: No se encontró ningún usuario con ese correo en la BD.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
            }

            System.out.println("Usuario encontrado. Rol: " + usuario.getRol());

            // 2. Comparamos la contraseña usando el BCrypt de Spring Security
            boolean coincide = passwordEncoder.matches(password, usuario.getPassword());
            System.out.println("¿La contraseña coincide con BCrypt?: " + coincide);

            if (coincide) {
                System.out.println("✅ LOGIN EXITOSO");
                
                // Armamos la respuesta plana mapeando directamente los atributos de tu Entidad
                return ResponseEntity.ok().body(Map.of(
                    "idUsuario", usuario.getIdUsuario(),
                    "correo", usuario.getCorreo(),
                    "rol", usuario.getRol(),
                    "cambioPendiente", usuario.getCambioPendiente() // 🆕 Viaja seguro a React
                ));
                
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
}