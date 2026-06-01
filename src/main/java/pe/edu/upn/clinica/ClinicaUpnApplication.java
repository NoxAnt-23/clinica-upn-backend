package pe.edu.upn.clinica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration; //momentaneo

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) //Momentaneo
public class ClinicaUpnApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClinicaUpnApplication.class, args);
    }

}