package com.georeport;

import com.georeport.entity.User;
import com.georeport.entity.Worker;
import com.georeport.repository.UserRepository;
import com.georeport.repository.WorkerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@SpringBootApplication
public class GeoReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeoReportApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, 
                                      WorkerRepository workerRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            // Create default admin user if not exists
            if (!userRepository.existsByUsername("admin")) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole("admin");
                admin.setFullName("System Administrator");
                admin.setEmail("admin@georeport.com");
                admin.setPhoneNumber("1234567890");
                admin.setDepartment("Administration");
                admin.setCreatedAt(LocalDateTime.now());
                admin.setIsActive(true);
                userRepository.save(admin);
                System.out.println("Default admin user created: username=admin, password=admin123");
            }

            // Create default government user if not exists
            if (!userRepository.existsByUsername("gov_user")) {
                User govUser = new User();
                govUser.setUsername("gov_user");
                govUser.setPassword(passwordEncoder.encode("gov123"));
                govUser.setRole("government");
                govUser.setFullName("Government Officer");
                govUser.setEmail("gov@georeport.com");
                govUser.setPhoneNumber("0987654321");
                govUser.setDepartment("Public Works");
                govUser.setCreatedAt(LocalDateTime.now());
                govUser.setIsActive(true);
                userRepository.save(govUser);
                System.out.println("Default government user created: username=gov_user, password=gov123");
            }

            // Create some default workers if not exists
            if (workerRepository.count() == 0) {
                Worker worker1 = new Worker();
                worker1.setName("John Doe");
                worker1.setDepartment("Roads");
                worker1.setPhoneNumber("1111111111");
                worker1.setEmail("john.doe@georeport.com");
                worker1.setSpecialization("Road Maintenance");
                worker1.setIsAvailable(true);
                worker1.setCreatedAt(LocalDateTime.now());
                workerRepository.save(worker1);

                Worker worker2 = new Worker();
                worker2.setName("Jane Smith");
                worker2.setDepartment("Water");
                worker2.setPhoneNumber("2222222222");
                worker2.setEmail("jane.smith@georeport.com");
                worker2.setSpecialization("Water Infrastructure");
                worker2.setIsAvailable(true);
                worker2.setCreatedAt(LocalDateTime.now());
                workerRepository.save(worker2);

                Worker worker3 = new Worker();
                worker3.setName("Bob Johnson");
                worker3.setDepartment("Electricity");
                worker3.setPhoneNumber("3333333333");
                worker3.setEmail("bob.johnson@georeport.com");
                worker3.setSpecialization("Electrical Systems");
                worker3.setIsAvailable(true);
                worker3.setCreatedAt(LocalDateTime.now());
                workerRepository.save(worker3);

                System.out.println("Default workers created");
            }

            System.out.println("GeoReport Application started successfully!");
            System.out.println("API Base URL: http://localhost:8080/api");
            System.out.println("Database: georeport.db (SQLite)");
        };
    }
}
