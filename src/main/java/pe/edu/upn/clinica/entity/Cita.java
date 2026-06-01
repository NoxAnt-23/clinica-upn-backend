package pe.edu.upn.clinica.entity;

public class Cita {
    private Integer idCita;
    private Integer idPaciente;
    private Integer idPersonal; 
    private String fecha;
    private String hora;
    private String modalidad;
    private String especialidad; // <-- ¡Añadido!
    private String descripcion;  // <-- Lo mantenemos para el problema del paciente
    private String estado;
    private String sede;
    private String consultorio;
    private String enlaceSesion;

    // --- GETTERS Y SETTERS ---
    public Integer getIdCita() { return idCita; }
    public void setIdCita(Integer idCita) { this.idCita = idCita; }

    public Integer getIdPaciente() { return idPaciente; }
    public void setIdPaciente(Integer idPaciente) { this.idPaciente = idPaciente; }

    public Integer getIdPersonal() { return idPersonal; }
    public void setIdPersonal(Integer idPersonal) { this.idPersonal = idPersonal; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getModalidad() { return modalidad; }
    public void setModalidad(String modalidad) { this.modalidad = modalidad; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getSede() { return sede; }
    public void setSede(String sede) { this.sede = sede; }

    public String getConsultorio() { return consultorio; }
    public void setConsultorio(String consultorio) { this.consultorio = consultorio; }

    public String getEnlaceSesion() { return enlaceSesion; }
    public void setEnlaceSesion(String enlaceSesion) { this.enlaceSesion = enlaceSesion; }
}