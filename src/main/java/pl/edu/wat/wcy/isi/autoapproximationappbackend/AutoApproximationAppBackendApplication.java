package pl.edu.wat.wcy.isi.autoapproximationappbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.edu.wat.wcy.isi.autoapproximationappbackend.configuration.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class AutoApproximationAppBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoApproximationAppBackendApplication.class, args);
    }

}
