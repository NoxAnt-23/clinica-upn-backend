package pe.edu.upn.clinica.entity;

import java.time.LocalDateTime;

public class TokenRecuperacion {
    private int idToken;
    private String token;
    private int idUsuario; // Cambiado para calzar con id_usuario de tu BD
    private LocalDateTime fechaExpiracion;

    public TokenRecuperacion() {}

    public TokenRecuperacion(String token, int idUsuario, int minutosValidez) {
        this.token = token;
        this.idUsuario = idUsuario;
        this.fechaExpiracion = LocalDateTime.now().plusMinutes(minutosValidez);
    }

    public boolean estaExpirado() {
        return LocalDateTime.now().isAfter(this.fechaExpiracion);
    }

    // Getters y Setters
    public int getIdToken() { return idToken; }
    public void setIdToken(int idToken) { this.idToken = idToken; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public LocalDateTime getFechaExpiracion() { return fechaExpiracion; }
    public void setFechaExpiracion(LocalDateTime fechaExpiracion) { this.fechaExpiracion = fechaExpiracion; }
}