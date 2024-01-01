package com.praca.inzynierska.gardenservicemanagement;

import com.praca.inzynierska.gardenservicemanagement.webFront.config.StartupConfiguration;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
				classes = GardenServiceManagementApplication.class)
@Import(StartupConfiguration.class)
class GardenServiceManagementApplicationTests {


	@Test
	void shouldDoNothing() {


	}

}
