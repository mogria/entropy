package org.playentropy;

import org.springframework.boot.actuate.system.ApplicationPidListener;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(Application.class);
        app.addListeners(new ApplicationPidListener("playentropy.pid"));
        app.run(args);
    }
}
