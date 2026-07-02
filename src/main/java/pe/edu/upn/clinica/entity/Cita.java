package pe.edu.upn.clinica.entity;

public class Cita {
    private Integer idCita;
    private Integer idPaciente;
    private Integer idPersonal; 
    private String fecha;
    private String hora;
    private String modalidad;
    private String especialidad; 
    private String descripcion;  
    private String estado;
    private String sede;
    private String consultorio;
    private String enlaceSesion;

    
    // 🔥 ¡NUEVOS ATRIBUTOS PARA EL PORTAL DEL MÉDICO!
    private String paciente; // Para guardar el nombre concatenado
    private String dni;      // Para que funcione el buscador en React

    // --- GETTERS Y SETTERS ORIGINALES ---
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

    // --- GETTERS Y SETTERS NUEVOS ---
    public String getPaciente() { return paciente; }
    public void setPaciente(String paciente) { this.paciente = paciente; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
}