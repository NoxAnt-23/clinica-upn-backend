package pe.edu.upn.clinica.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(nullable = false, unique = true, length = 100)
    private String correo;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 20)
    private String rol;

    // Variable bandera para el primer inicio de sesión obligatorio
    @Column(name = "cambio_pendiente", nullable = false, columnDefinition = "TINYINT DEFAULT 0")
    private int cambioPendiente;

    // 🆕 NUEVOS ATRIBUTOS EXTRA: Mapeados dinámicamente mediante el LEFT JOIN del DAO
    // @Transient le indica a JPA que estos campos no son columnas de la tabla 'usuario'
    @Transient
    private Integer idPaciente;
    
    @Transient
    private String nombre;
    
    @Transient
    private String apellido;
    
    @Transient
    private String dni;
    
    @Transient
    private String celular;

    // --- CONSTRUCTORES ---
    public Usuario() {
    }

    // Constructor antiguo para mantener compatibilidad con tus flujos existentes
    public Usuario(Integer idUsuario, String correo, String password, String rol) {
        this.idUsuario = idUsuario;
        this.correo = correo;
        this.password = password;
        this.rol = rol;
        this.cambioPendiente = 0; // Valor por defecto
    }

    // Constructor completo incluyendo la bandera cambioPendiente
    public Usuario(Integer idUsuario, String correo, String password, String rol, int cambioPendiente) {
        this.idUsuario = idUsuario;
        this.correo = correo;
        this.password = password;
        this.rol = rol;
        this.cambioPendiente = cambioPendiente;
    }

    // --- GETTERS Y SETTERS MANUALES ---
    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getCambioPendiente() {
        return cambioPendiente;
    }

    public void setCambioPendiente(int cambioPendiente) {
        this.cambioPendiente = cambioPendiente;
    }

    // 🆕 GETTERS Y SETTERS COMPLETOS PARA LA DATA DEL PACIENTE
    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
}