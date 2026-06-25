package pe.edu.upn.clinica.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;

@Entity
@Table(name = "paciente")
public class Paciente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente") // ✅ Mapea la llave primaria real de la tabla paciente
    private Integer idPaciente;
    
    @Column(name = "id_usuario") // ✅ Mapea la relación con la cuenta global
    private Integer idUsuario;
    
    private String nombre;
    private String apellido;
    
    @Column(nullable = true)
    private String celular;
    
    @Column(nullable = true)
    private String direccion;
    
    @Column(nullable = true)
    private String sexo;
    
    @Column(name = "fecha_nacimiento") // Sincroniza el alias de la BD
    private String fechaNacimiento;
    
    private String dni;
    private String correo;
    
    @Transient // No se guarda en la tabla física de paciente, solo viaja en el login
    private String password;

    // Constructores
    public Paciente() {}

    // Getters y Setters Corregidos
    public Integer getIdPaciente() { return idPaciente; }
    public void setIdPaciente(Integer idPaciente) { this.idPaciente = idPaciente; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}