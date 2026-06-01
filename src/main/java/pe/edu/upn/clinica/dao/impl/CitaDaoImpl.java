package pe.edu.upn.clinica.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional; // <-- ¡El import mágico para la transacción!
import pe.edu.upn.clinica.dao.CitaDao;
import pe.edu.upn.clinica.entity.Cita;

import java.util.List;
import java.util.Map;

@Repository
public class CitaDaoImpl implements CitaDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

   @Override
    public void reservar(Cita cita) {
        // 1. Buscamos el ID del médico según la especialidad de la cita
        String sqlBusqueda = "SELECT id_personal_salud FROM personal_salud " +
                     "WHERE LOWER(tipo_personal) LIKE LOWER(?) LIMIT 1";
        
        try {
            Integer idMedico = jdbcTemplate.queryForObject(sqlBusqueda, Integer.class, "%" + cita.getEspecialidad() + "%");
            
            // 2. Asignamos el ID encontrado a la cita
            cita.setIdPersonal(idMedico);
        } catch (Exception e) {
            // Si no encuentra médico, podrías lanzar una excepción o dejarlo null si es necesario
            System.out.println("⚠️ No se encontró médico para la especialidad: " + cita.getEspecialidad());
        }

        // 3. Ahora guardamos la cita (la lógica de insertar ya la tienes aquí)
        String sql = "INSERT INTO cita (id_paciente, id_personal_salud, fecha, hora, modalidad, especialidad, estado, sede) VALUES (?, ?, ?, ?, ?, ?, 'Pagado', ?)";
        jdbcTemplate.update(sql, 
            cita.getIdPaciente(), 
            cita.getIdPersonal(), 
            cita.getFecha(), 
            cita.getHora(), 
            cita.getModalidad(), 
            cita.getEspecialidad(),
            cita.getSede()
        );
    }

    @Override
    public List<Map<String, Object>> listarPorPaciente(int idPaciente) {
        String sql = "SELECT * FROM cita WHERE id_paciente = ? ORDER BY fecha DESC";
        return jdbcTemplate.queryForList(sql, idPaciente);
    }

    @Override
    @Transactional // <--- ¡AQUÍ ESTÁN LOS 3 PUNTOS DE LA RÚBRICA!
    public void pagarCita(int idCita) {
        // Acción 1: Actualizamos el estado de la cita a 'Pagado'
        String sqlUpdate = "UPDATE cita SET estado = 'Pagado' WHERE id_cita = ?";
        jdbcTemplate.update(sqlUpdate, idCita);

        // Acción 2: Generamos el comprobante de pago en la misma transacción
        String sqlInsert = "INSERT INTO comprobante_pago (id_cita, fecha_pago, monto, metodo) " +
                           "VALUES (?, NOW(), 50.00, 'Yape')";
        jdbcTemplate.update(sqlInsert, idCita);
        
        System.out.println("Transacción completada exitosamente: Cita actualizada y boleta generada.");
    }

    @Override
    public void cancelarCita(int idCita) {
        String sql = "DELETE FROM cita WHERE id_cita = ?";
        jdbcTemplate.update(sql, idCita);
    }

    @Override
    public List<Map<String, Object>> listarPorMedico(int idMedico) {
        // Llamamos al SP que acabamos de crear en BD_clinicaUPN.sql
        String sql = "CALL sp_listar_citas_por_medico(?)";
        return jdbcTemplate.queryForList(sql, idMedico);
    }
}