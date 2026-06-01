package pe.edu.upn.clinica.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "paciente")
public class Paciente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;
    
    private String nombre;
    private String apellido;
    
    @Column(nullable = true)
    private String celular;
    
    @Column(nullable = true)
    private String direccion;
    
    @Column(nullable = true)
    private String sexo;
    
    private String fechaNacimiento;
    private String dni;
    private String correo;
    
    // En tu archivo Paciente.java
    @jakarta.persistence.Transient
    private String password;

    // Constructores
    public Paciente() {}

    // Getters y Setters
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

    // Getters y Setters de la contraseña
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}