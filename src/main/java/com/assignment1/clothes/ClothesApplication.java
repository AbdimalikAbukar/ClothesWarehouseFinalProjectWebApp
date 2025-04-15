package com.assignment1.clothes;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.assignment1.clothes.client.DistributionCentreClient;
import com.assignment1.clothes.model.Clothe;
import com.assignment1.clothes.model.Clothe.Brand;
import com.assignment1.clothes.model.User;
import com.assignment1.clothes.repository.ClotheRepository;
import com.assignment1.clothes.repository.UserRepository;

@SpringBootApplication
public class ClothesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClothesApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(ClotheRepository clotheRepository, UserRepository userRepository,
			PasswordEncoder passwordEncoder, DistributionCentreClient distributionCentreClient) {
		return args -> {
			// Add sample clothes
			clotheRepository.save(new Clothe(null, "Ethiopian Pants", 2500.50, 2024, Clothe.Brand.DIOR));
			clotheRepository.save(new Clothe(null, "Classic Hoodie", 1200.75, 2022, Clothe.Brand.GUCCI));
			clotheRepository.save(new Clothe(null, "Leather Jacket", 1200.99, 2025, Clothe.Brand.BALENCIAGA));
			clotheRepository.save(new Clothe(null, "Slim Fit Jeans", 1200.89, 2023, Clothe.Brand.STONE_ISLAND));

		};
	}

	private void createUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		if (userRepository.findByUsername("admin").isEmpty()) {
			User admin = new User();
			admin.setUsername("admin");
			admin.setPassword(passwordEncoder.encode("admin123"));
			admin.setRole(User.Role.ADMIN.name());
			userRepository.save(admin);
		}

		if (userRepository.findByUsername("warehouse").isEmpty()) {
			User warehouseEmployee = new User();
			warehouseEmployee.setUsername("warehouse");
			warehouseEmployee.setPassword(passwordEncoder.encode("warehouse123"));
			warehouseEmployee.setRole(User.Role.WAREHOUSE.name());
			userRepository.save(warehouseEmployee);
		}

		if (userRepository.findByUsername("user").isEmpty()) {
			User regularUser = new User();
			regularUser.setUsername("user");
			regularUser.setPassword(passwordEncoder.encode("user123"));
			regularUser.setRole(User.Role.USER.name());
			userRepository.save(regularUser);
		}
	}
}