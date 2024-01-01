package com.praca.inzynierska.gardenservicemanagement.webFront.config;

import com.praca.inzynierska.gardenservicemanagement.datastore.user.UserEntity;
import com.praca.inzynierska.gardenservicemanagement.datastore.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartupConfiguration {

    @Bean
    public CommandLineRunner startupData(UserRepository userRepository) {
        return (args) -> {
            var user = userRepository.findUserByUserName("admin");
            if(user.isPresent()) return;
            else userRepository.save(UserEntity.builder().userName("admin").password("admin").build());
        };
    }
}
