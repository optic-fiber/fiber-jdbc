package com.cheroliv.fiber


import com.cheroliv.fiber.service.InterService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.ImportResource

@Slf4j
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan(basePackages = "com.cheroliv.fiber")
@ImportResource("classpath:applicationContext.xml")
class Application implements CommandLineRunner {
//TODO: reprendre l'application avec tout en mysql degager postgresql pour le garder sur un autre projet en jpa
    static void main(String[] args) {
        SpringApplication.run(Application.class, args)
    }

    @Autowired
    InterService interService




    @Override
    void run(String... strings) throws Exception {
        interService.setUp()
        interService.importFromJsonFile interService.fiberJsonFilePath
        interService.processClasseurFeuilles()
        interService.saveToJsonFile interService.fiberJsonFilePath
    }
}