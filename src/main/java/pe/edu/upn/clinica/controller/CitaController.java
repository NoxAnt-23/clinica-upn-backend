package pe.edu.upn.clinica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate; // <-- ¡IMPORT NECESARIO AGREGADO!
import org.springframework.web.bind.annotation.*;
import pe.edu.upn.clinica.dao.CitaDAO;
import pe.edu.upn.clinica.entity.Cita;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true") // Aseguramos CORS por si acaso
public class CitaController {

    @Autowired
    private CitaDAO citaDao;

    @Autowired
    private JdbcTemplate jdbcTemplate; // <-- ¡INYECCIÓN MÁGICA AGREGADA PARA RESOLVER EL ERROR!

    @PostMapping("/reservar")
    public ResponseEntity<?> reservarCita(@RequestBody Cita cita) {
        try {
            // 🛡️ PARCHE DE CONTROL: Sobrescribimos cualquier intento de forzar "Pagado" 
            // Garantiza que la cita nazca como debe ser para que funcione tu modal de Yape
            cita.setEstado("Pendiente de Pago");
            
            citaDao.reservar(cita);
            return ResponseEntity.ok().body("{\"mensaje\": \"Cita reservada con éxito\"}");
        } catch (Exception e) {
            // Esto es un "abridor de cajas negras"
            e.printStackTrace(); // Esto manda el error completo a la terminal de VS Code
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.toString());
        }
    }

    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<?> listarCitas(@PathVariable int idPaciente) {
        try {
            List<Map<String, Object>> citas = citaDao.listarPorPaciente(idPaciente);
            return ResponseEntity.ok(citas);
        } catch (Exception e) {
            System.out.println("❌ ERROR AL OBTENER CITAS: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener las citas");
        }
    }

    @PutMapping("/pagar/{idCita}")
    public ResponseEntity<?> pagarCita(@PathVariable int idCita) {
        try {
            System.out.println("=== PROCESANDO PAGO YAPE PARA CITA ID: " + idCita + " ===");
            citaDao.pagarCita(idCita);
            return ResponseEntity.ok().body("{\"mensaje\": \"Pago registrado con éxito\"}");
        } catch (Exception e) {
            System.out.println("❌ ERROR AL PAGAR: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar el pago");
        }
    }

    @DeleteMapping("/cancelar/{idCita}")
    public ResponseEntity<?> cancelarCita(@PathVariable int idCita) {
        try {
            citaDao.cancelarCita(idCita);
            return ResponseEntity.ok().body("{\"mensaje\": \"Cita cancelada correctamente\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al cancelar");
        }
    }

    @GetMapping("/medico/citas/{idMedico}")
    public ResponseEntity<?> listarCitasMedico(@PathVariable int idMedico) {
        // Llama al método del DAO que configuramos con tu sp_listar_citas_por_medico
        return ResponseEntity.ok(citaDao.listarPorMedico(idMedico));
    }

    @PutMapping("/enlace/{id}")
    public ResponseEntity<?> actualizarEnlaceCita(@PathVariable int id, @RequestBody Map<String, String> request) {
        String nuevoEnlace = request.get("enlace");
        
        try {
            // Ejecutamos el query directo para actualizar la columna enlace_sesion en MySQL
            String sql = "UPDATE cita SET enlace_sesion = ? WHERE id_cita = ?";
            int filasAfectadas = jdbcTemplate.update(sql, nuevoEnlace, id);
            
            if (filasAfectadas > 0) {
                return ResponseEntity.ok(Map.of("status", "success", "message", "Enlace guardado correctamente"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la cita especificada");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servidor: " + e.getMessage());
        }
    }
}