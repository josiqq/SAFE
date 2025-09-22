package com.josiqq.safe;

import com.josiqq.safe.model.Bombero;
import com.josiqq.safe.service.BomberoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SafeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafeApplication.class, args);
	}

	@Bean
	CommandLineRunner run(BomberoService bomberoService) {
		return args -> {
			// Crear un usuario administrador si no existe
			if (bomberoService.findByUsername("admin").isEmpty()) {
				Bombero admin = new Bombero();
				admin.setUsername("admin");
				admin.setPassword("admin123"); // El servicio se encargará de encriptarlo
				admin.setNombre("Administrador");
				admin.setApellido("del Sistema");
				bomberoService.save(admin);
				System.out.println(">>> Usuario 'admin' creado con contraseña 'admin123'");
			}
		};
	}
}