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

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    // --- PUERTA PARA RECIBIR NUEVOS PACIENTES DESDE EL PANEL DE ADMIN ---
    @PostMapping("/pacientes")
    public ResponseEntity<?> registrarPaciente(@RequestBody Map<String, Object> paciente) {
        try {
            // 1. Rellenamos los datos que NO vienen desde el modal de React
            paciente.putIfAbsent("direccion", "No especificada");
            paciente.putIfAbsent("sexo", "N"); // O el valor que uses por defecto
            paciente.putIfAbsent("fecha_nacimiento", "2000-01-01"); // Fecha por defecto
            
            // 2. Usamos el DNI como contraseña temporal para el paciente
            String dni = (String) paciente.get("dni");
            String passEncriptada = passwordEncoder.encode(dni);
            paciente.put("password", passEncriptada);

            // 3. ¡Ahora sí llamamos a la base de datos!
            adminDao.registrarPaciente(paciente);
            
            return ResponseEntity.ok().body("{\"mensaje\": \"Paciente registrado exitosamente desde Admin\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar paciente");
        }
    }

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

    // --- PUERTA PARA ACTUALIZAR PERSONAL ---
    @PutMapping("/personal/{id}")
    public ResponseEntity<?> actualizarPersonal(@PathVariable("id") int id, @RequestBody Map<String, String> payload) {
        try {
            System.out.println("=== ACTUALIZANDO PERSONAL ID: " + id + " ===");
            
            // Si React envía una nueva contraseña, la encriptamos antes de guardar
            String nuevaPass = payload.get("password");
            if (nuevaPass != null && !nuevaPass.trim().isEmpty()) {
                payload.put("password", passwordEncoder.encode(nuevaPass));
            }

            // Llamamos a la base de datos
            adminDao.actualizarPersonal(id, payload);
            
            return ResponseEntity.ok().body("{\"mensaje\": \"Personal actualizado exitosamente\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el registro");
        }
    }

    @DeleteMapping("/personal/{id}")
    public ResponseEntity<?> eliminarPersonal(@PathVariable("id") int id) {
        try {
            adminDao.eliminarPersonal(id);
            return ResponseEntity.ok().body("{\"mensaje\": \"Personal eliminado exitosamente\"}");
        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el registro");
        }
    }
    // --- ACTUALIZAR PACIENTE ---
    @PutMapping("/pacientes/{id}")
    public ResponseEntity<?> actualizarPaciente(@PathVariable("id") int id, @RequestBody Map<String, Object> payload) {
        try {
            adminDao.actualizarPaciente(id, payload);
            return ResponseEntity.ok().body("{\"mensaje\": \"Paciente actualizado exitosamente\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar paciente");
        }
    }

    // --- ELIMINAR PACIENTE ---
    @DeleteMapping("/pacientes/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable("id") int id) {
        try {
            adminDao.eliminarPaciente(id);
            return ResponseEntity.ok().body("{\"mensaje\": \"Paciente eliminado exitosamente\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar paciente");
        }
    }
   
}