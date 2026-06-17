package pe.edu.upn.clinica.dao;

import java.util.List;
import java.util.Map;

public interface AdminDao {
    List<Map<String, Object>> listarPacientes();
    List<Map<String, Object>> listarCitasTotales();
    List<Map<String, Object>> listarPersonal();
    void registrarPersonal(Map<String, String> datosMedico);
    void registrarPaciente(Map<String, Object> paciente);
    void eliminarPersonal(int idPersonal);
    void actualizarPersonal(int id, Map<String, String> payload);
    void eliminarPaciente(int idPaciente);
    void actualizarPaciente(int id, Map<String, Object> paciente);
}