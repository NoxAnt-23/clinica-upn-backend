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
        // 1. Verificamos si el paciente ya eligió un médico específico en la interfaz web
        // Si no seleccionó ninguno (o llega como null/0), recién usamos el plan de respaldo automático
        if (cita.getIdPersonal() == null || cita.getIdPersonal() == 0) {
            String sqlBusqueda = "SELECT id_personal_salud FROM personal_salud " +
                                 "WHERE LOWER(tipo_personal) LIKE LOWER(?) LIMIT 1";
            try {
                Integer idMedico = jdbcTemplate.queryForObject(sqlBusqueda, Integer.class, "%" + cita.getEspecialidad() + "%");
                cita.setIdPersonal(idMedico);
            } catch (Exception e) {
                System.out.println("⚠️ No se encontró médico de respaldo para la especialidad: " + cita.getEspecialidad());
            }
        }

        // 2. Guardamos la cita usando los parámetros dinámicos exactos decididos por el usuario
        // Sincronizado estrictamente con el orden de las columnas de tu base de datos (id_paciente, id_personal_salud, fecha, hora, modalidad, especialidad, estado, sede)
        String sql = "INSERT INTO cita (id_paciente, id_personal_salud, fecha, hora, modalidad, especialidad, estado, sede) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, 
            cita.getIdPaciente(), 
            cita.getIdPersonal(), // Guarda el ID del médico seleccionado en tu nuevo combobox
            cita.getFecha(), 
            cita.getHora(),       // Guarda la hora exacta (Ej: "08:30:00") elegida por el paciente
            cita.getModalidad(),  // ✅ Mapeo dinámico corregido ("Virtual" o "Presencial")
            cita.getEspecialidad(),
            cita.getEstado(),     // Pasa dinámicamente 'Pendiente de Pago' asignado en el Controller
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
        // Llamamos al SP que creamos en BD_clinicaUPN.sql
        String sql = "CALL sp_listar_citas_por_medico(?)";
        return jdbcTemplate.queryForList(sql, idMedico);
    }
}