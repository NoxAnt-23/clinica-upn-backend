package pe.edu.upn.clinica.dao;

import pe.edu.upn.clinica.entity.Cita;
import java.util.List;
import java.util.Map;

public interface CitaDAO {
    void reservar(Cita cita);
    List<Map<String, Object>> listarPorPaciente(int idPaciente);

    void pagarCita(int idCita);

    void cancelarCita(int idCita);

    List<Map<String, Object>> listarPorMedico(int idMedico);
}