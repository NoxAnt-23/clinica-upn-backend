package pe.edu.upn.clinica.controller;

import pe.edu.upn.clinica.dao.UsuarioDAO;
import pe.edu.upn.clinica.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try {
            String identificador = request.get("identificador");
            String password = request.get("password");

            System.out.println("=== INTENTO DE LOGIN (DAO IMPL) ===");
            System.out.println("Identificador recibido: [" + identificador + "]");
            
            Usuario usuario = usuarioDAO.buscarPorCorreo(identificador);

            if (usuario == null) {
                System.out.println("❌ ERROR: No se encontró ningún usuario con ese correo en la BD.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
            }

            System.out.println("Usuario encontrado. Rol: " + usuario.getRol());

            boolean coincide = passwordEncoder.matches(password, usuario.getPassword());
            System.out.println("¿La contraseña coincide con BCrypt?: " + coincide);

            if (coincide) {
                System.out.println("✅ LOGIN EXITOSO");
                
                // 🛠️ Usamos HashMap dinámico para evitar NullPointerException con campos Transient vacíos
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("idUsuario", usuario.getIdUsuario());
                responseBody.put("correo", usuario.getCorreo());
                responseBody.put("rol", usuario.getRol());
                responseBody.put("cambioPendiente", usuario.getCambioPendiente());
                
                // 🆕 EXPEDIMOS LOS NUEVOS ATRIBUTOS DEL PACIENTE AL FRONTEND
                responseBody.put("idPaciente", usuario.getIdPaciente());
                responseBody.put("nombre", usuario.getNombre() != null ? usuario.getNombre() : "");
                responseBody.put("apellido", usuario.getApellido() != null ? usuario.getApellido() : "");
                responseBody.put("dni", usuario.getDni() != null ? usuario.getDni() : "");
                responseBody.put("celular", usuario.getCelular() != null ? usuario.getCelular() : "");
                
                return ResponseEntity.ok().body(responseBody);
                
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