package pe.edu.upn.clinica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upn.clinica.dao.AdminDao;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminDao adminDao;

    @GetMapping("/pacientes")
    public ResponseEntity<?> listarPacientes() {
        try {
            List<Map<String, Object>> pacientes = adminDao.listarPacientes();
            return ResponseEntity.ok(pacientes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al listar pacientes");
        }
    }

    @GetMapping("/citas")
    public ResponseEntity<?> listarCitasTotales() {
        try {
            List<Map<String, Object>> citas = adminDao.listarCitasTotales();
            return ResponseEntity.ok(citas);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al listar citas");
        }
    }
    
    // --- LISTAR PERSONAL REAL ---
    @GetMapping("/personal")
    public ResponseEntity<?> listarPersonal() {
        try {
            List<Map<String, Object>> personal = adminDao.listarPersonal();
            return ResponseEntity.ok(personal);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al listar personal");
        }
    }

    // --- PUERTA PARA RECIBIR NUEVOS PACIENTES DESDE REACT ---
    @PostMapping("/pacientes")
    public ResponseEntity<?> registrarPaciente(@RequestBody Map<String, Object> paciente) {
        try {
            System.out.println("=== REGISTRANDO NUEVO PACIENTE (ADMIN) ===");
            System.out.println("DNI: " + paciente.get("dni"));
            System.out.println("Nombres: " + paciente.get("nombres"));
            
            // (Aquí luego conectaremos tu DAO para el INSERT o el Stored Procedure de paciente)
            
            return ResponseEntity.ok().body("{\"mensaje\": \"Paciente registrado exitosamente\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar paciente");
        }
    }

    // --- GUARDAR PERSONAL REAL ---

    @Autowired
    private PasswordEncoder passwordEncoder; // Inyectamos el encriptador

    @PostMapping("/personal")
    public ResponseEntity<?> registrarPersonalMedico(@RequestBody Map<String, String> payload) {
        try {
            // ¡EL TRUCO ESTÁ AQUÍ! Encriptamos la clave antes de guardarla
            String passPlana = payload.get("password");
            String passEncriptada = passwordEncoder.encode(passPlana);
            payload.put("password", passEncriptada); // Reemplazamos la plana por la encriptada

            adminDao.registrarPersonal(payload);
            return ResponseEntity.ok().body("{\"mensaje\": \"Personal médico registrado\"}");
            
        } catch (org.springframework.dao.DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("El correo " + payload.get("correo") + " ya está registrado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al crear cuenta.");
        }
    }
}