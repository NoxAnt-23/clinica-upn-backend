package pe.edu.upn.clinica.controller;

import pe.edu.upn.clinica.entity.Paciente;
import pe.edu.upn.clinica.service.PacienteService; // <--- ESTO DEBE COINCIDIR
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping("/registrar")
    public ResponseEntity<String> registrar(@RequestBody Paciente paciente) {
        try {
            pacienteService.registrarPaciente(paciente);
            return new ResponseEntity<>("Paciente registrado exitosamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}