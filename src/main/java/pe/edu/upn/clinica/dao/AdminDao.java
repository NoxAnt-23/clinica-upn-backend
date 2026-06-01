package pe.edu.upn.clinica.dao;

import java.util.List;
import java.util.Map;

public interface AdminDao {
    List<Map<String, Object>> listarPacientes();
    List<Map<String, Object>> listarCitasTotales();
    List<Map<String, Object>> listarPersonal();
    void registrarPersonal(Map<String, String> datosMedico);
    //void eliminarPersonal(int idPersonal);
}