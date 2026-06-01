package pe.edu.upn.clinica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upn.clinica.dao.CitaDao;
import pe.edu.upn.clinica.entity.Cita;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/citas")

public class CitaController {

    @Autowired
    private CitaDao citaDao;

    @PostMapping("/reservar")
    public ResponseEntity<?> reservarCita(@RequestBody Cita cita) {
        try {
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
        // Aquí llamarías a tu nuevo Stored Procedure sp_listar_citas_por_medico
        return ResponseEntity.ok(citaDao.listarPorMedico(idMedico));
    }
    
}