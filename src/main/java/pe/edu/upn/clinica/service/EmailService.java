package pe.edu.upn.clinica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreoRecuperacion(String correoDestino, String token) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        
        mensaje.setTo(correoDestino);
        mensaje.setSubject("Clínica UPN - Recuperación de Contraseña");
        
        // Aquí construimos el enlace que el frontend (React) va a leer
        String urlRecuperacion = "http://localhost:5173/recuperar-password?token=" + token;
        
        String cuerpoMensaje = "Hola,\n\n"
                + "Hemos recibido una solicitud para restablecer la contraseña de tu cuenta en el portal médico.\n"
                + "Para proceder, por favor haz clic en el siguiente enlace:\n\n"
                + urlRecuperacion + "\n\n"
                + "Este enlace es de un único uso. Si tú no solicitaste este cambio, puedes ignorar este correo.\n\n"
                + "Atentamente,\n"
                + "Soporte Técnico - Clínica UPN";
                
        mensaje.setText(cuerpoMensaje);
        
        // Dispara el correo en vivo a los servidores de Google
        mailSender.send(mensaje);
    }
}