package com.Application.EmpRegistration;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.Application.EmpRegistration.entity.Role;
import com.Application.EmpRegistration.repository.RoleRepository;

@SpringBootApplication
public class EmpRegistrationApplication implements CommandLineRunner{
	
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
	
	@Autowired
	RoleRepository roleRepository;
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(EmpRegistrationApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception
    {
        // Inserting the data in the mysql table.
        Role role1 = new Role(1L, "ROLE_ADMIN");
        Role role2 = new Role(2L, "ROLE_USER");
        
        // ob.save() method 
        roleRepository.save(role1);
        roleRepository.save(role2);
      
    }

}
