package pe.edu.upn.clinica.controller;

import pe.edu.upn.clinica.dao.TokenRecuperacionDAO;
import pe.edu.upn.clinica.dao.UsuarioDAO; 
import pe.edu.upn.clinica.entity.TokenRecuperacion;
import pe.edu.upn.clinica.entity.Usuario;
import pe.edu.upn.clinica.service.EmailService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class RestablecerPasswordController {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private TokenRecuperacionDAO tokenDAO;

    @Autowired
    private EmailService emailService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/olvide-password")
    public ResponseEntity<?> solicitarRecuperacion(@RequestBody Map<String, String> request) {
        String correo = request.get("correo");
        Usuario usuario = usuarioDAO.buscarPorCorreo(correo);

        if (usuario == null) {
            return ResponseEntity.ok().body(Map.of("message", "Si el correo está registrado, se enviará un enlace de recuperación."));
        }

        tokenDAO.eliminarPorUsuarioId(usuario.getIdUsuario());

        String tokenCadena = UUID.randomUUID().toString();
        TokenRecuperacion tokenModel = new TokenRecuperacion(tokenCadena, usuario.getIdUsuario(), 15);
        
        tokenDAO.guardar(tokenModel);
        emailService.enviarCorreoRecuperacion(correo, tokenCadena);

        return ResponseEntity.ok().body(Map.of("message", "Enlace de recuperación enviado con éxito."));
    }

    @PostMapping("/restablecer-password")
    public ResponseEntity<?> restablecerContraseña(@RequestBody Map<String, String> request) {
        String tokenInput = request.get("token");
        String nuevoPassword = request.get("password");

        TokenRecuperacion tokenBD = tokenDAO.buscarPorToken(tokenInput);

        if (tokenBD == null || tokenBD.estaExpirado()) {
            return ResponseEntity.badRequest().body(Map.of("error", "El enlace es inválido o ya ha expirado."));
        }

        String passwordHasheado = passwordEncoder.encode(nuevoPassword);
        usuarioDAO.actualizarPassword(tokenBD.getIdUsuario(), passwordHasheado);
        tokenDAO.eliminarPorUsuarioId(tokenBD.getIdUsuario());

        return ResponseEntity.ok().body(Map.of("message", "Contraseña actualizada correctamente. Ya puedes iniciar sesión."));
    }

    // 🔒 NUEVO ENDPOINT: Cambiar contraseña temporal en el primer inicio de sesión
    @PostMapping("/actualizar-primer-password")
    public ResponseEntity<?> actualizarPrimerPassword(@RequestBody Map<String, Object> request) {
        try {
            int idUsuario = (int) request.get("idUsuario");
            String nuevoPassword = (request.get("password")).toString();

            // 1. Encriptamos la nueva contraseña con BCrypt
            String passwordHasheado = passwordEncoder.encode(nuevoPassword);
            
            // 2. Actualizamos la clave en la tabla usuario
            usuarioDAO.actualizarPassword(idUsuario, passwordHasheado);
            
            // 3. Apagamos la bandera para que pueda ingresar directamente en su próximo Login
            // Nota: Llama al método del DAOImpl que unificamos con JdbcTemplate anteriormente
            ((pe.edu.upn.clinica.dao.impl.UsuarioDAOImpl) usuarioDAO).desactivarCambioPendiente(idUsuario);

            return ResponseEntity.ok().body(Map.of("message", "Contraseña activada de forma exitosa."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "No se pudo actualizar la contraseña."));
        }
    }
}