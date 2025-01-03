package com.ExpenseTracker.SpendLit;

import com.ExpenseTracker.SpendLit.entity.Role;
import com.ExpenseTracker.SpendLit.entity.User;
import com.ExpenseTracker.SpendLit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
// implements CommandLineRunner - ensures that your application always has an admin account upon startup.
@SpringBootApplication
public class SpendLitApplication implements CommandLineRunner {
	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpendLitApplication.class, args);
	}

	@Override
	public void run(String... args) {
		// Check if an admin account already exists in the database
		User adminAccount = userRepository.findByRole(Role.ADMIN);
		// If no admin account exists
		// Create a new admin user
		if(null == adminAccount){
			User user = new User();
			user.setEmail("admin@gmail.com");
			user.setFirstName("admin");
			user.setLastName("admin");
			user.setRole(Role.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userRepository.save(user);
		}

	}
}
